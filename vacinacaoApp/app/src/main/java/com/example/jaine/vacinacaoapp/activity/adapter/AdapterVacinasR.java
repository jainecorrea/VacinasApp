package com.example.jaine.vacinacaoapp.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.fragment.VacinasRFragment;
import com.example.jaine.vacinacaoapp.activity.teste.Imunizacao;
import com.example.jaine.vacinacaoapp.activity.teste.ItemInunizacao;

import java.util.ArrayList;
import java.util.List;

public class AdapterVacinasR extends RecyclerView.Adapter<AdapterVacinasR.MyViewHolder> {

    //private List<ItemInunizacao> itens;
    private List<ItemInunizacao> vacinasRealizadas;
    private Context context;

    public AdapterVacinasR(List<ItemInunizacao> imunizacao){
        this.vacinasRealizadas = imunizacao;
    }
    /*public AdapterVacinasR(List<ItemInunizacao> itens, Context context){
        this.itens = itens;
        this.context = context;
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_vacinas_r, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

      ItemInunizacao imunizacao = vacinasRealizadas.get(position);
      holder.nome.setText(imunizacao.getNomeVacina() + " - " + imunizacao.getLoteVacina());

    }

    @Override
    public int getItemCount() {
        return vacinasRealizadas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome;

        //Construtor
        public MyViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textNomeVacinaR);
        }
    }
}
