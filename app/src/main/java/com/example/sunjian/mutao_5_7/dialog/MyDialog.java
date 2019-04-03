package com.example.sunjian.mutao_5_7.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sunjian.mutao_5_7.R;
import com.example.sunjian.mutao_5_7.dao.UserDAO;
import com.example.sunjian.mutao_5_7.entity.User;

/**
 * Created by sunjian on 2018/4/28.
 */

public class MyDialog extends Dialog {
    private Button yesBtn;
    private Button noBtn;
    private EditText edt_name;
    private EditText edt_newpass;
    private User user;
    private UserDAO userDAO;

    public MyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialoglayout);

        initView();

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = edt_name.getText().toString();
                String upassword = edt_newpass.getText().toString();
                user = new User(uname, upassword);
                userDAO = new UserDAO(getContext());
                Cursor cursor = userDAO.getUser("name", uname);
                if (uname.equals("") || upassword.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                            .setTitle("提示")
                            .setMessage("用户名或密码不能为空！")
                            .setNegativeButton("确定", new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                    Toast.makeText(getContext(), "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    if (cursor.moveToFirst()) {
                        String sqliteName = cursor.getString(cursor.getColumnIndex("name"));
                        if (uname.equals(sqliteName)) {
                            userDAO.updateUser(user);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                                    .setTitle("提示")
                                    .setMessage("您已修改密码成功！")
                                    .setPositiveButton("确定", new OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                            builder.show();
                            Toast.makeText(getContext(), "修改密码成功！", Toast.LENGTH_LONG).show();
                            cursor.close();
                            dismiss();
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                                .setTitle("提示")
                                .setMessage("没有此用户！")
                                .setNegativeButton("确定", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        builder.show();
                        Toast.makeText(getContext(), "没有此用户！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initView() {
        yesBtn = findViewById(R.id.yes_btn);
        noBtn = findViewById(R.id.no_btn);
        edt_name = findViewById(R.id.edt_name);
        edt_newpass = findViewById(R.id.edt_newpass);
    }
}
