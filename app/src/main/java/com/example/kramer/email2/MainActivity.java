package com.example.kramer.email2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.sun.mail.imap.protocol.BASE64MailboxDecoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import javax.mail.internet.MimeUtility;


public class MainActivity extends AppCompatActivity {
    private int contentFlag = 0;
    private Context mContext;
    private List<Email> mData;
    private String date, senderName, receiverName, subject, content;
    private String userName;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        userName = bd.getString("userName");
        password = bd.getString("password");

        UserRepo repo = new UserRepo(this);
        repo.insert(new User(userName, password));
        mContext = MainActivity.this;
        mData = new LinkedList<Email>();

        InThread thread = new InThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        init1();
        init2();
    }

    private void init2() {
        EmailAdapter mAdapter = new EmailAdapter((LinkedList<Email>) mData, mContext);
        ListView list_inbox = (ListView) findViewById(R.id.inBoxList);
        list_inbox.setAdapter(mAdapter);
    }

    private void init1() {


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Send.class);
                Bundle bundle = new Bundle();
                bundle.putString("userName", userName);
                bundle.putString("password", password);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    class InThread extends Thread {
        @Override
        public void run() {
            Socket client = null;
            try {
                // 向POP3服务程序建立一个套接字连接。
                client = new Socket("pop.163.com", 110);
                // 创建一个BufferedReader对象，以便从套接字读取输出。
                InputStream is = client.getInputStream();
                BufferedReader sockin = new BufferedReader(new InputStreamReader(is));
                // 创建一个PrintWriter对象，以便向套接字写入内容。
                OutputStream os = client.getOutputStream();
                PrintWriter sockout = new PrintWriter(os, true);
                // 显示同SMTP服务程序的握手过程。
                System.out.println("S:" + sockin.readLine());
                sockout.println("user " + userName);
                System.out.println("S:" + sockin.readLine());
                sockout.println("pass " + password);
                System.out.println("S:" + sockin.readLine());
                sockout.println("stat");
                String temp[] = sockin.readLine().split(" ");
                int count = Integer.parseInt(temp[1]);//得到信箱中共有多少封邮件
                for (int i = count; i > 0; i--) {//依次打印出邮件的内容
                    sockout.println("retr " + i);
                    System.out.println("以下为第" + i + "封邮件的内容");
                    while (true) {
                        String reply = sockin.readLine();
                        System.out.println(reply);
                        if (reply.contains("Date:"))
                            date = reply.substring(11, 22);
                        if (reply.contains("Subject:") || reply.contains("SUBJECT:"))
                            subject = reply.substring(8);
                        if (reply.contains("From:") || reply.contains("FROM:")) {
                            if (reply.contains("<"))
                                senderName = reply.substring(reply.indexOf("<") + 1, reply.indexOf(">"));
                            else
                                senderName = reply.substring(reply.indexOf(":") + 1);
                            System.out.println("!!!!!!!!!!!!!" + reply + "!!!!!!!!!" + senderName);
                        }
                        if (reply.contains("To:"))
                            if (reply.contains("<"))
                                receiverName = reply.substring(reply.indexOf("<") + 1, reply.indexOf(">"));
                            else
                                receiverName = reply.substring(reply.indexOf(':') + 2);
                        if (reply.contains("text/plain")) contentFlag = 3;
                        else if (contentFlag == 3) contentFlag--;
                        else if (contentFlag == 2) contentFlag--;
                        else if (contentFlag == 1) {
                            System.out.println("!!!!!!!!!" + reply);
                            content = MimeUtility.decodeText(reply);
                            contentFlag = 0;
                        }
                        if (reply.toLowerCase().equals(".")) {
                            mData.add(new Email(date, senderName, subject, content));
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(e.toString());
            } finally {
                try {
                    if (client != null) {
                        client.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

//        menu.add(1, Menu.FIRST,1,"注销");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_cancellation:
                cancellation();
                Toast.makeText(this, "注销", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.change_user:
                changeUser();
            default:
                return false;
        }
    }

    private void cancellation() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        User.LOGINAGAIN = true;
        startActivity(intent);
    }

    private void changeUser() {
        Intent intent = new Intent(MainActivity.this, UserList.class);
        startActivity(intent);
    }
}
