package com.example.hello.myapplication.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hello.myapplication.R;
import com.example.hello.myapplication.common.events.CheckNewMedicPlanEvent;
import com.example.hello.myapplication.utils.MediaPlayUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import me.mi.mp3.util.LameUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_start_music, bt_stop_music;
    private MediaPlayUtil mediaPlayUtil;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayUtil = new MediaPlayUtil();
        findViewbyId();
        setListener();
        EventBus.getDefault().register(this);
        verifyStoragePermissions(this);
        Log.d("=====MainActivity===", "======onCreate====");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("=====MainActivity===", "======onStart====");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("=====MainActivity===", "======onResume====");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("=====MainActivity===", "======onPause====");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("=====MainActivity===", "======onStop====");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("=====MainActivity===", "======onRestart====");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d("=====MainActivity===", "======onDestroy====");
    }

    private void findViewbyId() {
        bt_start_music = (Button) findViewById(R.id.bt_start_music);
        bt_stop_music = (Button) findViewById(R.id.bt_stop_music);
    }

    private void setListener() {
        bt_start_music.setOnClickListener(this);
        bt_stop_music.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CheckNewMedicPlanEvent event) {
        Log.d("=====MainActivity===", "======收到了====");
        finish();

    }

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start_music:
//                byte[] mSendVoiceData = FileUtils.getByteDataFromAssets(this, "demo.mp3");
//                StringBuffer stringBuffer = new StringBuffer();
//                for (byte item : mSendVoiceData) {
//                   // stringBuffer.append(String.format("%x ", item));
//                    Log.d("AppGlobal_MqttTag", String.format("%x ", item));
//                }
//              //  Log.d("AppGlobal_MqttTag", stringBuffer.toString());
                String pcmPath = "/storage/emulated/0/XZLFile/" + "厄贝沙坦片" + ".pcm";
                String mp3Path = "/storage/emulated/0/XZLFile/" + "厄贝沙坦片" + ".mp3";

                File mp3File = new File(mp3Path);
                if(mp3File.exists()){
                    mp3File.delete();
                }

                File file = new File(pcmPath);
                if (file.exists()) {
                    Log.e("========","=========》");
                    //11025Hz采样率，16bit，单声道，mp3
                    me.mi.mp3.util.LameUtil.init(8000, 1, 11025, 16, 7);;
                    me.mi.mp3.util.LameUtil.encodeFile(pcmPath, mp3Path);
                }

                break;
            case R.id.bt_stop_music:
                stopMp3();
                break;
        }
    }


    public synchronized void startLocalMp3() {
        mediaPlayUtil.startMp3(this);
    }

    public synchronized void stopMp3() {
        mediaPlayUtil.stop();
    }
}
