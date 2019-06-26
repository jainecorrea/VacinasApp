package com.example.jaine.vacinacaoapp.activity.helper;

import android.util.Base64;

public class Base64Custom {

    //Irá codificar para uma string
    public static String codificarBase64(String texto){
        // replaceAll() - localiza caracteres inválidos e substitui por vazio
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String decodificarBase64(String textoCodificado){
        return new String( Base64.decode(textoCodificado, Base64.DEFAULT) );
    }
}
