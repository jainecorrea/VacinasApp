package com.example.jaine.vacinacaoapp.activity.activity;

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

public class NovaAlturaActivity extends AppCompatActivity {

    private EditText aliasData, aliasAltura;
    private Button aliasSalvarAltura;
    private Acompanhamento acompanhamento;
    public static String cadernetaId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_altura);

        //Inicializar componentes
        inicializarComponentes();

        //Preenche o campo data com a data atual
        aliasData.setText(DataUtil.dataAtual());
        /*aliasDataNasc = (EditText)findViewById(R.id.editCadastroDataNasc);*/
        SimpleMaskFormatter smf = new SimpleMaskFormatter("N.NN");
        MaskTextWatcher mtw = new MaskTextWatcher(aliasAltura,smf);
        aliasAltura.addTextChangedListener(mtw);

        aliasSalvarAltura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarAcompanhamento();
            }
        });
    }

    private void salvarAcompanhamento(){

        if(validarCamposNovaAltura()){
            acompanhamento = new Acompanhamento();
            String data = aliasData.getText().toString();
            acompanhamento.setValor( Double.parseDouble(aliasAltura.getText().toString()) ); //Converte
            acompanhamento.setData( data );
            acompanhamento.setCaderneta(cadernetaId);
            acompanhamento.setTipo( "Altura" );
            acompanhamento.salvar( data );
            Toast.makeText(NovaAlturaActivity.this, "Altura salva no banco de dados com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public Boolean validarCamposNovaAltura(){

        String textoValor = aliasAltura.getText().toString();
        String data = aliasData.getText().toString();

        if(!textoValor.isEmpty()){
            if(!data.isEmpty()){
                return true;
            }else{
                Toast.makeText(NovaAlturaActivity.this, "O campo reservado para a data não foi preenchido!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(NovaAlturaActivity.this, "O campo reservado para a altura não foi preenchido!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void inicializarComponentes(){
        aliasData = findViewById(R.id.editNovaAlturaDataAtual);
        aliasSalvarAltura = findViewById(R.id.btnSalvarAltura);
        aliasAltura = findViewById(R.id.editAlturaAcompanhamento);
    }
}
