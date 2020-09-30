package com.example.fouthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Message;
import com.example.fouthapp.dao.UserDao;
import com.example.fouthapp.entity.User;

public class ChangePassActivity extends BaseActivity implements View.OnClickListener {

    private EditText old_pass;
    private EditText new_pass;
    private EditText re_new_pass;
    private Button btn_change_pass;
    private Button btn_reset;

    private String userId;
    private String username;
    private String oldpass ;
    private String newpass ;
    private String renewpass ;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what==0){
                /*error_message = findViewById(R.id.error_message);
                error_message.setText((String)msg.obj);*/
                Toast.makeText(ChangePassActivity.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
            }
            if (msg.what ==1){
                Toast.makeText(ChangePassActivity.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
                ActivityCollector.finishAll();
            }
            if (msg.what == 2){
                Toast.makeText(ChangePassActivity.this,(String)msg.obj,Toast.LENGTH_SHORT).show();
            }

        }
    };

    public static void actionStart(Context context, String userId, String username){
        Intent intent = new Intent(context,ChangePassActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("username",username);
        context.startActivity(intent);
    }

    private void getUserMessage(){
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        username = intent.getStringExtra("username");
        System.out.println(userId);
        System.out.println(username);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        //获取用户登录信息
        getUserMessage();
        //获取各控件
        old_pass = (EditText) findViewById(R.id.old_pass);
        new_pass = (EditText) findViewById(R.id.new_pass);
        re_new_pass = (EditText) findViewById(R.id.re_new_pass);
        btn_change_pass = (Button) findViewById(R.id.btn_change_pass);
        btn_reset = (Button) findViewById(R.id.btn_reset);

        //更改密码
        btn_change_pass.setOnClickListener(this);
        btn_reset.setOnClickListener(this);

        ActivityCollector.addActivity(this);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_change_pass:
                changePass();
                break;
            case R.id.btn_reset:
                old_pass.setText(null);
                new_pass.setText(null);
                re_new_pass.setText(null);
                break;

        }
    }

    private void  changePass(){
        oldpass = old_pass.getText().toString().trim();
        newpass = new_pass.getText().toString().trim();
        renewpass = re_new_pass.getText().toString().trim();

        System.out.println(oldpass);
        System.out.println(newpass);
        System.out.println(renewpass);

        if (oldpass.isEmpty()||newpass.isEmpty()||renewpass.isEmpty()){
            Toast.makeText(ChangePassActivity.this,"请将信息输入完整！！！",Toast.LENGTH_SHORT).show();
            return;
        }else {
            if (!newpass.equals(renewpass)){
                Toast.makeText(ChangePassActivity.this,"两次输入密码不一致！！！",Toast.LENGTH_SHORT).show();
                return;
            }else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        UserDao dao = new UserDao();
                        String passByUserId = dao.getPassByUserId(userId);
                        if (passByUserId.equals(oldpass)){
                            boolean result = dao.changePass(userId, newpass);
                            if (result){
                                Message msg = Message.obtain();
                                msg.what = 1;
                                msg.obj = "密码更改成功,请重新登录！！！";
                                handler.sendMessage(msg);
                            }else {
                                Message msg = Message.obtain();
                                msg.what = 2;
                                msg.obj = "密码更改失败！！！";
                                handler.sendMessage(msg);
                            }
                        }else {
                            Message msg =Message.obtain();
                            msg.what = 0;
                            msg.obj = "旧密码错误，请重新输入！！！";
                            handler.sendMessage(msg);
                        }

                    }
                }).start();
            }
        }



    }
}