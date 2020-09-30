package com.example.fouthapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fouthapp.ActivityCollector;
import com.example.fouthapp.BaseActivity;
import com.example.fouthapp.MapSign;
import com.example.fouthapp.R;
import com.example.fouthapp.RegisterActivity;
import com.example.fouthapp.dao.StudentDao;
import com.example.fouthapp.util.cache.ACache;
import com.example.fouthapp.util.constant.Constant;
import com.example.lockpattern.util.LockPatternUtil;
import com.example.lockpattern.widget.LockPatternView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sym on 2015/12/24.
 */
public class GestureLoginActivity extends BaseActivity {

    private static final String TAG = "LoginGestureActivity";

    @BindView(R.id.lockPatternView)
    LockPatternView lockPatternView;
    @BindView(R.id.messageTv)
    TextView messageTv;
    @BindView(R.id.forgetGestureBtn)
    Button forgetGestureBtn;

    private ACache aCache;
    private static final long DELAYTIME = 600l;
    private byte[] gesturePassword;

    private String userId;
    private String username;

    public static void actionStart(Context context, String userId, String username){
        Intent intent = new Intent(context, GestureLoginActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("username",username);
        context.startActivity(intent);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            if (msg.what==1){
                /*error_message = findViewById(R.id.error_message);
                error_message.setText((String)msg.obj);*/
                Toast.makeText(GestureLoginActivity.this,(String)msg.obj,Toast.LENGTH_LONG).show();
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_login);
        getUserMessage();
        setToolbar("手势打卡");
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        this.init();

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

    private void init() {
        aCache = ACache.get(GestureLoginActivity.this);
        //得到当前用户的手势密码
        gesturePassword = aCache.getAsBinary(Constant.GESTURE_PASSWORD);
        lockPatternView.setOnPatternListener(patternListener);
        updateStatus(Status.DEFAULT);
    }

    private LockPatternView.OnPatternListener patternListener = new LockPatternView.OnPatternListener() {

        @Override
        public void onPatternStart() {
            lockPatternView.removePostClearPatternRunnable();
        }

        @Override
        public void onPatternComplete(List<LockPatternView.Cell> pattern) {
            if(pattern != null){
                if(LockPatternUtil.checkPattern(pattern, gesturePassword)) {
                    updateStatus(Status.CORRECT);
                } else {
                    updateStatus(Status.ERROR);
                }
            }
        }
    };

    /**
     * 更新状态
     * @param status
     */
    private void updateStatus(Status status) {
        messageTv.setText(status.strId);
        messageTv.setTextColor(getResources().getColor(status.colorId));
        switch (status) {
            case DEFAULT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                break;
            case ERROR:
                lockPatternView.setPattern(LockPatternView.DisplayMode.ERROR);
                lockPatternView.postClearPatternRunnable(DELAYTIME);
                break;
            case CORRECT:
                lockPatternView.setPattern(LockPatternView.DisplayMode.DEFAULT);
                loginGestureSuccess();
                break;
        }
    }

    /**
     * 手势登录成功（去首页）
     */
    private void loginGestureSuccess() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = sdf.format(new Date());
                System.out.println(date);
                StudentDao dao = new StudentDao();
                dao.setSign(userId);
                dao.setSignTime(userId,date);

                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = "签到成功";
                //向主线程发送数据
                handler.sendMessage(msg);
            }
        }).start();

    }

    /**
     * 忘记手势密码（去账号登录界面）
     */
    @OnClick(R.id.forgetGestureBtn)
    void forgetGesturePasswrod() {
        Intent intent = new Intent(GestureLoginActivity.this, CreateGestureActivity.class);
        startActivity(intent);
        this.finish();
    }

    private enum Status {
        //默认的状态
        DEFAULT(R.string.gesture_default, R.color.grey_a5a5a5),
        //密码输入错误
        ERROR(R.string.gesture_error, R.color.red_f4333c),
        //密码输入正确
        CORRECT(R.string.gesture_correct, R.color.grey_a5a5a5);

        private Status(int strId, int colorId) {
            this.strId = strId;
            this.colorId = colorId;
        }
        private int strId;
        private int colorId;
    }
}
