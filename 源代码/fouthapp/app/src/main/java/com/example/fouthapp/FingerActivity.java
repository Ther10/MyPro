package com.example.fouthapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.fouthapp.activity.GestureLoginActivity;
import com.example.fouthapp.dao.StudentDao;
import com.hailong.biometricprompt.fingerprint.FingerprintCallback;
import com.hailong.biometricprompt.fingerprint.FingerprintVerifyManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FingerActivity extends FragmentActivity{

    private String userId;
    private String username;

    public static void actionStart(Context context, String userId, String username){
        Intent intent = new Intent(context, GestureLoginActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("username",username);
        context.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what==1){
                /*error_message = findViewById(R.id.error_message);
                error_message.setText((String)msg.obj);*/
                Toast.makeText(FingerActivity.this,(String)msg.obj,Toast.LENGTH_LONG).show();
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);
        getUserMessage();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.tvFingerprint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FingerprintVerifyManager.Builder builder = new FingerprintVerifyManager.Builder(FingerActivity.this);
                builder.usepwdVisible(true);
                builder.callback(fingerprintCallback)
                        .fingerprintColor(ContextCompat.getColor(FingerActivity.this, R.color.white))
                        .build();
            }
        });
        ActivityCollector.addActivity(this);
    }
    private void getUserMessage(){
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        username = intent.getStringExtra("username");
        System.out.println(userId);
        System.out.println(username);
    }

    private FingerprintCallback fingerprintCallback = new FingerprintCallback() {
        @Override
        public void onSucceeded() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = sdf.format(new Date());
                    System.out.println(date);
                    StudentDao dao = new StudentDao();
                    dao.setSign(userId);
                    dao.setSignTime(userId,date);

                    android.os.Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = "签到成功";
                    //向主线程发送数据
                    handler.sendMessage(msg);
                }
            }).start();
            //Toast.makeText(FingerActivity.this, getString(R.string.biometricprompt_verify_success), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            Toast.makeText(FingerActivity.this, getString(R.string.biometricprompt_verify_failed), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUsepwd() {
            Toast.makeText(FingerActivity.this, getString(R.string.fingerprint_usepwd), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(FingerActivity.this, getString(R.string.fingerprint_cancel), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onHwUnavailable() {
            Toast.makeText(FingerActivity.this, getString(R.string.biometricprompt_finger_hw_unavailable), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNoneEnrolled() {
            //弹出提示框，跳转指纹添加页面
            AlertDialog.Builder builder = new AlertDialog.Builder(FingerActivity.this);
            builder.setTitle(getString(R.string.biometricprompt_tip))
                    .setMessage(getString(R.string.biometricprompt_finger_add))
                    .setCancelable(false)
                    .setNegativeButton(getString(R.string.biometricprompt_finger_add_confirm), (new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent(Settings.ACTION_FINGERPRINT_ENROLL);
                            startActivity(intent);
                        }
                    }
                    ))
                    .setPositiveButton(getString(R.string.biometricprompt_cancel), (new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }
                    ))
                    .create().show();
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}