package com.example.jaine.vacinacaoapp.activity.modelo;

import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.helper.DataUtil;
import com.google.firebase.database.DatabaseReference;

public class Acompanhamento {

    private String idAcompanhamento;
    private String data;
    private String tipo;
    private double valor;
    private String caderneta;

    public Acompanhamento() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference campanhaRef = firebaseRef
                .child("ADMIN-CAMPANHAS");
        setIdAcompanhamento( campanhaRef.push().getKey() );
    }

    public void salvar(String dataEscolhida){
        String mesAno = DataUtil.mesAnoDataEscolhida( dataEscolhida );
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference acompanhamentoRef = firebase.child("USUARIO-ACOMPANHAMENTO")
                .child( getCaderneta() )
                .child( mesAno )
                .child( getIdAcompanhamento());
        acompanhamentoRef.setValue(this);
    }

    public void remover(){
        //String mesAno = DataUtil.mesAnoDataEscolhida( dataEscolhida );
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference crianca2Ref = firebaseRef
                .child("USUARIO-ACOMPANHAMENTO")
                .child( getCaderneta() );
                //.child( dataEscolhida )
                //.child( getIdAcompanhamento() );
        crianca2Ref.removeValue();
    }

    public String getIdAcompanhamento() {
        return idAcompanhamento;
    }

    public void setIdAcompanhamento(String idAcompanhamento) {
        this.idAcompanhamento = idAcompanhamento;
    }

    public String getCaderneta() {
        return caderneta;
    }

    public void setCaderneta(String caderneta) {
        this.caderneta = caderneta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
