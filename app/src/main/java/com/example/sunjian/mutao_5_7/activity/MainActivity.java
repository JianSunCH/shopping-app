package com.example.sunjian.mutao_5_7.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.sunjian.mutao_5_7.R;
import com.example.sunjian.mutao_5_7.fragment.MainFragment;
import com.example.sunjian.mutao_5_7.fragment.ShopFragment;
import com.example.sunjian.mutao_5_7.fragment.UserFragment;

/**
 * Created by sunjian on 2018/4/28.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected LinearLayout menu_main;
    protected LinearLayout menu_shop;
    protected LinearLayout menu_user;
    protected MainFragment mainFragment = new MainFragment();
    protected ShopFragment shopFragment = new ShopFragment();
    protected UserFragment userFragment = new UserFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        this.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.rl_content, mainFragment)
                .add(R.id.rl_content, shopFragment).hide(shopFragment)
                .add(R.id.rl_content, userFragment).hide(userFragment)
                .commit();
    }

    public void initView() {
        menu_main = findViewById(R.id.menu_main);
        menu_shop = findViewById(R.id.menu_shop);
        menu_user = findViewById(R.id.menu_user);

        menu_main.setOnClickListener(this);
        menu_shop.setOnClickListener(this);
        menu_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_main :
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .show(mainFragment)
                        .hide(shopFragment)
                        .hide(userFragment)
                        .commit();
                break;
            case R.id.menu_shop :
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mainFragment)
                        .show(shopFragment)
                        .hide(userFragment)
                        .commit();
                break;
            case R.id.menu_user :
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mainFragment)
                        .hide(shopFragment)
                        .show(userFragment)
                        .commit();
                break;
        }
    }
}
