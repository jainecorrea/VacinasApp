package com.example.jaine.vacinacaoapp.activity.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    public EditText campoEmail, campoSenha;
    public Button botaoLogin;
    public ProgressBar progressBar;

    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Esconde a ActionBar
        getSupportActionBar().hide();
        //Verifica se o usuário já está logado
        verificarUsuarioLogado();
        //Inicializa componentes
        inicializarComponentes();

        //Fazer login do usuário
        progressBar.setVisibility(View.GONE); //Oculto a progressBar
        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if(!textoEmail.isEmpty()){
                    if(!textoSenha.isEmpty()){

                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        validarLogin(usuario);

                    }else{
                        Toast.makeText(LoginActivity.this, "Preencha o campo com a senha", Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(LoginActivity.this, "Preencha o campo com o email", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Verifica se o usuário já está logado
    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), OpcoesActivity.class));
            finish();
        }
    }

    public void validarLogin(Usuario usuario){

        progressBar.setVisibility(View.VISIBLE); //Torno visível a progressBar
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //Caso tudo ocorra bem ao fazer o login do usuário
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), OpcoesActivity.class));
                    finish();
                }else{

                    progressBar.setVisibility(View.GONE);
                    String erroExcecao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "E-mail e senha não correspondem a um usuário cadastrado";
                    }catch (FirebaseAuthInvalidUserException e ) {
                        erroExcecao = "Usuário não está cadastrado";
                    }catch (Exception e) {
                        erroExcecao = "Erro ao logar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, erroExcecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Chama a activity de cadastro a partir do textView
    public void abrirCadastro(View view){
        Intent cadastro = new Intent(this, CadastroActivity.class);
        startActivity(cadastro);
    }

    //Chama a activity de menu_listagem
    //public void verificaLogin(View view) {
      //  Intent menu = new Intent(this, OpcoesActivity.class);
        //startActivity(menu);
        //finish();
    //}

    public void inicializarComponentes()
    {
        botaoLogin = (Button)findViewById(R.id.buttonLogin);
        campoEmail = (EditText)findViewById(R.id.editLoginEmail);
        campoSenha = (EditText)findViewById(R.id.editLoginSenha);
        progressBar = (ProgressBar)findViewById(R.id.progressLogin);
    }
}
