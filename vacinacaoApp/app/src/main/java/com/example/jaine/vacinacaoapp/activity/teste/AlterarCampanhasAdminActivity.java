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
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AlterarCampanhasAdminActivity extends AppCompatActivity {

    private EditText aliasNome, aliasInicio, aliasTermino, aliasPublico;
    private Button aliasAlterar;
    public DatabaseReference databaseReference;
    private String idUsuarioLogado;
    public static String campanhaIdAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_campanhas_admin);

        //Configurações iniciais
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();
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
        aliasNome = findViewById(R.id.editAlterarNomeCampanha);
        aliasInicio = findViewById(R.id.editAlterarInicioCampanha);
        aliasTermino = findViewById(R.id. editAlterarTerminoCampanha);
        aliasPublico = findViewById(R.id.editAlterarPublicoCampanha);
        aliasAlterar = findViewById(R.id.btnAlterarDadosCampanha);
    }

    //Recupera os dados
    private void recuperarDados() {
        DatabaseReference vacinaRef = databaseReference
                .child("ADMIN-CAMPANHAS")
                //.child( idUsuarioLogado )
                .child(campanhaIdAdmin);
        vacinaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Campanha campanha = dataSnapshot.getValue(Campanha.class);
                    aliasNome.setText(campanha.getTitulo());
                    aliasInicio.setText(campanha.getDataInicio());
                    aliasTermino.setText(campanha.getDataTermino());
                    aliasPublico.setText(campanha.getPublicoAlvo());

                    //Toast.makeText(AlterarCampanhasAdminActivity.this, "Vacina seleciondada: " + campanhaIdAdmin, Toast.LENGTH_SHORT).show();
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
            String inicio = aliasInicio.getText().toString();
            String termino = aliasTermino.getText().toString();
            String publico = aliasPublico.getText().toString();

            if(!nome.isEmpty()){
                if(!inicio.isEmpty()){
                    if(!termino.isEmpty()){
                        if(!publico.isEmpty()){

                            //altera o nome
                            databaseReference.child("ADMIN-CAMPANHAS").child(campanhaIdAdmin).child("titulo").setValue(nome);
                                    //.child( idUsuarioLogado )
                            //altera a data de início
                            databaseReference.child("ADMIN-CAMPANHAS").child(campanhaIdAdmin).child("dataInicio").setValue(inicio);
                                    //.child( idUsuarioLogado )
                            //altera a data de término
                            databaseReference.child("ADMIN-CAMPANHAS").child(campanhaIdAdmin).child("dataTermino").setValue(termino);
                                    //.child( idUsuarioLogado )
                            //altera o público
                            databaseReference.child("ADMIN-CAMPANHAS").child(campanhaIdAdmin).child("publicoAlvo").setValue(publico);
                                    //.child( idUsuarioLogado )

                            exibirMensagem("Campanha de vacinação alterada com sucesso");
                            finish();

                        }else{
                            exibirMensagem("Preencha o campo com público alvo da campanha de vacinação!");
                        }
                    }else{
                        exibirMensagem("Preencha o campo com a data de término da campanha de vacinação!");
                    }
                }else{
                    exibirMensagem("Preencha o campo com a data de início da campanha de vacinação!");
                }
            }else{
                exibirMensagem("Preencha o campo com o nome da campanha de vacinação!");
            }

    }

    public void exibirMensagem(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }
}
