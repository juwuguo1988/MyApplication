package com.example.hello.myapplication.utils;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.example.hello.myapplication.common.XZLApplication;
import com.example.hello.myapplication.listener.FileSaveListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hello on 18/5/1.
 */

public class BDTTsCovertWavUtils {
    protected static String appId = "11005757";
    protected static String appKey = "Ovcz19MGzIKoDDb3IsFFncG1";
    protected static String secretKey = "e72ebb6d43387fc7f85205ca7e6706e2";
    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
    // assets目录下bd_etts_common_speech_m15_mand_eng_high_am-mix_v3.0.0_20170505.dat为离线男声模型；
    // assets目录下bd_etts_common_speech_f7_mand_eng_high_am-mix_v3.0.0_20170512.dat为离线女声模型
    private static String offlineVoice = OfflineResource.VOICE_FEMALE;
    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    private static TtsMode ttsMode = TtsMode.MIX;
    private static PcmToWavUtil pcmToWavUtil = new PcmToWavUtil();
    final static String path = "/storage/emulated/0/TempFile/output-0.pcm";
    //按原路径把音频文件后缀改一下;
    final static String outpath = path.replace(".pcm", ".wav");
 //        private static SpeechSynthesizerListener listener = new MessageListener() {
//        @Override
//        public void onSynthesizeFinish(String utteranceId) {
//            super.onSynthesizeFinish(utteranceId);
//            Log.e("======", "=======合成结束！！！==");
//            pcmToWavUtil.pcmToWav(path, outpath);
//        }
//    };
    static String tmpDir = FileUtils.createTmpDir(XZLApplication.getContext());
    private static SpeechSynthesizerListener listener = new FileSaveListener(null, tmpDir) {


    };


    public static InitConfig getInitConfig() {
        Map<String, String> params = getParams();
        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
        InitConfig initConfig = new InitConfig(appId, appKey, secretKey, ttsMode, params, listener);
        return initConfig;
    }


    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    private static Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        // 以下参数均为选填
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_VOLUME, "5");
        // 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");

        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 离线资源文件， 从assets目录中复制到临时目录，需要在initTTs方法前完成
        OfflineResource offlineResource = createOfflineResource(offlineVoice);
        // 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
        params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename());
        params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
                offlineResource.getModelFilename());

        return params;
    }

    private static OfflineResource createOfflineResource(String voiceType) {
        OfflineResource offlineResource = null;
        try {
            offlineResource = new OfflineResource(XZLApplication.getContext(), voiceType);
        } catch (IOException e) {
            // IO 错误自行处理
            e.printStackTrace();
        }
        return offlineResource;
    }

}
