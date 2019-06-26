package com.example.jaine.vacinacaoapp.activity.teste;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlterarAdminActivity extends AppCompatActivity {

    private EditText aliasNome, aliasDosagem, aliasDescricao, aliasEfeitos, aliasUsoCombinado;
    private Button aliasAlterar;

    public DatabaseReference databaseReference;
    private List<Vacina> vacinas = new ArrayList<Vacina>();
    public static String vacinaId;

    private EditText combobox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_admin);

        //Configurações iniciais
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        inicializarComponentes();

        //Recupera os dados
        recuperarDados();

        aliasAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterarDados();
            }
        });
    }

    //Inicializa componentes
    private void inicializarComponentes(){
        aliasNome = findViewById(R.id.editAlterarNomeAdmin);
        aliasDosagem = findViewById(R.id.editAlterarDosagemAdmin);
        aliasDescricao = findViewById(R.id.editAlterarDescricaoAdmin);
        aliasEfeitos = findViewById(R.id.editAlterarEfeitosAdmin);
        aliasUsoCombinado = findViewById(R.id.editAlterarUsoAdmin);
        aliasAlterar = findViewById(R.id.btnAlterarAdmin);
        combobox = findViewById(R.id.editAlterarAAdminVacina);
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
                    combobox.setText(vacina.getAplicacaoVacina());

                    //Toast.makeText(AlterarAdminActivity.this, "Vacina seleciondada: " + vacinaId, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Altera os dados
    private void alterarDados(){
        String nome = aliasNome.getText().toString();
        String dosagem = aliasDosagem.getText().toString();
        String descricao = aliasDescricao.getText().toString();
        String efeitos = aliasEfeitos.getText().toString();
        String uso = aliasUsoCombinado.getText().toString();
        String aplicacao = combobox.getText().toString();

        if(!nome.isEmpty()){
            if(!dosagem.isEmpty()){
                if(!descricao.isEmpty()){
                    if(!efeitos.isEmpty()){
                        if(!uso.isEmpty()){
                            if(!aplicacao.isEmpty()){

                                //altera o nome
                                databaseReference.child( "ADMIN-VACINAS" ).child( vacinaId ).child( "nomeVacina" ).setValue(nome);

                                //altera o dosagem
                                databaseReference.child( "ADMIN-VACINAS" ).child( vacinaId ).child( "dosagemVacina" ).setValue(dosagem);

                                //altera a descricao
                                databaseReference.child( "ADMIN-VACINAS" ).child( vacinaId ).child( "descricaoVacina" ).setValue(descricao);

                                //altera o efeito
                                databaseReference.child( "ADMIN-VACINAS" ).child( vacinaId ).child( "efeitosVacina" ).setValue(efeitos);

                                //altera o uso
                                databaseReference.child( "ADMIN-VACINAS" ).child( vacinaId ).child( "usoCombinadoVacina" ).setValue(uso);

                                //altera a aplicacao
                                databaseReference.child( "ADMIN-VACINAS" ).child( vacinaId ).child( "aplicacaoVacina" ).setValue(aplicacao);

                                exibirMensagem("Dados alterados com sucesso");
                                finish();

                            }else{
                                exibirMensagem("Preeencha o campo com o modo de aplicação da vacina!");
                            }
                        }else{
                            exibirMensagem("Preeencha o campo com uso combinado da vacina!");
                        }
                    }else{
                        exibirMensagem("Preeencha o campo com os efetios colaterais da vacina!");
                    }
                }else{
                    exibirMensagem("Preeencha o campo com a descrição da vacina!");
                }
            }else{
                exibirMensagem("Preeencha o campo com a dosagem da vacina!");
            }
        }else{
            exibirMensagem("Preeencha o campo com o nome da vacina!");
        }
    }

    //Exibe mensagem
    public void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }
}
