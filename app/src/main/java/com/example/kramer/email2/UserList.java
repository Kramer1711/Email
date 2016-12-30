package com.example.kramer.email2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kramer on 2016/12/30.
 */

public class UserList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayList<User> users;
    List<User> mData;
    User user;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.user_list);

        mData = new LinkedList<>();

        users = new ArrayList<>();
        UserRepo repo = new UserRepo(this);
        users = repo.getUserList();
        user = new User();
        for (int i = 0; i < users.size(); i++) {
            user = users.get(i);
            mData.add(user);
        }
        UserAdapter mAdapter = new UserAdapter((LinkedList<User>) mData, UserList.this);
        ListView list_users = (ListView) findViewById(R.id.userList);
        list_users.setAdapter(mAdapter);
        list_users.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        System.out.println("!!!");
//        Toast.makeText(UserList.this,"!!!",Toast.LENGTH_SHORT).show();
        TextView content = (TextView) view.findViewById(R.id.userItem);
        Intent intent = new Intent(UserList.this, MainActivity.class);
        User user = new UserRepo(UserList.this).selectByUserName(content.getText().toString());
        Bundle bd = new Bundle();
        System.out.println("!!!"+content.getText().toString());
        bd.putString("userName", user.getUserName());
        bd.putString("password", user.getPassword());
        intent.putExtras(bd);
        startActivity(intent);
    }
}
