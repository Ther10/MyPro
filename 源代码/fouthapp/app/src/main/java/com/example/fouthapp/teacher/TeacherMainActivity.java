package com.example.fouthapp.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.fouthapp.ActivityCollector;
import com.example.fouthapp.BottomActivity;
import com.example.fouthapp.R;
import com.example.fouthapp.SecondFragment;
import com.example.fouthapp.entity.Teacher;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TeacherMainActivity extends AppCompatActivity {

    private String userId;
    private String username;

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public static void actionStart(Context context, String userId){
        Intent intent = new Intent(context, TeacherMainActivity.class);
        intent.putExtra("userId",userId);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, String userId, String username){
        Intent intent = new Intent(context,TeacherMainActivity.class);
        intent.putExtra("userId",userId);
        intent.putExtra("username",username);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        getUserMessage();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,R.id.fragment);
        //AppBarConfiguration configuration = new AppBarConfiguration.Builder(bottomNavigationView.getMenu()).build();
        //NavigationUI.setupActionBarWithNavController(this,navController,configuration);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        ActivityCollector.addActivity(this);

        //finish();
    }

    private void getUserMessage(){
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        username = intent.getStringExtra("username");
        System.out.println(userId);
        System.out.println(username);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}