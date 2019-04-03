package com.example.sunjian.mutao_5_7.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sunjian.mutao_5_7.R;
import com.example.sunjian.mutao_5_7.entity.FoodInfo;

import java.util.List;

/**
 * Created by sunjian on 2018/4/28.
 */

public class ListViewAdapter extends BaseAdapter {
    protected List<FoodInfo> foodInfoList;
    protected Context context;

    public ListViewAdapter(Context context, List<FoodInfo> foodInfoList) {
        this.context = context;
        this.foodInfoList = foodInfoList;
    }

    @Override
    public int getCount() {
        return foodInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_list_view, null);
            viewHolder.image_item_list_view = convertView.findViewById(R.id.image_item_list_view);
            viewHolder.tv_name_item_list_view = convertView.findViewById(R.id.tv_name_item_list_view);
            viewHolder.tv_description_item_list_view = convertView.findViewById(R.id.tv_description_item_list_view);
            viewHolder.tv_price_item_list_view = convertView.findViewById(R.id.tv_price_item_list_view);
            viewHolder.tv_action_item_list_view = convertView.findViewById(R.id.tv_action_item_list_view);
            viewHolder.tv_count_item_list_view = convertView.findViewById(R.id.tv_count_item_list_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.image_item_list_view.setImageBitmap(foodInfoList.get(position).getImg());
        viewHolder.tv_name_item_list_view.setText(foodInfoList.get(position).getName());
        viewHolder.tv_description_item_list_view.setText(foodInfoList.get(position).getDescription());
        viewHolder.tv_price_item_list_view.setText(foodInfoList.get(position).getPrice());
        viewHolder.tv_action_item_list_view.setText(foodInfoList.get(position).getAction());
        viewHolder.tv_count_item_list_view.setText(foodInfoList.get(position).getCount());

        return convertView;
    }

    class ViewHolder {
        ImageView image_item_list_view;
        TextView tv_name_item_list_view;
        TextView tv_description_item_list_view;
        TextView tv_price_item_list_view;
        TextView tv_action_item_list_view;
        TextView tv_count_item_list_view;

    }
}
