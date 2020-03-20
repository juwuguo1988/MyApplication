package com.example.hello.myapplication.utils.ui;

import android.content.Context;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.hello.myapplication.utils.config.AppConstant;


public class UI {
    public final static int ORG_SCREEN_WIDTH = 480;
    public final static int ORG_SCREEN_HEIGHT = 800;

    public final static float[] BUTTON_PRESSED = new float[]{0.2f, 0, 0, 0, 50.8f, 0, 0.2f, 0, 0, 50.8f, 0, 0, 0.2f, 0, 50.8f, 0, 0, 0, 1, 0};
    public final static float[] BUTTON_PRESSED1 = new float[]{42 / 255, 0, 0, 0, 0, 0, 42 / 255, 0, 0, 0, 0, 0, 42 / 255, 0, 0, 0, 0, 0, 1, 0};

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmW2(int dmw, int w) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWTrue(int dmw, int w) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW(int dmw, int dmh, int w) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2(int dmw, int dmh, int w) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWH16_9(int dmw, int w) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width * 9 / 16;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2LR(int dmw, int dmh, int w, int l, int r) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width;
        params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;
        params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2LT(int dmw, int dmh, int w, int l, int t) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width;
        params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;
        params.topMargin = dmw * t / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHTR(int dmw, int dmh, int w, int h, int t, int r) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width;
        params.topMargin = dmw * t / ORG_SCREEN_WIDTH;
        params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararm_Text_Time(int dmw, int dmh, double t, double l) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.topMargin = (int) t;
        params.leftMargin = (int) l;

