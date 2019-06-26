package com.example.jaine.vacinacaoapp.activity.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.fragment.CampanhasRFragment;
import com.example.jaine.vacinacaoapp.activity.fragment.VacinasRFragment;
import com.example.jaine.vacinacaoapp.activity.teste.TesteVacinaRealizadaActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class AbasActivity extends AppCompatActivity {

    public static String cadernetaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abas);

        //Com isso podemos vizualizar o botão voltar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Tira a sombra
        getSupportActionBar().setElevation(0);

        //Configurando bottomNavitionViewEX
        configuraBottomNavigation();

        //Irá garregar automaticamente o Fragment de Vacinas
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewPager, new VacinasRFragment()).commit();
    }

    //Método responsável por criar o BottonNavigation
    private void configuraBottomNavigation()
    {
        BottomNavigationViewEx bnve = findViewById(R.id.bottomNavigation);
        //faz configurações iniciais do bnve
        //bnve.enableAnimation(true);
        //bnve.enableItemShiftingMode(true); //muda o modo de exibição apenas do item
        //bnve.enableShiftingMode(false);
        //bnve.setTextVisibility(true); //exibe o texto

        //Habilitar navegação
        habilitarNavegação(bnve);
    }

    //Método responsável por tratar eventos de click no BottomNavigationEX
    private void habilitarNavegação(BottomNavigationViewEx viewEX)
    {
        viewEX.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch(item.getItemId())
                {
                    case R.id.ic_vacinas :
                        fragmentTransaction.replace(R.id.viewPager, new VacinasRFragment()).commit();
                        return true;
                    case R.id.ic_campanhas :
                        fragmentTransaction.replace(R.id.viewPager, new CampanhasRFragment()).commit();
                        return true;
                }
                return false;
            }
        });
    }

    //Infla o menu com opção de adicionar nova campanha
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_abas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Opções menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addCampanha){
            Intent novaCampanha = new Intent(this, NovaCampanhaActivity.class);
            //Intent novaVacina =  new Intent(this, TesteVacinaRealizadaActivity.class);
            startActivity(novaCampanha);
        }
        if(id == R.id.addVacina){
            Intent novaVacina =  new Intent(this, ListaVacinaActivity.class);
            startActivity(novaVacina);
        }
        return super.onOptionsItemSelected(item);
    }
}
