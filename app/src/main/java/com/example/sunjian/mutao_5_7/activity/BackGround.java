package com.example.sunjian.mutao_5_7.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.sunjian.mutao_5_7.R;

/**
 * Created by sunjian on 2018/4/28.
 */

public class BackGround extends AppCompatActivity {
    private Button btn_back;
    private EditText nameEdt, passwordEdt, idEdt;
    private RadioGroup genderGp;
    private ListView stuList;
    private RadioButton malerb;
    private String genderStr = "男";
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_ground);
        initDB();
        initView();
        sexChoice();
    }

    private void initView() {
        btn_back = findViewById(R.id.btn_back);
        nameEdt = findViewById(R.id.name_edt);
        passwordEdt = findViewById(R.id.password_edt);
        idEdt = findViewById(R.id.id_edt);
        malerb = findViewById(R.id.male);
        genderGp = findViewById(R.id.gender_gp);
        stuList = findViewById(R.id.stu_list);
    }

    private void initDB() {
        String path = Environment.getExternalStorageDirectory() + "/user.db";
//        String path = getFilesDir() + "/user.db";
        SQLiteOpenHelper helper = new SQLiteOpenHelper(this, path, null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                Toast.makeText(BackGround.this, "数据库创建", Toast.LENGTH_SHORT).show();
                String sql = "create table info_tb (_id integer primary key autoincrement," +
                        "name varhcar(20)," +
                        "password varhcar(20), " +
                        "gender varhcar(4))";
                sqLiteDatabase.execSQL(sql);
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
                Toast.makeText(BackGround.this, "数据库升级", Toast.LENGTH_SHORT).show();
            }
        };
        db = helper.getReadableDatabase();
    }

    private void sexChoice() {
        genderGp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.male) {
                    //“男”
                    genderStr = "男";
                } else {
                    //"女"
                    genderStr = "女";
                }
            }
        });
    }

    public void operate(View v) {
        String nameStr = nameEdt.getText().toString();
        String passStr = passwordEdt.getText().toString();
        String idStr = idEdt.getText().toString();

        switch (v.getId()) {
            case R.id.insert_btn:
                ContentValues values = new ContentValues();
                values.put("name", nameStr);
                values.put("password", passStr);
                values.put("gender", genderStr);
                long id = db.insert("info_tb", null, values);
                Toast.makeText(this, "添加成功，新学员学号是：" + id, Toast.LENGTH_SHORT).show();
                break;
            case R.id.select_btn:
                Cursor c = db.query("info_tb", null, null, null, null, null, null);
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                        this, R.layout.item, c,
                        new String[]{"_id", "name", "password", "gender"},
                        new int[]{R.id.id_item, R.id.name_item, R.id.pass_item, R.id.gender_item});
                stuList.setAdapter(adapter);
                break;
            case R.id.delete_btn:
                int count = db.delete("info_tb", "_id=?", new String[]{idStr});
                if (count > 0) {
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.update_btn:
                ContentValues values2 = new ContentValues();
                values2.put("name", nameStr);
                values2.put("password", passStr);
                values2.put("gender", genderStr);
                int count2 = db.update("info_tb", values2, "_id=?", new String[]{idStr});
                if (count2 > 0) {
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_back:
                finish();
                break;
        }
        nameEdt.setText("");
        passwordEdt.setText("");
        idEdt.setText("");
        malerb.setChecked(true);
        return;
    }
}
