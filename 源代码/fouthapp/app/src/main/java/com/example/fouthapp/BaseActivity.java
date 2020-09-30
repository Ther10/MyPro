package com.example.fouthapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    public Toolbar toolbar;

    public void setToolbar(String title){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
      // String title1= (String) toolbar.getTitle();
        toolbar.setTitle(title);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.firstFragment:
            {
                Intent intent = new Intent(getApplicationContext(),BottomActivity.class);
                startActivity(intent);
                //Toast.makeText(BaseActivity.this,"Item1 Clicked",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.secondFragment:
            {
                Toast.makeText(BaseActivity.this,"Item2 Clicked",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.thirdFragment:
            {
                Toast.makeText(BaseActivity.this,"Item3 Clicked",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.fourthFragment:
            {
                Toast.makeText(BaseActivity.this,"Item4 Clicked",Toast.LENGTH_LONG).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}