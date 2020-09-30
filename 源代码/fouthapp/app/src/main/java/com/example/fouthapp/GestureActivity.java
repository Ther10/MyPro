package com.example.fouthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fouthapp.activity.CreateGestureActivity;
import com.example.fouthapp.activity.GestureLoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GestureActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        setToolbar("手势打卡");
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ActivityCollector.addActivity(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @OnClick(R.id.create_gesture)
    void toCreateGesture(){
        //Log.d("create","create");
        Intent intent = new Intent(GestureActivity.this, CreateGestureActivity.class);
        startActivity(intent);
        //this.finish();
    }

    @OnClick(R.id.login_gesture)
    void toLoginGesture(){
        Intent intent = new Intent(GestureActivity.this, GestureLoginActivity.class);
        startActivity(intent);
        //this.finish();
    }
}