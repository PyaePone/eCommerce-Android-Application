package com.example.cos209finalprojectfurnivibe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cos209finalprojectfurnivibe.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edtuname.getText().toString().equals("") || binding.edtpsw.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Both username and password required to sign up an account!", Toast.LENGTH_LONG).show();
                } else if (binding.edtRetype.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please retype your password!", Toast.LENGTH_LONG).show();
                } else if (!binding.edtRetype.getText().toString().equals(binding.edtpsw.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "The retyped password does not match!", Toast.LENGTH_LONG).show();
                } else {
                    String username = binding.edtuname.getText().toString();
                    String password = binding.edtpsw.getText().toString();
                    dbHelper = new DBHelper(SignUpActivity.this);
                    Boolean check = dbHelper.addUser(username, password);
                    if (check){
                        Toast.makeText(SignUpActivity.this, "Sign Up Successful! Please login with your new account", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    }
                    else
                        Toast.makeText(SignUpActivity.this, "User with same name already exists.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}