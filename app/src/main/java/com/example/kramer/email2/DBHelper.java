package com.example.kramer.email2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kramer on 2016/12/25.
 */

public class DBHelper extends SQLiteOpenHelper {
    //数据库版本号
    private static final int DATABASE_VERSION = 4;

    //数据库名称
    private static final String DATABASE_NAME = "crud.db";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表
        String CREATE_TABLE_USER = "CREATE TABLE " +
                User.TABLE + " ( id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                User.KEY_userName + "  VARCHAR(20), " +
                User.KEY_password + "  VARCHAR(20))";
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果旧表存在，删除，所以数据将会消失
        db.execSQL("DROP TABLE IF EXISTS User");

        //再次创建表
        onCreate(db);
    }
}
