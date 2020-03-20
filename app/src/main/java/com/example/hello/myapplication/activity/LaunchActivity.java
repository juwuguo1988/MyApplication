package com.example.hello.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.hello.myapplication.R;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        findViewbyId();
        setListener();
    }


    private void findViewbyId() {
        bt_start = (Button) findViewById(R.id.bt_start);
    }

    private void setListener() {
        bt_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                FilmCropPhotoActivity.actionStart(this);
                break;
        }
    }
}
