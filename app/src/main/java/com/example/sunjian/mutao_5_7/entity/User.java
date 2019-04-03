package com.example.sunjian.mutao_5_7.entity;

/**
 * Created by sunjian on 2018/4/28.
 */

public class User {
    private int id;
    private String name;
    private String password;
    private String gender;

    public User() {

    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String gender) {
        this.name = name;
        this.password = password;
        this.gender = gender;
    }

    public User(int id, String name, String password, String gender) {
        super();
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
