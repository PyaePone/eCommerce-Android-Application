package com.example.cos209finalprojectfurnivibe;

import android.graphics.drawable.Drawable;

public class SliderItems {
    private Drawable image;

    public SliderItems(){

    }

    public SliderItems(Drawable image) {
        this.image = image;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
