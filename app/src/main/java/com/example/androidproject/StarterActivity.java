package com.example.androidproject;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.androidproject.activities.CategoriesListFragment;
import com.example.androidproject.activities.ProductsListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class StarterActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private  ImageView imageView;
    private TextView user_name;
    private TextView user_email;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);



        //FirebaseApp.initializeApp(this);
        mFirebaseAuth=FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        imageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.user_image);
        user_email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_email);
        user_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.app_name,R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




        fillUserData();
        //dataFill("https://www.easemytrip.com/travel/img/metro-library-2.jpg");

    }


    public void fillUserData(){
        Bundle extras = getIntent().getExtras();
        if(extras.getString("user_email") != null){

            String userEmail = extras.getString("user_email");
            user_email.setText(userEmail);
            user_name.setText("firebase user");
            if(extras.getString("user_name") != null ){
                String userName = extras.getString("user_name");
                Log.d("extras",extras.getString("user_name"));
                user_name.setText(userName);
                if(getIntent().getByteArrayExtra("user_image") != null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(
                            getIntent().getByteArrayExtra("user_image"), 0, getIntent().getByteArrayExtra("user_image").length);
                    imageView.setImageBitmap(bitmap);
                }
            }

        }

    }


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    public void dataFill(String link){
        final String linkImage = link;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    loadImage();
                }catch(IOException e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void loadImage() throws MalformedURLException, IOException{
        URL uri = new URL("https://www.easemytrip.com/travel/img/metro-library-2.jpg");
        final Bitmap bmp =BitmapFactory.decodeStream(uri.openStream());
        Log.d("tag 1",""+bmp.toString());
        imageView.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bmp);
            }
        });

    }


    public void deconnect(){

        mFirebaseAuth.signOut();
        Repo.connected = false;
    }
    private void init(){
        HomeFragment fragment = new HomeFragment();
        doFragmentTransaction(fragment,"HomeFragment",false,"");
    }
    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment,tag);

        if(addToBackStack){

            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
            doFragmentTransaction(new UserFragment(),"user fragment",true,"passed");
            // Handle the camera action
        } else if (id == R.id.nav_modifier) {
            doFragmentTransaction(new HomeFragment(),"Home fragment",true,"passed");
        } else if (id == R.id.nav_deconnexion) {
            SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
            prefs.edit().clear();
            deconnect();
            Intent mainIntent = new Intent(getApplicationContext(),BottomActivity.class);
            startActivity(mainIntent);
        } else if (id == R.id.nav_publications) {

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    doFragmentTransaction(new HomeFragment(),"Home fragment",true,"passed");
                    return true;
                case R.id.navigation_categories:
                    Repo.loadData();
                    doFragmentTransaction(new CategoriesListFragment(),"Categories Fragment",true,"passed");
                    return true;
                case R.id.navigation_contact:
                    doFragmentTransaction(new ContactFragment(),"Contact fragment",true,"passed");
                    return true;
                case R.id.navigation_products:
                    Repo.loadData();
                    doFragmentTransaction(new ProductsListFragment(null),"all products",true,"passed");
                    return true;

            }
            return false;
        }
    };





}
