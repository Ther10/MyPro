package com.example.fouthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessageActivity extends BaseActivity {

    private TextView message_title;
    private Button check_in;

    public static void actionStart(Context context, String messageId, String messageName){
        Intent intent = new Intent(context,MessageActivity.class);
        intent.putExtra("messageId",messageId);
        intent.putExtra("messageName",messageName);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        String messageName = getIntent().getStringExtra("messageName");

        //设置通知标题
        message_title = (TextView) findViewById(R.id.message_title);
        message_title.setText(messageName);

        //设置打卡点击事件
        check_in = (Button) findViewById(R.id.check_in);
        check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MessageActivity.this, BottomActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
                finish();
            }
        });


        setToolbar("");
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

}
