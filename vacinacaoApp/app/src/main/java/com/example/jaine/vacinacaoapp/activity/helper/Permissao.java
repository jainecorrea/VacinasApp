package com.example.jaine.vacinacaoapp.activity.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validarPermissoes(String[] permissoes, Activity activity, int requestCode)
    {
        //Verifico qual a versão que o usuário está usando
        if(Build.VERSION.SDK_INT >= 23)
        {
            List<String> listaPermissoes = new ArrayList<>();

            //Percorre as permissões passadas verificando uma a uma se a permissão já foi aceita
            for(String permissao : permissoes)
            {
                //recupera se temos a permissão
                Boolean temPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                //caso a permissão não tenha sido concedida, será adicionada
                if(!temPermissao)
                {
                    listaPermissoes.add(permissao);
                }
                //Caso a lista esteja vazia, ná será necessário slicitar permissão
                if(listaPermissoes.isEmpty())
                {
                    return true;
                }
                //descobre quantas permissões são necessárias e cria o array
                String[] novasPermissoes = new String[listaPermissoes.size()];
                //Converta para uma Array
                listaPermissoes.toArray(novasPermissoes);
                //Solicita permissão
                ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
            }
        }
        return true;
    }

}
