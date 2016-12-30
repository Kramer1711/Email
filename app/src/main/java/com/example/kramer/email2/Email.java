package com.example.kramer.email2;

/**
 * Created by Kramer on 2016/12/19.
 */

public class Email {
    private String date;
    private String senderName;
    private String receiverName;
    private String subject;
    private String content;


    public Email(String date, String subject, String senderName, String content) {
        this.date = date;
        this.senderName = senderName;
        this.subject = subject;
        this.content = content;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public String getDate() {
        return this.date;

    }

    public String getReceiverName() {
        return this.receiverName;

    }

    public String getSubject() {
        return this.subject;

    }

    public String getContent() {
        return this.content;
    }

}
