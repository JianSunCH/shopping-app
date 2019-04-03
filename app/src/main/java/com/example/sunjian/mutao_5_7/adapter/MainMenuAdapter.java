package com.example.sunjian.mutao_5_7.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sunjian.mutao_5_7.R;
import com.example.sunjian.mutao_5_7.entity.Menu;

import java.util.List;

/**
 * Created by sunjian on 2018/4/28.
 */

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuViewHolder> {
    protected Context context;
    protected List<Menu> menus;

    public MainMenuAdapter(Context context, List<Menu> menus) {
        this.context = context;
        this.menus = menus;
    }

    @Override
    public MainMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainMenuViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main_menu, null));
    }

    @Override
    public void onBindViewHolder(MainMenuViewHolder holder, int position) {
        Menu menu = menus.get(position);
        holder.imageMenuIcon.setImageResource(menu.icon);
        holder.tv_menu_name.setText(menu.menuName);
    }

    @Override
    public int getItemCount() {
        return null != menus ? menus.size() : 0;
    }
}

class MainMenuViewHolder extends RecyclerView.ViewHolder {
    ImageView imageMenuIcon;
    TextView tv_menu_name;

    public MainMenuViewHolder(View itemView) {
        super(itemView);
        imageMenuIcon = itemView.findViewById(R.id.image_menu_icon);
        tv_menu_name = itemView.findViewById(R.id.tv_menu_name);
    }
}
