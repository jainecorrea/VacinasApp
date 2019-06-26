package com.example.jaine.vacinacaoapp.activity.activity;

import android.annotation.SuppressLint;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.helper.DataUtil;
import com.example.jaine.vacinacaoapp.activity.modelo.Acompanhamento;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class NovoPesoActivity extends AppCompatActivity {

    private EditText aliasData, aliasPeso;
    private Button aliasSalvarPeso;
    private Acompanhamento acompanhamento;
    public static String cadernetaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_peso);

        //Inicializar componentes
        inicializarComponentes();

        //Preenche o campo data com a data atual
        aliasData.setText(DataUtil.dataAtual());

        /*aliasDataNasc = (EditText)findViewById(R.id.editCadastroDataNasc);
        SimpleMaskFormatter smf = new SimpleMaskFormatter("##,##0.000 KG");
        MaskTextWatcher mtw = new MaskTextWatcher(aliasPeso,smf);
        aliasPeso.addTextChangedListener(mtw);*/

        aliasSalvarPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarAcompanhamento();
            }
        });
    }

    private void salvarAcompanhamento(){

        if(validarCamposNovoPeso()){
            acompanhamento = new Acompanhamento();
            String data = aliasData.getText().toString();
            acompanhamento.setValor( Double.parseDouble(aliasPeso.getText().toString()) ); //Converte
            acompanhamento.setData( data );
            acompanhamento.setCaderneta(cadernetaId);
            acompanhamento.setTipo( "Peso" );
            acompanhamento.salvar( data );
            Toast.makeText(NovoPesoActivity.this, "Peso salvo com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public Boolean validarCamposNovoPeso(){

        String textoValor = aliasPeso.getText().toString();
        String data = aliasData.getText().toString();

        if(!textoValor.isEmpty()){
            if(!data.isEmpty()){
                return true;
            }else{
                Toast.makeText(NovoPesoActivity.this, "O campo reservado para a data não foi preenchido!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(NovoPesoActivity.this, "O campo reservado para a altura não foi preenchido!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void inicializarComponentes(){
        aliasData = findViewById(R.id.editNovoPesoDataAtual);
        aliasPeso = findViewById(R.id.editPesoAcompanhamento);
        aliasSalvarPeso = findViewById(R.id.btnSalvarNovoPeso);
    }
}
