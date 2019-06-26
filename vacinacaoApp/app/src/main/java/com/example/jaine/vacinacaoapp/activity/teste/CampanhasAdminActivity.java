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
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CampanhasAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Campanha> campanhas = new ArrayList<Campanha>();
    private AdapterCampanhasAdmin adapterCampanhasAdmin;

    private DatabaseReference databaseReference;
    private String idUsuarioLogado;
    private ProgressBar progressBar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanhas_admin);

        //Configurações iniciais
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();

        progressBar = findViewById(R.id.progressCampanhasAdmin);

        //RecyclerView
        recyclerView = findViewById(R.id.recyclerCampanhasAdmin);
        //Configurar adapter
        adapterCampanhasAdmin = new AdapterCampanhasAdmin(campanhas, CampanhasAdminActivity.this, onClickLista());
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterCampanhasAdmin);

        //FloatingActionButton
        FloatingActionButton fab = findViewById(R.id.floatCampanhasAdmin);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Configuração de ação do FAB*/
                Intent addCampanhas = new Intent(CampanhasAdminActivity.this, AdicionarCampanhasActivity.class );
                startActivity(addCampanhas);
            }
        });

        //Recupera campanhas
        recuperarCampanhas("");
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
                recuperarCampanhas(s);
                return true;
            }
        });
        //searchView.setOnQueryTextListener(onSearch());
        return super.onCreateOptionsMenu(menu);

    }

    private void recuperarCampanhas(String pesquisa){
        Query query;
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference campanhasRef = databaseReference
                .child("ADMIN-CAMPANHAS");
                //.child(idUsuarioLogado);

        if(pesquisa.equals("")){
            query = campanhasRef.orderByChild("titulo");
        }else{
            query = campanhasRef.orderByChild("titulo").startAt(pesquisa).endAt(pesquisa+"\uf8ff");
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                campanhas.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    campanhas.add(ds.getValue(Campanha.class));
                }

                adapterCampanhasAdmin.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public AdapterCampanhasAdmin.ListaOnClickListener onClickLista(){
        return new AdapterCampanhasAdmin.ListaOnClickListener() {

            @Override
            public void onClickExibirInformacoes(View view, int idx) {
                Intent i = new Intent(CampanhasAdminActivity.this, DescricaoCampanhaAdminActivity.class);
                startActivity(i);
            }

            @Override
            public void onClickEditarInformacoes(View view, int idx) {
                Intent i = new Intent(CampanhasAdminActivity.this, AlterarCampanhasAdminActivity.class);
                startActivity(i);
            }

            @Override
            public void onClickExcluirInformacoes(int idx) {
                excluirCampanhas(idx);
                //Campanha campanhaSelecionada =  campanhas.get(idx);
                //campanhaSelecionada.remover();
                //Toast.makeText(CampanhasAdminActivity.this, "Campanha de vacinação excluída", Toast.LENGTH_SHORT).show();
            }
        };
    }

    //Excluindo informações da caderneta
    private void excluirCampanhas(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir");
        builder.setMessage("Todas as informações serão excluídas. Tem certeza que deseja excluir?");
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Campanha campanhaSelecionada =  campanhas.get(position);
                campanhaSelecionada.remover();
                Toast.makeText(CampanhasAdminActivity.this, "Campanha excluída", Toast.LENGTH_SHORT).show();
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
