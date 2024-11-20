package com.example.cos209finalprojectfurnivibe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cos209finalprojectfurnivibe.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private int numberOrder = 1;
    private CartTB cartTB;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imageView = binding.imageView;

        cartTB = new CartTB(this);

        getBundles();
    }

    private void getBundles() {
        String title = getIntent().getStringExtra("title");
        double price = getIntent().getDoubleExtra("price", 0.0);
        double rating = getIntent().getDoubleExtra("rating", 0.0);
        int imageUrl = getIntent().getIntExtra("imageResourceId", 0);
        String description = getIntent().getStringExtra("description");

        binding.txtTitle.setText(title);
        binding.txtPrice.setText("$" + price);
        binding.ratingBar.setRating((float) rating);
        binding.txtRating.setText(rating + " Rating");
        binding.txtDesc.setText(description);

            RecommendInfo object = new RecommendInfo(title, price, rating, imageUrl, description);

            Glide.with(this)
                    .load(imageUrl)
                    .into(binding.imageView);
            binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (object != null) {
                        object.setNumberInCart(numberOrder);
                        cartTB.addItem(object);
                    } else {
                        Toast.makeText(DetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}