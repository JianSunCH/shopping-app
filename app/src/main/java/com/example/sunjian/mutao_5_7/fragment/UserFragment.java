package com.example.sunjian.mutao_5_7.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sunjian.mutao_5_7.R;
import com.example.sunjian.mutao_5_7.activity.BackGround;
import com.example.sunjian.mutao_5_7.activity.LoginActivity;

/**
 * Created by sunjian on 2018/4/28.
 */

public class UserFragment extends Fragment {
    protected Button btn_image_login;
    protected TextView tv_background;
    protected TextView tv_my_name;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_image_login = getView().findViewById(R.id.btn_image_login);
        tv_background = getView().findViewById(R.id.tv_background);
        tv_my_name = getView().findViewById(R.id.tv_my_name);

        btn_image_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getActivity(), LoginActivity.class);
                startActivity(login);
            }
        });

        tv_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent background = new Intent(getActivity(), BackGround.class);
                startActivity(background);
            }
        });
    }
}
