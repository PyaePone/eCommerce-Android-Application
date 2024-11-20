package com.example.cos209finalprojectfurnivibe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.cos209finalprojectfurnivibe.databinding.ViewholderRecommendBinding;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    ArrayList<RecommendInfo> items;
    Context context;
    public RecommendAdapter(ArrayList<RecommendInfo> items) {
        this.items = items;
    }



    @NonNull
    @Override
    public RecommendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderRecommendBinding binding = ViewholderRecommendBinding.inflate(LayoutInflater.from(context), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecommendInfo object = items.get(position);
        holder.binding.txtTitle.setText(object.getTitle());
        holder.binding.txtReview.setText(""+ object.getReview());
        holder.binding.txtPrice.setText("$" + object.getPrice());
        holder.binding.txtRating.setText("(" + object.getRating() + ")");
        holder.binding.txtOldPrice.setText("$" + object.getOldPrice());
        holder.binding.txtOldPrice.setPaintFlags(holder.binding.txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.binding.ratingBar.setRating((float) object.getRating());

        RequestOptions options = new RequestOptions();
        options = options.transform(new CenterCrop());

        Glide.with(context)
                .load(object.getUrl())
                .apply(options)
                .into(holder.binding.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", items.get(position).getTitle());
                intent.putExtra("price", items.get(position).getPrice());
                intent.putExtra("rating", items.get(position).getRating());
                intent.putExtra("description", items.get(position).getDescription());
                intent.putExtra("imageResourceId", items.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderRecommendBinding binding;
        public ViewHolder(ViewholderRecommendBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
