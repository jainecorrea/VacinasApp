package com.example.jaine.vacinacaoapp.activity.teste;

import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Imunizacao {

    private String idUsuario;
    private String idAdministrador; //É necessário?
    private String idImunizacao;
    private String idCaderneta;
    private List<ItemInunizacao> itens;

    public Imunizacao() {
    }

    public Imunizacao(String idUsu) {
        setIdUsuario( idUsu );
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference imunizacaoRef = firebaseRef
                .child("VACINAS-REALIZADAS") //vacinas_usuario
                .child( idUsu );
        setIdImunizacao( imunizacaoRef.push().getKey() );
    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference imunizacaoRef = firebaseRef
                .child("VACINAS-REALIZADAS")
                .child( getIdUsuario() )
                .child( getIdCaderneta() );
        imunizacaoRef.setValue( this );
    }

    public void remover(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference imunizacaoRef = firebaseRef
                .child("VACINAS-REALIZADAS")
                .child( getIdUsuario() )
                .child( getIdCaderneta() );
        imunizacaoRef.removeValue();
    }

    public void confirmar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference imunizacaoRef = firebaseRef
                .child("CONFIRMACAO-VACINAS-REALIZADAS")
                .child( getIdCaderneta() );
                //.child( getIdImunizacao() );
        imunizacaoRef.setValue( this );
    }

    public String getIdImunizacao() {
        return idImunizacao;
    }

    public void setIdImunizacao(String idImunizacao) {
        this.idImunizacao = idImunizacao;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(String idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getIdCaderneta() {
        return idCaderneta;
    }

    public void setIdCaderneta(String idCaderneta) {
        this.idCaderneta = idCaderneta;
    }

    public List<ItemInunizacao> getItens() {
        return itens;
    }

    public void setItens(List<ItemInunizacao> itens) {
        this.itens = itens;
    }
}
