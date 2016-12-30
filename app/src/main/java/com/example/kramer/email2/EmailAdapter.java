package com.example.kramer.email2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by Kramer on 2016/12/19.
 */

public class EmailAdapter extends BaseAdapter {

    private LinkedList<Email> mData;
    private Context mContext;

    public EmailAdapter(LinkedList<Email> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.inbox_item, parent, false);
        TextView fristNameText = (TextView) convertView.findViewById(R.id.senderFirstName);
        TextView senderNameText = (TextView) convertView.findViewById(R.id.senderName);
        TextView themeText = (TextView) convertView.findViewById(R.id.theme);
        TextView contentText = (TextView) convertView.findViewById(R.id.content);
        TextView dateText = (TextView) convertView.findViewById(R.id.sendDate);
        String a = String.valueOf(Character.toUpperCase(mData.get(position).getSubject().charAt(0)));
        fristNameText.setText(a);
        senderNameText.setText(mData.get(position).getSenderName());
        themeText.setText(mData.get(position).getSubject());
        contentText.setText(mData.get(position).getContent());
        dateText.setText(mData.get(position).getDate());
        return convertView;
    }
}
