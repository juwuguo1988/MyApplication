package com.example.hello.myapplication.utils.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.example.hello.myapplication.R;

public class DialogUtils {

    /**
     * 我知道了
     *
     * @param ctx
     * @param message
     */
    public static void showDialog(final Context ctx, final String message) {
        showDialog(ctx, ctx.getString(R.string.prompt), message, ctx.getString(R.string.i_know), null, null, false);
    }

    /**
     * @param ctx
     * @param message
     * @param confirmButtonText
     */
    public static void showDialog(final Context ctx, final String message, final String confirmButtonText) {
        showDialog(ctx, ctx.getString(R.string.prompt), message, confirmButtonText, null, null, false);
    }

    public static void showDialog(final Context ctx, final String titleName, final String message, final String confirmButtonText, final String cancleButtonText, final DialogInterface.OnClickListener listener, boolean isShowCancelButton) {
        try {
            Builder dialog = new Builder(ctx);
            dialog.setTitle(titleName).setMessage(message).setPositiveButton(confirmButtonText, listener);
            if (isShowCancelButton) {
                dialog.setNegativeButton(cancleButtonText, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            Log.e("DialogUtils", e.toString());
        }
    }

    public static void showDialog(final Context ctx, final String titleName, final String message, final String confirmButtonText, final String cancleButtonText, final DialogInterface.OnClickListener listener) {
        try {
            Builder dialog = new Builder(ctx);
            dialog.setTitle(titleName).setMessage(message).setPositiveButton(confirmButtonText, listener);
            dialog.setNegativeButton(cancleButtonText, listener);
            dialog.show();
        } catch (Exception e) {
            Log.e("DialogUtils", e.toString());
        }
    }

    public static void showDialog(final Context ctx, final String titleName, final String message, final String confirmButtonText, final String neutralButtonText, final String cancleButtonText, final DialogInterface.OnClickListener listener) {
        try {
            Builder dialog = new Builder(ctx);
            dialog.setTitle(titleName).setMessage(message).setPositiveButton(confirmButtonText, listener);
            dialog.setNeutralButton(neutralButtonText, listener);
            dialog.setNegativeButton(cancleButtonText, listener);
            dialog.show();
        } catch (Exception e) {
            Log.e("DialogUtils", e.toString());
        }
    }

    /**
     * 弹出Pop窗口
     *
     * @param context 依赖界面上下文
     * @param anchor  触发pop界面的控件
     * @param viewId  pop窗口界面layout
     * @param xoff    窗口X偏移量
     * @param yoff    窗口Y偏移量
     */
    public static PopupWindow popwindow(Context context, View anchor, int viewId, int xoff, int yoff) {
        ViewGroup menuView = (ViewGroup) LayoutInflater.from(context).inflate(viewId, null);
        PopupWindow pw = new PopupWindow(menuView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pw.setTouchable(true);
        pw.setFocusable(true);
        pw.setOutsideTouchable(true);
        return pw;
    }

}
