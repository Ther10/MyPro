package com.example.fouthapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.bin.david.form.core.SmartTable;
import com.example.fouthapp.activity.GestureLoginActivity;
import com.example.fouthapp.dao.StudentDao;
import com.example.fouthapp.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class MySignActivity extends BaseActivity {

    private SmartTable table;
    private List<Student> list = new ArrayList<Student>();

    private String userId;
    private String username;

    public static void actionStart(Context context, String userId, String username){
        Intent intent = new Intent(context, MySignActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("username",username);
        context.startActivity(intent);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what==0){
                table = findViewById(R.id.table);
                table.setData(list);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sign);

        getUserMessage();
        //table.setZoom(true,2,0.2f);
        /*list.add(new User("2018413449","123456","admin"));
        list.add(new User("2018413449","123456","admin"));
        list.add(new User("2018413449","123456","admin"));*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                StudentDao dao = new StudentDao();
                list = dao.outMessageBySno(userId);
                Message msg = Message.obtain();
                msg.what = 0;
                //向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();

        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    private void getUserMessage(){
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        username = intent.getStringExtra("username");
        System.out.println(userId);
        System.out.println(username);
    }
}