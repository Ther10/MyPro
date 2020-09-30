package com.example.fouthapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseBackgroundFormat;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.example.fouthapp.dao.StudentDao;
import com.example.fouthapp.entity.Student;
import com.example.fouthapp.entity.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.bin.david.form.core.SmartTable;
public class TableActivity extends BaseActivity {

    private SmartTable table;
    private List<Student> list = new ArrayList<Student>();

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
        setContentView(R.layout.activity_table);

        //table.setZoom(true,2,0.2f);
        /*list.add(new User("2018413449","123456","admin"));
        list.add(new User("2018413449","123456","admin"));
        list.add(new User("2018413449","123456","admin"));*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                StudentDao dao = new StudentDao();
                list = dao.outMessage();
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
}