package com.example.cos209finalprojectfurnivibe;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class CartTB {

    private Context context;
    private DBHelper dbHelper;
    SharedPreferences preferences;
    public CartTB(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);
        preferences = context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
    }

    private int getUserId(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("user", new String[]{"id"}, "username = ? AND password = ?", new String[]{username, password}, null, null, null);
        int userId = -1;
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }
        cursor.close();
        return userId;
    }

    public void addOrder(RecommendInfo item, String location) {
        String username = preferences.getString("username", null);
        String password = preferences.getString("password", null);
        int userId = getUserId(username, password);
        if (userId!= -1) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", item.getTitle());
            contentValues.put("price", item.getPrice());
            contentValues.put("quantity", item.getNumberInCart());
            contentValues.put("image", item.getUrl());
            contentValues.put("user_id", userId);
            contentValues.put("location", location);
            db.insert("purchase", null, contentValues);
            db.close();
        }
        else {
            Toast.makeText(context, "Error. Please Try Again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void addItem(RecommendInfo item) {
        String username = preferences.getString("username", null);
        String password = preferences.getString("password", null);
        int userId = getUserId(username, password);
        if (userId!= -1) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("title", item.getTitle());
            contentValues.put("price", item.getPrice());
            contentValues.put("number_in_cart", item.getNumberInCart());
            contentValues.put("image", item.getUrl());
            contentValues.put("user_id", userId);
            db.insert("cart", null, contentValues);
            db.close();
            Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Error. Please Try Again.", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<RecommendInfo> getListCart(){
        String username = preferences.getString("username", null);
        String password = preferences.getString("password", null);
        int userId = getUserId(username, password);
        if (userId!= -1) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query("cart", new String[]{"id", "title", "price", "number_in_cart", "image"}, "user_id = ?", new String[]{String.valueOf(userId)}, null, null, null);
            ArrayList<RecommendInfo> listItem = new ArrayList<>();
            while (cursor.moveToNext()) {
                RecommendInfo item = new RecommendInfo();
                item.setId(cursor.getInt(0));
                item.setTitle(cursor.getString(1));
                item.setPrice(cursor.getDouble(2));
                item.setNumberInCart(cursor.getInt(3));
                item.setUrl(cursor.getInt(4));
                listItem.add(item);
            }
            cursor.close();
            db.close();
            return listItem;
        }
        return null;
    }

    public RecommendInfo getItem(ArrayList<RecommendInfo> list, int position){
        if (list != null &&!list.isEmpty() && position < list.size()) {
            return list.get(position);
        }
        else {
            return null;
        }
    }

    public void minusItem (ArrayList<RecommendInfo> listItem, int position, ItemNumberChangeListener itemNumberChangeListener){
        if (listItem != null && position >= 0 && position < listItem.size()) {
            RecommendInfo item = listItem.get(position);
            if (item != null) {
                if (item.getNumberInCart() >= 1) {
                    item.setNumberInCart(item.getNumberInCart() - 1);
                    updateItem(item);
                }
                else {
                    deleteItem(listItem, position);
                }
                itemNumberChangeListener.changed();
            }
        }
        getListCart();
    }

    public void updateItem(RecommendInfo item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", item.getTitle());
        contentValues.put("price", item.getPrice());
        contentValues.put("number_in_cart", item.getNumberInCart());
        contentValues.put("image", item.getUrl());
        db.update("cart", contentValues, "id = ?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public void deleteItem(ArrayList<RecommendInfo> listItemSelected, int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("cart", "id = ?", new String[]{String.valueOf(listItemSelected.get(position).getId())});
        db.close();
        getListCart();
        Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
    }

    public void deleteAllItems() {
        String username = preferences.getString("username", null);
        String password = preferences.getString("password", null);
        int userId = getUserId(username, password);
        if (userId != -1) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("cart", "user_id = ?", new String[]{String.valueOf(userId)});
            db.close();
            getListCart();
            Toast.makeText(context, "Purchase Successful", Toast.LENGTH_SHORT).show();
        }
    }

    public void eraseCart() {
        String username = preferences.getString("username", null);
        String password = preferences.getString("password", null);
        int userId = getUserId(username, password);
        if (userId != -1) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("cart", "user_id = ?", new String[]{String.valueOf(userId)});
            db.close();
            getListCart();
        }
    }

    public void erasePurchase() {
        String username = preferences.getString("username", null);
        String password = preferences.getString("password", null);
        int userId = getUserId(username, password);
        if (userId != -1) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("purchase", "user_id = ?", new String[]{String.valueOf(userId)});
            db.close();
        }
    }

    public void plusItem (ArrayList <RecommendInfo> listItem,
                          int position, ItemNumberChangeListener itemNumberChangeListener){
        RecommendInfo item = listItem.get(position);
            item.setNumberInCart(item.getNumberInCart() + 1);
            updateItem(item);
        itemNumberChangeListener.changed();
    }

    public Double getTotalFee () {
        String username = preferences.getString("username", null);
        String password = preferences.getString("password", null);
        int userId = getUserId(username, password);
        if (userId != -1) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query("cart", new String[]{"price", "number_in_cart"}, "user_id = ?", new String[]{String.valueOf(userId)}, null, null, null);
            double fee = 0;
            while (cursor.moveToNext()) {
                fee += cursor.getDouble(0) * cursor.getInt(1);
            }
            cursor.close();
            db.close();
            return fee;
        }
        return null;
    }

    public void deleteUser() {
        String username = preferences.getString("username", null);
        String password = preferences.getString("password", null);
        int userId = getUserId(username, password);
        if (userId != -1) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("user", "id = ?", new String[]{String.valueOf(userId)});
            db.close();
            Toast.makeText(context, "Your account has been deleted", Toast.LENGTH_SHORT).show();
        }
    }
}