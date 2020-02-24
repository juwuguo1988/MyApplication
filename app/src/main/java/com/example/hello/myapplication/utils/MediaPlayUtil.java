package com.example.hello.myapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MediaPlayUtil {
    private static final String TAG = "MediaPlayUtil";
    public MediaPlayer player;
    private Timer mTimer;
    private long mTimeOutilli = 30000;

    public synchronized void startMp3(final Context mContext) {
        if (player == null) {
            player = getMediaPlayer(mContext);
        }
        try {
            AssetFileDescriptor afd = mContext.getAssets().openFd("demo.mp3");
            player.reset();
            player.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.setLooping(true);
            player.start();
        } catch (Exception e) {
            player.release();
            player = null;
            e.printStackTrace();
        }

        mTimer = new Timer();// 构造函数new Timer(true)
        // 表明这个timer以daemon方式运行（优先级低，程序结束timer也自动结束）。
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                stop();
            }
        };
        mTimer.schedule(timerTask, mTimeOutilli);
    }

    public synchronized void startMp3(final Context mContext, final String path, final int startCurrent) {
        Intent intent = new Intent("com.android.music.musicservicecommand");
        intent.putExtra("command", "pause");
        mContext.sendBroadcast(intent);
        if (player == null) {
            player = new MediaPlayer();
        }
        new Thread() {

            @Override
            public void run() {
                super.run();
                try {
                    player.setOnCompletionListener(new OnCompletionListener() {

                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            pause();
                            if (onPlayCompleteListener != null) {
                                onPlayCompleteListener.playComplete(mp);
                            }
                        }
                    });
                    player.reset();// 重设
                    player.setDataSource(path);
                    player.prepare();
                    player.seekTo(startCurrent * 1000);
                    player.start();

                    player.setOnErrorListener(new OnErrorListener() {

                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            switch (what) {
                                case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                                    mp.release();
                                    player = new MediaPlayer();
                                    return true;
                                case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                } catch (Exception e) {
                    player.release();
                    player = new MediaPlayer();
                    e.printStackTrace();
                }
            }

        }.start();

    }

    public void pause() {
        try {
            if (player != null) {
                try {
                    player.pause();
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    public void stop() {
        try {
            if (player != null) {
                player.reset();
                player.release();
                player = null;
            }

            if (mTimer != null) {
                mTimer.cancel();
            }
        } catch (Exception e) {
        }
    }

    public boolean isPlaying() {
        try {
            if (player != null && player.isPlaying()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public interface OnPlayCompleteListener {
        void playComplete(MediaPlayer mp);
    }

    public void setCompletionListener(OnPlayCompleteListener listener) {
        onPlayCompleteListener = listener;
    }

    private OnPlayCompleteListener onPlayCompleteListener;

    private MediaPlayer getMediaPlayer(Context context) {
        MediaPlayer mediaplayer = new MediaPlayer();
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            return mediaplayer;
        }
        try {
            Class<?> cMediaTimeProvider = Class.forName("android.media.MediaTimeProvider");
            Class<?> cSubtitleController = Class.forName("android.media.SubtitleController");
            Class<?> iSubtitleControllerAnchor = Class.forName("android.media.SubtitleController$Anchor");
            Class<?> iSubtitleControllerListener = Class.forName("android.media.SubtitleController$Listener");
            Constructor constructor = cSubtitleController.getConstructor(
                    new Class[]{Context.class, cMediaTimeProvider, iSubtitleControllerListener});
            Object subtitleInstance = constructor.newInstance(context, null, null);
            Field f = cSubtitleController.getDeclaredField("mHandler");
            f.setAccessible(true);
            try {
                f.set(subtitleInstance, new Handler() {
                    @Override
                    public void publish(LogRecord logRecord) {

                    }

                    @Override
                    public void flush() {

                    }

                    @Override
                    public void close() throws SecurityException {

                    }
                });
            } catch (IllegalAccessException e) {
                return mediaplayer;
            } finally {
                f.setAccessible(false);
            }
            Method setsubtitleanchor = mediaplayer.getClass().getMethod("setSubtitleAnchor",
                    cSubtitleController, iSubtitleControllerAnchor);
            setsubtitleanchor.invoke(mediaplayer, subtitleInstance, null);
        } catch (Exception e) {
            Log.d(TAG, "getMediaPlayer crash ,exception = " + e);
        }
        return mediaplayer;
    }

}
