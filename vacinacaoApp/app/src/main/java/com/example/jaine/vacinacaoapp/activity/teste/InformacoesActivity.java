package com.example.jaine.vacinacaoapp.activity.teste;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.activity.CampanhasActivity;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InformacoesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Vacina> vacinas = new ArrayList<Vacina>();
    private AdapterVacinas adapterVacinas;

    private DatabaseReference databaseReference;
    private String idUsuarioLogado;
    private ProgressBar progressBar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes);

        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();

        progressBar = findViewById(R.id.progressInformacoesAdmin);

        //RecyclerView
        recyclerView = findViewById(R.id.recyclerVacinas);
        //Configurar adapter
        adapterVacinas = new AdapterVacinas( vacinas, InformacoesActivity.this, onClickLista());
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterVacinas);

        //FloatingActionButton
        FloatingActionButton fab = findViewById(R.id.fabVacinasAdmin);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Configuração de ação do FAB*/
                Intent addVacinasAdmin = new Intent(InformacoesActivity.this, AdicionarVacinaActivity.class );
                startActivity(addVacinasAdmin);
            }
        });

        //Recupera vacinas
        recuperarVacinas("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_listagem, menu);

        //Configurar botão de pesquisa
        MenuItem item = menu.findItem(R.id.menuPesquisa);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recuperarVacinas(s);
                return true;
            }
        });
        //searchView.setOnQueryTextListener(onSearch());
        return super.onCreateOptionsMenu(menu);

    }

    private void recuperarVacinas(String pesquisa){
        Query query;
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference vacinasRef = databaseReference
                .child("ADMIN-VACINAS");
                //.child(idUsuarioLogado);

        if(pesquisa.equals("")){
            query = vacinasRef.orderByChild("nomeVacina");
        }else{
            query = vacinasRef.orderByChild("nomeVacina").startAt(pesquisa).endAt(pesquisa+"\uf8ff");
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                vacinas.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    vacinas.add(ds.getValue(Vacina.class));
                }

                adapterVacinas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public AdapterVacinas.ListaOnClickListener onClickLista(){
        return new AdapterVacinas.ListaOnClickListener() {
            @Override
            public void onClickExibirInformacoes(View view, int idx) {
                Intent i = new Intent(InformacoesActivity.this, DescricaoVacinaActivity.class);
                startActivity(i);
            }

            @Override
            public void onClickEditarInformacoes(View view, int idx) {
                Intent i = new Intent(InformacoesActivity.this, AlterarAdminActivity.class);
                startActivity(i);
            }

            @Override
            public void onClickExcluirInformacoes(int idx) {
                excluirVacina(idx);
                //Vacina vacinaSelecionada = vacinas.get(idx);
                //vacinaSelecionada.remover();
                //Toast.makeText(InformacoesActivity.this, "Vacina excluída", Toast.LENGTH_SHORT).show();
            }
        };
    }

    //Excluindo informações da caderneta
    private void excluirVacina(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir");
        builder.setMessage("Todas as informações serão excluídas. Tem certeza que deseja excluir?");
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Vacina vacinaSelecionada = vacinas.get(position);
                vacinaSelecionada.remover();
                Toast.makeText(InformacoesActivity.this, "Vacina excluída", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
