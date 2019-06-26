package com.example.jaine.vacinacaoapp.activity.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.fragment.DatePickerFragment;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.modelo.Crianca;
import com.example.jaine.vacinacaoapp.activity.modelo.Crianca2;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlterarDadosActivity extends AppCompatActivity{

    public EditText aliasAlterarDataNascCrianca, aliasAlterarNomeCrianca, aliasAlterarSexoCrianca;
    public ProgressBar progressBar;
    public ImageView imagemAlterar;
    public Button btnAlterarDadosCrianca;
    public ImageButton aliasGaleria;
    private static final int SELECAO_GALERIA = 200;

    public static String cadernetaId;
    public DatabaseReference databaseReference;
    private List<Crianca2> criancas = new ArrayList<Crianca2>();
    private StorageReference storageReference;
    private String idUsuarioLogado;
    private Crianca2 crianca;
    private Uri imageUri;
    private String downloadImageUrl;
    private ProgressDialog progresDialog;

    private String armazenaFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_dados);

        //Configurações iniciais
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        storageReference = ConfiguracaoFirebase.getFirebaseStorage();
        idUsuarioLogado = UsuarioFirebase.getIdUsuario();

        //Inicializar componentes
        inicializarComponentes();

        //Adicionando eventos de click
        aliasGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });

        btnAlterarDadosCrianca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storageInformation();
            }
        });

        recuperarDados();
    }

    private void abrirGaleria(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Verifica se o usuário conseguiu abrir a galeria
        if(i.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(i, SELECAO_GALERIA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECAO_GALERIA && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            imagemAlterar.setImageURI(imageUri);
        }
    }

    private void storageInformation(){

        progresDialog.setTitle("Alterando foto");
        progresDialog.setMessage("Aguarde a foto está sendo salva no banco de dados.");
        progresDialog.setCanceledOnTouchOutside(false);
        progresDialog.show();

        final StorageReference filePath = storageReference.child("imagens").child(imageUri.getLastPathSegment()+ ".jpeg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AlterarDadosActivity.this, "Erro: " + message, Toast.LENGTH_SHORT).show();
                progresDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AlterarDadosActivity.this, "Sucesso ao fazer upload da imagem", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AlterarDadosActivity.this, "Imagem salva com sucesso", Toast.LENGTH_SHORT).show();
                            alterarFoto();
                        }
                    }
                });
            }
        });
    }

    private void alterarFoto(){
        databaseReference.child("USUARIO-CRIANCAS").child(idUsuarioLogado).child(cadernetaId).child("urlImagemCrianca").setValue(downloadImageUrl);
        progresDialog.dismiss();
        finish();
    }

    private void recuperarDados(){
        DatabaseReference criancaRef = databaseReference
                .child("USUARIO-CRIANCAS")
                .child( idUsuarioLogado )
                .child( cadernetaId );
        criancaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    crianca = dataSnapshot.getValue(Crianca2.class);
                    aliasAlterarDataNascCrianca.setText(crianca.getDataNascCrianca());
                    aliasAlterarDataNascCrianca.setFocusable(false);
                    aliasAlterarNomeCrianca.setText(crianca.getNomeCrianca());
                    aliasAlterarNomeCrianca.setFocusable(false);
                    aliasAlterarSexoCrianca.setText(crianca.getSexoCrianca());
                    aliasAlterarSexoCrianca.setFocusable(false);

                    armazenaFoto = crianca.getUrlImagemCrianca();
                    Picasso.with(getApplicationContext())
                            .load(armazenaFoto)
                            .placeholder(R.drawable.imagem_padrao)
                            .fit()
                            .into(imagemAlterar);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarComponentes(){
        aliasAlterarNomeCrianca = findViewById(R.id.editAlterarNomeCrianca);
        aliasAlterarSexoCrianca = findViewById(R.id.editAlterarSexoCrianca);
        btnAlterarDadosCrianca = findViewById(R.id.btnAlterarDadosCrianca);
        aliasAlterarDataNascCrianca = findViewById(R.id.editAlterarDataNasc);
        aliasGaleria = findViewById(R.id.imageButtonAlterarGaleria);
        imagemAlterar = findViewById(R.id.imagemAlterar);
        progresDialog = new ProgressDialog(this);
    }
}
