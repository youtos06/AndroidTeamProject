package com.example.androidproject.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.androidproject.R;
import com.example.androidproject.models.com.Product;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class ProductsListAdapter extends  Adapter<ProductsListAdapter.ProductViewHolder> {
     static int notFavIcon = R.drawable.ic_favorite_border_black_24dp;
    static int favIcon = R.drawable.ic_favorite_black_24dp;
    public   ArrayList<Product> products;

    public void setOnItemClickListener(ViewHolderClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private ViewHolderClickListener onItemClickListener;

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_item,parent,false);
         return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.setProduct(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    class ProductViewHolder extends ViewHolder {
        Product product;
        TextView name;
        TextView description;
        ImageView productImage;
        RatingBar ratingBar;
        ImageView favoriteIcon;
         ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClickListener(product);
                }
            });
            name = itemView.findViewById(R.id.productName);
            description = itemView.findViewById(R.id.description);
            productImage= itemView.findViewById(R.id.productImage);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            favoriteIcon = itemView.findViewById(R.id.favoriteIcon);
            favoriteIcon.setOnClickListener(onFavoriteChange());
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    product.setRating(ratingBar.getRating());
                }
            });
         }

        private View.OnClickListener onFavoriteChange() {
             return new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     product.setFav(!product.isFav());
                     updateFavStatus();
                 }
             };
        }

        void updateData(){
            this.name.setText(product.getName());
            this.description.setText(product.getDescription());
            if (product.getbImage() !=null)
                this.productImage.setImageBitmap(product.getbImage());
            else Picasso.get().load("https://picsum.photos/100/100").memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(this.productImage);
            updateFavStatus();
            ratingBar.setRating(product.getRating());

        }
        void updateFavStatus(){
            if (product.isFav()){
                this.favoriteIcon.setImageResource(favIcon);
            }else {
                this.favoriteIcon.setImageResource(notFavIcon);
            }
        }

        void setProduct(Product product) {
             this.product = product;
             updateData();
        }

    }
    public interface ViewHolderClickListener{
        void onItemClickListener(Product product);

    }
}
