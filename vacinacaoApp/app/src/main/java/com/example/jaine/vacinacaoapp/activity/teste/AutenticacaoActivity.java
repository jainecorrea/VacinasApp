package com.example.jaine.vacinacaoapp.activity.teste;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.activity.OpcoesActivity;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class AutenticacaoActivity extends AppCompatActivity {

    private EditText aliasEmail, aliasSenha;
    private Button aliasEntrar;
    private Switch tipoAcesso, tipoUsuario;
    private LinearLayout linearTipoUsuario;
    private FirebaseAuth autenticacao;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);

        inicializarComponentes();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        tipoAcesso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){ //empresa -- administrador
                    linearTipoUsuario.setVisibility(View.VISIBLE);
                }else{ //usuário
                    linearTipoUsuario.setVisibility(View.GONE);
                }
            }
        });

        //Verificar usuário logado
        verificarUsuarioLogado();

        progressBar.setVisibility(View.GONE);
        aliasEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = aliasEmail.getText().toString();
                String senha = aliasSenha.getText().toString();

                if(!email.isEmpty()){
                    if(!senha.isEmpty()){

                        //Verifica o estado do switch
                        if(tipoAcesso.isChecked()){ //Cadastro
                            progressBar.setVisibility(View.VISIBLE); //Torno visível a progressBar
                            autenticacao.createUserWithEmailAndPassword(
                                    email, senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(AutenticacaoActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                                        String tipoUsuario = getTipoUsuario();
                                        UsuarioFirebase.atualizarTipoUsuario(tipoUsuario);
                                        abrirTelaPrincipal(tipoUsuario);

                                    }else{
                                        progressBar.setVisibility(View.GONE);
                                        String erroExcecao = "";
                                        try{
                                            throw task.getException();
                                        }catch (FirebaseAuthWeakPasswordException e){
                                            erroExcecao = "Digite uma senha mais forte";
                                        }catch (FirebaseAuthInvalidCredentialsException e) {
                                            erroExcecao = "Por favor, digite um e-mail válido";
                                        }catch (FirebaseAuthUserCollisionException e ) {
                                            erroExcecao = "Esta conta já foi cadastrada";
                                        }catch (Exception e) {
                                            erroExcecao = "Erro ao cadastrar usuário: " + e.getMessage();
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(AutenticacaoActivity.this, erroExcecao, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else{ //Login
                            progressBar.setVisibility(View.VISIBLE); //Torno visível a progressBar
                            autenticacao.signInWithEmailAndPassword(
                                    email, senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if(task.isSuccessful()){
                                        Toast.makeText(AutenticacaoActivity.this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                                        String tipoUsuario = task.getResult().getUser().getDisplayName();
                                        abrirTelaPrincipal(tipoUsuario);
                                    }else{
                                    progressBar.setVisibility(View.GONE);
                                        Toast.makeText(AutenticacaoActivity.this, "Erro ao fazer login: "+task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }else{
                        Toast.makeText(AutenticacaoActivity.this, "Preencha o campo com a senha!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AutenticacaoActivity.this, "Preencha o campo com o e-mail!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Verifica se usuário logado é um admistrador ou um usuário
    private void verificarUsuarioLogado(){
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if(usuarioAtual != null){
            String tipoUsuario = usuarioAtual.getDisplayName();
            abrirTelaPrincipal(tipoUsuario);
        }
    }

    //Retorna o tipo do usuário
    private String getTipoUsuario(){
        //Se estiver configurado, retorno o tipo A, que é um administrador. Senão retorna o tipo U, que é um usuário
        return tipoUsuario.isChecked() ?  "A" : "U";
    }

    private void abrirTelaPrincipal(String tipoUsuario){
        if(tipoUsuario.equals("A")){ //Admin
            limpar();
            finish();
            startActivity(new Intent(getApplicationContext(), MenuAdminActivity.class));
        }else{ //usuário
            limpar();
            finish();
            startActivity(new Intent(getApplicationContext(), OpcoesActivity.class));
        }
    }

    private void limpar(){
        aliasEmail.setText("");
        aliasSenha.setText("");
    }

    private void inicializarComponentes(){
        aliasEmail = findViewById(R.id.editNovoEmailCadastro);
        aliasSenha = findViewById(R.id.editNovoSenhaCadastro);
        aliasEntrar = findViewById(R.id.btnNovoEntrar);
        tipoAcesso = findViewById(R.id.tipoAcesso);
        tipoUsuario = findViewById(R.id.tipoUsuario);
        progressBar = findViewById(R.id.progressAutenticacao);
        linearTipoUsuario = findViewById(R.id.linearTipoUsuario);
    }
}
