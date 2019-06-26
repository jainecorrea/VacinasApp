package com.example.jaine.vacinacaoapp.activity.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.adapter.AdapterLembretes;

public class LembretesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembretes);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Configuração de ação do FAB*/
                Intent addLembrete = new Intent(LembretesActivity.this, NovoLembreteActivity.class);
                startActivity(addLembrete);
            }
        });


        //RecyclerView
        recyclerView = (RecyclerView)findViewById(R.id.recyclerLembretes);
        //Configurar adapter
        AdapterLembretes adapterLembretes = new AdapterLembretes();
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterLembretes);
    }
}
