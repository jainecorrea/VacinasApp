package com.example.jaine.vacinacaoapp.activity.modelo;

import android.net.Uri;

import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Crianca2 {

    private String idUsuario;
    private String idCaderneta;
    private String urlImagemCrianca;
    private String nomeCrianca;
    private String dataNascCrianca;
    private String sexoCrianca;

    public Crianca2() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference crianca2Ref = firebaseRef
                .child("USUARIO-CRIANCAS");
        setIdCaderneta( crianca2Ref.push().getKey() );//Com isso toda vez que criarmos uma caderneta iremos pegar o id dela
    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference crianca2Ref = firebaseRef
                .child("USUARIO-CRIANCAS")
                .child( getIdUsuario() )
                .child( getIdCaderneta() );
        crianca2Ref.setValue(this);
    }

    public void remover(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference crianca2Ref = firebaseRef
                .child("USUARIO-CRIANCAS")
                .child( getIdUsuario() )
                .child( getIdCaderneta() );
        crianca2Ref.removeValue();
    }

    public void alterar(String nomeCrianca){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference crianca2Ref = firebaseRef
                .child("USUARIO-CRIANCAS")
                .child( getIdUsuario() )
                .child( getIdCaderneta() );
        crianca2Ref.setValue( nomeCrianca );
    }

    public String getUrlImagemCrianca() {
        return urlImagemCrianca;
    }

    public void setUrlImagemCrianca(String urlImagemCrianca) {
        this.urlImagemCrianca = urlImagemCrianca;
    }

    public String getSexoCrianca() {
        return sexoCrianca;
    }

    public void setSexoCrianca(String sexoCrianca) {
        this.sexoCrianca = sexoCrianca;
    }

    public String getIdCaderneta() {
        return idCaderneta;
    }

    public void setIdCaderneta(String idCaderneta) {
        this.idCaderneta = idCaderneta;
    }

    public void setDataNascCrianca(String dataNascCrianca) {
        this.dataNascCrianca = dataNascCrianca;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeCrianca() {
        return nomeCrianca;
    }

    public void setNomeCrianca(String nomeCrianca) {
        this.nomeCrianca = nomeCrianca;
    }

    public String getDataNascCrianca() {
        return dataNascCrianca;
    }
}
