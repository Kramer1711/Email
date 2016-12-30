package com.example.kramer.email2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private EditText userNameText;
    private EditText passwordText;
    private Button login;
    private ImageView userClear;
    private ImageView passClear;
    private ProgressDialog dialog;
    private UserRepo repo;
    private User user;
    private String userName;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        init();
        repo = new UserRepo(this);/*
        repo.delete("ttxi1711@163.com");
        repo.delete("ttyou1711@163.com");*/
        if (User.LOGINAGAIN == true)
            newLogin();
        else {
            if (repo.isEmpty()) {
                newLogin();
            } else {
                user = repo.getFirst();
                Intent intent = new Intent(Login.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userName", user.getUserName());
                bundle.putString("password", user.getPassword());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

    private void init() {
        userNameText = (EditText) findViewById(R.id.userEdit1);
        passwordText = (EditText) findViewById(R.id.passwordEdit1);
        login = (Button) findViewById(R.id.loginButton1);
        userClear = (ImageView) findViewById(R.id.del_userName);
        passClear = (ImageView) findViewById(R.id.del_password);
        ClickListener.addListener(userNameText, userClear);
        ClickListener.addListener(passwordText, passClear);
    }

    public void newLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userNameText.getText().length() == 0 || passwordText.getText().length() == 0) {
                    Toast.makeText(Login.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    dialog = new ProgressDialog(Login.this);
                    dialog.setMessage("正在登入，请稍后");
                    dialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userName = userNameText.getText().toString();
                            password = passwordText.getText().toString();
//                        userName = "ttyou1711@163.com";
//                        password = "qkssk1711";
                            Pop pop = new Pop(userName, password);
                            Message msg = handler.obtainMessage();
                            if (pop.login()) {
                                msg.arg1 = 0;
                                handler.sendMessage(msg);
                            } else {
                                msg.arg1 = 1;
                                handler.sendMessage(msg);
                            }
                        }
                    }).start();
                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 0) {
                dialog.dismiss();
                Toast.makeText(Login.this, "登陆成功!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, MainActivity.class);
                Bundle bd = new Bundle();
                System.out.println("跳转" + userName + password);
                bd.putString("userName", userName);
                bd.putString("password", password);
                intent.putExtras(bd);
                startActivity(intent);
            } else {
                dialog.dismiss();
                Toast.makeText(Login.this, "登陆失败！请重试！", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
