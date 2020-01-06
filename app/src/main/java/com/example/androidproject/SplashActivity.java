package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //FirebaseApp.initializeApp(this);
        mFirebaseAuth=FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!isNetworkAvailable(this)) {
            Toast.makeText(this,"No Internet connection",Toast.LENGTH_LONG).show();
            finish(); //Calling this method to close this activity when internet is not available.
        }else{
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {

                    /* Create an Intent that will start the Menu-Activity. */
                    connected();

                }
            }, SPLASH_DISPLAY_LENGTH);
        }


    }

    public void connected(){
        //mFirebaseAuth.signOut();

        if(mFirebaseAuth.getCurrentUser() != null){
            Intent mainIntent = new Intent(getApplicationContext(),StarterActivity.class);
            mainIntent.putExtra("user_email",mFirebaseAuth.getCurrentUser().getEmail());
            mainIntent.putExtra("user_name",mFirebaseAuth.getCurrentUser().getDisplayName());
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Repo.connected = true;
            startActivity(mainIntent);
        }else{
            Intent mainIntent = new Intent(getApplicationContext(),BottomActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
        }

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

}
