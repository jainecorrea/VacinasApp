package com.example.jaine.vacinacaoapp.activity.teste;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.activity.NovoLembreteActivity;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DescricaoVacinaActivity extends AppCompatActivity {

    private TextView aliasNome, aliasDosagem, aliasDescricao, aliasEfeitos, aliasUsoCombinado, aliasAplicacao;

    public DatabaseReference databaseReference;
    public static String vacinaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao_vacina);

        //Configurações iniciais
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        inicializarComponentes();

        recuperarDados();
    }

    //Inicializa os componentes
    private void inicializarComponentes(){
        aliasNome = findViewById(R.id.textNomeDadosVacina);
        aliasDosagem = findViewById(R.id.textDosagemDadosVacina);
        aliasDescricao = findViewById(R.id.textDescricaoDadosVacina);
        aliasEfeitos = findViewById(R.id.textEfeitosDadosVacina);
        aliasUsoCombinado = findViewById(R.id.textUsoCombinadoDadosVacina);
        aliasAplicacao = findViewById(R.id.textModoAplicacaoDadosVacina);
    }

    //Recupera os dados
    private void recuperarDados(){
        DatabaseReference vacinaRef = databaseReference
                .child("ADMIN-VACINAS")
                .child( vacinaId );
        vacinaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Vacina vacina = dataSnapshot.getValue(Vacina.class);
                    aliasNome.setText(vacina.getNomeVacina());
                    aliasDosagem.setText(vacina.getDosagemVacina());
                    aliasDescricao.setText(vacina.getDescricaoVacina());
                    aliasEfeitos.setText(vacina.getEfeitosVacina());
                    aliasUsoCombinado.setText(vacina.getUsoCombinadoVacina());
                    aliasAplicacao.setText(vacina.getAplicacaoVacina());
                    //Toast.makeText(DescricaoVacinaActivity.this, "Vacina seleciondada: " + vacinaId, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Infla o menu de itens de informações
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_informacoes, menu);
        return super.onCreateOptionsMenu(menu);
        //return true;
    }*/

    //Opções - marcar como feita e adicionar lembrete
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addLembreteInformaçoes){
            Intent novoLembrete = new Intent(this, NovoLembreteActivity.class);
            startActivity(novoLembrete);
        }
        /*
        if (id == R.id.vacinaRealizada){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Vacina realizada");
            builder.setMessage("Deseja marcar a vacina como feita?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(DescricaoVacinaActivity.this, "Vacina marcada como feita", Toast.LENGTH_LONG).show();
                    item.setEnabled(false);
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }
        */
        return super.onOptionsItemSelected(item);
        //return true;
    }
}
