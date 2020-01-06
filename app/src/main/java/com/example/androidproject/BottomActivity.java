package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;

import com.example.androidproject.activities.CategoriesListFragment;
import com.example.androidproject.activities.ProductsListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BottomActivity extends AppCompatActivity {
    private TextView mTextMessage;




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_non_home:
                    doFragmentTransaction(new HomeFragment(),"Home fragment",true,"passed");
                    return true;
                case R.id.navigation_non_products:
                    Repo.loadData();
                    doFragmentTransaction(new ProductsListFragment(null),"all products",true,"passed");
                    return true;
                case R.id.navigation_non_categories:
                    Repo.loadData();
                    doFragmentTransaction(new CategoriesListFragment(),"Categories Fragment",true,"passed");
                    return true;


            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        BottomNavigationView navView = findViewById(R.id.nav_non_connected);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button btnConnect = (Button) findViewById(R.id.connect_redirect);
        btnConnect.setOnClickListener(onConnectListener());

        init();
    }

    private View.OnClickListener onConnectListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getApplicationContext(),LoginActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
            }
        };
    }




    private void init(){
        HomeFragment fragment = new HomeFragment();
        doFragmentTransaction(fragment,"HomeFragment",false,"");
    }
    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_non_connected_container,fragment,tag);

        if(addToBackStack){

            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

}
