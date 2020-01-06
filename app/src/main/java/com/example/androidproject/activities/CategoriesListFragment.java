package com.example.androidproject.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.androidproject.R;
import com.example.androidproject.Repo;
import com.example.androidproject.adapters.CategorieListAdapter;
import com.example.androidproject.models.com.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CategoriesListFragment extends Fragment implements CategorieListAdapter.ViewHolderClickListener {
    @BindView(R.id.categoriesList)
    RecyclerView categoriesRv;
    @BindView(R.id.categoryBtn)
    FloatingActionButton categoryAddBtn;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.categoryBtn)
    void onclick(){
        //SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);

        if(Repo.connected){

            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,new ajouter_categorie())
                    .addToBackStack(null)
                    .commit();
        }else {

            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_non_connected_container,new ajouter_categorie())
                    .addToBackStack(null)
                    .commit();
        }


    }
    CategorieListAdapter categoriesListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_categories_list, container, false);
        ButterKnife.bind(this,view);
               if (!Repo.connected){
      categoryAddBtn.hide(); }
        setUpCategoriesRv();
        return view;
    }

    private void setUpCategoriesRv() {
        categoriesListAdapter = new CategorieListAdapter();
        categoriesListAdapter.setCategories(Repo.categories);
        categoriesListAdapter.setOnItemClickListener(this);
        categoriesRv.setAdapter(categoriesListAdapter);
        categoriesRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onItemClickListener(Category category) {

       // SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
       // System.out.println("uhuhuhyyhyhyhy"+prefs.getBoolean("connected",false));
       // Log.d("connected_item",""+prefs.getBoolean("connected",false));
        if(Repo.connected) {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProductsListFragment(category))
                    .addToBackStack(null)
                    .commit();
        }else{
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_non_connected_container, new ProductsListFragment(category))
                    .addToBackStack(null)
                    .commit();
        }

    }
}
