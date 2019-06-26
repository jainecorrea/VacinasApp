package com.example.jaine.vacinacaoapp.activity.modelo;

import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class CampanhasRealizadas {

    public String idUsuario;
    private String nomeCampanhaR;
    private String dataCampanhaR;
    private String loteCampanhaR;
    private String idCaderneta;
    private String idCampanha;

    public CampanhasRealizadas() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference camapanhaRef = firebaseRef
                .child("USUARIO-CAMPANHAS");
        setIdCampanha(camapanhaRef.push().getKey());//Com isso toda vez que criarmos uma caderneta iremos pegar o id dela*/

    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference campanhaRef = firebaseRef
                .child("USUARIO-CAMPANHAS")
                .child( getIdUsuario() )
                .child( getIdCaderneta() )
                .child( getIdCampanha() );
        campanhaRef.setValue(this);
    }

    public void remover(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
            DatabaseReference campanhaRef = firebaseRef
                    .child("USUARIO-CAMPANHAS")
                    .child( getIdUsuario() )
                    .child( getIdCaderneta() )
                    .child( getIdCampanha() );
            campanhaRef.removeValue();
    }

    public String getLoteCampanhaR() {
        return loteCampanhaR;
    }

    public void setLoteCampanhaR(String loteCampanhaR) {
        this.loteCampanhaR = loteCampanhaR;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getIdCaderneta() {
        return idCaderneta;
    }

    public void setIdCaderneta(String idCaderneta) {
        this.idCaderneta = idCaderneta;
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

    public String getNomeCampanhaR() {
        return nomeCampanhaR;
    }

    public void setNomeCampanhaR(String nomeCampanhaR) {
        this.nomeCampanhaR = nomeCampanhaR;
    }

    public String getDataCampanhaR() {
        return dataCampanhaR;
    }

    public void setDataCampanhaR(String dataCampanhaR) {
        this.dataCampanhaR = dataCampanhaR;
    }
}
