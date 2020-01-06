package com.example.androidproject.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.Repo;
import com.example.androidproject.activities.ProductDetailsFragment;
import com.example.androidproject.activities.ajouter_article;
import com.example.androidproject.adapters.ProductsListAdapter;
import com.example.androidproject.models.com.Category;
import com.example.androidproject.models.com.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductsListFragment extends Fragment  implements ProductsListAdapter.ViewHolderClickListener{

    @BindView(R.id.productList)
    public RecyclerView productsRv;
    @BindView(R.id.notFound)
    TextView notFound;
    @BindView(R.id.search)
    public SearchView searchView;
    @BindView(R.id.floatingActionButton4)
    FloatingActionButton addProductBtn;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.floatingActionButton4)
    public  void onclick(){

        //SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        //System.out.printf(""+prefs.getBoolean("connected",false));
        if(Repo.connected) {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ajouter_article())
                    .addToBackStack(null)
                    .commit();
        }else{
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_non_connected_container, new ajouter_article())
                    .addToBackStack(null)
                    .commit();

        }
    }
    ProductsListAdapter productsListAdapter;
    private Category category;
    public ProductsListFragment(Category category) {
        this.category = category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { super.onCreate(savedInstanceState);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onItemClickListener(Product product) {

       // SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);

        if(Repo.connected) {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,new ProductDetailsFragment(product))
                    .addToBackStack(null)
                    .commit();
        }else{
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_non_connected_container,new ProductDetailsFragment(product))
                    .addToBackStack(null)
                    .commit();

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.productslist_fragment, container, false);
        ButterKnife.bind(this,view);

       if (!Repo.connected){
          addProductBtn.hide(); }

        setUpProductsRv();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(final String s) {
                final ArrayList<Product> products = new ArrayList<>();
                productsListAdapter.products.forEach(new Consumer<Product>() {
                    @Override
                    public void accept(Product product) {
                        if (product.getName().contains(s)) products.add(product);
                    }
                });
                productsListAdapter.setProducts(products);
                productsListAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpProductsRv(){
        productsListAdapter = new ProductsListAdapter();
        final ArrayList<Product> products = new ArrayList<>();
        if (category !=null){
            Repo.products.forEach(new Consumer<Product>() {
                @Override
                public void accept(Product product) {
                    if (product.getCategory() == category){
                        products.add(product);
                    }
                }
            });
        }

        productsListAdapter.setProducts(category ==null ? Repo.products:products);
        if (products.isEmpty()) {
            notFound.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.INVISIBLE);
        }
        productsListAdapter.setOnItemClickListener(this);
        productsRv.setAdapter(productsListAdapter);
        productsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
