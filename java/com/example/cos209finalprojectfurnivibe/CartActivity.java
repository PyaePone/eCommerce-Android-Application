package com.example.cos209finalprojectfurnivibe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cos209finalprojectfurnivibe.databinding.ActivityCartBinding;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private double tax;
    private CartTB cartTB;
    CartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cartTB = new CartTB(this);
        calculateBill();
        setVariable();
        CartList();
    }

    private void CartList() {
        displaycart();
        int imageResourceId = 0;
        binding.cartView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        cartAdapter = new CartAdapter(cartTB.getListCart(), this, new ItemNumberChangeListener() {
            @Override
            public void changed() {
                calculateBill();
                displaycart();
            }
        }, imageResourceId);
        binding.cartView.setAdapter(cartAdapter);
    }

    private void displaycart() {
        if (cartTB.getListCart().isEmpty()){
            binding.txtEmpty.setVisibility(View.VISIBLE);
            binding.scrollViewCart.setVisibility(View.GONE);
        }
        else{
            binding.txtEmpty.setVisibility(View.GONE);
            binding.scrollViewCart.setVisibility(View.VISIBLE);
        }}

    private void calculateBill() {
        double percentTax = 0.02;
        double delivery = 10;
        tax = Math.round((cartTB.getTotalFee() + percentTax * 100.0) / 100.0);

        double totalPrice = Math.round(cartTB.getTotalFee() + tax + delivery);
        double itemTotal = Math.round(cartTB.getTotalFee());

        binding.txtTotalFee.setText("$" + itemTotal);
        binding.txtTax.setText("$" + tax);
        binding.txtDelivery.setText("$" + delivery);
        binding.txtTotal.setText("$" + totalPrice);}

    private void setVariable() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edtlocation.getText().toString().equals("")) {
                    Toast.makeText(CartActivity.this, "Please enter your location", Toast.LENGTH_SHORT).show();
                }
                else {
                    displaydialog();
                }
            }
        });
        }

    private void displaydialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setTitle("Purchase Confirmation");
        builder.setMessage("Are you sure you wish to place order now?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList<RecommendInfo> listItemSelected = cartTB.getListCart();
                for (RecommendInfo item : listItemSelected) {
                    cartTB.addOrder(item, binding.edtlocation.getText().toString());
                }
                cartTB.deleteAllItems();
                listItemSelected.clear();
                cartAdapter.notifyDataSetChanged();
                dialog.dismiss();
                startActivity(new Intent(CartActivity.this, MainActivity.class));
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    }