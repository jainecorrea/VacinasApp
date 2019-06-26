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
import com.example.jaine.vacinacaoapp.activity.fragment.CampanhasRFragment;
import com.example.jaine.vacinacaoapp.activity.modelo.CampanhasRealizadas;
import com.example.jaine.vacinacaoapp.activity.modelo.Crianca2;

import java.util.List;

public class AdapterCampanhasR  extends RecyclerView.Adapter<AdapterCampanhasR.MyViewHolder> {

    private List<CampanhasRealizadas> campanhasRealizadas;
    private Context context;

    public ListaOnClickListener listaOnClickListener;

    public AdapterCampanhasR(List<CampanhasRealizadas> listaCampanhas, Context c, ListaOnClickListener listaOnClickListener){
        this.campanhasRealizadas =  listaCampanhas;
        this.listaOnClickListener = listaOnClickListener;
        this.context = c;
    }

    public interface ListaOnClickListener{
        void onClickDelete(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_campanhas_r, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {

        CampanhasRealizadas campanhasR = campanhasRealizadas.get(i);
        holder.nome.setText(campanhasR.getNomeCampanhaR());
        //holder.data.setText(campanhasR.getDataCampanhaR() + " - " + campanhasR.getLoteCampanhaR());
        //holder.lote.setText(campanhasR.getLoteCampanhaR());
        holder.data.setText("Data: " + campanhasR.getDataCampanhaR() + " - Lote: " + campanhasR.getLoteCampanhaR());
        //holder.lote.setText(campanhasR.getLoteCampanhaR());

        holder.excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CampanhasRFragment.campanhaRealizada= campanhasRealizadas.get(i).getIdCampanha();
                listaOnClickListener.onClickDelete(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return campanhasRealizadas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nome;
        TextView data;
        //TextView lote;
        ImageView excluir;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textNomeCampanhaR);
            data = itemView.findViewById(R.id.textDataCampanhaR);
            //lote = itemView.findViewById(R.id.loteCampanhaR);
            excluir = itemView.findViewById(R.id.imagemExcluirCampanha);
        }
    }
}
