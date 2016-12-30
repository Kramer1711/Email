package com.example.kramer.email2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
//import java.net.Socket;

/**
 * Created by Kramer on 2016/12/25.
 */

public class Pop {
    private static String POP3Server = "pop.163.com";
    static String user = "";
    static String pass = "";
    private static int POP3Port = 110;

    Pop(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public boolean login() {
        Socket link = null;
        try {
            link = new Socket(POP3Server, POP3Port);
            InputStream intext = link.getInputStream();
            BufferedReader inwriter = new BufferedReader(new InputStreamReader(intext));
            OutputStream outtext = link.getOutputStream();
            PrintWriter outreader = new PrintWriter(outtext, true);
            outreader.println("user " + Pop.user);
//            System.out.println(inwriter.readLine());
            outreader.println("pass " + Pop.pass);
//            System.out.println(inwriter.readLine());
            String res = inwriter.readLine();
            if (res.substring(1, 3).equals("OK")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (link != null) {
                    link.close();
                }
            } catch (IOException e) {

            }
        }
    }

}
