package com.example.jaine.vacinacaoapp.activity.teste;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jaine.vacinacaoapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Tira a sombra
        getSupportActionBar().hide();
        //getSupportActionBar().setElevation(0);

        //Faz com que a SplashActivity aparece por um tempo delimitado
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                abrirAuteticacao();
            }
        }, 3000);  ///3000 = 3 segundos
    }

    private void abrirAuteticacao(){
        Intent i = new Intent(SplashActivity.this, AutenticacaoActivity.class );
        startActivity(i);
        finish(); //irá fechar a SplashActivity
    }
}
