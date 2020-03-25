package com.example.hello.myapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hello.myapplication.R;
import com.example.hello.myapplication.utils.cardphoto.camera.IDCardCamera;
import com.example.hello.myapplication.utils.device.RxPermissionUtils;


public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LaunchActivity";
    private Button bt_start;
    private Button bt_image_operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        findViewbyId();
        setListener();
    }


    private void findViewbyId() {
        bt_start = findViewById(R.id.bt_start);
        bt_image_operator = findViewById(R.id.bt_image_operator);
    }

    private void setListener() {
        bt_start.setOnClickListener(this);
        bt_image_operator.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                RxPermissionUtils.getInstance(this).getPowerStatus(() -> {
                            FilmCropPhotoActivity.actionStart(this);
                        }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.bt_image_operator:
                RxPermissionUtils.getInstance(this).getPowerStatus(() -> {
                            IDCardCamera.create(this).openCamera(IDCardCamera.TYPE_IDCARD_FRONT);
                        }, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK && intent != null) {
            switch (requestCode) {
                case IDCardCamera.RESULT_CODE:
                    //获取图片路径，显示图片
                    String mPhotoPath = IDCardCamera.getImagePath(intent);
                    if (!TextUtils.isEmpty(mPhotoPath)) {
                        if (requestCode == IDCardCamera.TYPE_IDCARD_FRONT) { //身份证正面

                        } else if (requestCode == IDCardCamera.TYPE_IDCARD_BACK) {  //身份证反面

                        }
                        Log.d(TAG, mPhotoPath);
                    }
                    break;
            }
        }
    }
}
