package com.example.jaine.vacinacaoapp.activity.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.fragment.DatePickerFragment;
import com.example.jaine.vacinacaoapp.activity.modelo.CampanhasRealizadas;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.text.DateFormat;
import java.util.Calendar;

public class NovaCampanhaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public EditText aliasDataCampanha, aliasNomeCampanha, aliasLote;
    public Button btnCampanha;

    private String idUsuarioLogado;

    public static String cadernetaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_campanha);

        inicializarComponentes();

        //Retorna o id do usuário logado
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();

        //Criando máscara para data
        //SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        //MaskTextWatcher mtw = new MaskTextWatcher(aliasDataCampanha,smf);
        //aliasDataCampanha.addTextChangedListener(mtw);
        aliasDataCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), " date picker ");
            }
        });
        // Comando para o teclado virtual não aparecer
        aliasDataCampanha.setInputType(InputType.TYPE_NULL);

        btnCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarDadosCampanha();
            }
        });

    }

    private void validarDadosCampanha(){

        String nomeCampanha = aliasNomeCampanha.getText().toString();
        String dataCampanha = aliasDataCampanha.getText().toString();
        String loteCampanha = aliasLote.getText().toString();

        if(!loteCampanha.isEmpty()){
            if(!nomeCampanha.isEmpty()){
                if (!dataCampanha.isEmpty()) {
                    ////
                    CampanhasRealizadas cR = new CampanhasRealizadas();
                    cR.setDataCampanhaR( dataCampanha );
                    cR.setIdUsuario( idUsuarioLogado );
                    cR.setIdCaderneta( cadernetaId );
                    cR.setNomeCampanhaR( nomeCampanha );
                    cR.setLoteCampanhaR( loteCampanha );
                    cR.salvar();
                    finish();
                    exibirMensagem("Campanha adicionada com sucesso!"+ cadernetaId);

                }else{
                    exibirMensagem("Digite a data da campanha de vacinação!");
                }
            }else{
                exibirMensagem("Digite o nome da campanha de vacinação!");
            }
        }else{
            exibirMensagem("Digite o lote da campanha de vacinação!");
        }
    }

    public void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void inicializarComponentes(){
        aliasDataCampanha = findViewById(R.id.editAdicionarDataCampanha);
        aliasNomeCampanha = findViewById(R.id.editAdicionarNomeCampanha);
        aliasLote = findViewById(R.id.editTextLoteNovaCampanha);
        btnCampanha = findViewById(R.id.btnAdicionarCampanha);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentSateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
        EditText aliasData = findViewById(R.id.editAdicionarDataCampanha);
        aliasData.setText(currentSateString);
    }
}
