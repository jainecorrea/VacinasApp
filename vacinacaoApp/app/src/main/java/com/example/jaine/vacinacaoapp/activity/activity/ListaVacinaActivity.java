package com.example.jaine.vacinacaoapp.activity.activity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.modelo.Crianca2;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.example.jaine.vacinacaoapp.activity.teste.AdapterListaVacina;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.teste.Imunizacao;
import com.example.jaine.vacinacaoapp.activity.teste.ItemInunizacao;
import com.example.jaine.vacinacaoapp.activity.teste.Vacina;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaVacinaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterListaVacina adapterListaVacina;
    private List<Vacina> vacinas = new ArrayList<Vacina>();
    public List<Crianca2> criancas = new ArrayList<Crianca2>();

    private DatabaseReference databaseReference;

    private String idUsuarioLogado;
    public static String cadernetaId;
    private List<ItemInunizacao> itensCaderneta = new ArrayList();
    private Imunizacao imunizacaoRecuperada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vacina);

        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();

        //RecyclerView
        recyclerView = findViewById(R.id.recyclerListaVacina);
        //Configurar adapter
        adapterListaVacina = new AdapterListaVacina(vacinas);
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListaVacina);

        //Configura click no RecyclerView
        clickNoAdapter();

        //Recupera vacinas
        recuperarVacinas();

        //Recupera as vacinas confrimadas pelo usuário
        recuperarVacinasRealizadas();
    }

    private void clickNoAdapter(){
        adapterListaVacina.setOnItemClickListener(new AdapterListaVacina.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                exibirDialog(position);

            }
        });
    }

    private void exibirAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informações");
        builder.setMessage("É necessário que todas as informações sejam preeenchidas. Por favor, digite o lote da vacina.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void exibirDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vacina realizada");
        builder.setMessage("Deseja adicioná-la as vacinas realizadas?");

        int maxLength = 8;
        final EditText editLote = new EditText(this);
        editLote.setHint("Digite o lote da vacina");
        editLote.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        builder.setView( editLote );

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String lote = editLote.getText().toString();

                if(lote.isEmpty()){
                    exibirAlert();
                }
                else{
                    Vacina cadernetaSelecionada = vacinas.get(position);
                    ItemInunizacao itemImunizacao = new ItemInunizacao();
                    itemImunizacao.setIdVacina( cadernetaSelecionada.getIdVacina() );
                    itemImunizacao.setNomeVacina( cadernetaSelecionada.getNomeVacina() );
                    itemImunizacao.setLoteVacina( Integer.parseInt(lote) );

                    //Verificando se a vacina já foi adicionada a determinada caderneta
                    boolean tem=false;
                    for(ItemInunizacao item:itensCaderneta){
                        if(item.getNomeVacina().equals(itemImunizacao.getNomeVacina())){
                            tem = true;
                        }
                    }
                    if(tem != true) {
                        itensCaderneta.add(itemImunizacao);
                    }else{
                        Toast.makeText(ListaVacinaActivity.this, "Essa vacina já foi realizada!", Toast.LENGTH_SHORT).show();
                    }

                    //Verifico se o vacina já existe
                    if(imunizacaoRecuperada == null){
                        imunizacaoRecuperada = new Imunizacao(idUsuarioLogado);
                    }

                    imunizacaoRecuperada.setItens( itensCaderneta );
                    imunizacaoRecuperada.setIdCaderneta( cadernetaId );
                    imunizacaoRecuperada.salvar();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    //Recupera as vacinas que foram cadastradas pelo administrador
    private void recuperarVacinas(){

        DatabaseReference listaVacinaRef = databaseReference
                .child("ADMIN-VACINAS");
        listaVacinaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vacinas.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    vacinas.add(ds.getValue(Vacina.class));
                }

                adapterListaVacina.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void confirmarRealizacaoVacina() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alterações");
        builder.setMessage("Deseja confirmar as alterações realizadas?");

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                imunizacaoRecuperada.confirmar();
                imunizacaoRecuperada.remover();
                imunizacaoRecuperada = null;
                finish();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Infla o menu com opção de adicionar nova campanha
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirmar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Opções menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.addVacinasRealizadas:
                confirmarRealizacaoVacina();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void recuperarVacinasRealizadas(){

        DatabaseReference vacinasR = databaseReference
                .child("CONFIRMACAO-VACINAS-REALIZADAS")
                //.child( idUsuarioLogado )
                .child( cadernetaId );
        vacinasR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                itensCaderneta = new ArrayList<>(); //faz com que a lista fique gerada caso seja add novos após ser confimado
                //verifico se algum item foi adicionado
                if(dataSnapshot.getValue() != null){
                    imunizacaoRecuperada = dataSnapshot.getValue(Imunizacao.class);
                    itensCaderneta = imunizacaoRecuperada.getItens();

                    for(ItemInunizacao itemInunizacao : itensCaderneta){
                        int lote = itemInunizacao.getLoteVacina();
                       String nome = itemInunizacao.getNomeVacina();
                    }
                }//aliasLote.setText("Lote: "+String.valueOf(lote));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
