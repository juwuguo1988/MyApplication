package com.example.hello.myapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.example.hello.myapplication.R;
import com.example.hello.myapplication.utils.InitConfig;
import com.example.hello.myapplication.utils.MySyntherizer;
import com.example.hello.myapplication.utils.NonBlockSyntherizer;

import java.util.HashMap;
import java.util.Map;

public class SpeakTextActivity extends BaseActivity implements View.OnClickListener{
    private Button bt_start_music, bt_stop_music;
    protected String appId = "11176533";

    protected String appKey = "mp5CXM5wK5KnKxLA8a1GXxmS";

    protected String secretKey = "pKl9r1XYCGhCTBORhvxiiTviveHISHrG";

    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.MIX;
    // 主控制类，所有合成控制方法从这个类开始
    protected MySyntherizer synthesizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewbyId();
        setListener();
        checkTTS();
        initialTts();
    }

    private void findViewbyId() {
        bt_start_music = (Button) findViewById(R.id.bt_start_music);
        bt_stop_music = (Button) findViewById(R.id.bt_stop_music);
    }

    private void setListener() {
        bt_start_music.setOnClickListener(this);
        bt_stop_music.setOnClickListener(this);
    }

    /**
     * 检查TTS是否可以使用
     */
    private void checkTTS() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start_music:
                speak();
                break;
            case R.id.bt_stop_music:

                break;
        }
    }

    /**
     * 初始化引擎，需要的参数均在InitConfig类里
     * <p>
     * DEMO中提供了3个SpeechSynthesizerListener的实现
     * MessageListener 仅仅用log.i记录日志，在logcat中可以看见
     * UiMessageListener 在MessageListener的基础上，对handler发送消息，实现UI的文字更新
     * FileSaveListener 在UiMessageListener的基础上，使用 onSynthesizeDataArrived回调，获取音频流
     */
    protected void initialTts() {
        Map<String, String> params = getParams();
        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
        InitConfig initConfig = new InitConfig(appId, appKey, secretKey, ttsMode, params, null);
        synthesizer = new MySyntherizer(this,initConfig, mainHandler); // 此处可以改为MySyntherizer 了解调用过程
    }

    /**
     * speak 实际上是调用 synthesize后，获取音频流，然后播放。
     * 获取音频流的方式见SaveFileActivity及FileSaveListener
     * 需要合成的文本text的长度不能超过1024个GBK字节。
     */
    private void speak() {
        mShowText.setText("");
        String text = "龚晓轩是王八蛋";
        // 需要合成的文本text的长度不能超过1024个GBK字节。
      /*  if (TextUtils.isEmpty(mInput.getText())) {
            text = "百度语音，面向广大开发者永久免费开放语音合成技术。";
            mInput.setText(text);
        }*/
        // 合成前可以修改参数：
        // Map<String, String> params = getParams();
        // synthesizer.setParams(params);
        int result = synthesizer.speak(text);
        checkResult(result, "speak");
    }

    private void checkResult(int result, String method) {
        if (result != 0) {
            toPrint("error code :" + result + " method:" + method + ", 错误码文档:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }


    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        // 以下参数均为选填
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_VOLUME, "9");
        // 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");

        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);

        return params;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synthesizer.release();
    }
}
