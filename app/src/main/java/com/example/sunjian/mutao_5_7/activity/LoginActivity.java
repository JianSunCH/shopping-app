package com.example.sunjian.mutao_5_7.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sunjian.mutao_5_7.R;
import com.example.sunjian.mutao_5_7.dao.UserDAO;
import com.example.sunjian.mutao_5_7.dialog.MyDialog;
import com.example.sunjian.mutao_5_7.entity.User;

import static com.example.sunjian.mutao_5_7.activity.RegisterActivity.REGISTERNAME;


/**
 * Created by sunjian on 2018/4/28.
 */

public class LoginActivity extends AppCompatActivity {
    public static final String WELCOMENAME = "WELCOMENAME";
    protected Button btn_login_register;
    protected Button btn_login;
    protected Button btn_findPassword;
    protected Button btn_reset;
    protected EditText et_login_name;
    protected EditText et_login_password;
    protected UserDAO userDAO;
    protected User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        btn_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        if (getIntent() != null) {
            String name = getIntent().getStringExtra(REGISTERNAME);
            et_login_name.setText(name);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = et_login_name.getText().toString();
                String upassword = et_login_password.getText().toString();

                userDAO = new UserDAO(LoginActivity.this);
                user = new User(uname, upassword);
                Cursor cursor = userDAO.getUser("name", uname);
                if (cursor.moveToFirst()) {
                    String sqliteName = cursor.getString(cursor.getColumnIndex("name"));
                    String sqlitePass = cursor.getString(cursor.getColumnIndex("password"));
                    if (uname.equals(sqliteName) && upassword.equals(sqlitePass)) {
                        Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_LONG).show();
                        cursor.close();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else if (uname.equals("")) {
                        Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                    } else if (upassword.equals("")) {
                        Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_login_name.setText("");
                et_login_password.setText("");
            }
        });

        btn_findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog dialog = new MyDialog(LoginActivity.this, R.style.mydialog);
                dialog.show();
            }
        });
    }

    private void initView() {
        btn_login_register = findViewById(R.id.btn_login_register);
        et_login_name = findViewById(R.id.et_login_name);
        et_login_password = findViewById(R.id.et_login_password);
        btn_login = findViewById(R.id.btn_login);
        btn_findPassword = findViewById(R.id.btn_findPassword);
        btn_reset = findViewById(R.id.btn_reset);
    }
}
