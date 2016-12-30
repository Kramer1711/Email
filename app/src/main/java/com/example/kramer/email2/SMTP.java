package com.example.kramer.email2;

import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by perfect on 2016/11/26.
 */

public class SMTP {
    private static String SMTPServer = "smtp.163.com";

    SMTP() {
    }

    public static boolean sendEmail(String user1, String psw1, String receiver1, String subject1, String data1) {
        int SMTPPort = 25;
        Socket client = null;
        String user = Base64.encodeToString(user1.getBytes(), Base64.NO_WRAP);
        String psw = Base64.encodeToString(psw1.getBytes(), Base64.NO_WRAP);
        try {
            // 向SMTP服务程序建立一个套接字连接。
            client = new Socket(SMTP.SMTPServer, SMTPPort);
            // 创建一个BufferedReader对象，以便从套接字读取输出。
            InputStream is = client.getInputStream();
            BufferedReader sockin = new BufferedReader(new InputStreamReader(is));
            // 创建一个PrintWriter对象，以便向套接字写入内容。
            OutputStream os = client.getOutputStream();
            PrintWriter sockout = new PrintWriter(os, true);
            // 显示同SMTP服务程序的握手过程。
            sockout.println("HELO smtp.163.com ");//握手
            System.out.println("握手：" + sockin.readLine());
            sockout.println("AUTH LOGIN");//请求登录
            System.out.println("请求登录：" + sockin.readLine());
            sockout.println(user);//用户名
            System.out.println("334接受：" + sockin.readLine());
            sockout.println(psw);//密码
            System.out.println("登录成功：" + sockin.readLine());
            sockout.println("MAIL FROM: " + "<" + user1 + ">");//发送者
            System.out.println("确认消息：" + sockin.readLine());
            sockout.println("RCPT TO: " + "<" + receiver1 + ">");//接受者
            System.out.println("确认消息：" + sockin.readLine());
            sockout.println("DATA");
            System.out.println("开始接受：" + sockin.readLine());
            sockout.println("TO:" + receiver1);
            sockout.println("FROM:" + user1);
            sockout.println("SUBJECT:" + subject1);
            sockout.println("\n" + data1);
            sockout.println(".");
            System.out.println("服务器都接受：" + sockin.readLine());
            //此处的.为特殊标记，表示邮件结束
            String success = sockin.readLine();
            System.out.println("成功" + success);
            Log.i("res", success);
            sockout.println("quit");
            if (success.startsWith("250"))
                return true;
            else
                return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
