package com.example.sunjian.mutao_5_7.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class RegisterActivity extends AppCompatActivity {
    public static final String REGISTERNAME = "registername";
    protected EditText et_register_name;
    protected EditText getEt_register_password;
    protected Button btn_register;
    protected UserDAO userDAO;
    protected User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_register_name = findViewById(R.id.et_register_name);
        getEt_register_password = findViewById(R.id.et_register_password);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et_name = et_register_name.getText().toString();
                String et_pass = getEt_register_password.getText().toString();
                if (et_name.equals("") && et_pass.equals("")) {
                    Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_LONG).show();
                } else if (et_name.equals("")) {
                    Toast.makeText(RegisterActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                } else if (et_pass.equals("")) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                    userDAO = new UserDAO(RegisterActivity.this);
                    user = new User(et_name, et_pass);
                    userDAO.addUser(user);
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra(REGISTERNAME, et_name);
                    startActivity(intent);
                }
            }
        });
    }
}

