package com.example.androidproject.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.example.androidproject.R;
import com.example.androidproject.Repo;
import com.example.androidproject.models.com.Category;

import java.util.Objects;


public class ajouter_categorie extends Fragment {

    @BindView(R.id.frag_ajouterCat_designation)
    TextView designation;

    @BindView(R.id.frag_ajouterCat_description)
    TextView description;

    @BindView(R.id.frag_ajouterCat_imgCat)
    ImageView imgCategorie;

    public static final String TAG="AjouterCategorie";
    public int pickImgRequestCode=1001;
    Bitmap imageTosend= null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_ajouter_categorie, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    public void pickImg(){
        Intent PickImg= new Intent(Intent.ACTION_PICK);
        PickImg.setType("image/*");
        startActivityForResult(PickImg,pickImgRequestCode);
    }

    @OnClick(R.id.frag_ajouterCat_imgCat)
    void importerImg(){
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
            }
            //permission granted
            pickImg();
        }
        //older sdk doesn't need runtime permission
        pickImg();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.frag_ajouterCat_ajouterCatBtn)
    void ajouterCat(){
        if (designation.getText().length()<2){
            designation.setError("doit contenir un minimum de 2 caractères");
        }
        else if (description.getText().length()<2){
            description.setError("doit contenir un minimum de 2 caractères");
        }
        else {
            Bitmap bitmap = imageTosend;
            Repo.categories.add(0,new Category( designation.getText().toString(), description.getText().toString(),bitmap));
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_non_connected_container,new CategoriesListFragment())
                    .addToBackStack(null)
                    .commit();

        }
    }

    @OnClick(R.id.frag_ajouterCat_goBack)
    public void goBack(){
        getActivity().onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("image_var","inside");
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pickImgRequestCode && resultCode==Activity.RESULT_OK){
            imgCategorie.setImageURI(data.getData());
            imageTosend=imgCategorie.getDrawingCache();
        }
    }
}