        return params;
    }

    public static LinearLayout.LayoutParams getRelativeLayoutPararmWHLTrue(int dmw, int dmh, int w, int h, double l) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w;
        params.height = h;
        params.leftMargin = (int) l;
        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHLTrue(int w, int h, double l) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w;
        params.height = h;
        params.leftMargin = (int) l;
        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2L(int dmw, int dmh, int w, int l) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width;
        params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2RT(int dmw, int dmh, int w, int r, int t) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width;
        params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;
        params.topMargin = dmw * t / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2TL(int dmw, int dmh, int w, int t, int l) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width;
        params.topMargin = dmw * t / ORG_SCREEN_WIDTH;
        params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2TR(int dmw, int dmh, int t, int r) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.height = params.width;
        params.topMargin = dmw * t / ORG_SCREEN_WIDTH;
        params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2TR(int dmw, int dmh, int w, int t, int r) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width;
        params.topMargin = dmw * t / ORG_SCREEN_WIDTH;
        params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2BR(int dmw, int dmh, int w, int b, int r) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width;
        params.bottomMargin = dmw * b / ORG_SCREEN_WIDTH;
        params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2BR(int dmw, int dmh, int b, int r) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.bottomMargin = dmw * b / ORG_SCREEN_WIDTH;
        params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2R(int dmw, int dmh, int w, int r) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width;
        params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2B(boolean isByDeviceWidth, int dmw, int dmh, int w, int b) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        if (isByDeviceWidth) {
            params.width = dmw * w / ORG_SCREEN_WIDTH;
            params.height = params.width;
            params.bottomMargin = dmw * b / ORG_SCREEN_WIDTH;
        } else {
            params.width = dmh * w / ORG_SCREEN_HEIGHT;
            params.height = params.width;
            params.bottomMargin = dmh * b / ORG_SCREEN_HEIGHT;
        }

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWB(int dmw, int dmh, int w, int b) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.bottomMargin = dmw * b / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHB(int dmw, int dmh, int w, int h, int b) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width * h / w;
        params.bottomMargin = dmw * b / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHR_ByW(int dmw, int dmh, int w, int h, int r) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width * h / w;
        params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWH_ByW(int dmw, int dmh, int w, int h) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width * h / w;

        return params;
    }

    public static LayoutParams getFrameLayoutPararmWH_ByW(int dmw, int dmh, int w, int h) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width * h / w;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2LTB(boolean isByDeviceWidth, int dmw, int dmh, int w, int l, int t, int b) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        if (isByDeviceWidth) {
            params.width = dmw * w / ORG_SCREEN_WIDTH;
            params.height = params.width;
            params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;
            params.topMargin = dmw * t / ORG_SCREEN_WIDTH;
            params.bottomMargin = dmw * b / ORG_SCREEN_WIDTH;
        } else {
            params.width = dmh * w / ORG_SCREEN_HEIGHT;
            params.height = params.width;
            params.leftMargin = dmh * l / ORG_SCREEN_HEIGHT;
            params.topMargin = dmh * t / ORG_SCREEN_HEIGHT;
            params.bottomMargin = dmh * b / ORG_SCREEN_HEIGHT;
        }

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2T(int dmw, int dmh, int w, int t) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width;
        params.topMargin = dmw * t / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmH(int dmw, int dmh, int h) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.height = dmw * h / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmLR(int dmw, int dmh, int l, int r) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;
        params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmLT(int dmw, int dmh, int l, int t) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        // ORIENTATION_LANDSCAPE
        if (dmw > dmh) {
            params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;
            params.topMargin = dmh * t / ORG_SCREEN_WIDTH;
        }
        // ORIENTATION_PORTRAIT
        else {
            params.leftMargin = dmw * l / ORG_SCREEN_HEIGHT;
            params.topMargin = dmh * t / ORG_SCREEN_WIDTH;
        }

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWH(int dmw, int dmh, int w, int h) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width * h / w;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHB_ByW(int dmw, int dmh, int w, int h, int b) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width * h / w;
        params.bottomMargin = dmw * b / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHTB_ByW(int dmw, int dmh, int w, int h, int t, int b) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width * h / w;
        params.topMargin = dmw * t / ORG_SCREEN_WIDTH;
        params.bottomMargin = dmw * b / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHL_ByW(int dmw, int dmh, int w, int h, int l) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width * h / w;
        params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHLTB_ByW(int dmw, int dmh, int w, int h, int l, int t, int b) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width * h / w;
        params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;
        params.topMargin = dmw * t / ORG_SCREEN_WIDTH;
        params.bottomMargin = dmw * b / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmHTrue(int h) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        params.height = h;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHTrue(int w, int h) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w;
        params.height = h;

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmWHTrue(int w, int h) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w;
        params.height = h;

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmWHLRTrue(int w, int h, int l, int r) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w;
        params.height = h;
        params.leftMargin = l;
        params.rightMargin = r;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmLTrue(int l) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.leftMargin = l;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHT(int dmw, int dmh, int w, int h, int t) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width * h / w;
        params.topMargin = dmh * t / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmLTrue(double w, double l) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.leftMargin = (int) l;
        params.width = (int) w;
        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmHTrue1(double w, double l) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.width = (int) w;
        params.leftMargin = (int) l;

        return params;
    }

    public static int getTransW(int dmw, int dmh, int w) {
        // ORIENTATION_LANDSCAPE
        if (dmw > dmh) {
            return dmw * w / ORG_SCREEN_HEIGHT;
        }
        // ORIENTATION_PORTRAIT
        else {
            return dmh * w / ORG_SCREEN_HEIGHT;
        }
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHLT(int dmw, int dmh, int w, int h, int l, int t) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        // ORIENTATION_LANDSCAPE
        if (dmw > dmh) {
            params.width = dmw * w / ORG_SCREEN_HEIGHT;
            params.height = dmh * h / ORG_SCREEN_WIDTH;
            params.leftMargin = dmw * l / ORG_SCREEN_HEIGHT;
            params.topMargin = dmh * t / ORG_SCREEN_WIDTH;
        }
        // ORIENTATION_PORTRAIT
        else {
            params.width = dmw * w / ORG_SCREEN_WIDTH;
            params.height = dmh * h / ORG_SCREEN_HEIGHT;
            params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;
            params.topMargin = dmh * t / ORG_SCREEN_HEIGHT;
        }

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmFillWT(int dmw, int dmh, int w, int t) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        // ORIENTATION_LANDSCAPE
        if (dmw > dmh) {
            params.width = dmw * w / ORG_SCREEN_HEIGHT;
            params.topMargin = dmh * t / ORG_SCREEN_WIDTH;
        }
        // ORIENTATION_PORTRAIT
        else {
            params.width = dmw * w / ORG_SCREEN_WIDTH;
            params.topMargin = dmh * t / ORG_SCREEN_HEIGHT;
        }

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWLT(int dmw, int dmh, int w, int l, int t) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        // ORIENTATION_LANDSCAPE
        if (dmw > dmh) {
            params.width = dmw * w / ORG_SCREEN_HEIGHT;
            params.leftMargin = dmw * l / ORG_SCREEN_HEIGHT;
            params.topMargin = dmh * t / ORG_SCREEN_WIDTH;
        }
        // ORIENTATION_PORTRAIT
        else {
            params.width = dmw * w / ORG_SCREEN_WIDTH;
            params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;
            params.topMargin = dmh * t / ORG_SCREEN_HEIGHT;
        }

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHRB(int dmw, int dmh, int w, int h, int r, int b) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        // ORIENTATION_LANDSCAPE
        if (dmw > dmh) {
            params.width = dmw * w / ORG_SCREEN_HEIGHT;
            params.height = dmw * h / ORG_SCREEN_HEIGHT;
            params.rightMargin = dmw * r / ORG_SCREEN_HEIGHT;
            params.bottomMargin = dmh * b / ORG_SCREEN_WIDTH;
        }
        // ORIENTATION_PORTRAIT
        else {
            params.width = dmw * w / ORG_SCREEN_HEIGHT;
            params.height = dmh * h / ORG_SCREEN_WIDTH;
            params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;
            params.bottomMargin = dmh * b / ORG_SCREEN_HEIGHT;
        }

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmB(int dmw, int dmh, int b) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        // ORIENTATION_LANDSCAPE
        if (dmw > dmh) {
            params.bottomMargin = dmh * b / ORG_SCREEN_WIDTH;
        }
        // ORIENTATION_PORTRAIT
        else {
            params.bottomMargin = dmh * b / ORG_SCREEN_HEIGHT;
        }

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmL(int dmw, int dmh, int l) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmLH(int dmw, int dmh, int l, int h) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;
        params.height = dmw * h / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmT(boolean isByDeviceWidth, int dmw, int dmh, int t) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        if (isByDeviceWidth) {
            params.topMargin = dmw * t / ORG_SCREEN_WIDTH;
        } else {
            params.topMargin = dmh * t / ORG_SCREEN_HEIGHT;
        }

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmTRTrue(int t, int r) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.topMargin = t;
        params.rightMargin = r;

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutLayoutPararmT(int dmw, int dmh, int t) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.topMargin = dmw * t / ORG_SCREEN_WIDTH;

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutLayoutPararmTR(int dmw, int dmh, int t, int r) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.topMargin = dmw * t / ORG_SCREEN_WIDTH;
        params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutLayoutPararmTL(int dmw, int dmh, int t, int l) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.topMargin = dmw * t / ORG_SCREEN_WIDTH;
        params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutLayoutPararmB(int dmw, int dmh, int b) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.bottomMargin = dmw * b / ORG_SCREEN_WIDTH;

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutLayoutPararmWT(boolean isByDeviceWidth, int dmw, int dmh, int w, int t) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        if (isByDeviceWidth) {
            params.width = dmw * w / ORG_SCREEN_WIDTH;
            params.topMargin = dmw * t / ORG_SCREEN_WIDTH;
        } else {
            params.width = dmh * w / ORG_SCREEN_HEIGHT;
            params.topMargin = dmh * t / ORG_SCREEN_HEIGHT;
        }

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutLayoutPararmFT(boolean isByDeviceWidth, int dmw, int dmh, int t) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        if (isByDeviceWidth) {
            params.topMargin = dmw * t / ORG_SCREEN_WIDTH;
        } else {
            params.topMargin = dmh * t / ORG_SCREEN_HEIGHT;
        }

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutLayoutPararmT(boolean isByDeviceWidth, int dmw, int dmh, int t) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        if (isByDeviceWidth) {
            params.topMargin = dmw * t / ORG_SCREEN_WIDTH;
        } else {
            params.topMargin = dmh * t / ORG_SCREEN_HEIGHT;
        }

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmRB(int dmw, int dmh, int r, int b) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        // ORIENTATION_LANDSCAPE
        if (dmw > dmh) {
            params.rightMargin = dmw * r / ORG_SCREEN_HEIGHT;
            params.bottomMargin = dmh * b / ORG_SCREEN_WIDTH;
        }
        // ORIENTATION_PORTRAIT
        else {
            params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;
            params.bottomMargin = dmh * b / ORG_SCREEN_HEIGHT;
        }

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWH16_9True(int w) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w;
        params.height = params.width * 9 / 16;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWH16_12True(int w) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w;
        params.height = params.width * 12 / 16;

        return params;
    }

    public static RelativeLayout.LayoutParams getCropLayoutPararmWH16_9(int crop_width, int crop_height, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = crop_width;
        params.height = crop_height;

        params.leftMargin = leftMargin;
        params.topMargin = topMargin;
        params.rightMargin = rightMargin;
        params.bottomMargin = bottomMargin;

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmInParentCenterWHTrue(int w, int h) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w;
        params.height = h;
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmWH16_9True(int w) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w;
        params.height = params.width * 9 / 16;

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmWHLRTB16_9True(int w, int l, int r, int t, int b) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w - l - r;
        params.height = params.width * 9 / 16;
        params.leftMargin = l;
        params.rightMargin = r;
        params.topMargin = t;
        params.bottomMargin = b;

        return params;
    }

    public static RelativeLayout.LayoutParams getCropPhotoRelativeLayoutPararmWHLR16_9True(int w, int l, int r) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w - l - r;
        params.height = w * 9 / 16;
        params.leftMargin = l;
        params.rightMargin = r;

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmLRTB16_9True(int l, int r, int t, int b) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.leftMargin = l;
        params.rightMargin = r;
        params.topMargin = t;
        params.bottomMargin = b;

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmH_WFillTrue(int h) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

        params.height = h;

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmW_HFillTrue(int w) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);

        params.width = w;

        return params;
    }

    public static LinearLayout.LayoutParams getRelativeLayoutPararmWHTrue(int dmw, int dmh, int w, int h) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w;
        params.height = h;
        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHTTrue_Time(int dmw, int dmh, double t) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.topMargin = (int) t;
        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmW2RState(int dmw, int dmh, int w, int r) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width;
        params.rightMargin = dmw * r / ORG_SCREEN_WIDTH;

        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararm_state(int dmw, int dmh, int w) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.width = (int) (dmw * w / ORG_SCREEN_HEIGHT * 0.7);
        params.height = dmw * w / ORG_SCREEN_WIDTH;
        params.leftMargin = AppConstant.CROP_PADDING;
        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararm_state_Right(int dmw, int dmh, int w) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.width = (int) (dmw * w / ORG_SCREEN_HEIGHT * 0.7);
        params.height = dmw * w / ORG_SCREEN_WIDTH;
        params.leftMargin = dmw - AppConstant.CROP_PADDING - params.width;
        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmW(int dmw, int dmh, int w) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;

        return params;

    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmWH(int dmw, int dmh, int w, int h) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = dmw * h / ORG_SCREEN_WIDTH;

        return params;

    }

    public static LinearLayout.LayoutParams getLinearLayoutPararmWHTTrue_Logo(int dmw, int dmh, int t) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        params.topMargin = dmh * t / ORG_SCREEN_HEIGHT;
        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmWHTrue_LogoWidth(int w) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w;
        params.height = params.width;
        return params;
    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmWHLRTB_LogoWidth(int w, int l, int r, int t, int b) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = w - l - r;
        params.height = params.width;
        params.leftMargin = l;
        params.rightMargin = r;
        params.topMargin = t;
        params.bottomMargin = b;

        return params;
    }

    public static LinearLayout.LayoutParams getRelativeLayoutPararm_WHT(int dmw, int dmh, int w, int h, int t) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = dmw * h / ORG_SCREEN_WIDTH;
        params.topMargin = dmw * t / ORG_SCREEN_HEIGHT;
        return params;

    }

    public static RelativeLayout.LayoutParams getRelativeLayoutPararmWHByWidth(int dmw, int dmh, int w, int h) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;
        params.height = params.width * h / w;

        return params;

    }

    public static LinearLayout.LayoutParams getLinearLayoutPararm_RewardComments_Width(int dmw, int dmh, int w) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.width = dmw * w / ORG_SCREEN_WIDTH;

        return params;
    }

    public static RelativeLayout.LayoutParams getLinearLayoutPararm_RewardMoney_Left(int dmw, int dmh, int l) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.leftMargin = dmw * l / ORG_SCREEN_WIDTH;

        return params;
    }

    public static LinearLayout.LayoutParams getLinearLayoutPararm_RewardRoot_TB(int dmw, int dmh, int t, int b) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        params.topMargin = dmh * t / ORG_SCREEN_HEIGHT;
        params.bottomMargin = dmh * b / ORG_SCREEN_HEIGHT;

        return params;
    }

    public static int getNaviBarHeight(int dmw, int w, int h) {

        return dmw * w / ORG_SCREEN_WIDTH * h / w;
    }
}
