package com.example.jaine.vacinacaoapp.activity.teste;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.fragment.VacinasRFragment;

import java.util.List;

public class AdapterVacinas extends RecyclerView.Adapter<AdapterVacinas.MyViewHolder> {

    private List<Vacina> vacinas;
    private Context context;
    private ListaOnClickListener listaOnClickListener;

    public AdapterVacinas(List<Vacina> vacinas, Context context, ListaOnClickListener listaOnClickListener) {
        this.vacinas = vacinas;
        this.context = context;
        this.listaOnClickListener = listaOnClickListener;
    }

    public interface ListaOnClickListener{
        void onClickExibirInformacoes(View view, int idx);
        void onClickEditarInformacoes(View view, int idx);
        void onClickExcluirInformacoes(int idx);
    }

    //Método responsável por criar a visualização
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Converte o objeto xml em um objeto do tipo view
        View itemListaVacina = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_vacina, parent, false);
        return new MyViewHolder(itemListaVacina);
    }

    //Nesse método eu posso acessar os componentes
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Vacina vacina = vacinas.get(position);

        holder.nome.setText(vacina.getNomeVacina());

        holder.icone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DescricaoVacinaActivity.vacinaId = vacinas.get(position).getIdVacina();
                listaOnClickListener.onClickExibirInformacoes(holder.itemView, position);
            }
        });

        holder.icone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlterarAdminActivity.vacinaId = vacinas.get(position).getIdVacina();
                listaOnClickListener.onClickEditarInformacoes(holder.itemView, position);
            }
        });

        holder.icone3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaOnClickListener.onClickExcluirInformacoes(position);
            }
        });

    }

    //Retorna a quantidade de itens que vão ser exibidos
    @Override
    public int getItemCount() {
        return vacinas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome;
        ImageView icone1;
        ImageView icone2;
        ImageView icone3;

        public MyViewHolder(View itemView) {
            super(itemView);

            //Recupera os componentes de tela -- é o itemView que acessa o objeto view
            nome = itemView.findViewById(R.id.textVacina);
            icone1 = itemView.findViewById(R.id.imageExibir);
            icone2 = itemView.findViewById(R.id.imageAlterarAd);
            icone3 = itemView.findViewById(R.id.imageExcluirAd);
        }
    }
}
