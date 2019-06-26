package com.example.jaine.vacinacaoapp.activity.teste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;

public class AdicionarVacinaActivity extends AppCompatActivity {

    public EditText aliasNomeVacina, aliasDosagemVacina, aliasDescricaoVacina, aliasEfeitoVacina, aliasUsoCombinado;
    public Button aliasAdicionarVacina;

    private String idUsuarioLogado;

    private String[] menuS = new String[] {"Via oral", "Injetável"};
    private Spinner combobox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_vacina);

        idUsuarioLogado = UsuarioFirebase.getIdUsuario();
        inicializarComponentes();

        //Spinner
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuS);
        combobox.setAdapter(adaptador);

        aliasAdicionarVacina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarDados();
            }
        });
    }

    private void validarDados(){
        String nome = aliasNomeVacina.getText().toString();
        String dose = aliasDosagemVacina.getText().toString();
        String descricao = aliasDescricaoVacina.getText().toString();
        String efeitos = aliasEfeitoVacina.getText().toString();
        String uso = aliasUsoCombinado.getText().toString();
        String aplicacao = combobox.getSelectedItem().toString();

        if(!nome.isEmpty()) {
            if (!dose.isEmpty()){
                if (!descricao.isEmpty()) {
                    if (!efeitos.isEmpty()) {
                        if (!uso.isEmpty()) {
                            if (!aplicacao.isEmpty()) {

                                Vacina vacina = new Vacina();
                                vacina.setIdUsuario(idUsuarioLogado);
                                vacina.setNomeVacina(nome);
                                vacina.setDosagemVacina( dose );
                                vacina.setDescricaoVacina(descricao);
                                vacina.setEfeitosVacina(efeitos);
                                vacina.setUsoCombinadoVacina(uso);
                                vacina.setAplicacaoVacina(aplicacao);
                                vacina.salvar();
                                finish();
                                exibirMensagem("Sucesso ao cadastrar vacina");

                            } else {
                                exibirMensagem("Preencha o campo com a forma de aplicação da vacina!");
                            }
                        } else {
                            exibirMensagem("Preencha o campo com o uso combinado da vacina!");
                        }
                    } else {
                        exibirMensagem("Preencha o campo com os efeitos da vacina!");
                    }
                } else {
                    exibirMensagem("Preencha o campo com a descrição da vacina!");
                }
        }else{
                exibirMensagem("Preencha o campo com a dosagem da vacina!");
            }
        }else{
            exibirMensagem("Preencha o campo de nome!");
        }
    }

    public void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void inicializarComponentes(){
        aliasNomeVacina = findViewById(R.id.editNomeVacinaSalvar);
        aliasDosagemVacina = findViewById(R.id.editDosagemVacinaSalvar);
        aliasDescricaoVacina = findViewById(R.id.editDescricaoVacinaSalvar);
        aliasEfeitoVacina = findViewById(R.id.editEfeitosVacinaSalvar);
        aliasUsoCombinado = findViewById(R.id.editUsoCombinadoVacinaSalvar);
        aliasAdicionarVacina = findViewById(R.id.btnSalvarVacina);
        combobox = findViewById(R.id.spinnerVacinaSalvar);
    }
}
