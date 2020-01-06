package com.example.androidproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.R;
import com.example.androidproject.models.com.Category;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class CategorieListAdapter extends RecyclerView.Adapter<CategorieListAdapter.CategoryViewHolder> {
    ArrayList<Category> categories;
    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.categorie_item,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.setCategory(category);
    }
    public void setOnItemClickListener(CategorieListAdapter.ViewHolderClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private CategorieListAdapter.ViewHolderClickListener onItemClickListener;
    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        ImageView categoryImage;
        Category category;
        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClickListener(category);
                }
            });
            name = itemView.findViewById(R.id.category_name);
            description = itemView.findViewById(R.id.category_description);
            categoryImage = itemView.findViewById(R.id.category_image);
        }

        void setCategory(Category category) {
            this.category = category;
            updateData();
        }
        void updateData(){
            this.name.setText(category.getName());
            this.description.setText(category.getDescription());
            if (category.getBitmap() !=null)
                this.categoryImage.setImageBitmap(category.getBitmap());
            else Picasso.get().load("https://picsum.photos/100/100").memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(this.categoryImage);
        }
    }
    public interface ViewHolderClickListener{
        void onItemClickListener(Category category);

    }
}


