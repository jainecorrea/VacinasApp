package com.example.jaine.vacinacaoapp.activity.teste;

import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Campanha {

    private String idUsuario;
    private String idCampanha;
    private String titulo;
    private String dataInicio;
    private String dataTermino;
    private String publicoAlvo;

    public Campanha() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference campanhaRef = firebaseRef
                .child("ADMIN-CAMPANHAS");
        setIdCampanha( campanhaRef.push().getKey() );//Com isso toda vez que criarmos uma vacina iremos pegar o id dela
    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference campanhaRef = firebaseRef
                .child("ADMIN-CAMPANHAS")
                //.child( getIdUsuario() )
                .child( getIdCampanha() );
        campanhaRef.setValue(this);
    }

    public void remover(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference crianca2Ref = firebaseRef
                .child("ADMIN-CAMPANHAS")
                //.child( getIdUsuario() )
                .child( getIdCampanha() );
        crianca2Ref.removeValue();
    }

    public void removerNotificacao(int position){

    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdCampanha() {
        return idCampanha;
    }

    public void setIdCampanha(String idCampanha) {
        this.idCampanha = idCampanha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(String publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }
}
