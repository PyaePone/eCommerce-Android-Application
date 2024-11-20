package com.example.cos209finalprojectfurnivibe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cos209finalprojectfurnivibe.databinding.ViewholderCartBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<RecommendInfo> listItemSelected;
    ItemNumberChangeListener itemNumberChangeListener;
    private CartTB cartTb;
    int imageResourceId;

    public CartAdapter(ArrayList<RecommendInfo> listItemSelected, Context context, ItemNumberChangeListener itemNumberChangeListener, int imageResourceId) {
        this.listItemSelected = listItemSelected;
        this.itemNumberChangeListener = itemNumberChangeListener;
        cartTb = new CartTB(context);
        this.imageResourceId = imageResourceId;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderCartBinding binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.binding.txtTitle.setText(listItemSelected.get(position).getTitle());
        holder.binding.txtFeeEachItem.setText("$" + listItemSelected.get(position).getPrice());
        holder.binding.txtTotalEachItem.setText("$" + Math.round((listItemSelected.get(position).getNumberInCart() * listItemSelected.get(position).getPrice())));
        holder.binding.txtNumberofItem.setText(String.valueOf(listItemSelected.get(position).getNumberInCart()));

        Glide.with(holder.itemView.getContext())
                .load(listItemSelected.get(position).getUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.binding.imageitem);

        holder.binding.btnPlusCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartTb.plusItem(listItemSelected, position, new ItemNumberChangeListener() {
                    @Override
                    public void changed() {
                        listItemSelected.set(position, cartTb.getItem(listItemSelected, position));
                        notifyDataSetChanged();
                        itemNumberChangeListener.changed();
                    }
                });
            }
        });

        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    cartTb.deleteItem(listItemSelected, position);
                    listItemSelected.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                    itemNumberChangeListener.changed();
                    cartTb.getListCart();
            }
        });

        holder.binding.btnMinusCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartTb.minusItem(listItemSelected, position, new ItemNumberChangeListener() {
                    @Override
                    public void changed() {
                        listItemSelected.set(position, cartTb.getItem(listItemSelected, position));
                        notifyItemChanged(position);
                        notifyDataSetChanged();
                        itemNumberChangeListener.changed();
                        cartTb.getListCart();
                        if (listItemSelected.get(position).getNumberInCart() < 1){
                            cartTb.deleteItem(listItemSelected, position);
                            listItemSelected.remove(position);
                            notifyItemRemoved(position);
                            itemNumberChangeListener.changed();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderCartBinding binding;
        public ViewHolder(ViewholderCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
