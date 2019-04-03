package com.example.sunjian.mutao_5_7.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.example.sunjian.mutao_5_7.entity.User;

import java.io.File;

/**
 * Created by sunjian on 2018/4/28.
 */

public class UserDAO {
    private SQLiteDatabase db;

    public UserDAO(Context context) {
        String path = Environment.getExternalStorageDirectory() + File.separator + "user.db";
//        String path = context.getFilesDir() + "/user.db";
        SQLiteOpenHelper helper = new SQLiteOpenHelper(context, path, null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            }
        };
        db = helper.getReadableDatabase();
    }

    public void addUser(User user) {
        String sql = "insert into info_tb (name,password) values(?,?)";
        db.execSQL(sql, new Object[]{user.getName(), user.getPassword()});
    }

    public Cursor getUser(String... strs) {
        String sql = "select * from info_tb";
        if (strs.length != 0) {
            sql += " where " + strs[0] + "='" + strs[1] + "'";
        }
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public void updateUser(User user) {
        String sql = "update info_tb set password=? where name=?";
        db.execSQL(sql, new Object[]{user.getPassword(), user.getName()});
    }
}
