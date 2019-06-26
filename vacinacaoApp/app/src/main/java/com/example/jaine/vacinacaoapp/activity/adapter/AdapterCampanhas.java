package com.example.jaine.vacinacaoapp.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.teste.Campanha;

import java.util.List;

public class AdapterCampanhas extends RecyclerView.Adapter<AdapterCampanhas.MyViewHolder>{

    private List<Campanha> campanhas;

    public AdapterCampanhas(List<Campanha> campanhas){
        this.campanhas = campanhas;
    }

    public interface ListaOnClickListener {
        void onClickDelete(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Converte o objeto xml em um objeto do tipo view
        View listagemCampanhas = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_campanhas, parent, false);
        return new MyViewHolder(listagemCampanhas);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Campanha campanha = campanhas.get(position);
        holder.titulo.setText(campanha.getTitulo());
        holder.dataInicio.setText(campanha.getDataInicio());
        holder.dataTermino.setText(campanha.getDataTermino());
        holder.publicoAlvo.setText(campanha.getPublicoAlvo());

    }

    public Campanha getCampanhaAt(int position){
        return campanhas.get(position);
    }

    @Override
    public int getItemCount() {
        return campanhas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView dataInicio;
        TextView dataTermino;
        TextView publicoAlvo;
        TextView titulo;

        public MyViewHolder(View itemView) {
            super(itemView);

            dataInicio = itemView.findViewById(R.id.textInicioCampanha);
            dataTermino = itemView.findViewById(R.id.textTerminoCampanha);
            publicoAlvo = itemView.findViewById((R.id.textPublicoAlvo));
            titulo = itemView.findViewById(R.id.textTituloCampnha);
        }
    }
}
