package com.example.jaine.vacinacaoapp.activity.teste;

import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Vacina {

    private String idUsuario;
    private String idVacina;
    private String nomeVacina;
    private String dosagemVacina;
    private String descricaoVacina;
    private String efeitosVacina;
    private String usoCombinadoVacina;
    private String aplicacaoVacina;

    public Vacina() {
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference vacinaRef = firebaseRef
                .child("ADMIN-VACINAS");
        setIdVacina( vacinaRef.push().getKey() );//Com isso toda vez que criarmos uma vacina iremos pegar o id dela
    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference vacinaRef = firebaseRef
                .child("ADMIN-VACINAS")
                //.child( getIdUsuario() )
                .child( getIdVacina() );
        vacinaRef.setValue(this);
    }

    public void remover(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference crianca2Ref = firebaseRef
                .child("ADMIN-VACINAS")
                //.child( getIdUsuario() )
                .child( getIdVacina() );
        crianca2Ref.removeValue();
    }

    public String getDosagemVacina() {
        return dosagemVacina;
    }

    public void setDosagemVacina(String dosagemVacina) {
        this.dosagemVacina = dosagemVacina;
    }

    public String getEfeitosVacina() {
        return efeitosVacina;
    }

    public void setEfeitosVacina(String efeitosVacina) {
        this.efeitosVacina = efeitosVacina;
    }

    public String getUsoCombinadoVacina() {
        return usoCombinadoVacina;
    }

    public void setUsoCombinadoVacina(String usoCombinadoVacina) {
        this.usoCombinadoVacina = usoCombinadoVacina;
    }

    public String getAplicacaoVacina() {
        return aplicacaoVacina;
    }

    public void setAplicacaoVacina(String aplicacaoVacina) {
        this.aplicacaoVacina = aplicacaoVacina;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdVacina() {
        return idVacina;
    }

    public void setIdVacina(String idVacina) {
        this.idVacina = idVacina;
    }

    public String getNomeVacina() {
        return nomeVacina;
    }

    public void setNomeVacina(String nomeVacina) {
        this.nomeVacina = nomeVacina;
    }

    public String getDescricaoVacina() {
        return descricaoVacina;
    }

    public void setDescricaoVacina(String descricaoVacina) {
        this.descricaoVacina = descricaoVacina;
    }
}
