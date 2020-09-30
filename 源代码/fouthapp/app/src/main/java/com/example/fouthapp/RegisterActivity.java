package com.example.fouthapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fouthapp.dao.LoginDao;
import com.example.fouthapp.dao.RegisterDao;
import com.example.fouthapp.dao.StudentDao;
import com.example.fouthapp.dao.TeacherDao;
import com.example.fouthapp.entity.Student;
import com.example.fouthapp.entity.Teacher;
import com.example.fouthapp.entity.User;
import com.example.fouthapp.teacher.TeacherMainActivity;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup rg_role;
    private RadioButton radio_Teacher;
    private RadioButton radio_Student;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private User user;
    private String userId;
    private String username;
    private String password1;
    private String password2;
    private String role = "老师";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what==0){
                /*error_message = findViewById(R.id.error_message);
                error_message.setText((String)msg.obj);*/
                Toast.makeText(RegisterActivity.this,(String)msg.obj,Toast.LENGTH_LONG).show();
            }
            if (msg.what==1){
                showDialog();
            }
            if (msg.what==2){
                Toast.makeText(RegisterActivity.this,(String)msg.obj,Toast.LENGTH_LONG).show();
            }
            if (msg.what==3){
                Toast.makeText(RegisterActivity.this,(String)msg.obj,Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //初始化方法
        initUI();

        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    private void initUI() {
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_reset).setOnClickListener(this);
        findViewById(R.id.gotoLogin).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        radio_Teacher = (RadioButton)findViewById(R.id.Teacher);
        radio_Student = (RadioButton) findViewById(R.id.Student);
        editText1 = (EditText)findViewById(R.id.edit_usernum);
        editText2 = (EditText)findViewById(R.id.edit_username);
        editText3 = (EditText)findViewById(R.id.edit_password1);
        editText4 = (EditText)findViewById(R.id.edit_password2);

        userId = editText1.getText().toString().trim();
        username = editText2.getText().toString().trim();
        password1 = editText3.getText().toString().trim();
        password2 = editText4.getText().toString().trim();


        switch (view.getId())
        {
            case R.id.btn_register:
                Log.i("btn_register","注册按钮被点击");
                //如果用户名或密码为空，不进行跳转，进行提示
                if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(username)||TextUtils.isEmpty(password1)||TextUtils.isEmpty(password2))
                {
                    Log.i("isEmpty","用户名或密码为空");
                    Toast.makeText(getApplicationContext(),"用户名和密码不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                //如果用户名和密码均不为空
                else {
                    //而两次密码不一致，不进行跳转，进行提示
                    if(!(password2.equals(password1)))
                    {
                        Log.i("equal","两次密码输入不一致！");
                        Toast.makeText(getApplicationContext(),"两次密码输入不一致！",Toast.LENGTH_LONG).show();
                        return;
                    }
                    else {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if(radio_Student.isChecked()){
                                    role = "学生";
                                }
                                if(radio_Teacher.isChecked()){
                                    role = "老师";
                                }
                                //根据role的值判断老师和学生表是否有此用户
                                if (role.equals("老师")){
                                    TeacherDao tdao = new TeacherDao();
                                    Teacher teacher = tdao.findUserByTno(userId);
                                    if (teacher!=null){
                                        register();
                                    }else{
                                        Message msg = Message.obtain();
                                        msg.what = 2;
                                        msg.obj = "您尚未在本学校教师名单，请选择学生身份，或联系管理员";
                                        //向主线程发送数据
                                        handler.sendMessage(msg);
                                    }
                                }else {
                                    StudentDao sdao = new StudentDao();
                                    Student student = sdao.findUserBySno(userId);
                                    if (student!=null){
                                        register();
                                    }else{
                                        Message msg = Message.obtain();
                                        msg.what = 3;
                                        msg.obj = "您尚未在本学校学生名单，请选择老师身份，或联系管理员";
                                        //向主线程发送数据
                                        handler.sendMessage(msg);
                                    }
                                }

                            }
                        }).start();


                    }

                }
                break;
            case R.id.btn_reset:
                //清空输入数据
                editText1.setText(null);
                editText2.setText(null);
                editText3.setText(null);
                editText4.setText(null);
                rg_role=(RadioGroup)findViewById(R.id.rg_role);
                rg_role.check(R.id.Teacher);
                break;
            case R.id.gotoLogin:
                Intent intent1 = new Intent();
                intent1.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(intent1);
            default:
                break;

        }
    }

    private void register(){
        RegisterDao registerDao = new RegisterDao();
        boolean result = registerDao.register(userId,password1,username,role);
        if (result){
            user = new User(userId,password1);
            Message msg = Message.obtain();
            msg.what = 1;
            //向主线程发送数据
            handler.sendMessage(msg);
        }else{
            Message msg = Message.obtain();
            msg.what = 0;
            msg.obj = "注册失败，用户已存在";
            //向主线程发送数据
            handler.sendMessage(msg);
        }
    }

    private void showDialog() {
        Intent intent = new Intent();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("用户须知");
        builder.setMessage("是否同意协议");
        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(radio_Teacher.isChecked())
                {
                   TeacherMainActivity.actionStart(RegisterActivity.this,userId,username);
                    /*intent.setClass(getApplicationContext(), BottomActivity.class);
                    startActivity(intent);*/
                }
                else if(radio_Student.isChecked())
                {
                    BottomActivity.actionStart(RegisterActivity.this,userId,username);
                    /*intent.setClass(getApplicationContext(), BottomActivity.class);
                    startActivity(intent);*/
                }

            }
        });
        builder.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
