package com.example.kramer.email2;

/**
 * Created by Kramer on 2016/12/29.
 */

public class User {
    //TABLE NAME
    public static final String TABLE = "User";
    //表属性
    public static final String KEY_userName = "userName";
    public static final String KEY_password = "password";
    //属性
    private String userName;
    private String password;
    public static boolean LOGINAGAIN = false;

    User() {
    }

    User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }
}
