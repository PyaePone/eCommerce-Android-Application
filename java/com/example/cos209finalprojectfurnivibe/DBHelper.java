package com.example.cos209finalprojectfurnivibe;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DBHelper extends SQLiteOpenHelper {

    byte[] imageInBytes;
    SharedPreferences preferences;
    public DBHelper(@Nullable Context context) {
        super(context, "furnivibe.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        db.execSQL("CREATE TABLE cart (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, price REAL, number_in_cart INTEGER, image TEXT, user_id INTEGER, FOREIGN KEY (user_id) REFERENCES user(id))");
        db.execSQL("CREATE TABLE purchase (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, price REAL, quantity INTEGER, location TEXT, image TEXT, user_id INTEGER, FOREIGN KEY (user_id) REFERENCES user(id))");
        db.execSQL("CREATE TABLE customization (id INTEGER PRIMARY KEY AUTOINCREMENT, length REAL, width REAL, height REAL, detail TEXT, name TEXT, email TEXT, location TEXT, image BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS cart");
        db.execSQL("DROP TABLE IF EXISTS purchase");
        db.execSQL("DROP TABLE IF EXISTS customization");
        onCreate(db);
    }

    public boolean addCustom(String length, String width, String height, String detail, String name, String email, String location, Bitmap image) {
        SQLiteDatabase db = getReadableDatabase();
        ByteArrayOutputStream objectByteOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, objectByteOutputStream);
        imageInBytes = objectByteOutputStream.toByteArray();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", imageInBytes);
        contentValues.put("length", length);
        contentValues.put("width", width);
        contentValues.put("height", height);
        contentValues.put("detail", detail);
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("location", location);
        long result = db.insert("customization", null, contentValues);
        if (result == -1) return false;
        else return true;
    }

    public boolean addUser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);

        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username=?", new String[]{username});

        if (cursor.getCount() >0){
            return false;
        }
        else{
            long result = db.insert("user", null, contentValues);
            if (result == -1) return false;
            else return true;
        }
    }

    public Cursor checkUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username=? AND password=?", new String[]{username, password});
        return cursor;
    }
}
