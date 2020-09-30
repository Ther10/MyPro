package com.example.fouthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar("首页");
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public void ToMapSign(View view){
        Intent intent = new Intent(this,MapSign.class);
        this.startActivity(intent);
    }

    public void ToGesture(View view){
        Intent intent = new Intent(this, GestureActivity.class);
        this.startActivity(intent);
    }

    public void ToFinger(View view){
        Intent intent = new Intent(this, FingerActivity.class);
        this.startActivity(intent);
    }
}