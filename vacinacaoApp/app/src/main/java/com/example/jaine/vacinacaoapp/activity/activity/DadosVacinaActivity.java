package com.example.jaine.vacinacaoapp.activity.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.example.jaine.vacinacaoapp.activity.teste.Vacina;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DadosVacinaActivity extends AppCompatActivity {

    private TextView aliasNome, aliasDosagem, aliasDescricao, aliasEfeitos, aliasUsoCombinado, aliasAplicacao;
    public DatabaseReference databaseReference;
    public static String vacinaId;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_vacina);

        //Configurações iniciais
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        inicializarComponentes();

        recuperarDados();

        //Configurações iniciais
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        aliasNome = findViewById(R.id.textNomeDadosVacina);
        aliasDosagem = findViewById(R.id.textDosagemDadosVacina);
        aliasDescricao = findViewById(R.id.textDescricaoDadosVacina);
        aliasEfeitos = findViewById(R.id.textEfeitosDadosVacina);
        aliasUsoCombinado = findViewById(R.id.textUsoCombinadoDadosVacina);
        aliasAplicacao = findViewById(R.id.textModoAplicacaoDadosVacina);
        progressBar = findViewById(R.id.progressDadosVacina);
    }

    //Recupera os dados
    private void recuperarDados() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference vacinaRef = databaseReference
                .child("ADMIN-VACINAS")
                .child(vacinaId);
        vacinaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                if (dataSnapshot.getValue() != null) {
                    Vacina vacina = dataSnapshot.getValue(Vacina.class);
                    aliasNome.setText(vacina.getNomeVacina());
                    aliasDosagem.setText(vacina.getDosagemVacina());
                    aliasDescricao.setText(vacina.getDescricaoVacina());
                    aliasEfeitos.setText(vacina.getEfeitosVacina());
                    aliasUsoCombinado.setText(vacina.getUsoCombinadoVacina());
                    aliasAplicacao.setText(vacina.getAplicacaoVacina());
                    //Toast.makeText(DadosVacinaActivity.this, "Vacina seleciondada: " + vacinaId, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
