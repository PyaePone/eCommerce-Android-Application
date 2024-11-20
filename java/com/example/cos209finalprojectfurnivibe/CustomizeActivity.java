package com.example.cos209finalprojectfurnivibe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cos209finalprojectfurnivibe.databinding.ActivityCustomizeBinding;

public class CustomizeActivity extends AppCompatActivity {
    private ActivityCustomizeBinding binding;
    DBHelper db;
    private Uri imageFilePath;
    private Bitmap imagestore;
    private static final int PICK_IMAGE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageFilePath = data.getData();
                imagestore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);
                binding.imgupload.setImageBitmap(imagestore);
            }
        }catch (Exception e) {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomizeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DBHelper(this);
        binding.imgupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });

        binding.btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String length = binding.edtlength.getText().toString();
                String width = binding.edtwidth.getText().toString();
                String height = binding.edtheight.getText().toString();
                String detail = binding.edtdetail.getText().toString();
                String name = binding.edtname.getText().toString();
                String email = binding.edtemail.getText().toString();
                String location = binding.edtlocation.getText().toString();
                if (imagestore == null) {
                    Toast.makeText(CustomizeActivity.this, "Please select an image", Toast.LENGTH_LONG).show();
                } else if (length.equals("") || width.equals("") || height.equals("") || detail.equals("")) {
                    Toast.makeText(CustomizeActivity.this, "Please fill the item information", Toast.LENGTH_LONG).show();
                }
                else if (name.equals("")) {
                    Toast.makeText(CustomizeActivity.this, "Please fill your name", Toast.LENGTH_LONG).show();
                } else if (email.equals("") || location.equals("")) {
                    Toast.makeText(CustomizeActivity.this, "Please fill your contact information", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomizeActivity.this);
                    builder.setTitle("Confirm Customization");
                    builder.setMessage("Are you sure you wish to place order?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Boolean check = db.addCustom(length, width, height, detail, name, email, location, imagestore);
                            if (check){
                                Toast.makeText(CustomizeActivity.this, "Order has been recorded. Thank you!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(CustomizeActivity.this, MainActivity.class));
                            }
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
