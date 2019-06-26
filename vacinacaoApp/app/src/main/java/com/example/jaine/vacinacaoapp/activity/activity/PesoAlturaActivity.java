package com.example.jaine.vacinacaoapp.activity.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.adapter.AdapterPesoAltura;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.helper.DataUtil;
import com.example.jaine.vacinacaoapp.activity.modelo.Acompanhamento;
import com.example.jaine.vacinacaoapp.activity.modelo.Crianca2;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.List;

public class PesoAlturaActivity extends AppCompatActivity {

    private MaterialCalendarView calendario;
    private RecyclerView recyclerListaPesoAltura;
    private AdapterPesoAltura adapterPesoAltura;
    private Toolbar toolbar;

    public ValueEventListener valueEventListenerAcompanhamento;
    private List<Acompanhamento> acompanhamentos = new ArrayList<>();
    private DatabaseReference databaseReference;
    public static String cadernetaId;
    public static String acompanhamentoId;
    public String valor;
    private Acompanhamento acompanhamentoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso_altura);

        //Inicializar componentes
        inicializarComponentes();
        configuraCalendario();
        getSupportActionBar().setElevation(0); //Tira a sombra
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase().child("USUARIO-ACOMPANHAMENTO").child( cadernetaId );

        //RecyclerView
        adapterPesoAltura = new AdapterPesoAltura(acompanhamentos, onClickListener());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerListaPesoAltura.setLayoutManager(layoutManager);
        recyclerListaPesoAltura.setHasFixedSize(true);
        recyclerListaPesoAltura.setAdapter(adapterPesoAltura);
    }

    public void carregadados(String mesAno){

        valueEventListenerAcompanhamento = databaseReference.child(mesAno).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                acompanhamentos.clear();

                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Acompanhamento acompanhamento = new Acompanhamento();
                    acompanhamento.setValor(dados.child("valor").getValue(Double.class));
                    acompanhamento.setTipo(dados.child("tipo").getValue(String.class));
                    acompanhamento.setIdAcompanhamento(dados.getKey());
                    acompanhamentos.add( acompanhamento );
                }
                adapterPesoAltura.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void adicionarPeso(View view){
        Intent peso = new Intent(this, NovoPesoActivity.class);
        startActivity(peso);
    }

    public void adicionarAltura(View view){
        Intent altura = new Intent(this, NovaAlturaActivity.class);
        startActivity(altura);
    }

    public void configuraCalendario(){

        //Configura valores para os meses
        CharSequence meses[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        calendario.setTitleMonths(meses);

        CalendarDay dataAtual = calendario.getCurrentDate();
        String mesSelecionado = String.format("%02d", (dataAtual.getMonth() + 1)); //Configuração para o mês ficar com 2 digitos
        //mesAnoSelecionado = String.valueOf( mesSelecionado + "" + dataAtual.getYear()); //Recupera o ano e o mês selecionado
        valor = mesSelecionado+""+dataAtual.getYear();

        calendario.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
                String mesSelecionado = String.format("%02d", (calendarDay.getMonth() + 1));
                //mesAnoSelecionado = String.valueOf( mesSelecionado + "" + calendarDay.getYear());
                valor = mesSelecionado+""+calendarDay.getYear();

                databaseReference.removeEventListener( valueEventListenerAcompanhamento );
                carregadados(valor);
            }
        });
    }

    private void inicializarComponentes(){
        toolbar = findViewById(R.id.toolbar);
        calendario = findViewById(R.id.calendarView);
        recyclerListaPesoAltura = findViewById(R.id.recyclerListaPesoAltura);
    }

   @Override
    protected void onStart() {
        super.onStart();
       carregadados(valor);
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener( valueEventListenerAcompanhamento );
    }

    public AdapterPesoAltura.ListaOnClickListener onClickListener(){
        return new AdapterPesoAltura.ListaOnClickListener() {
            @Override
            public void onClickDelete(int position) {
                excluirAcompanhamento(position);
            }
        };
    }

    private void excluirAcompanhamento(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir");
        builder.setMessage("Tem certeza que deseja excluir?");
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                acompanhamentoSelecionado = acompanhamentos.get(position);
                databaseReference.child(valor).child(acompanhamentoSelecionado.getIdAcompanhamento()).removeValue();
                Toast.makeText(PesoAlturaActivity.this, acompanhamentoSelecionado.getTipo() + " excluído(a) com sucesso", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
