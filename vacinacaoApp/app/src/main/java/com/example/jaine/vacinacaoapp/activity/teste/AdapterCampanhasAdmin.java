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

import java.util.List;

public class AdapterCampanhasAdmin extends RecyclerView.Adapter<AdapterCampanhasAdmin.MyViewHolder> {

    private List<Campanha> campanhas;
    private Context context;
    private ListaOnClickListener listaOnClickListener;

    public AdapterCampanhasAdmin(List<Campanha> campanhas, Context context, ListaOnClickListener listaOnClickListener){
        this.campanhas = campanhas;
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //Converte o objeto xml em um objeto do tipo view
        //View itemListaVacina = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_vacina, parent, false);
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_campanhas_admin, parent, false);
        return new MyViewHolder(item);
    }

    //Nesse método eu posso acessar os componentes
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Campanha campanha = campanhas.get(position);

        holder.nome.setText(campanha.getTitulo());

        holder.icone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DescricaoCampanhaAdminActivity.campanhaIdAdmin = campanhas.get(position).getIdCampanha();
                listaOnClickListener.onClickExibirInformacoes(holder.itemView, position);
            }
        });

        holder.icone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlterarCampanhasAdminActivity.campanhaIdAdmin = campanhas.get(position).getIdCampanha();
                listaOnClickListener.onClickEditarInformacoes(holder.itemView, position);
            }
        });

        holder.icone3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaOnClickListener.onClickExcluirInformacoes(position);
            }
        });

        //holder.nome.setText(vacina.getNomeVacina());
    }

    //Retorna a quantidade de itens que vão ser exibidos
    @Override
    public int getItemCount() {
        return campanhas.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome;
        ImageView icone1;
        ImageView icone2;
        ImageView icone3;

        public MyViewHolder(View itemView) {
            super(itemView);

            //Recupera os componentes de tela -- é o itemView que acessa o objeto view
            nome = itemView.findViewById(R.id.textCampanhasAd);
            icone1 = itemView.findViewById(R.id.imageVizualizarCampanhasAdmin);
            icone2 = itemView.findViewById(R.id.imageAtualizarCampanhasAdmin);
            icone3 = itemView.findViewById(R.id.imageExcluirCampanhasAdmin);

        }
    }
}
