package com.example.jaine.vacinacaoapp.activity.modelo;

public class Crianca {

    private String UID;
    private String foto;
    private String nome;
    private String dataNasc;
    //private boolean sexo;


    public Crianca() {
    }

    /*********************************************************/
    public Crianca(String foto){
        this.foto = foto;
    }

    public Crianca(String UID, String nome, String dataNasc, String foto) {
        this.UID = UID;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    /********************************************************/

    /*public Crianca(String UID, String nome, String dataNasc) {
        this.UID = UID;
        this.nome = nome;
        this.dataNasc = dataNasc;
    }*/

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    /*public Boolean getFoto() {
        return foto;
    }

    public void setFoto(Boolean foto) {
        this.foto = foto;
    }*/

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    /*public Boolean getSexo() {
        return sexo;
    }*/

    /*public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }*/
}
