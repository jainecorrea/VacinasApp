package com.example.jaine.vacinacaoapp.activity.teste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class AdicionarCampanhasActivity extends AppCompatActivity {

    private EditText aliasNome, aliasInicio, aliasTermino, aliasPublico;
    private Button btnAddCampanha;

    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_campanhas);

        //Configurações iniciais
        inicializarComponentes();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();

        //Criando máscara para data
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(aliasInicio,smf);
        aliasInicio.addTextChangedListener(mtw);

        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(aliasTermino,smf2);
        aliasTermino.addTextChangedListener(mtw2);

        /*String s = "31/02/2009";
        DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
        df.setLenient (false); // aqui o pulo do gato
        try {
            df.parse (s);
            // data válida
        } catch (ParseException ex) {
            // data inválida
        }*/

        btnAddCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarDados();
            }
        });
    }

    private void validarDados(){
        String nome = aliasNome.getText().toString();
        String incio = aliasInicio.getText().toString();
        String termino = aliasTermino.getText().toString();
        String publico = aliasPublico.getText().toString();

        if(!nome.isEmpty()){
            if(!incio.isEmpty()){
                if(!termino.isEmpty()){
                    if(!publico.isEmpty()){

                        Campanha campanha = new Campanha();
                        campanha.setIdUsuario( idUsuarioLogado );
                        campanha.setTitulo( nome );
                        campanha.setDataInicio( incio );
                        campanha.setDataTermino( termino );
                        campanha.setPublicoAlvo( publico );
                        campanha.salvar();
                        finish();
                        exibirMensagem("Campanha de vacinação cadastrada com sucesso");

                    }else{
                        exibirMensagem("Preencha o campo com público alvo da campanha de vacinção!");
                    }
                }else{
                    exibirMensagem("Preencha o campo com a data de término da campanha de vacinação!");
                }
            }else{
                exibirMensagem("Preencha o campo com a data de início da campanha de vacinção!");
            }
        }else{
            exibirMensagem("Preencha o campo com o nome da campanha de vacinação!");
        }
    }

    public void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void inicializarComponentes(){
        aliasNome = findViewById(R.id.editNomeCampanhaAdmin);
        aliasInicio = findViewById(R.id.editInicioCampanhaAdmin);
        aliasTermino = findViewById(R.id.editTerminoCampanhaAdmin);
        aliasPublico = findViewById(R.id.editPublicCampanhaAdmin);
        btnAddCampanha = findViewById(R.id.btnAddCampanhaAdmin);
    }
}
