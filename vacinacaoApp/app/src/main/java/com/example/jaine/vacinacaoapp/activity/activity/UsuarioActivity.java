package com.example.jaine.vacinacaoapp.activity.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jaine.vacinacaoapp.R;
import com.example.jaine.vacinacaoapp.activity.fragment.DatePickerFragment;
import com.example.jaine.vacinacaoapp.activity.helper.ConfiguracaoFirebase;
import com.example.jaine.vacinacaoapp.activity.helper.Permissao;
import com.example.jaine.vacinacaoapp.activity.modelo.Crianca;
import com.example.jaine.vacinacaoapp.activity.modelo.Crianca2;
import com.example.jaine.vacinacaoapp.activity.modelo.Usuario;
import com.example.jaine.vacinacaoapp.activity.modelo.UsuarioFirebase;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class UsuarioActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    //Permissões
    private String[] permissoesNecessarias = new String[]
            {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            };

    private ImageButton aliasGaleria;
    private static final int SELECAO_GALERIA = 200;
    public ImageView imagemAdd;

    private String[] menuS = new String[] {"Feminino", "Masculino"};
    private Spinner combobox;

    public EditText aliasDataNasc, aliasNomeCrianca;
    public Button btnAdicionarCrianca;
    public List<Crianca> criancas;
    public static String cadernetaId;
    public DatabaseReference databaseReference;

    private StorageReference mStorageRef;
    private String idUsuarioLogado;
    private Uri imageUri;
    private String downloadImageUrl;
    private String nome, dataNasc, sexo;

    private ProgressDialog progresDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        idUsuarioLogado = UsuarioFirebase.getIdUsuario();
        criancas = new ArrayList<Crianca>();
        databaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
        mStorageRef = ConfiguracaoFirebase.getFirebaseStorage();

        progresDialog = new ProgressDialog(this);

        //Validar permissões
        Permissao.validarPermissoes(permissoesNecessarias, this, 1);

        //Com isso podemos vizualizar o botão voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Spinner
        combobox = findViewById(R.id.spinnerSexo);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuS);
        combobox.setAdapter(adaptador);

        //Criando máscara para data
        /*aliasDataNasc = (EditText)findViewById(R.id.editCadastroDataNasc);
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(aliasDataNasc,smf);
        aliasDataNasc.addTextChangedListener(mtw);*/
        aliasDataNasc = findViewById(R.id.editCadastroDataNasc);
        aliasDataNasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), " date picker ");
            }
        });
        // Comando para o teclado virtual não aparecer
        aliasDataNasc.setInputType(InputType.TYPE_NULL);

        aliasNomeCrianca = findViewById(R.id.editCadastroNomeCrianca);
        btnAdicionarCrianca = findViewById(R.id.btnCadastroDadosCrianca);

        //Inicializo componentes
        aliasGaleria = findViewById(R.id.imageButtonCadastroCriancaGaleria);
        imagemAdd = findViewById(R.id.ImagemAdd);

        aliasGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //openFileChoose();
                abrirGaleria();
            }
        });

        //Salva no banco
        btnAdicionarCrianca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateCrianca();
            }
        });
    }

    private void validateCrianca(){
        nome = aliasNomeCrianca.getText().toString();
        dataNasc = aliasDataNasc.getText().toString();
        sexo = combobox.getSelectedItem().toString();

        if(imageUri == null){
            Toast.makeText(UsuarioActivity.this, "Escolha uma imagem!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(nome)){
            Toast.makeText(UsuarioActivity.this, "Digite o nome da criança!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(dataNasc)){
            Toast.makeText(UsuarioActivity.this, "Selecione uma data de nascimento para a criança!", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(sexo)){
            Toast.makeText(UsuarioActivity.this, "Selecione o sexo da criança!", Toast.LENGTH_SHORT).show();
        }
        else{
            storageInformation();
        }
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
            imagemAdd.setImageURI(imageUri);
        }
    }

    private void storageInformation(){

        progresDialog.setTitle("Nova caderneta");
        progresDialog.setMessage("Aguarde os dados estão sendo salvos no banco de dados.");
        progresDialog.setCanceledOnTouchOutside(false);
        progresDialog.show();

        final StorageReference filePath = mStorageRef.child("imagens").child(imageUri.getLastPathSegment()+ ".jpeg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(UsuarioActivity.this, "Erro: " + message, Toast.LENGTH_SHORT).show();
                progresDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UsuarioActivity.this, "Sucesso ao fazer upload da imagem", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(UsuarioActivity.this, "Imagem salva no banco de dados com sucesso", Toast.LENGTH_SHORT).show();
                            saveCriancaInfoToDataBase();
                        }
                    }
                });
            }
        });
    }

    private void saveCriancaInfoToDataBase(){

        Crianca2 crianca2 = new Crianca2();
        crianca2.setIdUsuario( idUsuarioLogado );
        crianca2.setUrlImagemCrianca( downloadImageUrl );
        crianca2.setNomeCrianca( nome );
        crianca2.setDataNascCrianca( dataNasc );
        crianca2.setSexoCrianca( sexo );
        crianca2.salvar();
        progresDialog.dismiss();
        finish();
    }

    //Caso o usuário negue as permissões
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for( int permissaoResultado : grantResults)
        {
            if(permissaoResultado == PackageManager.PERMISSION_DENIED)
            {
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões.");
        builder.setCancelable(false); //Não será possível clicar em outra parte da tela para remover o alerta
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentSateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
        EditText aliasData = findViewById(R.id.editCadastroDataNasc);
        aliasData.setText(currentSateString);
    }
}
