package com.example.sunjian.mutao_5_7.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunjian.mutao_5_7.R;
import com.example.sunjian.mutao_5_7.entity.ProductEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sunjian on 2018/4/28.
 */

public class Product3 extends AppCompatActivity {
    public static final String PRODUCT_URL = "http://www.imooc.com/api/shopping?type=12";
    public static final String NAME = "name";
    public static final String IMG = "img";
    public static final String ORIGINALPRICE = "originalprice";
    public static final String T_PRICE = "tPrice";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String DATA = "data";
    public static final String IMG_URL = "http://www.imooc.com/data/shopping/img/xia.png";
    protected Button btn_product3;
    protected TextView tv_name_product;
    protected ImageView image_product;
    protected TextView tv_originalprice_product;
    protected TextView tv_tPrice_product;
    protected Button btn_price_product;
    protected TextView tv_description_product;
    protected ProductEntity productEntity;
    protected RatingBar ratingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product3);
        initView();
        btn_product3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBarListener());
        btn_price_product.setOnClickListener(new PriceListener());
        new ProductDataAsyncTask().execute();
    }

    private class RatingBarListener implements RatingBar.OnRatingBarChangeListener {

        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            Toast.makeText(Product3.this, "您选择了" + rating + "星", Toast.LENGTH_SHORT).show();
        }
    }

    private class PriceListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Product3.this)
                    .setTitle("提示")
                    .setMessage("您已购买成功！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.show();
        }
    }

    private void initView() {
        btn_product3 = findViewById(R.id.btn_product3);
        tv_name_product = findViewById(R.id.tv_name_product);
        image_product = findViewById(R.id.image_product);
        tv_originalprice_product = findViewById(R.id.tv_originalprice_product);
        tv_tPrice_product = findViewById(R.id.tv_tPrice_product);
        btn_price_product = findViewById(R.id.btn_price_product);
        tv_description_product = findViewById(R.id.tv_description_product);
        ratingBar = findViewById(R.id.ratingBar);
        productEntity = new ProductEntity();
    }

    public class ProductDataAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            Bitmap bitmap = getImagefromNetWork(IMG_URL);
            productEntity.setImg(bitmap);
            return request(PRODUCT_URL);

        }

        private String request(String productUrl) {
            try {
                URL url = new URL(productUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(30000);
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String length;
                    while ((length = bufferedReader.readLine()) != null) {
                        stringBuilder.append(length);
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
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject data = jsonObject.getJSONObject(DATA);
                String name = data.getString(NAME);
                String img = data.getString(IMG);
                int originalprice = data.getInt(ORIGINALPRICE);
                int tPrice = data.getInt(T_PRICE);
                String price = data.getString(PRICE);
                String description = data.getString(DESCRIPTION);

                productEntity.setName(name);
                productEntity.setOriginalprice(originalprice);
                productEntity.settPrice(tPrice);
                productEntity.setPrice(price);
                productEntity.setDescription(description);

                tv_name_product.setText(productEntity.getName());
                image_product.setImageBitmap(productEntity.getImg());
                tv_originalprice_product.setText("¥" + productEntity.getOriginalprice());
                tv_tPrice_product.setText(productEntity.gettPrice() + "");
                btn_price_product.setText("¥" + productEntity.getPrice() + "拿下");
                tv_description_product.setText(productEntity.getDescription());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public static Bitmap getImagefromNetWork(String path) {
        try {

            URL url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(10 * 1000);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}
