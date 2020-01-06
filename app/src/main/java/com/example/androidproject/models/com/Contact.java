package com.example.androidproject.models.com;

public class Contact {
    private Integer imageId;
    private String fullName;
    private String email;

    public Contact(Integer imageId, String fullName, String email) {
        this.imageId = imageId;
        this.fullName = fullName;
        this.email = email;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
