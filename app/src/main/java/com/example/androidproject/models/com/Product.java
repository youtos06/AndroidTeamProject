package com.example.androidproject.models.com;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private long id;
    private String name;
    private Bitmap bImage;
    private Uri imageUri;
    private String description;
    private float rating;
    private int nbrOfVotes;

    public int getNbrOfVotes() {
        return nbrOfVotes;
    }

    public void setNbrOfVotes(int nbrOfVotes) {
        this.nbrOfVotes = nbrOfVotes;
    }

    private boolean isFav;
    private ArrayList<Comment> comments;
    Category category;

    public Bitmap getbImage() {
        return bImage;
    }

    public void setbImage(Bitmap bImage) {
        this.bImage = bImage;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Uri getImage() {
        return imageUri;
    }

    public void setImage(Uri image) {
        this.imageUri = image;
    }

    public Product(String name, String description,Category category,float rating, Uri uri,int nbrOfVotes) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.imageUri = uri;
        this.category = category;
        this.nbrOfVotes = nbrOfVotes;
    }
    public Product(String name,String description,Category category,float rating,Bitmap bitmap){
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.bImage = bitmap;
        this.category = category;
    }
    public Product(String name, String description, float rating, ArrayList<Comment> comments) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.comments = comments;
    }

    public Product( String name,String description, float rating,Uri image, boolean isFav) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.isFav = isFav;
        this.imageUri = image;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image=" + imageUri +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", isFav=" + isFav +
                ", comments=" + comments +
                '}';
    }
}
