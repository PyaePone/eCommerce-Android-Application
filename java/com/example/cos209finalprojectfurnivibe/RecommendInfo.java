package com.example.cos209finalprojectfurnivibe;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;

public class RecommendInfo implements Serializable {
    private String title;
    private String description;
    private ArrayList<Drawable> picUrl;
    private String imageUrl;
    private double price;
    private double oldPrice;
    private int review;
    private double rating;
    private int NumberInCart;
    private Drawable image;
    private String location;
    private int url;
    private int id;

    public RecommendInfo(String title, double price, double rating, int imageUrl, String description) {
        this.title = title;
        this.price = price;
        this.rating = rating;
        this.url = imageUrl;
        this.description = description;

    }

    public RecommendInfo() {

    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public RecommendInfo(Drawable recommendImage, int imageResourceId, String recommendItem, String descriptions, int recommendReview, double recommendPrice, double recommendRating, double recommendOldPrice) {
        this.image = recommendImage;
        this.url = imageResourceId;
        this.description = descriptions;
        this.title = recommendItem;
        this.review = recommendReview;
        this.price = recommendPrice;
        this.rating = recommendRating;
        this.oldPrice = recommendOldPrice;
    }

    public RecommendInfo(int imageResourceId, String recommendItem, String descriptions, int recommendReview, double recommendPrice, double recommendRating, double recommendOldPrice) {
        this.url = imageResourceId;
        this.description = descriptions;
        this.title = recommendItem;
        this.review = recommendReview;
        this.price = recommendPrice;
        this.rating = recommendRating;
        this.oldPrice = recommendOldPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Drawable> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(ArrayList<Drawable> image) {
        this.picUrl = picUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumberInCart() {
        return NumberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        NumberInCart = numberInCart;
    }

    public String getLocation() {return location;}

    public void setLocation(String location) {this.location = location;}
}
