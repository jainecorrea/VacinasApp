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
import com.example.jaine.vacinacaoapp.activity.activity.DadosVacinaActivity;
import com.example.jaine.vacinacaoapp.activity.teste.Vacina;

import java.util.List;

public class AdapterSobre extends RecyclerView.Adapter<AdapterSobre.MyViewHolder> {

    private List<Vacina> vacinas;
    private Context context;
    private ListaOnClickListener listaOnClickListener;

    ///
    public AdapterSobre (List<Vacina> vacinas, Context context, ListaOnClickListener listaOnClickListener) {
        this.vacinas = vacinas;
        this.context = context;
        this.listaOnClickListener = listaOnClickListener;
    }

    public interface ListaOnClickListener{
        void onClick(View view, int idx);
        //void onClick(int position);
        //void onClickEdit(View view, int idx);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //Converte o objeto xml em um objeto do tipo view
        View listaVacinaFeita = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sobre, parent, false);
        return new MyViewHolder(listaVacinaFeita);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Vacina vacina = vacinas.get(position);
        //holder.vacinaFeita.setText(vacina.getNomeVacina());

        holder.nome.setText(vacina.getNomeVacina());
        holder.icone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DadosVacinaActivity.vacinaId = vacinas.get(position).getIdVacina();
                listaOnClickListener.onClick(holder.itemView, position);
                //listaOnClickListener.onClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return vacinas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome;
        ImageView icone;

        public MyViewHolder(View itemView) {
            super(itemView);

            //Recupera os componentes de tela -- é o itemView que acessa o objeto view
            nome = itemView.findViewById(R.id.sobreNomeVacina);
            icone = itemView.findViewById(R.id.sobreVizualizar);

        }
    }

}
