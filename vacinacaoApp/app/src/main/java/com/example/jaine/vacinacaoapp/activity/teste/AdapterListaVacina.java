package com.example.jaine.vacinacaoapp.activity.teste;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jaine.vacinacaoapp.R;

import java.util.List;

public class AdapterListaVacina extends RecyclerView.Adapter<AdapterListaVacina.MyViewHolder>{

    private List<Vacina> vacinas;
    //private Context context;

    ///
    private onItemClickListener mListener;

    ///
    public interface onItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public AdapterListaVacina (List<Vacina> vacinas){
        this.vacinas = vacinas;
        //this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Converte o objeto xml em um objeto do tipo view
        View listaVacinaFeita = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_vacina, parent
                , false);
        return new MyViewHolder(listaVacinaFeita, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vacina vacina = vacinas.get(position);
        holder.vacinaFeita.setText(vacina.getNomeVacina());
    }

    @Override
    public int getItemCount() {
        return vacinas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView vacinaFeita;
        public MyViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);

            //Recupera os componentes de tela -- é o itemView que acessa o objeto view
            vacinaFeita = itemView.findViewById(R.id.textVacinaFeita);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                    //listener.onItemClick();
                }
            });
        }
    }
}
