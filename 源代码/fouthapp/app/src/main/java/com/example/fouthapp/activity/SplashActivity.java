package com.example.fouthapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fouthapp.R;
import com.example.fouthapp.util.BitmapUtil;
import com.example.fouthapp.util.cache.ACache;
import com.example.fouthapp.util.constant.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_iv)
    ImageView splashIv;

    private ACache aCache;
    private Bitmap splashBitmap;
    private int screenWidth, screenHeight;
    private Handler handler = new Handler(){};
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        this.init();
    }

    private void init() {
        aCache = ACache.get(this);
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        //Log.e(TAG, "screen width: " + screenWidth);
        //Log.e(TAG, "screen height: " + screenHeight);
        //Log.e(TAG, "imageview width: " + splashIv.getWidth());
        //Log.e(TAG, "imageview height: " + splashIv.getHeight());
        //Log.e(TAG, "status bar height: " + getStatusBarHeight());
        splashBitmap = BitmapUtil.resizeBitmap(screenWidth, screenHeight - getStatusBarHeight(),
                BitmapFactory.decodeResource(getResources(), R.mipmap.splash));
        splashIv.setImageBitmap(splashBitmap);
        this.doJump();
    }

    private void doJump() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String gesturePassword = aCache.getAsString(Constant.GESTURE_PASSWORD);
                if(gesturePassword == null || "".equals(gesturePassword)) {
                    Intent intent = new Intent(SplashActivity.this, CreateGestureActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, GestureLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
