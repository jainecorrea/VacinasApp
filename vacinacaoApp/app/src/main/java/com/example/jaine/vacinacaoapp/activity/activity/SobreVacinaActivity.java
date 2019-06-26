package com.example.jaine.vacinacaoapp.activity.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.adapter.AdapterSobre;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.example.jaine.vacinacaoapp.activity.teste.AdapterListaVacina;
import com.example.jaine.vacinacaoapp.activity.teste.ItemInunizacao;
import com.example.jaine.vacinacaoapp.activity.teste.Vacina;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class SobreVacinaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterSobre adapterSobre;
    private List<Vacina> vacinas = new ArrayList<Vacina>();

    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_vacina);

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Teste");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        //Configurações iniciais
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        //searchView = findViewById(R.id.materialSearchView);
        progressBar = findViewById(R.id.progressSobreVacina);

        //RecyclerView
        recyclerView = findViewById(R.id.recyclerSobre);
        //Configurar adapter
        adapterSobre = new AdapterSobre(vacinas, SobreVacinaActivity.this, onClickLista());
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterSobre);

        //Configura click no RecyclerView
        //clickNoAdapter();

        //Recupera vacinas
        recuperarVacinas("");
    }

   /* private void clickNoAdapter(){
        adapterListaVacina.setOnItemClickListener(new AdapterListaVacina.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //recuperarCadernetaDeVacina();
                Intent i = new Intent(SobreVacinaActivity.this, DadosVacinaActivity.class);
                startActivity(i);
                /*final Vacina cadernetaSelecionada = vacinas.get(position);


            }
        });
    }*/

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
/*
    private SearchView.OnQueryTextListener onSearch(){
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                recuperarVacinas(s);
                return false;
            }
        };
    }
*/
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuPesquisa:
                realizarPesquisa();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void realizarPesquisa(){

    }*/

    private void recuperarVacinas(String pesquisa){
        Query query;
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference listaVacinaRef = databaseReference
                .child("ADMIN-VACINAS");

        if(pesquisa.equals("")){
            query = listaVacinaRef.orderByChild("nomeVacina");
        }else{
            query = listaVacinaRef.orderByChild("nomeVacina").startAt(pesquisa).endAt(pesquisa+"\uf8ff");
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                vacinas.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    vacinas.add(ds.getValue(Vacina.class));
                }

                adapterSobre.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public AdapterSobre.ListaOnClickListener onClickLista(){
        return new AdapterSobre.ListaOnClickListener() {

            @Override
            public void onClick(View view, int idx) {
                Intent i = new Intent(SobreVacinaActivity.this, DadosVacinaActivity.class);
                startActivity(i);
            }
        };
    }
}
