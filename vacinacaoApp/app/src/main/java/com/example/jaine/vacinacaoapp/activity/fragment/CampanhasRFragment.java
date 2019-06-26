package com.example.jaine.vacinacaoapp.activity.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.adapter.AdapterCampanhasR;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.modelo.CampanhasRealizadas;
import com.example.jaine.vacinacaoapp.activity.modelo.Crianca2;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CampanhasRFragment extends Fragment {


    private RecyclerView recyclerViewListaCampanhas;
    private AdapterCampanhasR adapter;
    public static String cadernetaId;
    public static String campanhaRealizada;

    private List<CampanhasRealizadas> listaCampanhaRealizadas = new ArrayList<>();
    private DatabaseReference campanhasRef;
    private ValueEventListener valueEventListenerCampanhasRealizadas;
    private String idUsuarioLogado;


    public CampanhasRFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_campanhas_r, container, false);

        //Configurações iniciais
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();
        recyclerViewListaCampanhas = view.findViewById(R.id.recyclerViewCampanhasR);
        campanhasRef = ConfiguracaoFirebase.getFirebaseDataBase().child("USUARIO-CAMPANHAS")
                .child(idUsuarioLogado)
                .child(cadernetaId);

        //Configura o adapter
        adapter =  new AdapterCampanhasR(listaCampanhaRealizadas, getActivity(), onClickLista()); //crio uma instância

        //Configura o recyclerView -- como estou dentro de um fragment uso o getActivity(), assim consigo passar a activity principal como parâmetro
        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(getActivity());
        recyclerViewListaCampanhas.setLayoutManager(layoutManager);
        recyclerViewListaCampanhas.setHasFixedSize(true);
        recyclerViewListaCampanhas.setAdapter(adapter);

        return view;
    }

    //Com isso, quando nos carregarmos nosso framgentes irremos recuperar os canotato qe quando nao será removido
    @Override
    public void onStart() {
        super.onStart();
        recuperarCampanhas();
    }

    @Override
    public void onStop() {
        super.onStop();
        campanhasRef.removeEventListener( valueEventListenerCampanhasRealizadas );
    }

    private void recuperarCampanhas(){

        valueEventListenerCampanhasRealizadas = campanhasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaCampanhaRealizadas.clear();

                for(DataSnapshot dados: dataSnapshot.getChildren()){

                    CampanhasRealizadas cp = dados.getValue(CampanhasRealizadas.class);
                    listaCampanhaRealizadas.add(cp);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void excluirCampanhasRealizadas(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Excluir");
        builder.setMessage("Tem certeza que deseja excluir?");
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CampanhasRealizadas campanhaSelecionada = listaCampanhaRealizadas.get(position);
                campanhaSelecionada.remover();
                Toast.makeText(getContext(), "Campanha excluída com sucesso", Toast.LENGTH_SHORT).show();
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

    //Eventos de click no CardView
    public AdapterCampanhasR.ListaOnClickListener onClickLista()
    {
        return new AdapterCampanhasR.ListaOnClickListener() {
            @Override
            public void onClickDelete(int position) {
                excluirCampanhasRealizadas(position);
                //Toast.makeText(getContext(), "Exclusão realizda com sucesso", Toast.LENGTH_SHORT).show();
            }

        };
    }
}
