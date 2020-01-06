package com.example.androidproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {
    EditText Prenom ,Nom,Email,Password,Telephone;
    Button RegsiterBtn;
    TextView LoginBtn;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;
    ImageView cameraIcon;
    CheckBox check;
    Bitmap imageTemp = null;
    public int pickImgRequestCode=1001;

    public void pickImg(){
        Intent PickImg= new Intent(Intent.ACTION_PICK);
        PickImg.setType("image/*");
        startActivityForResult(PickImg,pickImgRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pickImgRequestCode && resultCode== Activity.RESULT_OK){
            cameraIcon.setImageURI(data.getData());

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Nom = findViewById(R.id.Nom);
        Prenom= findViewById(R.id.Prénom);
        Email = findViewById(R.id.Email);
        Password= findViewById(R.id.password);
        Telephone=findViewById(R.id.Tel);
        RegsiterBtn=findViewById(R.id.Registerbtn);
        LoginBtn=findViewById(R.id.TxtSign);
        progressBar=findViewById(R.id.progressBar);
        cameraIcon=findViewById(R.id.cameraRegister);
        check=findViewById(R.id.checkbox);

        FirebaseApp.initializeApp(this);
        mFirebaseAuth=FirebaseAuth.getInstance();



        if(mFirebaseAuth.getCurrentUser()!=null){

            Intent intent = new Intent(getApplicationContext(),StarterActivity.class);
            intent.putExtra("user_name",mFirebaseAuth.getCurrentUser().getDisplayName());
            intent.putExtra("user_email",mFirebaseAuth.getCurrentUser().getEmail());

            startActivity(intent);

            finish();

        }

        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                    }
                    //permission granted
                    pickImg();
                }
                //older sdk doesn't need runtime permission
                pickImg();


            }
        });




        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(RegisterActivity.this, "bienvenue a bord de JeTeSTe,on vous propose d'utiliser l'application librement.Prière de partager des articles qui ont atirés votre attention.Il est strictement interdit de partger des photos provocantes", Toast.LENGTH_SHORT).show();

            }
        });

        RegsiterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Email.setError("Email requis");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Password.setError("Mot de passe requis");
                    return;
                }
                if(password.length()<6){
                    Password.setError("\n" +
                            "Mot de passe doit posséder au moins 8 caractères");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Créer un compte utilisateur dans la FireBase
                mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Compte créée", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), StarterActivity.class);
                            intent.putExtra("user_name",Nom.getText().toString().trim()+" "+ Prenom.getText().toString().trim());
                            intent.putExtra("user_email", Email.getText().toString().trim());
                            Bitmap bitmap = cameraIcon.getDrawingCache(); // your bitmap
                            ByteArrayOutputStream bs = new ByteArrayOutputStream();
                            if(bs != null){
//                                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
  //                              intent.putExtra("byteArray", bs.toByteArray());
                            }

                            SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                            prefs.edit().putBoolean("connected",true);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }


        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}
