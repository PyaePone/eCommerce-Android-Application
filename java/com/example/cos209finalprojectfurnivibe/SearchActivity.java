package com.example.cos209finalprojectfurnivibe;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cos209finalprojectfurnivibe.databinding.ActivityMainBinding;
import com.example.cos209finalprojectfurnivibe.databinding.ActivitySearchBinding;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private RecyclerView.Adapter Adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RecommendInfo> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
            }
        });

        Search();
    }
    private void Search() {
        Adapter = new RecommendAdapter(items);
        layoutManager = new LinearLayoutManager(this);
        binding.viewSearch.setLayoutManager(layoutManager);
        binding.viewSearch.setAdapter(Adapter);
        displayitems();
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.viewSearch.setVisibility(View.VISIBLE);
                filterData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                binding.viewSearch.setVisibility(View.VISIBLE);
                filterData(newText);
                return false;
            }
        });
        Adapter = new RecommendAdapter(items);
        binding.viewSearch.setAdapter(Adapter);
    }

    private void displayitems(){
        Intent intent = getIntent();
        ArrayList<RecommendInfo> itemlist = (ArrayList<RecommendInfo>)intent.getSerializableExtra("items2");
        if (!itemlist.isEmpty()){
            items.clear();
            items.addAll(itemlist);
            Adapter.notifyDataSetChanged();
            binding.viewSearch.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
            binding.viewSearch.setNestedScrollingEnabled(true);
        }
    }

    private void filterData(String input){
        ArrayList<RecommendInfo> filteredItems = new ArrayList<>();
        for (RecommendInfo item : items){
            if (item.getTitle().toLowerCase().contains(input.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        Adapter = new RecommendAdapter(filteredItems);
        binding.viewSearch.setAdapter(Adapter);
        Adapter.notifyDataSetChanged();

        if (filteredItems.isEmpty()) {
            binding.txtEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.txtEmpty.setVisibility(View.GONE);
        }
    }

}
