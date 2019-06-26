package com.example.jaine.vacinacaoapp.activity.activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.adapter.AdapterCampanhas;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.example.jaine.vacinacaoapp.activity.teste.Campanha;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CampanhasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterCampanhas adapterCampanhas;
    private List<Campanha> campanhas = new ArrayList<Campanha>();
    private Campanha campanha;

    private DatabaseReference databaseReference;
    private String idUsuarioLogado;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanhas);

        //Configurações iniciais
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();
        inicializarComponentes();
        //swipe();

        //Configurações do RecyclerView
        adapterCampanhas = new AdapterCampanhas(campanhas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterCampanhas);

        //Recupera campanhas
        recuperarCampanhas();
    }


    private void recuperarCampanhas(){
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference campanhasRef = databaseReference
                .child("ADMIN-CAMPANHAS");
        campanhasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                campanhas.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    campanhas.add(ds.getValue(Campanha.class));
                }

                adapterCampanhas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarComponentes(){
        progressBar = findViewById(R.id.progresCampanhasUsuario);
        recyclerView = findViewById(R.id.recyclerCampanhas);
    }
}
