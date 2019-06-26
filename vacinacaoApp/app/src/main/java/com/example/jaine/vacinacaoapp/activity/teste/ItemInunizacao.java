package com.example.jaine.vacinacaoapp.activity.teste;

public class ItemInunizacao {

    private String idVacina;
    private String nomeVacina;
    private String dataVacina;
    private String nomeCrianca;
    private int loteVacina;

    public ItemInunizacao() {

    }

    public int getLoteVacina() {
        return loteVacina;
    }

    public void setLoteVacina(int loteVacina) {
        this.loteVacina = loteVacina;
    }

    public String getNomeCrianca() {
        return nomeCrianca;
    }

    public void setNomeCrianca(String nomeCrianca) {
        this.nomeCrianca = nomeCrianca;
    }

    public String getDataVacina() {
        return dataVacina;
    }

    public void setDataVacina(String dataVacina) {
        this.dataVacina = dataVacina;
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
}
