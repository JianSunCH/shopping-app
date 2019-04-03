package com.example.sunjian.mutao_5_7.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunjian on 2018/4/28.
 */

public class Food {
    protected int status;
    protected String msg;
    protected List<FoodInfo> foodInfoList = new ArrayList<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<FoodInfo> getFoodInfoList() {
        return foodInfoList;
    }

    public void setFoodInfoList(List<FoodInfo> foodInfoList) {
        this.foodInfoList = foodInfoList;
    }
}
