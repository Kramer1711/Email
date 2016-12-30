package com.example.kramer.email2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kramer on 2016/12/29.
 */

public class UserRepo {
    private DBHelper dbHelper;

    UserRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insert(User user) {
        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO " + User.TABLE +
                "(" + User.KEY_userName + "," + User.KEY_password + ") values(" +
                user.getUserName() + "," +
                user.getPassword() + ")";
        if (isHave(user.getUserName()) == false) {
            ContentValues values = new ContentValues();
            values.put(User.KEY_userName, user.getUserName());
            values.put(User.KEY_password, user.getPassword());
            db.insert(User.TABLE, null, values);
            db.close();
        } else {
            db.close();
            return;
        }
    }

    public User selectByUserName(String userName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        User user;
        String sql = "SELECT " +
                User.KEY_userName + "," +
                User.KEY_password + " FROM " +
                User.TABLE + " WHERE " +
                User.KEY_userName + "='" + userName + "'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        String userName1 = cursor.getString(cursor.getColumnIndex(User.KEY_userName));
        String password = cursor.getString(cursor.getColumnIndex(User.KEY_password));
        user = new User(userName1, password);
        return user;
    }

    public boolean isHave(String userName){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT " +
                User.KEY_userName + "," +
                User.KEY_password + " FROM " +
                User.TABLE + " WHERE " +
                User.KEY_userName + "='" + userName + "'";
        Cursor cursor = db.rawQuery(sql, null);
        int count = 0;
        if(cursor.moveToFirst())
            do {
                count++;
            }while (cursor.moveToNext());
        if(count == 0) return false;
        else return true;
    }

    public boolean isEmpty() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                User.KEY_userName + "," +
                User.KEY_password +
                " FROM " + User.TABLE;
        int count = 0;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                count++;
                System.out.println(cursor.getString(cursor.getColumnIndex(User.KEY_userName)));
            } while (cursor.moveToNext());
        }
        if (count == 0) {
            return true;
        } else return false;
    }

    public ArrayList<User> getUserList() {
        ArrayList<User> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + User.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst())
            do {
                User user = new User();
                String userName = cursor.getString(cursor.getColumnIndex(User.KEY_userName));
                String password = cursor.getString(cursor.getColumnIndex(User.KEY_password));
                user = new User(userName, password);
                userList.add(user);
            } while (cursor.moveToNext());
        return userList;
    }

    public User getFirst() {
        User user = new User();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectSquery = "SELECT " +
                User.KEY_userName + "," +
                User.KEY_password +
                " FROM " + User.TABLE;
        Cursor cursor = db.rawQuery(selectSquery, null);
        cursor.moveToFirst();
        String userName = cursor.getString(cursor.getColumnIndex(User.KEY_userName));
        System.out.println("!!!" + userName);
        String password = cursor.getString(cursor.getColumnIndex(User.KEY_password));
        System.out.println("!!!" + password);
        user = new User(userName, password);
        return user;
    }

    public boolean delete(String userName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int flag = db.delete(User.TABLE, User.KEY_userName + "=?", new String[]{userName});
        db.close();
        if (flag == 0)
            return false;
        else return true;
    }

    public void delectAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "SELECT * FROM " + User.TABLE;
        db.rawQuery(sql, null);
        db.close();
    }
}
