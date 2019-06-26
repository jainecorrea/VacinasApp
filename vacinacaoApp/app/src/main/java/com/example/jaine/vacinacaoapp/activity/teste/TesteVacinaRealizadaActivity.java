package com.example.jaine.vacinacaoapp.activity.teste;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.adapter.AdapterVacinasR;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TesteVacinaRealizadaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewListaVacinas;
    private AdapterVacinasR adapter;

    public static String cadernetaId;

    private List<Imunizacao> imunizacoes = new ArrayList<>();

    private List<ItemInunizacao> vacinasRealizadas = new ArrayList<>();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_vacina_realizada);

        //Configurações iniciais
        recyclerViewListaVacinas = findViewById(R.id.recyclerViewListaVacinasRealizadasTeste);
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();

        //Configura o adapter
        adapter =  new AdapterVacinasR(vacinasRealizadas);

        //Configura o recyclerView -- como estou dentro de um fragment uso o getActivity(), assim consigo passar a activity principal como parâmetro
        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(getApplicationContext());
        recyclerViewListaVacinas.setLayoutManager(layoutManager);
        recyclerViewListaVacinas.setHasFixedSize(true);
        recyclerViewListaVacinas.setAdapter(adapter);

        //Recupera vacinas
        recuperarVacinas();
    }

    private void recuperarVacinas(){
        DatabaseReference vacinasRealizadasRef = databaseReference.child("CONFIRMACAO-VACINAS-REALIZADAS").child( cadernetaId ).child("itens");
        vacinasRealizadasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vacinasRealizadas.clear();

                if(dataSnapshot.getValue() != null){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        ItemInunizacao item = new ItemInunizacao();
                        item.setLoteVacina(ds.child("loteVacina").getValue(Integer.class));
                        item.setNomeVacina(ds.child("nomeVacina").getValue(String.class));
                        vacinasRealizadas.add(item);
                        //Imunizacao imunizacao = ds.getValue(Imunizacao.class);
                        //Imunizacao imunizacao = (Imunizacao) ds.getValue();
                        //Imunizacao imunizacao = (Imunizacao) ds.getValue();
                        //imunizacoes.add(imunizacao);
                        //String idCaderneta = ds.child("iCaderneta").getValue(String.class);
                        //Log.d("TESTE", idCaderneta);
                    }
                    //for(ItemInunizacao item:vacinasRealizadas){
                       // Log.i("VACINAS",item.getNomeVacina());
                    //}
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
