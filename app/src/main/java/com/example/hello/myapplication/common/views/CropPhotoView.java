package com.example.hello.myapplication.common.views;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.hello.myapplication.common.bean.AdjustParam;
import com.example.hello.myapplication.utils.config.AppConstant;

public class CropPhotoView extends AppCompatImageView implements OnTouchListener {

    private static boolean CROP_PHOTO_MIN_ZOOM = false;
    private Bitmap bitmap = null;
    private boolean bOnTouchEnable = true;
    private final int UI_MOVE_ANIME_START = 1;
    private final int UI_MOVE_ANIME_STOP = 2;
    private final int UI_ROTATE_ANIME_START = 3;
    private final int UI_ROTATE_ANIME_STOP = 4;

    private DecimalFormat decimalFormat = new DecimalFormat("##0.00000000");

    private double mNowDegrees = 0.0f;
    private float to_degrees = 0.0f;
    private final float one_step_degrees = 1.0f;
    private float step_count_degrees = 0.0f;
    private final int rotate_speed = 0; // 越小越快

    //
    private float to_move_x = 0.0f;
    private float to_move_y = 0.0f;
    private float one_step_move_x = 1.0f;
    private float one_step_move_y = 1.0f;
    private float step_count_move_xy = 0.0f;
    private float step_total_count_move_xy = 0.0f;
    private final int move_xy_speed = 2; // 越小越快

    private final static float CROP_WIDTH_1920 = 1920;
    private final static float CROP_HEIGHT_1080 = 1080;

    private float view_width, view_height, crop_width, crop_height, bmWidth, bmHeight;
    public float origWidth, origHeight;
    private float outPutPhotoW, outPutPhotoH;
    private double crop_diagonally, crop_degree_diagonally_s;
    private Matrix matrix = new Matrix();
    private float[] m = new float[9];

    private boolean isFitDependWidth = true;

    public AdjustParam mAdjustParam = new AdjustParam();
    private float centerOffsetX;
    private float centerOffsetY;

    private static final int NONE = 0;
    private static final int PAN = 1;
    private static final int ZOOM = 2;
    public int mode = NONE;

    public PointF LastPoint = new PointF();
    public PointF StartPoint = new PointF();
    public PointF P1 = new PointF();
    public PointF P2 = new PointF();
    public PointF P3 = new PointF();
    public PointF P4 = new PointF();
    public float saveScale = 1f;
    float minScale = 1f;
    float maxScale = 3f;
    private ScaleGestureDetector mScaleDetector;
    private Context mContext;
    private int padding;

    private float max_crop_in_padding;
    private float horizontal_minScale;// 水平时最小的缩放系数
    private float vertical_minScale;// 垂直时最小的缩放系数
    private float vertical_saveScale = 0;

