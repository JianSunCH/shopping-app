package com.example.sunjian.mutao_5_7.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sunjian.mutao_5_7.activity.Address;
import com.example.sunjian.mutao_5_7.activity.Product3;
import com.example.sunjian.mutao_5_7.R;
import com.example.sunjian.mutao_5_7.adapter.GridViewAdapter;
import com.example.sunjian.mutao_5_7.adapter.ListViewAdapter;
import com.example.sunjian.mutao_5_7.entity.Food;
import com.example.sunjian.mutao_5_7.entity.FoodInfo;
import com.example.sunjian.mutao_5_7.util.DataUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunjian on 2018/4/28.
 */

public class MainFragment extends Fragment {
    public static final String URL_STRING = "http://www.imooc.com/api/shopping?type=11";
    public static final String STATUS = "status";
    public static final String MSG = "msg";
    public static final String DATA = "data";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String COUNT = "count";
    public static final String DESCRIPTION = "description";
    public static final String ACTION = "action";
    public static final String IMG = "img";

    protected TextView tv_product3;
    protected Button btn_address;

    protected GridView gv_main_menu;
    protected int[] menuIcons = {R.drawable.fly1, R.drawable.car, R.drawable.autombile1, R.drawable.cake,
            R.drawable.food, R.drawable.watch, R.drawable.cp, R.drawable.phone};
    protected String[] menus;
    protected String[] img_url;

    protected ListView list_view;
    public static final List<FoodInfo> FOOD_INFO_LIST = new ArrayList<>();

    Bitmap[] bitmaps = new Bitmap[6];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_product3 = getView().findViewById(R.id.tv_product3);
        btn_address = getView().findViewById(R.id.btn_address);
        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Address.class));
            }
        });

        gv_main_menu = getView().findViewById(R.id.gv_main_menu);
        menus = this.getActivity().getResources().getStringArray(R.array.main_menu);
        img_url = this.getActivity().getResources().getStringArray(R.array.img_url);

        //菜单
        //布局样式 采用GridView
        GridViewAdapter gridViewAdapter = new GridViewAdapter(getActivity(), DataUtil.getMainMenus(menuIcons, menus));
        gv_main_menu.setAdapter(gridViewAdapter);

        list_view = getView().findViewById(R.id.list_view);
        new RequestDataAsyncTask().execute();
        list_view.setOnItemClickListener(new addListener());

    }

    public class RequestDataAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            for (int i = 0; i < 6; i++) {
                Bitmap bitmap = Product3.getImagefromNetWork(img_url[i]);
                bitmaps[i] = bitmap;
            }
            return request(URL_STRING);
        }

        private String request(String urlString) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(30000);
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    return stringBuilder.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Food food = new Food();
            try {
                JSONObject jsonObject = new JSONObject(result);
                final int status = jsonObject.getInt(STATUS);
                final String msg = jsonObject.getString(MSG);
                food.setStatus(status);
                food.setMsg(msg);

                JSONArray dataArray = jsonObject.getJSONArray(DATA);
                for (int i = 0; i < dataArray.length(); i++) {
                    FoodInfo foodInfo = new FoodInfo();
                    JSONObject jsonObject1 = (JSONObject) dataArray.get(i);
                    final String name = jsonObject1.getString(NAME);
                    final String price = jsonObject1.getString(PRICE);
                    final String count = jsonObject1.getString(COUNT);
                    final String description = jsonObject1.getString(DESCRIPTION);
                    final String action = jsonObject1.getString(ACTION);
                    final String img = jsonObject1.getString(IMG);
                    foodInfo.setName(name);
                    foodInfo.setPrice(price);
                    foodInfo.setCount(count);
                    foodInfo.setDescription(description);
                    foodInfo.setAction(action);
                    for (int j = 0; j < bitmaps.length; j++) {
                        foodInfo.setImg(bitmaps[i]);
                    }
                    FOOD_INFO_LIST.add(foodInfo);
                }
                food.setFoodInfoList(FOOD_INFO_LIST);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            list_view.setAdapter(new ListViewAdapter(getActivity(), food.getFoodInfoList()));

        }
    }

    private class addListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (FOOD_INFO_LIST.get(position).getName().equals("松仁大虾")) {
                startActivity(new Intent(getActivity(), Product3.class));
            }
        }
    }

}
