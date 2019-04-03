package com.example.sunjian.mutao_5_7.entity;

import android.graphics.Bitmap;

/**
 * Created by sunjian on 2018/4/28.
 */

public class ProductEntity {
    protected String name;
    protected Bitmap img;
    protected int originalprice;
    protected int tPrice;
    protected String price;
    protected String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(int originalprice) {
        this.originalprice = originalprice;
    }

    public int gettPrice() {
        return tPrice;
    }

    public void settPrice(int tPrice) {
        this.tPrice = tPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
