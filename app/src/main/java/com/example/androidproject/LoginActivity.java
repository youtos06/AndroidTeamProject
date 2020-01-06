package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproject.R;
import com.example.androidproject.StarterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

@RequiresApi(api = Build.VERSION_CODES.M)

public class LoginActivity extends AppCompatActivity {
    EditText emailId ,password;
    ProgressBar progressBar;
    Button btnSignIn;
    TextView TxtRegister;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //FirebaseApp.initializeApp(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId=findViewById(R.id.editText);
        password=findViewById(R.id.editText2);
        btnSignIn =findViewById(R.id.bouton);
        TxtRegister=findViewById(R.id.textView4);
        progressBar=findViewById(R.id.progressBar);



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd= password.getText().toString();

                if(email.isEmpty()) {
                    emailId.setError("entrer votre email");
                    emailId.requestFocus();

                }
                if (pwd.isEmpty()){
                    password.setError("entrer votre password");
                    password.requestFocus();

                }
                if (email.isEmpty()& pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this,"champs vides!",Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.VISIBLE);


                mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Vous étes connectés",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), StarterActivity.class);
                            intent.putExtra("user_email",emailId.getText().toString());
                            intent.putExtra("user_name",mFirebaseAuth.getCurrentUser().getDisplayName());
                            //SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                            //prefs.edit().putBoolean("connected",true);
                            Repo.connected = true;
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Erreur" +task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                });







            }

        });





        TxtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });


    }


}



