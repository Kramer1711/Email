package com.example.kramer.email2;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Kramer on 2016/12/25.
 */

public class ClickListener {
    public static void addListener(final EditText edit, final ImageView imgBtn) {

        edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String s1 = s + "";
                if (s.length() > 0) {
                    imgBtn.setVisibility(View.VISIBLE);
                } else {
                    imgBtn.setVisibility(View.INVISIBLE);
                }

            }
        });

        imgBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 清空输入框
                edit.setText("");

            }
        });

    }
}
