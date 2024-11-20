package com.example.cos209finalprojectfurnivibe;

import android.graphics.drawable.Drawable;

public class CategoryInfo {
    private String title;
    private int id;
    private String picUrl;
    private Drawable image;

    public CategoryInfo(String title, int id, String picUrl) {
        this.title = title;
        this.id = id;
        this.picUrl = picUrl;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public CategoryInfo(Drawable image, String categoryItem){
        this.image = image;
        this.title = categoryItem;
    }

    public CategoryInfo(){}
    public CategoryInfo(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
