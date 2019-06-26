package com.example.jaine.vacinacaoapp.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jaine.vacinacaoapp.R;

public class CalendarioBasicoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_basico);

        //Tira a sombra
        getSupportActionBar().setElevation(0);
    }
}
