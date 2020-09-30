package com.example.fouthapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fouthapp.dao.LoginDao;
import com.example.fouthapp.entity.User;
import com.example.fouthapp.teacher.TeacherMainActivity;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    //用于日志检查错误
    private static final String LOGINBT = "loginbt";
    private static final String TAG = "tag";
    //用户名、密码、身份声明
    private EditText editText1;
    private EditText editText2;
    private RadioGroup rg_role;
    private RadioButton radio_Teacher;
    private RadioButton radio_Student;
    private String userId;
    private String password=null;
    private String username=null;
    private String role;
    private Button btn_login;
    //意图声明，用于跳转页面
    private Intent intent;
    private MyDatabaseHelper dbHelper;

    private String dbUserId;
    private String dbPassword;

    private TextView error_message;

    private int flag=0;//用于遍历后判断用户是否存在，不存在为0，存在置为1

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what==0){
                /*error_message = findViewById(R.id.error_message);
                error_message.setText((String)msg.obj);*/
                Toast.makeText(LoginActivity.this,"用户或密码错误，登录失败",Toast.LENGTH_LONG).show();
            }
            if (msg.what==1){
                if (radio_Teacher.isChecked()){
                    if (role.equals("老师"))
                        showDialog();
                    else
                        Toast.makeText(LoginActivity.this,"身份选择错误！！",Toast.LENGTH_LONG).show();
                }else {
                    if (role.equals("学生"))
                        showDialog();
                    else
                        Toast.makeText(LoginActivity.this,"身份选择错误！！",Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化控件
        initUI();

    }



    private void initUI() {

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_reset).setOnClickListener(this);
        findViewById(R.id.gotoRegister).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        radio_Teacher = (RadioButton) findViewById(R.id.Teacher);
        radio_Student = (RadioButton) findViewById(R.id.Student);
        rg_role = (RadioGroup) findViewById(R.id.rg_role);
        editText1 = (EditText) findViewById(R.id.edit_usernum);
        editText2 = (EditText) findViewById(R.id.edit_password);

        //获取用户名何密码相应值
        userId = editText1.getText().toString().trim();
        password = editText2.getText().toString().trim();
        //实例化意图对象
        intent = new Intent();

        //根据点击位置做出不同反应
        switch (view.getId()) {
            //跳转到学生登录界面
            case R.id.btn_login:

                Log.d(LOGINBT, "登录按钮被点击");
                //执行输入校验
                if(validate())
                {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            LoginDao dao = new LoginDao();
                            User user = dao.checkUser(userId,password);
                            if (user!=null){
                                flag=1;
                                //Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_LONG).show();
                                //showDialog();
                                username = user.getUsername();
                                role = user.getRole();
                                Message msg = Message.obtain();
                                msg.what = 1;
                                //向主线程发送数据
                                handler.sendMessage(msg);
                                /*Intent intent = new Intent(getApplication(),BottomActivity.class);
                                startActivity(intent);*/
                            }else{
                                Message msg = Message.obtain();
                                msg.what = 0;
                                msg.obj = "登陆失败，用户名或密码错误";
                                //向主线程发送数据
                                handler.sendMessage(msg);
                            }
                        }
                    }).start();

                }

                break;
            case R.id.btn_reset:
                //清空输入数据
                editText1.setText(null);
                editText2.setText(null);
                rg_role.check(R.id.Teacher);
                break;
            case R.id.gotoRegister:
                //跳转到注册页面
                Log.d(TAG,"即将跳转到注册页面");
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


    private boolean validate() {

        if((!TextUtils.isEmpty(userId))&&(!TextUtils.isEmpty(password)))
        {
            Log.i("validate","用户名和密码输入合法！");
            return true;
        }

        else{
            Log.i("validate","用户账号和密码不能为空");
            Toast.makeText(this,"用户账号不能为空！",Toast.LENGTH_LONG).show();
            return false;
        }


    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("用户须知");
        builder.setMessage("是否同意协议");
        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (radio_Teacher.isChecked()) {
                    /*intent.setClass(getApplicationContext(), BottomActivity.class);
                    startActivity(intent);*/
                    System.out.println("actionStart"+username);
                    TeacherMainActivity.actionStart(LoginActivity.this,userId,username);

                } else if (radio_Student.isChecked()) {

                    /*intent.setClass(getApplicationContext(), BottomActivity.class);
                    startActivity(intent);*/
                    BottomActivity.actionStart(LoginActivity.this,userId,username);

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
