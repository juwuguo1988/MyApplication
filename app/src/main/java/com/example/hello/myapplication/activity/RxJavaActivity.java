package com.example.hello.myapplication.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.example.hello.myapplication.R;
import com.example.hello.myapplication.utils.BDTTsCovertWavUtils;
import com.example.hello.myapplication.utils.MySyntherizer;

import java.util.ArrayList;

/**
 * Created by hello on 18/5/24.
 */

public class RxJavaActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RxJavaActivity";
    private Button bt_start;
    protected MySyntherizer mSynthesizer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_layout);
        findViewById();
        setListener();
        initPermission();
        LoggerProxy.printable(true); // 日志打印在logcat中
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findViewById() {
        bt_start = findViewById(R.id.bt_start);
    }

    private void setListener() {
        bt_start.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                if (mSynthesizer == null) {
                    mSynthesizer = new MySyntherizer(RxJavaActivity.this, BDTTsCovertWavUtils.getInitConfig(), null);
                }
                synthesize();
                break;
        }
    }

    private void synthesize() {
        int result = mSynthesizer.speak("正链接互联网中，请等待");
        Log.e("========", "======result====>" + result);
    }

    private void initPermission() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
            }
        }
        String[] tmpList = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }

}
