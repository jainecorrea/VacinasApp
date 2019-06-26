package com.example.jaine.vacinacaoapp.activity.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.adapter.AdapterCadernetas;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.modelo.Acompanhamento;
import com.example.jaine.vacinacaoapp.activity.modelo.CampanhasRealizadas;
import com.example.jaine.vacinacaoapp.activity.modelo.Crianca2;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.example.jaine.vacinacaoapp.activity.teste.AutenticacaoActivity;
import com.example.jaine.vacinacaoapp.activity.teste.InformacoesActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OpcoesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private AdapterCadernetas adapterCadernetas;
    private List<Crianca2> criancas = new ArrayList<Crianca2>();
    private List<CampanhasRealizadas> campanhasR = new ArrayList<CampanhasRealizadas>();
    private DatabaseReference databaseReference;

    private String idUsuarioLogado;
    public static String cadernetaId;
    public static String campanhaIdUsuario;

    private FirebaseAuth usuario;

    public static final int CONSTANTE = 1;

    //public FirebaseStorage mStorage;
    //public DatabaseReference databaseReference;
    //public ArrayList<Crianca> listaCrianca = new ArrayList<Crianca>();

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes);

        //Configurar RecyclerView
        progressBar = findViewById(R.id.progressExibirCadernetas);
        recyclerView = findViewById(R.id.recyclerCadernetas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapterCadernetas = new AdapterCadernetas(OpcoesActivity.this, criancas, onClickLista());
        recyclerView.setAdapter(adapterCadernetas);

        //Inicializa referência do FirebaseDatabase
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();

        usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //Recupera crianças
        recuperarCriancas();

        //Configuração da toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Configuração de ação do FAB*/
                Intent addCardeneta = new Intent(OpcoesActivity.this, UsuarioActivity.class);
                startActivity(addCardeneta);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    //Recupera crianças - Listagem de crianças do banco de dados
    private void recuperarCriancas(){
        progressBar.setVisibility(View.VISIBLE);
        final DatabaseReference criancasRef = databaseReference
                .child("USUARIO-CRIANCAS")
                .child( idUsuarioLogado );
        criancasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                criancas.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    criancas.add(ds.getValue(Crianca2.class));
                }
                adapterCadernetas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Eventos de click no CardView
    public AdapterCadernetas.ListaOnClickListener onClickLista()
    {
        return new AdapterCadernetas.ListaOnClickListener() {
            @Override
            public void onClickDelete(int position) {
                excluirCaderneta(position);
                /*Crianca2 cadernetaSelecionada = criancas.get(position);
                cadernetaSelecionada.remover();
                Toast.makeText(OpcoesActivity.this, "Caderneta excluída", Toast.LENGTH_SHORT).show();*/
            }
            @Override
            public void onClickEdit(View view, int idx) {
                //Crianca2 cadernetaSelecionada = criancas.get(idx);

                /*Bundle bundle = new Bundle();
                bundle.putString("id", cadernetaSelecionada.getIdCaderneta());
                //bundle.putString("id", String.valueOf(criancas.get(idx)));
                Intent intent = new Intent(OpcoesActivity.this, AlterarDadosActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, CONSTANTE);*/

                /*Bundle bundle = new Bundle();
                bundle.putString("nomeCliente", cliente.getNome(position));
                Intent intent = new Intent(context, MinhaOutraActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);*/

                Intent i = new Intent(OpcoesActivity.this, AlterarDadosActivity.class);
                startActivity(i);
            }
            @Override
            public void onClickList(View view, int idx) {
                //Intent i = new Intent(OpcoesActivity.this, ListaVacinaActivity.class);
                Intent i = new Intent(OpcoesActivity.this, AbasActivity.class);
                startActivity(i);
            }
            @Override
            public void onClickAcompanhamento(View view, int idx) {
                Intent i = new Intent(OpcoesActivity.this, PesoAlturaActivity.class);
                startActivity(i);
            }
        };
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sobre){
            Intent informacoes = new Intent(this, SobreVacinaActivity.class);
            startActivity(informacoes);
        }
        /*else if(id == R.id.calendarioBasico){
            Intent basico = new Intent(this, CalendarioBasicoActivity.class);
            startActivity(basico);
        }*/
        else if (id == R.id.campanhas){
            Intent campanhas = new Intent(this, CampanhasActivity.class);
            startActivity(campanhas);
        }
        else if (id == R.id.sobreApp) {
            Intent app = new Intent(this, MainActivity.class);
            startActivity(app);
        }
        else if (id == R.id.sair) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Sair");
            builder.setMessage("Tem certeza que deseja sair?");
            builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    usuario.signOut();
                    finish();
                    Intent intent = new Intent(OpcoesActivity.this, AutenticacaoActivity.class);
                    startActivity(intent);
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_usuario, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.SairDoApp){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Sair");
            builder.setMessage("Tem certeza que deseja sair?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    usuario.signOut();
                    finish();
                    //Intent intent = new Intent(OpcoesActivity.this, LoginActivity.class);
                    //startActivity(intent);
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else if (id == R.id.menuCampanha){
            Intent i = new Intent(OpcoesActivity.this, CampanhasActivity.class);
            startActivity(i);
        }
        return true;
    }*/

    //Excluindo informações da caderneta
    private void excluirCaderneta(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir");
        builder.setMessage("Todas as informações serão excluídas. Tem certeza que deseja excluir?");
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Crianca2 cadernetaSelecionada = criancas.get(position);
                DatabaseReference ac = databaseReference
                        .child("USUARIO-ACOMPANHAMENTO")
                        .child(cadernetaSelecionada.getIdCaderneta());
                ac.removeValue();
                DatabaseReference cR = databaseReference
                        .child("USUARIO-CAMPANHAS")
                        .child(idUsuarioLogado)
                        .child(cadernetaSelecionada.getIdCaderneta());
                cR.removeValue();
                DatabaseReference iR = databaseReference
                        .child("CONFIRMACAO-VACINAS-REALIZADAS")
                        .child(cadernetaSelecionada.getIdCaderneta());
                iR.removeValue();
                cadernetaSelecionada.remover();
                Toast.makeText(OpcoesActivity.this, "Caderneta excluída", Toast.LENGTH_SHORT).show();
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
