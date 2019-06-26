package com.example.jaine.vacinacaoapp.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jaine.vacinacaoapp.R;

public class AdapterLembretes extends RecyclerView.Adapter<AdapterLembretes.MyViewHolder> {

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemListaLembretes = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lembrete, parent, false);
        return new MyViewHolder(itemListaLembretes);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.descricao.setText("Vacina sarampo");
        holder.data.setText("23/10/2018");
        holder.horario.setText("17:00");
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView descricao;
        TextView data;
        TextView horario;

        public MyViewHolder(View itemView) {
            super(itemView);

            descricao = itemView.findViewById(R.id.textDescricaoDadosVacina);
            data = itemView.findViewById(R.id.textData);
            horario = itemView.findViewById(R.id.textHorario);
        }
    }
}
