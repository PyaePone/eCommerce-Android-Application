package com.example.cos209finalprojectfurnivibe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cos209finalprojectfurnivibe.databinding.ActivityCustomizeBinding;
import com.example.cos209finalprojectfurnivibe.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        binding.txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edtusername.getText().toString().equals("") || binding.edtpassword.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Username and Password required!", Toast.LENGTH_LONG).show();
                }
                else {
                    String username = binding.edtusername.getText().toString();
                    String password = binding.edtpassword.getText().toString();

                    dbHelper = new DBHelper(LoginActivity.this);
                    Cursor cursor = dbHelper.checkUser(username, password);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        getUserInfo(username, password);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", username);
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_LONG).show();
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username or password!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    private void getUserInfo(String username, String password) {
        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }
}
