package com.example.jaine.vacinacaoapp.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.activity.PesoAlturaActivity;
import com.example.jaine.vacinacaoapp.activity.modelo.Acompanhamento;

import java.util.List;

public class AdapterPesoAltura extends RecyclerView.Adapter<AdapterPesoAltura.MyViewHolder>{

    private List<Acompanhamento> acompanhamentos;
    public AdapterPesoAltura.ListaOnClickListener listaOnClickListener;
    private Context context;

    public AdapterPesoAltura(List<Acompanhamento> acompanhamentos, ListaOnClickListener listaOnClickListener){//List<Acompanhamento> acompanhamentos, Context context){
        this.acompanhamentos = acompanhamentos;
        this.listaOnClickListener = listaOnClickListener;
        //this.context = context;
    }

    public interface ListaOnClickListener{
        void onClickDelete(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Converte o objeto xml em um objeto do tipo view
        View itemListaPesoAltura = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_peso_altura, parent, false);
        return new MyViewHolder(itemListaPesoAltura);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //holder.tituloPesoAltura.setText("Altura");
        //holder.valorPesoAltura.setText("1.50");
        final Acompanhamento acompanhamento = acompanhamentos.get(position);

        //PesoAlturaActivity.idAcompanhamento = acompanhamento.getIdAcompanhamento();
        holder.tituloPesoAltura.setText(acompanhamento.getTipo());
        holder.valorPesoAltura.setText(String.valueOf(acompanhamento.getValor()));

        holder.excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OpcoesActivity.cadernetaId = criancas.get(position).getIdCaderneta();
                PesoAlturaActivity.acompanhamentoId = acompanhamentos.get(position).getIdAcompanhamento();
                listaOnClickListener.onClickDelete(position);
            }
        });

        //holder.tituloPesoAltura.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        //Configura a cor acompanhamento.getTipo() == "a" ||
        /*if(acompanhamento.getTipo().equals("p")){
            holder.tituloPesoAltura.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.valorPesoAltura.setText(String.valueOf(acompanhamento.getValor()));
        }*/
    }

    @Override
    public int getItemCount() {
        return acompanhamentos.size();
        //return acompanhamentos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tituloPesoAltura;
        TextView valorPesoAltura;
        ImageView excluir;

        public MyViewHolder(View itemView) {
            super(itemView);

            tituloPesoAltura = itemView.findViewById(R.id.textPesoAltura);
            valorPesoAltura = itemView.findViewById(R.id.textValorPesoAltura);
            excluir = itemView.findViewById(R.id.editExcluirPesoAltura);
        }
    }
}
