package com.example.androidproject.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.androidproject.R;
import com.example.androidproject.Repo;
import com.example.androidproject.models.com.Product;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ajouter_article extends Fragment {

    @BindView(R.id.frag_ajouterArticle_designation)
    TextView designation;

    @BindView(R.id.frag_ajouterArticle_description)
    TextView description;

    @BindView(R.id.frag_ajouterArticle_imgArticle)
    ImageView imgCategorie;

    @BindView(R.id.frag_ajouterArticle_catSpinner)
    Spinner catSpinner;

    @BindView(R.id.frag_ajouterArticle_rating)
    RatingBar articleRating;
    Bitmap imageTosend= null;
    public static final String TAG="ajouterArticleegorie";
    public int pickImgRequestCode=1001;
    private String[] catsArray;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        catsArray = new String[Repo.categories.size()];
        for (int i = 0; i < Repo.categories.size(); i++) {
            catsArray[i] = Repo.categories.get(i).getName();
        }
        View view= inflater.inflate(R.layout.fragment_ajouter_article, container, false);
        ButterKnife.bind(this,view);
        //fkin spinner stuffs
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,catsArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catSpinner.setAdapter(arrayAdapter);
        return view;
    }

    public void pickImg(){
        Intent PickImg= new Intent(Intent.ACTION_PICK);
        PickImg.setType("image/*");
        startActivityForResult(PickImg,pickImgRequestCode);
    }

    @OnClick(R.id.frag_ajouterArticle_imgArticle)
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
    @OnClick(R.id.frag_ajouterArticle_ajouterArticleBtn)
    void ajouterArticle(){
        if (designation.getText().length()<2){
            designation.setError("doit contenir un minimum de 2 caractères");
        }
        else if (description.getText().length()<2){
            description.setError("doit contenir un minimum de 2 caractères");
        }
        else {
            Product product = new Product( designation.getText().toString(),
                    description.getText().toString(),
                    Repo.categories.get((int) catSpinner.getSelectedItemId()),
                    articleRating.getRating(),
                    imageTosend
            );
            Repo.products.add(0,product);
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProductsListFragment(null))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @OnClick(R.id.frag_ajouterArticle_goBack)
    public void goBack(){
        getActivity().onBackPressed();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pickImgRequestCode && resultCode==Activity.RESULT_OK){
            imgCategorie.setImageURI(data.getData());
            imageTosend=imgCategorie.getDrawingCache();
        }
    }
}

