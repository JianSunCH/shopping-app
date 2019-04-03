# shopping-app
This is a small shopping app.

4.1 欢迎页面(SplashActivity)
 
![avatar](http://ppdh7s4hn.bkt.clouddn.com/%E5%9B%BE%E7%89%87%201.png)

SplashActivity部分代码如下：
public class SplashActivity extends AppCompatActivity {
    protected Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }, 2000);
    }
}

4.2用户登录注册功能的实现
首先要实现 RegisterActivity类，该类用来显示用户注册时的界面。用户在点击位于注册界面的“注册”按钮之后，RegisterActivity类会调用Intent传值，将注册所输入用户名传入到LoginActivity中，简化了用户的操作。
 
 ![avatar](http://ppdh7s4hn.bkt.clouddn.com/%E5%9B%BE%E7%89%87%203.png)

Intent传值部分代码如下：
Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
intent.putExtra(REGISTERNAME, et_name);
startActivity(intent);
验证数据库中用户名和密码是否匹配的部分代码如下：
if (getIntent() != null) {
    String name = getIntent().getStringExtra(REGISTERNAME);
    et_login_name.setText(name);
}

btn_login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String uname = et_login_name.getText().toString();
        String upassword = et_login_password.getText().toString();

        userDAO = new UserDAO(LoginActivity.this);
        user = new User(uname, upassword);
        Cursor cursor = userDAO.getUser("name", uname);
        if (cursor.moveToFirst()) {
            String sqliteName = cursor.getString(cursor.getColumnIndex("name"));
            String sqlitePass = cursor.getString(cursor.getColumnIndex("password"));
            if (uname.equals(sqliteName) && upassword.equals(sqlitePass)) {
                Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_LONG).show();
                cursor.close();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else if (uname.equals("")) {
                Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
            } else if (upassword.equals("")) {
                Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
});


如果忘记密码，可以点击找回密码，这是一个自定义的Dialog:
![avatar](http://ppdh7s4hn.bkt.clouddn.com/%E5%9B%BE%E7%89%87%204.png)
![avatar](http://ppdh7s4hn.bkt.clouddn.com/%E5%9B%BE%E7%89%87%205.png)
	
public MyDialog(@NonNull Context context, @StyleRes int themeResId) {
    super(context, themeResId);
    setContentView(R.layout.dialoglayout);

    initView();

    noBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    });
    yesBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String uname = edt_name.getText().toString();
            String upassword = edt_newpass.getText().toString();
            user = new User(uname, upassword);
            userDAO = new UserDAO(getContext());
            Cursor cursor = userDAO.getUser("name", uname);
            if (uname.equals("") || upassword.equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setTitle("提示")
                        .setMessage("用户名或密码不能为空！")
                        .setNegativeButton("确定", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();
                Toast.makeText(getContext(), "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
            } else {
                if (cursor.moveToFirst()) {
                    String sqliteName = cursor.getString(cursor.getColumnIndex("name"));
                    if (uname.equals(sqliteName)) {
                        userDAO.updateUser(user);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                                .setTitle("提示")
                                .setMessage("您已修改密码成功！")
                                .setPositiveButton("确定", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        builder.show();
                        Toast.makeText(getContext(), "修改密码成功！", Toast.LENGTH_LONG).show();
                        cursor.close();
                        dismiss();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                            .setTitle("提示")
                            .setMessage("没有此用户！")
                            .setNegativeButton("确定", new OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                    Toast.makeText(getContext(), "没有此用户！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });
}


4.3 主界面布局
 
图片数据以及文字数据均有网络获取，数据获取由json解析得来，部分代码如下：
![avatar](http://ppdh7s4hn.bkt.clouddn.com/%E5%9B%BE%E7%89%87%206.png)
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


GridViewAdapter所使用部分代码如下：

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






4.4 Fragment组件
之所以使用fragment是因为它可以做许多Activity可以做的事，辅助Activity让功能可以做得更加强大;一次编写，可以多个地方可以使用，解放了Activity。
MainActivity中fragment中部分代码如下：
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
    this.getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.rl_content, mainFragment)
            .add(R.id.rl_content, shopFragment).hide(shopFragment)
            .add(R.id.rl_content, userFragment).hide(userFragment)
            .commit();
}

public void initView() {
    menu_main = findViewById(R.id.menu_main);
    menu_shop = findViewById(R.id.menu_shop);
    menu_user = findViewById(R.id.menu_user);

    menu_main.setOnClickListener(this);
    menu_shop.setOnClickListener(this);
    menu_user.setOnClickListener(this);
}

@Override
public void onClick(View v) {
    switch (v.getId()) {
        case R.id.menu_main :
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .show(mainFragment)
                    .hide(shopFragment)
                    .hide(userFragment)
                    .commit();
            break;
        case R.id.menu_shop :
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .hide(mainFragment)
                    .show(shopFragment)
                    .hide(userFragment)
                    .commit();
            break;
        case R.id.menu_user :
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .hide(mainFragment)
                    .hide(shopFragment)
                    .show(userFragment)
                    .commit();
            break;
    }
}


4.5商品信息展示功能的实现
商品信息展示模块要实现的功能主要是商品分类和商品信息展示。该界面详情如图所示。
所显示数据也是通过网络获取然后json解析所得。
![avatar](http://ppdh7s4hn.bkt.clouddn.com/%E5%9B%BE%E7%89%87%208.png)
	 
4.6后台用户信息管理模块
![avatar](http://ppdh7s4hn.bkt.clouddn.com/%E5%9B%BE%E7%89%87%209.png)
![avatar](http://ppdh7s4hn.bkt.clouddn.com/%E5%9B%BE%E7%89%87%2010.png)
数据库创建的部分代码如下：
private void initDB() {
        String path = Environment.getExternalStorageDirectory() + "/user.db";
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



数据库逻辑的部分代码如下：
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
            Toast.makeText(this, "添加成功，新用户id是：" + id, Toast.LENGTH_SHORT).show();
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
