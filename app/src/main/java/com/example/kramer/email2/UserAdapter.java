package com.example.kramer.email2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by Kramer on 2016/12/30.
 */

public class UserAdapter extends BaseAdapter {

    private LinkedList<User> mData;
    private Context mContext;

    public UserAdapter(LinkedList<User> mData, Context mContext) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        TextView userName = (TextView) convertView.findViewById(R.id.userItem);
        userName.setText(mData.get(position).getUserName());
        return convertView;
    }
}
