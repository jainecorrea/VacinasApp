package com.example.jaine.vacinacaoapp.activity.teste;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DescricaoCampanhaAdminActivity extends AppCompatActivity {

    private TextView aliasNome, dataInicio, dataTermino, publicoAlvo;
    public DatabaseReference databaseReference;
    public static String campanhaIdAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao_campanha_admin);

        //Configurações iniciais
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        inicializarComponentes();

        recuperarDados();
    }

    //Inicializa os componentes
    private void inicializarComponentes(){
        aliasNome = findViewById(R.id.editNomeDescricaoAd);
        dataInicio = findViewById(R.id.editDataInicioDescricaoAd);
        dataTermino = findViewById(R.id.editTerminoDescricaoAd);
        publicoAlvo = findViewById(R.id. editPublicoDescricaoAd);
    }

    //Recupera os dados
    private void recuperarDados(){
        DatabaseReference campanhaRef = databaseReference
                .child("ADMIN-CAMPANHAS")
                .child( campanhaIdAdmin );
        campanhaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Campanha campanha = dataSnapshot.getValue(Campanha.class);
                    aliasNome.setText(campanha.getTitulo());
                    dataInicio.setText(campanha.getDataInicio());
                    dataTermino.setText(campanha.getDataTermino());
                    publicoAlvo.setText(campanha.getPublicoAlvo());

                    //Toast.makeText(DescricaoCampanhaAdminActivity.this, "Campanha seleciondada: " + campanhaIdAdmin, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
