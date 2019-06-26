package com.example.jaine.vacinacaoapp.activity.helper;

import java.text.SimpleDateFormat;

public class DataUtil {

    //Classe responsável por preencher o campo data atual
    public static String dataAtual(){

        //Vai retornar a data representada por um long
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = simpleDateFormat.format(data);
        return dataString;
    }

    //Separa a data, retirando as "/" e ficando apenas com os números
    public static String mesAnoDataEscolhida(String data){
        //23/05/2000
        String retornoData[] = data.split("/");
        String dia = retornoData[0]; //dia 23
        String mes = retornoData[1]; //mes 05
        String ano = retornoData[2]; //ano 2000

        String mesAno = mes + ano;
        return mesAno;
    }

}
