package com.example.fouthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import butterknife.ButterKnife;

public class LeaveActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_submit;
    private Button btn_reset;
    private EditText edit_name;
    private EditText edit_sno;
    private EditText leave_time;
    private RadioGroup rg_role;

    private String editname;
    private String editsno;
    private String leavetime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        //setToolbar("手势打卡");
        //ButterKnife.bind(this);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_reset = (Button) findViewById(R.id.btn_reset);

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_sno = (EditText) findViewById(R.id.edit_sno);
        leave_time = (EditText) findViewById(R.id.leave_time);
        rg_role = (RadioGroup) findViewById(R.id.rg_role);



        btn_submit.setOnClickListener(this);
        btn_reset.setOnClickListener(this);

        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                editname = edit_name.getText().toString().trim();
                editsno = edit_sno.getText().toString().trim();
                leavetime = leave_time.getText().toString().trim();
                if(TextUtils.isEmpty(editname)||TextUtils.isEmpty(editsno)||TextUtils.isEmpty(leavetime))
                {

                    Toast.makeText(getApplicationContext(),"请将信息填写完全！",Toast.LENGTH_LONG).show();
                    //return;
                }else {
                    Toast.makeText(LeaveActivity.this,"提交成功！",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_reset:
                edit_name.setText(null);
                edit_sno.setText(null);
                leave_time.setText(null);
                rg_role.check(R.id.health);
            break;
        }
    }
}