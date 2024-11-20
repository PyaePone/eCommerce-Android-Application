package com.example.cos209finalprojectfurnivibe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.example.cos209finalprojectfurnivibe.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    SharedPreferences preferences;
    private CartTB cartTB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cartTB = new CartTB(this);
        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        String username = preferences.getString("username", null);
        if (username != null) {
            binding.txtuser.setText(username);
        }
        Banners();
        Categories();
        Recommendations();
        bottomNavigation();
    }

    private void bottomNavigation() {
        binding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
        binding.btncustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomizeActivity.class));
            }
        });
        binding.btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFeedbackdialog();
            }
        });
        binding.btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccountMenu(v);
            }
        });
    }

    private void Recommendations() {
            ArrayList<RecommendInfo> items = new ArrayList<>();
            ArrayList<RecommendInfo> items2 = new ArrayList<>();

            String[] recommendImageNames = getResources().getStringArray(R.array.image_names);
            String[] recommendItems = getResources().getStringArray(R.array.types);
            String[] descriptions = getResources().getStringArray(R.array.descriptions);
            int[] recommendReviews = {35, 13, 18, 16, 40, 50, 34, 12, 27, 45, 30, 16, 22, 8, 10, 23, 5, 14};
            double[] recommendOldPrices = {59.99, 30.99, 100.79, 46.99, 29.59, 62.59, 63.59, 75.49, 77.79, 70.99, 65, 59.49, 70, 36.79, 89.50, 97.49, 94.0, 56.49};
            double[] recommendRatings = {5, 4.9, 3.5, 3.9, 3, 3.2, 4.3, 4.4, 4.7, 3.7, 4.8, 4.4, 4.0, 4.6, 4.8, 4.9, 3.9, 4.5};
            double[] recommendPrices = {55.49, 29, 98, 44.99, 27, 60, 60.99, 71, 75.49, 68, 62.60, 55, 66.49, 35, 83, 96, 91, 55};
            if (recommendImageNames.length != recommendItems.length) {
                throw new RuntimeException("recommendImageNames and recommendItems arrays must have the same length");
            }
            for (int i = 0; i < recommendImageNames.length; i++)  {
                int imageResourceId = getResources().getIdentifier(recommendImageNames[i], "drawable", getPackageName());
                if (imageResourceId != 0) {
                    Drawable recommendImage = getResources().getDrawable(imageResourceId);
                    RecommendInfo recommendInfo = new RecommendInfo(recommendImage, imageResourceId, recommendItems[i], descriptions[i], recommendReviews[i], recommendPrices[i], recommendRatings[i], recommendOldPrices[i]);
                    items.add(recommendInfo);
                    RecommendInfo sInfo = new RecommendInfo(imageResourceId, recommendItems[i], descriptions[i], recommendReviews[i], recommendPrices[i], recommendRatings[i], recommendOldPrices[i]);
                    items2.add(sInfo);
                } else {
                    Log.w("initRecommend", "Image resource not found: " + recommendImageNames[i]);
                }
            }
            if (!items.isEmpty()){
                binding.viewRecommend.setLayoutManager(new GridLayoutManager
                        (MainActivity.this,2));
                binding.viewRecommend.setAdapter(new RecommendAdapter(items));
                binding.viewRecommend.setNestedScrollingEnabled(true);
            }
        binding.btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("items2", items2);
                startActivity(intent);
            }
        });
    }

    private void Banners() {
        ArrayList<SliderItems> items = new ArrayList<>();

        String[] bannerImageNames = getResources().getStringArray(R.array.banner_image_names);

        for (String imageName : bannerImageNames) {
            int imageResourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            if (imageResourceId != 0) {
                Drawable bannerImage = getResources().getDrawable(imageResourceId);
                SliderItems sliderItem = new SliderItems(bannerImage);
                items.add(sliderItem);
            }
            else {
                Log.w("Banners", "Image resource not found:" + imageName);
            }
        }
        banner(items);
    }

    private void Categories() {
        ArrayList<CategoryInfo> items = new ArrayList<>();
        String[] categoryImageNames = getResources().getStringArray(R.array.category_image_names);
        String[] categoryItems = getResources().getStringArray(R.array.category_text);
        if (categoryImageNames.length != categoryItems.length) {
            throw new RuntimeException("categoryImageNames and categoryItems arrays must have the same length");
        }
        for (int i = 0; i < categoryImageNames.length; i++)  {
            int imageResourceId = getResources().getIdentifier(categoryImageNames[i], "drawable", getPackageName());
            if (imageResourceId != 0) {
                Drawable categoryImage = getResources().getDrawable(imageResourceId);
                CategoryInfo categoryInfo = new CategoryInfo(categoryImage, categoryItems[i]);
                items.add(categoryInfo);
            } else {
                Log.w("Categories", "Image resource not found: " + categoryImageNames[i]);
            }
        }
                    if (!items.isEmpty()){
                        binding.viewCategory.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        binding.viewCategory.setAdapter(new CategoryAdapter(items));
                        binding.viewCategory.setNestedScrollingEnabled(true);
                    }
    }

    private void banner(ArrayList<SliderItems> items){
        binding.viewPagerSlider.setAdapter(new SliderAdapter(items,binding.viewPagerSlider));
        binding.viewPagerSlider.setClipToPadding(false);
        binding.viewPagerSlider.setClipChildren(false);
        binding.viewPagerSlider.setOffscreenPageLimit(3);
        binding.viewPagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        binding.viewPagerSlider.setPageTransformer(compositePageTransformer);
    }

    private void displayLogOutAD(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("CONFIRM LOG OUT");
        builder.setMessage("Are you sure you wish to log out of your account now?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "You have logged out.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void displayDeleteAD(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("CONFIRM DELETE");
        builder.setMessage("Are you sure you wish to delete your account now?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cartTB.eraseCart();
                cartTB.erasePurchase();
                cartTB.deleteUser();
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void displayFeedbackdialog(){
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.feedback_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        builder.setTitle("Rate our app!");
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Rating submitted! Thank you for your feedback", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAccountMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_options);
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int option = item.getItemId();
                if (option == R.id.delete){
                    displayDeleteAD();
                    return true;
                } else if (option == R.id.logout) {
                    displayLogOutAD();
                    return true;
                }
                else {
                    Toast.makeText(MainActivity.this, "Please select something", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }
}