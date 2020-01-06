package com.example.androidproject;

import android.net.Uri;


import com.example.androidproject.models.com.Category;
import com.example.androidproject.models.com.Product;

import java.util.ArrayList;
import java.util.Random;

public class Repo {
    public static boolean connected = false;
    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<Category> categories = new ArrayList<>();
    public static void loadData(){
        // from api
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            categories.add(new Category("category "+i,"category description", Uri.parse("https://picsum.photos/100/100")));
        }
        for (int i = 0; i <10 ; i++) {
            Product product = new Product("product "+i,"description",categories.get(random.nextInt(categories.size())),4.2f,Uri.parse("https://picsum.photos/100/100")
                    ,random.nextInt(100));
            products.add(product);
        }

    }
}

