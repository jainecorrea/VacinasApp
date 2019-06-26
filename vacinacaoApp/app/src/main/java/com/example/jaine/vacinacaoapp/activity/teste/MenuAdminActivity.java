package com.example.jaine.vacinacaoapp.activity.teste;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class MenuAdminActivity extends AppCompatActivity {

    private Button aliasVacinas, aliasCampanhas;
    private FirebaseAuth usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        //Configurações iniciais
        inicializarComponentes();
        usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();

        aliasVacinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuAdminActivity.this, InformacoesActivity.class);
                startActivity(i);
            }
        });

        aliasCampanhas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuAdminActivity.this, CampanhasAdminActivity.class);
                startActivity(i);
            }
        });
    }

    //Menu de itens
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin_sair, menu);
        return true;
    }

    //Opção selecionada
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menuSairAdmin){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Sair");
            builder.setMessage("Tem certeza que deseja sair?");
            builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    usuario.signOut();
                    finish();
                    Intent intent = new Intent(MenuAdminActivity.this, AutenticacaoActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }
        return true;
    }

    //Inicializa componentes
    private void inicializarComponentes(){
        aliasVacinas = findViewById(R.id.btnAdminVacinas);
        aliasCampanhas = findViewById(R.id.btnAdminCampanhas);
    }
}
