package com.example.androidproject.models.com;

import android.graphics.Bitmap;
import android.net.Uri;

public class Category {
    String name;
    String description;
    Bitmap bitmap;
    Uri imageUri;

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Category(String name, String description, Bitmap bitmap) {
        this.name = name;
        this.description = description;
        this.bitmap = bitmap;
    }
    public Category(String name, String description, Uri imageUri) {
        this.name = name;
        this.description = description;
        this.imageUri = imageUri;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}