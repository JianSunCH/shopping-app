package com.example.sunjian.mutao_5_7.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sunjian.mutao_5_7.R;
import com.example.sunjian.mutao_5_7.entity.Menu;

import java.util.List;

/**
 * Created by sunjian on 2018/4/28.
 */

public class GridViewAdapter extends BaseAdapter {
    protected Context context;
    protected List<Menu> menuList;

    public GridViewAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_main_menu, null);
            viewHolder.image_menu_icon = convertView.findViewById(R.id.image_menu_icon);
            viewHolder.tv_menu_name = convertView.findViewById(R.id.tv_menu_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Menu menu = menuList.get(position);
        viewHolder.image_menu_icon.setImageResource(menu.icon);
        viewHolder.tv_menu_name.setText(menu.menuName);

        return convertView;
    }
    class ViewHolder {
        ImageView image_menu_icon;
        TextView tv_menu_name;
    }
}
