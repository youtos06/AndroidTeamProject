package com.example.androidproject.activities;

import androidx.lifecycle.ViewModelProviders;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.models.com.Product;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDetailsFragment extends Fragment {
    private Product product;
    @BindView(R.id.details_productName)
    TextView productName;
    @BindView(R.id.details_productDescription)
    TextView productDescription;
    @BindView(R.id.details_nbrOfVotes)
    TextView nbrOfVotes;
    @BindView(R.id.details_product_Image)
    ImageView imageView;
    @BindView(R.id.details_productRating)
    RatingBar ratingBar;
    @BindView(R.id.details_fav_icon)
     ImageView favoriteIcon;
     ProductDetailsFragment(Product product) {
       this.product = product;
    }
    @OnClick(R.id.details_fav_icon)
    void toggleFavStatus(){
         product.setFav(!product.isFav());
         showFavIcon();}

    private void showFavIcon() {
        if (product.isFav())
            favoriteIcon.setImageResource(R.drawable.ic_favorite_black_24dp);
        else favoriteIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_product_details, container, false);
        ButterKnife.bind(this,view);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                product.setRating((ratingBar.getRating() + product.getRating()) / 2);
            }
        });
        return view;
    }
    private void showProduct() {
       nbrOfVotes.setText(Integer.toString(product.getNbrOfVotes()));
        productName.setText(product.getName());
        productDescription.setText(product.getDescription());

        if (product.getbImage() !=null)
            this.imageView.setImageBitmap(product.getbImage());
        else Picasso.get().load(product.getImageUri()).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(this.imageView);
        ratingBar.setRating(product.getRating());
        showFavIcon();
    }

    @Override
    public void onResume() {
        super.onResume();
        showProduct();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
