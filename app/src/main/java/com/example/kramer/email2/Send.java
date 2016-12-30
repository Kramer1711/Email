package com.example.kramer.email2;

import com.example.kramer.email2.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Kramer on 2016/12/26.
 */

public class Send extends Activity {

    private ImageButton sendButton;
    private EditText receiveEdit, subjectEdit, contentEdit;
    private String recerver, subject, content;
    private String userName, password;

    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.send);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        /*
        userName = "ttyou1711@163.com";
        password = "qkssk1711";*/
        userName = bundle.getString("userName");
        password = bundle.getString("password");
        System.out.println("发送：\n" + userName);
        System.out.println(password);
        init();
    }

    public void init() {
        receiveEdit = (EditText) findViewById(R.id.receiverEdit);
        subjectEdit = (EditText) findViewById(R.id.subjectEdit);
        contentEdit = (EditText) findViewById(R.id.contentEdit);

        sendButton = (ImageButton) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recerver = receiveEdit.getText().toString().trim();
                subject = subjectEdit.getText().toString().trim();
                content = contentEdit.getText().toString().trim();
                final Message msg = handler.obtainMessage();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (SMTP.sendEmail(userName, password, recerver
                                , subject, content)) {
                            msg.arg1 = 1;
                            handler.handleMessage(msg);
//                            Toast.makeText(Send.this, "发送成功!", Toast.LENGTH_SHORT).show();
//                            System.out.println("发生成功！");
                        } else {
                            msg.arg1 = 0;
                            handler.handleMessage(msg);
                        }

                    }
                }).start();

            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Looper.prepare();
            if (msg.arg1 == 1) {
                Toast.makeText(Send.this, "发送成功！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Send.this, "发送失败！请重试！", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(Send.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("userName", userName);
            bundle.putString("password", password);
            intent.putExtras(bundle);
            startActivity(intent);
            Looper.loop();
        }
    };
}
