package com.example.jaine.vacinacaoapp.activity.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.activity.AbasActivity;
import com.example.jaine.vacinacaoapp.activity.activity.AlterarDadosActivity;
import com.example.jaine.vacinacaoapp.activity.activity.ListaVacinaActivity;
import com.example.jaine.vacinacaoapp.activity.activity.NovaAlturaActivity;
import com.example.jaine.vacinacaoapp.activity.activity.NovaCampanhaActivity;
import com.example.jaine.vacinacaoapp.activity.activity.NovoPesoActivity;
import com.example.jaine.vacinacaoapp.activity.activity.OpcoesActivity;
import com.example.jaine.vacinacaoapp.activity.activity.PesoAlturaActivity;
import com.example.jaine.vacinacaoapp.activity.activity.UsuarioActivity;
import com.example.jaine.vacinacaoapp.activity.fragment.CampanhasRFragment;
import com.example.jaine.vacinacaoapp.activity.fragment.VacinasRFragment;
import com.example.jaine.vacinacaoapp.activity.modelo.CampanhasRealizadas;
import com.example.jaine.vacinacaoapp.activity.modelo.Crianca;
import com.example.jaine.vacinacaoapp.activity.modelo.Crianca2;
import com.example.jaine.vacinacaoapp.activity.teste.TesteVacinaRealizadaActivity;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterCadernetas extends RecyclerView.Adapter<AdapterCadernetas.MyViewHolder>{

     public Context context;
     public ListaOnClickListener listaOnClickListener;
     public List<Crianca2> criancas;
     //public ArrayList<Crianca> criancas;
    //public ArrayList<Crianca2> criancas;

     //public static int criancaId;

    public AdapterCadernetas(Context context, List<Crianca2> criancas, ListaOnClickListener listaOnClickListener) {
        this.context = context;
        this.listaOnClickListener = listaOnClickListener;
        this.criancas = criancas;
    }

    public interface ListaOnClickListener{
        void onClickDelete(int position);
        void onClickEdit(View view, int idx);
        void onClickList(View view, int idx);
        void onClickAcompanhamento(View view, int idx);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Converte o objeto xml em um objeto do tipo view
        View itemListaCadernetas = LayoutInflater.from(context).inflate(R.layout.adapter_caderneta, parent, false);
        //View itemListaCadernetas = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_caderneta, parent, false);
        return new MyViewHolder(itemListaCadernetas);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        /****************************************/
        final Crianca2 crianca = criancas.get(position);
        Picasso.with(context)
                .load(crianca.getUrlImagemCrianca())
                .placeholder(R.drawable.imagem_padrao)
                .fit()
                .into(holder.foto);

        holder.nome.setText(crianca.getNomeCrianca());
        holder.idade.setText(crianca.getDataNascCrianca());
        //holder.progressBar.setVisibility(View.VISIBLE);

        holder.excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpcoesActivity.cadernetaId = criancas.get(position).getIdCaderneta();
                //OpcoesActivity.campanhaIdUsuario = campanhasR.get(position).getIdCampanha();
                listaOnClickListener.onClickDelete(position);
            }
        });
        holder.alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UsuarioActivity.cadernetaId = criancas.get(position).getIdCaderneta();
                holder.nome.setText(criancas.get(position).getNomeCrianca());
                AlterarDadosActivity.cadernetaId = criancas.get(position).getIdCaderneta();
                listaOnClickListener.onClickEdit(holder.itemView,position);
            }
        });
        holder.lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TesteVacinaRealizadaActivity.cadernetaId = criancas.get(position).getIdCaderneta();
                ListaVacinaActivity.cadernetaId = criancas.get(position).getIdCaderneta();
                NovaCampanhaActivity.cadernetaId = criancas.get(position).getIdCaderneta();
                VacinasRFragment.cadernetaId = criancas.get(position).getIdCaderneta();
                VacinasRFragment.cadernetaId = criancas.get(position).getIdCaderneta();
                CampanhasRFragment.cadernetaId = criancas.get(position).getIdCaderneta();
                listaOnClickListener.onClickList(holder.itemView, position);
            }
        });
        holder.acompanhamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PesoAlturaActivity.cadernetaId = criancas.get(position).getIdCaderneta();
                NovoPesoActivity.cadernetaId = criancas.get(position).getIdCaderneta();
                NovaAlturaActivity.cadernetaId = criancas.get(position).getIdCaderneta();
                listaOnClickListener.onClickAcompanhamento(holder.itemView, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return criancas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome;
        TextView idade;
        ImageView foto;
        ImageView lista;
        ImageView acompanhamento;
        ImageView alterar;
        ImageView excluir;
        //ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            //Recupera os componentes de tela -- é o itemView que acessa o objeto view
            nome = itemView.findViewById(R.id.textNomeC);
            idade = itemView.findViewById(R.id.textIdadeC);
            lista = itemView.findViewById(R.id.imageLista);
            acompanhamento = itemView.findViewById(R.id.imagePesoAltura);
            alterar = itemView.findViewById(R.id.imageAlterar);
            excluir = itemView.findViewById(R.id.imageExcluir);
            foto = itemView.findViewById(R.id.imageFoto);
            //progressBar = itemView.findViewById(R.id.progressListandoCriancas);
        }
    }
}
