package com.example.jaine.vacinacaoapp.activity.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.adapter.AdapterVacinasR;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.example.jaine.vacinacaoapp.activity.teste.Imunizacao;
import com.example.jaine.vacinacaoapp.activity.teste.ItemInunizacao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VacinasRFragment extends Fragment {

    private RecyclerView recyclerViewListaVacinas;
    private AdapterVacinasR adapter;
    public static String cadernetaId;

    private List<ItemInunizacao> vacinasRealizadas = new ArrayList<>();
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListenerVacinasRealizadas;

    public VacinasRFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vacinas_r, container, false);

        //Configurações iniciais
        recyclerViewListaVacinas = view.findViewById(R.id.recyclerViewListaVacinasRealizadas);
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase().child("CONFIRMACAO-VACINAS-REALIZADAS").child( cadernetaId ).child("itens");

        //Configura o adapter
        adapter =  new AdapterVacinasR(vacinasRealizadas);//crio uma instância

        //Configura o recyclerView -- como estou dentro de um fragment uso o getActivity(), assim consigo passar a activity principal como parâmetro
        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(getActivity());
        recyclerViewListaVacinas.setLayoutManager(layoutManager);
        recyclerViewListaVacinas.setHasFixedSize(true);
        recyclerViewListaVacinas.setAdapter(adapter);

        return view;
    }

    //DecimalFormat df = new DecimalFormat("0.00");
    //df.format(variavel)


    @Override
    public void onStart() {
        super.onStart();
        recuperarVacinasRealizadas();
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener( valueEventListenerVacinasRealizadas );
    }

    private void recuperarVacinasRealizadas(){
        valueEventListenerVacinasRealizadas = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vacinasRealizadas.clear();

                if(dataSnapshot.getValue() != null){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        ItemInunizacao item = new ItemInunizacao();
                        item.setLoteVacina(ds.child("loteVacina").getValue(Integer.class));
                        item.setNomeVacina(ds.child("nomeVacina").getValue(String.class));
                        vacinasRealizadas.add(item);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
