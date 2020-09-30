package com.example.fouthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import org.litepal.tablemanager.Connector;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;
    private Button bt_login1;
    private Button bt_register1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //初始化控件
        bt_login1 = (Button) findViewById(R.id.bt_login1);
        bt_login1.setOnClickListener(this);
        bt_register1 = (Button) findViewById(R.id.bt_register1);
        bt_register1.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        intent = new Intent();

        switch (view.getId()) {
            case R.id.bt_login1:
                Log.i("login","即将进入登录界面");
                //一次简单的数据库操作
                //Connector.getDatabase();
                intent.setClass(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_register1:
                Log.i("register","即将进入注册页面");
                //一次简单的数据库操作
                //Connector.getDatabase();
                intent.setClass(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}