    public CropPhotoView(Context context) {
        super(context);
        init();
        mContext = context;
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public CropPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        mContext = context;
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public CropPhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        mContext = context;
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public void init() {
        this.setOnTouchListener(this);
    }

    protected void onDraw(Canvas canvas) {
        // 画出变换后的图像
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, matrix, null);
        }
    }

    public void setBitmap(boolean crop_min_zoom, Bitmap bitmap, int padding, AdjustParam aAdjustParam, float dmw, float view_width, float view_height, float outPutPhotoW, float outPutPhotoH) {
        this.CROP_PHOTO_MIN_ZOOM = crop_min_zoom;

        mAdjustParam = aAdjustParam;

        this.outPutPhotoW = outPutPhotoW;
        this.outPutPhotoH = outPutPhotoH;

        // clear init start
        mNowDegrees = 0.0f;
        to_degrees = 0.0f;
        step_count_degrees = 0.0f;
        matrix = new Matrix();
        m = new float[9];

        LastPoint = new PointF();
        StartPoint = new PointF();
        P1 = new PointF();
        P2 = new PointF();
        P3 = new PointF();
        P4 = new PointF();
        saveScale = 1f;

        maxScale = 3f;
        // clear init end

        // 设置所编辑照片的bitmap
        this.bitmap = bitmap;

        // 设置剪裁区域余白边距
        this.padding = padding;

        // 原始图片宽度
        bmWidth = bitmap.getWidth();
        // 原始图片高度
        bmHeight = bitmap.getHeight();

        // 设置剪裁区域宽度
        crop_width = view_width - 2 * padding;
        // 设置剪裁区域高度
        crop_height = view_height - 2 * padding;

        // 设置剪裁区域中心横线与对角线夹角
        crop_degree_diagonally_s = Math.atan(crop_height / crop_width) / Math.PI * 180;

        // 设置剪裁区域对角线长度
        crop_diagonally = Math.sqrt((crop_width * crop_width + crop_height * crop_height)) / 2;

        // 设置所编辑照片宽度充满剪裁区域，高度可以超出剪裁区域
        fitScreen();

        // 如果此前该照片被裁剪过，则按照裁剪的坐标显示
        if (mAdjustParam.isAdjusted.equals("1")) {

            float org_crop_w = dmw - 2 * AppConstant.CROP_PADDING; // 剪裁区域的间距10(目前是固定值)
            float org_crop_h = org_crop_w * 9 / 16;
            float c_scale = crop_width / org_crop_w;

            float fCenterOffsetX = 0;
            float fCenterOffsetY = 0;
            float scaleR = 1.0f;

            if (isFitDependWidth) {
                fCenterOffsetX = c_scale * (Float.parseFloat(mAdjustParam.centerOffsetX) * org_crop_w / CROP_WIDTH_1920);
                fCenterOffsetY = c_scale * (Float.parseFloat(mAdjustParam.centerOffsetY) * org_crop_h / CROP_HEIGHT_1080);

                float savedScaleWidth = Float.parseFloat(mAdjustParam.zoomScale) * (org_crop_w / CROP_WIDTH_1920) * (float) outPutPhotoW;
                scaleR = savedScaleWidth / org_crop_w;
            } else {
                fCenterOffsetX = c_scale * (Float.parseFloat(mAdjustParam.centerOffsetX) * org_crop_w / CROP_WIDTH_1920);
                fCenterOffsetY = c_scale * (Float.parseFloat(mAdjustParam.centerOffsetY) * org_crop_h / CROP_HEIGHT_1080);

                float savedScaleHeight = Float.parseFloat(mAdjustParam.zoomScale) * (org_crop_h / CROP_HEIGHT_1080) * (float) outPutPhotoH;
                scaleR = savedScaleHeight / org_crop_h;
            }

            float deegrees = (-Float.parseFloat(mAdjustParam.rotateScale) * 360) / (2 * (float) Math.PI);
            mNowDegrees = deegrees;

            matrix.postRotate(deegrees, view_width / 2, view_height / 2);
            setZoomScale(scaleR, view_width / 2, view_height / 2);
            matrix.postTranslate(fCenterOffsetX, fCenterOffsetY >= 0 ? -fCenterOffsetY : -Math.round(fCenterOffsetY));
        }

        invalidate();
    }

    public void drawColor(Canvas canvas) {
        try {
            canvas.drawColor(Color.TRANSPARENT);
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, matrix, null);
            }
        } catch (Exception e) {
        }
    }

    // 设置父控件是否可以获取到触摸处理权限
    private void setParentScrollAble(boolean flag) {
        getParent().requestDisallowInterceptTouchEvent(!flag);
    }

    public boolean onTouch(View v, MotionEvent event) {

        if (bOnTouchEnable) {

            if (CROP_PHOTO_MIN_ZOOM) {
                if (Math.abs((int) mNowDegrees / 90) % 2 == 1) {
                    if (origWidth < origHeight) {
                        minScale = crop_height / origWidth;
                    }
                }
            } else {
                if (Math.abs((int) mNowDegrees / 90) % 2 == 1 && origWidth > origHeight) {
                    minScale = crop_width / origHeight;
                } else {
                    minScale = 1.0f;
                }
            }

            mScaleDetector.onTouchEvent(event);

            update4PointXY();
            PointF NowPoint = new PointF(event.getX(), event.getY());

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    setParentScrollAble(false);
//				CropViewOnTouchEvent down_event = new CropViewOnTouchEvent();
//				down_event.isOnTouch = true;
//				EventBus.getDefault().post(down_event);

                    LastPoint.set(event.getX(), event.getY());
                    StartPoint.set(LastPoint);
                    mode = PAN;

                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mode == PAN) {

                        float deltaX = NowPoint.x - LastPoint.x;
                        float deltaY = NowPoint.y - LastPoint.y;

                        matrix.postTranslate(deltaX, deltaY);

                        LastPoint.set(NowPoint.x, NowPoint.y);
                    }

                    break;

                case MotionEvent.ACTION_UP:
                    setParentScrollAble(true);

                case MotionEvent.ACTION_CANCEL:
                    mode = NONE;
                    noScaleTranslateToFitEdge(false);

//				CropViewOnTouchEvent up_event = new CropViewOnTouchEvent();
//				up_event.isOnTouch = false;
//				EventBus.getDefault().post(up_event);

                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    mode = NONE;

//				CropViewOnTouchEvent point_up_event = new CropViewOnTouchEvent();
//				point_up_event.isOnTouch = false;
//				EventBus.getDefault().post(point_up_event);

                    break;
            }
            invalidate();
        }

        return true;
    }

    public void setZoomScale(float mScaleFactor, float px, float py) {

        float origScale = saveScale;
        saveScale *= mScaleFactor;
        if (saveScale > maxScale) {
            saveScale = maxScale;
            mScaleFactor = maxScale / origScale;
        } else if (saveScale < minScale) {
            saveScale = minScale;
            mScaleFactor = minScale / origScale;
        }

        matrix.postScale(mScaleFactor, mScaleFactor, px, py);
        update4PointXY();

        // 缩小的时候
        if (mScaleFactor < 1) {
            // noScaleTranslateToFitEdge();
        }
    }

    public void noScaleTranslateToFitEdge(boolean smooth) {
        update4PointXY();

        float moveX = 0.0f;
        float moveY = 0.0f;
        float zfMarkX = 1.0f;
        float zfMarkY = 1.0f;

        // 照片的左边有余白的时候，平移至左边边缘对齐
        float left_padding = Math.min(Math.min(Math.min(P1.x, P2.x), P3.x), P4.x);
        if (left_padding > padding) {
            moveX = -(left_padding - padding);
            zfMarkX = -zfMarkX;
        }

        // 照片的右边有余白的时候，平移至右边边缘对齐
        float right_padding = Math.max(Math.max(Math.max(P1.x, P2.x), P3.x), P4.x);
        if (right_padding < crop_width + padding) {
            moveX = crop_width + padding - right_padding;
        }

        // 照片的上边有余白的时候，平移至上边边缘对齐
        float top_padding = Math.min(Math.min(Math.min(P1.y, P2.y), P3.y), P4.y);
        if (top_padding > padding) {
            moveY = -(top_padding - padding);
            zfMarkY = -zfMarkY;
        }

        // 照片的下边有余白的时候，平移至下边边缘对齐
        float bottom_padding = Math.max(Math.max(Math.max(P1.y, P2.y), P3.y), P4.y);
        if (bottom_padding < crop_height + padding) {
            moveY = crop_height + padding - bottom_padding;
        }

        if (CROP_PHOTO_MIN_ZOOM) {
            // 照片的左边和右边同时有余白时，平移至中间对齐
            if (saveScale <= 1.0f && Math.abs((int) mNowDegrees / 90) % 2 == 0) {
                if (left_padding > padding || right_padding < crop_width + padding) {
                    moveX = (-(left_padding - padding) + crop_width + padding - right_padding) / 2;
                    zfMarkX = -zfMarkX;
                }
            } else if (Math.abs((int) mNowDegrees / 90) % 2 == 1) {
                if (left_padding > padding || right_padding < crop_width + padding) {
                    moveX = (-(left_padding - padding) + crop_width + padding - right_padding) / 2;
                    zfMarkX = -zfMarkX;
                }
            }
        }

        if (smooth) {
            bOnTouchEnable = false;

            to_move_x = moveX;
            to_move_y = moveY;
            if (Math.abs(to_move_x) == 0 && Math.abs(to_move_y) != 0) {
                step_total_count_move_xy = Math.abs(to_move_y);
                one_step_move_x = 0;
                one_step_move_y = 1.0f * zfMarkY;
            } else if (Math.abs(to_move_x) != 0 && Math.abs(to_move_y) == 0) {
                step_total_count_move_xy = Math.abs(to_move_x);
                one_step_move_x = 1.0f * zfMarkX;
                one_step_move_y = 0;
            } else if (Math.abs(to_move_x) >= Math.abs(to_move_y)) {
                step_total_count_move_xy = Math.abs(to_move_y);
                one_step_move_x = Math.abs(moveX) / Math.abs(moveY) * zfMarkX;
                one_step_move_y = 1.0f * zfMarkY;
            } else {
                step_total_count_move_xy = Math.abs(to_move_x);
                one_step_move_x = 1.0f * zfMarkX;
                one_step_move_y = Math.abs(moveY) / Math.abs(moveX) * zfMarkY;
            }

            step_count_move_xy = 0.0f;

            mUIHandler.sendEmptyMessageDelayed(UI_MOVE_ANIME_START, 0);
        } else {
            matrix.postTranslate(moveX, moveY);
        }
    }

    public float getRotatedMaxCropInPadding(Matrix rotateM) {
        float[] f = new float[9];
        rotateM.getValues(f);
        // 图片4个顶点的坐标
        float x1 = f[0] * 0 + f[1] * 0 + f[2];
        float y1 = f[3] * 0 + f[4] * 0 + f[5];
        float x2 = f[0] * bmWidth + f[1] * 0 + f[2];
        float y2 = f[3] * bmWidth + f[4] * 0 + f[5];
        float x3 = f[0] * 0 + f[1] * bmHeight + f[2];
        float y3 = f[3] * 0 + f[4] * bmHeight + f[5];
        float x4 = f[0] * bmWidth + f[1] * bmHeight + f[2];
        float y4 = f[3] * bmWidth + f[4] * bmHeight + f[5];

        float max_crop_in_padding = 0.0f;
        float left = 0.0f;
        float top = 0.0f;
        float right = 0.0f;
        float bottom = 0.0f;

        // 照片的左边有余白的时候
        float left_padding = Math.min(Math.min(Math.min(x1, x2), x3), x4);
        if (left_padding > padding) {
            left = left_padding - padding;
        }

        // 照片的右边有余白的时候
        float right_padding = Math.max(Math.max(Math.max(x1, x2), x3), x4);
        if (right_padding < crop_width + padding) {
            right = crop_width + padding - right_padding;
        }

        // 照片的上边有余白的时候
        float top_padding = Math.min(Math.min(Math.min(y1, y2), y3), y4);
        if (top_padding > padding) {
            top = top_padding - padding;
        }

        // 照片的下边有余白的时候
        float bottom_padding = Math.max(Math.max(Math.max(y1, y2), y3), y4);
        if (bottom_padding < crop_height + padding) {
            bottom = crop_height + padding - bottom_padding;
        }

        max_crop_in_padding = Math.max(Math.max(Math.max(top, bottom), left), right);

        return max_crop_in_padding;
    }

    private void update4PointXY() {
        matrix.getValues(m);
        // 图片4个顶点的坐标
        P1.x = m[0] * 0 + m[1] * 0 + m[2];
        P1.y = m[3] * 0 + m[4] * 0 + m[5];
        P2.x = m[0] * bmWidth + m[1] * 0 + m[2];
        P2.y = m[3] * bmWidth + m[4] * 0 + m[5];
        P3.x = m[0] * 0 + m[1] * bmHeight + m[2];
        P3.y = m[3] * 0 + m[4] * bmHeight + m[5];
        P4.x = m[0] * bmWidth + m[1] * bmHeight + m[2];
        P4.y = m[3] * bmWidth + m[4] * bmHeight + m[5];
        // 图片现宽度
        // double width = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public AdjustParam getAdjustParam() {
        update4PointXY();

        // 照片的中心相对于裁剪框的中心的偏移量
        updateCenterOffsetXYParam();
        // 图片最终的尺寸相对图片原始尺寸的缩放系数。
        updateZoomScaleParam();

        // 把角度转换为PI
        // 逆时针旋转为正，最大不超过360（360即为0）
        double formatDegrees = -mNowDegrees;
        if (formatDegrees < 0) {
            formatDegrees = 360 + formatDegrees;
        }
        mAdjustParam.rotateScale = String.valueOf(decimalFormat.format(((Math.abs(formatDegrees) / 360) * 2 * Math.PI)));

        // showAdjustParam();

        return mAdjustParam;

    }

    // 照片的中心相对于裁剪框的中心的偏移量（裁剪框按照1920*1080计算）
    private void updateCenterOffsetXYParam() {

        centerOffsetX = (P1.x + P4.x) / 2;
        centerOffsetY = (P1.y + P4.y) / 2;

        float offsetMPointX = padding + crop_width / 2;
        float offsetMPointY = padding + crop_height / 2;

        // 向限(＋,＋)
        if (centerOffsetX >= offsetMPointX && centerOffsetY <= offsetMPointY) {
            centerOffsetX = centerOffsetX - offsetMPointX;
            centerOffsetY = offsetMPointY - centerOffsetY;
        }
        // 向限(＋,-)
        else if (centerOffsetX >= offsetMPointX && centerOffsetY >= offsetMPointY) {
            centerOffsetX = centerOffsetX - offsetMPointX;
            centerOffsetY = -(centerOffsetY - offsetMPointY);
        }
        // 向限(-,+)
        else if (centerOffsetX <= offsetMPointX && centerOffsetY <= offsetMPointY) {
            centerOffsetX = -(offsetMPointX - centerOffsetX);
            centerOffsetY = offsetMPointY - centerOffsetY;
        }
        // 向限(-,-)
        else if (centerOffsetX <= offsetMPointX && centerOffsetY >= offsetMPointY) {
            centerOffsetX = -(offsetMPointX - centerOffsetX);
            centerOffsetY = -(centerOffsetY - offsetMPointY);
        }

        centerOffsetX = (CROP_WIDTH_1920 * centerOffsetX) / crop_width;
        centerOffsetY = (CROP_HEIGHT_1080 * centerOffsetY) / crop_height;

        mAdjustParam.centerOffsetX = decimalFormat.format(centerOffsetX) + "";
        mAdjustParam.centerOffsetY = decimalFormat.format(centerOffsetY) + "";
    }

    // 图片最终的尺寸相对图片原始尺寸的缩放系数。小数点后保留8位 默认为0
    public void updateZoomScaleParam() {

        float zoomScale = 1.0f;

        if (isFitDependWidth) {
            if (origHeight >= crop_height) {
                float scaleWidth = Math.round(origWidth * saveScale);

                zoomScale = (CROP_WIDTH_1920 / origWidth) * (scaleWidth / outPutPhotoW);
            } else {
                float scaleHeight = Math.round(origHeight * saveScale);

                zoomScale = (CROP_HEIGHT_1080 / origHeight) * (scaleHeight / outPutPhotoH);
            }
        } else {
            if (origWidth >= crop_width) {
                float scaleHeight = Math.round(origHeight * saveScale);

                zoomScale = (CROP_HEIGHT_1080 / origHeight) * (scaleHeight / outPutPhotoH);
            } else {
                float scaleWidth = Math.round(origWidth * saveScale);

                zoomScale = (CROP_WIDTH_1920 / origWidth) * (scaleWidth / outPutPhotoW);
            }
        }

        mAdjustParam.zoomScale = decimalFormat.format(zoomScale) + "";

        Log.e("BJX", "[Photo Scale]" + mAdjustParam.zoomScale);
    }

    private void fitScreen() {

        // Fit to screen.
        float scale;
        float scaleX = (float) crop_width / (float) bmWidth;
        float scaleY = (float) crop_height / (float) bmHeight;
        // Fit crop_width
        scale = Math.max(scaleX, scaleY);

        if (scaleX >= scaleY) {
            isFitDependWidth = true;
        } else {
            isFitDependWidth = false;
        }

        // fit inCenter
        // scale = Math.min(scaleX, scaleY);

        if (!mAdjustParam.isAdjusted.equals("1")) {
            mAdjustParam.zoomScale = decimalFormat.format(scale) + "";
        }

        matrix.setScale(scale, scale);

        saveScale = 1f;

        // Center the image
        float redundantXSpace = ((float) crop_width - (scale * (float) bmWidth)) / 2;
        float redundantYSpace = ((float) crop_height - (scale * (float) bmHeight)) / 2;

        // 平移至剪裁区域左上角为定点
        matrix.postTranslate(redundantXSpace + padding, redundantYSpace + padding);

        // 剪裁区域初始照片宽度
        origWidth = scale * (float) bmWidth;
        // 剪裁区域初始照片高度
        origHeight = scale * (float) bmHeight;

        if (CROP_PHOTO_MIN_ZOOM) {
            horizontal_minScale = crop_height / origHeight;
            vertical_minScale = crop_width / origHeight;
            minScale = horizontal_minScale;
        } else {
            minScale = 1f;
        }
    }

    public void setRotate(float degrees, boolean smooth) {

        if (bOnTouchEnable) {
            mNowDegrees = mNowDegrees + degrees;

            if (mNowDegrees >= 360 || mNowDegrees <= -360) {
                mNowDegrees = 0;
            }

            update4PointXY();

            boolean needScale = true;

            float scaleWidth = Math.round(origWidth * saveScale);
            float scaleHeight = Math.round(origHeight * saveScale);

            double after_scale_w = scaleWidth;
            double after_scale_h = scaleHeight;

            if (degrees == 90 || degrees == -90) {
                Matrix rotateM = new Matrix();
                rotateM.set(matrix);
                rotateM.postRotate(degrees, view_width / 2, view_height / 2); // 预旋转，然后检查旋转后是否有余白
                max_crop_in_padding = getRotatedMaxCropInPadding(rotateM);

                // 如果旋转后4个边没有余白距离的话不放缩
                if (max_crop_in_padding == 0) {
                    needScale = false;
                }
                // 如果旋转后4个边有余白距离的话放缩
                else {
                    if (scaleWidth <= scaleHeight) {
                        if (scaleWidth < crop_height) {
                            saveScale = (scaleHeight + 2 * max_crop_in_padding) / scaleHeight;
                        } else {
                            needScale = false;
                        }
                    } else {
                        if (scaleHeight < crop_width) {
                            saveScale = (scaleHeight + 2 * max_crop_in_padding) / scaleHeight;
                            vertical_saveScale = saveScale;
                        } else {
                            needScale = false;
                        }
                    }
                }

            }
            // 不大于剪裁区域对角线与水平横线夹角的时候
            else if (Math.round(degrees) <= crop_degree_diagonally_s) {
                if (scaleWidth <= scaleHeight) {
                    after_scale_w = Math.cos((crop_degree_diagonally_s - degrees) * Math.PI / 180) * crop_diagonally * 2;

                    if (after_scale_w > scaleWidth) {
                        saveScale = (float) (after_scale_w / scaleWidth);
                    } else {
                        needScale = false;
                    }
                } else {
                    after_scale_h = Math.cos((crop_degree_diagonally_s - degrees) * Math.PI / 180) * crop_diagonally * 2;

                    if (after_scale_h > scaleHeight) {
                        saveScale = (float) (after_scale_h / scaleHeight);
                    } else {
                        needScale = false;
                    }
                }
            } else {
                if (scaleWidth <= scaleHeight) {
                    after_scale_w = Math.cos((90.0f - degrees - crop_degree_diagonally_s) * Math.PI / 180) * crop_diagonally * 2;

                    if (after_scale_w > scaleWidth) {
                        saveScale = (float) (after_scale_w / scaleWidth);
                    } else {
                        needScale = false;
                    }
                } else {
                    after_scale_h = Math.cos((90.0f - degrees - crop_degree_diagonally_s) * Math.PI / 180) * crop_diagonally * 2;

                    if (after_scale_h > scaleHeight) {
                        saveScale = (float) (after_scale_h / scaleHeight);
                    } else {
                        needScale = false;
                    }
                }
            }

            step_count_degrees = 0.0f;
            to_degrees = degrees;
            bOnTouchEnable = false;

            if (smooth) {
                mUIHandler.removeMessages(UI_ROTATE_ANIME_START);
                mUIHandler.removeMessages(UI_ROTATE_ANIME_STOP);
                mUIHandler.sendEmptyMessageDelayed(UI_ROTATE_ANIME_START, 0);
            } else {
                matrix.postRotate(degrees, view_width / 2, view_height / 2);
            }

            if (needScale) {
                matrix.postScale(saveScale, saveScale, view_width / 2, view_height / 2);
            }

            if (CROP_PHOTO_MIN_ZOOM) {
                if (Math.abs((int) mNowDegrees / 90) % 2 == 1) {
                    if (origWidth > origHeight) {
                        minScale = crop_height / origWidth;
                        if (needScale) {
                            saveScale = vertical_minScale;
                        }
                    } else {
                        minScale = vertical_minScale;
                        if (needScale) {
                            saveScale = vertical_minScale;
                        }
                    }
                } else if (Math.abs((int) mNowDegrees / 90) % 2 == 0) {
                    minScale = horizontal_minScale;
                    if (needScale) {
                        saveScale = (crop_width - max_crop_in_padding) / crop_width;
                    }
                }
            }

            noScaleTranslateToFitEdge(false);

            if (!smooth) {
                bOnTouchEnable = true;
                invalidate();
            }
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mode = ZOOM;
            return true;
        }

        public boolean onScale(ScaleGestureDetector detector) {
            float mScaleFactor = (float) Math.min(Math.max(.85f, detector.getScaleFactor()), 1.15);

            setZoomScale(mScaleFactor, detector.getFocusX(), detector.getFocusY());
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
            // noScaleTranslateToFitEdge(false);
        }
    }

    public Bitmap getCropImage() {

        Bitmap rectBitmap = null;

        try {
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

            rectBitmap = Bitmap.createBitmap(bitmap, padding, padding, (int) crop_width, (int) crop_height);

            Canvas canvas = new Canvas(rectBitmap);
            drawColor(canvas);

            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } catch (OutOfMemoryError err) {
            System.gc();
            rectBitmap = null;
        } catch (Exception e) {
            rectBitmap = null;
        }

        return rectBitmap;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        view_width = MeasureSpec.getSize(widthMeasureSpec);
        view_height = MeasureSpec.getSize(heightMeasureSpec);
    }

    Handler mUIHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UI_MOVE_ANIME_START:
                    bOnTouchEnable = false;

                    if (step_count_move_xy < step_total_count_move_xy) {
                        matrix.postTranslate(one_step_move_x, one_step_move_y);
                        mUIHandler.sendEmptyMessageDelayed(UI_MOVE_ANIME_START, move_xy_speed);
                        step_count_move_xy++;
                    } else {
                        mUIHandler.sendEmptyMessageDelayed(UI_MOVE_ANIME_STOP, 0);
                    }
                    invalidate();
                    break;
                case UI_MOVE_ANIME_STOP:
                    invalidate();
                    mUIHandler.removeMessages(UI_MOVE_ANIME_START);
                    mUIHandler.removeMessages(UI_MOVE_ANIME_STOP);
                    noScaleTranslateToFitEdge(false);
                    bOnTouchEnable = true;
                    break;
                case UI_ROTATE_ANIME_START:
                    bOnTouchEnable = false;

                    if (to_degrees >= 0) {

                        if (step_count_degrees < to_degrees) {
                            matrix.postRotate(one_step_degrees, view_width / 2, view_height / 2); // 要旋转的角度
                            mUIHandler.sendEmptyMessageDelayed(UI_ROTATE_ANIME_START, rotate_speed);
                        } else {
                            matrix.postRotate(to_degrees - step_count_degrees, view_width / 2, view_height / 2); // 要旋转的角度
                            mUIHandler.sendEmptyMessageDelayed(UI_ROTATE_ANIME_STOP, 0);
                        }
                        step_count_degrees++;
                    } else {

                        if (step_count_degrees > to_degrees) {
                            matrix.postRotate(-one_step_degrees, view_width / 2, view_height / 2); // 要旋转的角度
                            mUIHandler.sendEmptyMessageDelayed(UI_ROTATE_ANIME_START, rotate_speed);
                        } else {
                            matrix.postRotate(to_degrees - step_count_degrees, view_width / 2, view_height / 2); // 要旋转的角度
                            mUIHandler.sendEmptyMessageDelayed(UI_ROTATE_ANIME_STOP, 0);
                        }
                        step_count_degrees--;
                    }

                    invalidate();
                    break;
                case UI_ROTATE_ANIME_STOP:
                    invalidate();
                    mUIHandler.removeMessages(UI_ROTATE_ANIME_START);
                    mUIHandler.removeMessages(UI_ROTATE_ANIME_STOP);
                    noScaleTranslateToFitEdge(false);
                    bOnTouchEnable = true;
                    break;
                default:
                    break;
            }
        }
    };

    private void showAdjustParam() {
        Log.e("CROP", "-------showAdjustParam-------");
        Log.e("CROP", "centerOffsetX:" + mAdjustParam.centerOffsetX);
        Log.e("CROP", "centerOffsetY:" + mAdjustParam.centerOffsetY);
        Log.e("CROP", "zoomScale:" + mAdjustParam.zoomScale);
        Log.e("CROP", "rotateScale:" + mAdjustParam.rotateScale);
        Log.e("CROP", "mNowDegrees:" + mNowDegrees);
    }
}