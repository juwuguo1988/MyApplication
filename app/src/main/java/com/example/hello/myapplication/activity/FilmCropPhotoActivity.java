package com.example.hello.myapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.hello.myapplication.R;
import com.example.hello.myapplication.common.bean.AdjustParam;
import com.example.hello.myapplication.common.bean.BitmapModel;
import com.example.hello.myapplication.common.views.CropPhotoView;
import com.example.hello.myapplication.utils.config.AppConstant;
import com.example.hello.myapplication.utils.ui.BitmapUtils;
import com.example.hello.myapplication.utils.ui.DialogUtils;
import com.example.hello.myapplication.utils.ui.UI;

public class FilmCropPhotoActivity extends AppCompatActivity {
    public DisplayMetrics dm;
    public int dmw, dmh;
    private CropPhotoView iv_crop_photo;
    private ImageView iv_crop_left90;
    private ImageView iv_crop_right90;
    private ImageView iv_crop_pull_lt, iv_crop_pull_lb, iv_crop_pull_rt, iv_crop_pull_rb;
    private LinearLayout crop_area, photo_crop_frame_layout, mask_top, mask_bottom, mask_left, mask_right;
    private LinearLayout bmask_top, bmask_bottom, bmask_left, bmask_right;
    private RelativeLayout crop_line_layout, crop_pull_frame_layout, crop_frame_layout;
    private int crop_pull_frame_max_width;
    private int crop_width;
    private int crop_height;
    private int crop_leftMargin;
    private int crop_topMargin;
    private int crop_rightMargin;
    private int crop_bottomMargin;
    private final int max_scale = 3;


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FilmCropPhotoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_photo_layout);
        findViewById();
        initView();
        initListener();
    }

    private void findViewById() {
        iv_crop_photo = findViewById(R.id.iv_crop_photo);
        iv_crop_left90 = findViewById(R.id.iv_crop_left90);
        iv_crop_right90 = findViewById(R.id.iv_crop_right90);
        iv_crop_pull_lt = findViewById(R.id.iv_crop_pull_lt);
        iv_crop_pull_lb = findViewById(R.id.iv_crop_pull_lb);
        iv_crop_pull_rt = findViewById(R.id.iv_crop_pull_rt);
        iv_crop_pull_rb = findViewById(R.id.iv_crop_pull_rb);
        crop_area = findViewById(R.id.crop_area);
        photo_crop_frame_layout = findViewById(R.id.photo_crop_frame_layout);
        mask_top = findViewById(R.id.mask_top);
        mask_bottom = findViewById(R.id.mask_bottom);
        mask_left = findViewById(R.id.mask_left);
        mask_right = findViewById(R.id.mask_right);
        bmask_top = findViewById(R.id.bmask_top);
        bmask_bottom = findViewById(R.id.bmask_bottom);
        bmask_left = findViewById(R.id.bmask_left);
        bmask_right = findViewById(R.id.bmask_right);
        crop_line_layout = findViewById(R.id.crop_line_layout);
        crop_frame_layout = findViewById(R.id.crop_frame_layout);
        crop_pull_frame_layout = findViewById(R.id.crop_pull_frame_layout);
    }

    private void initView() {
        crop_line_layout.setVisibility(View.GONE);
        mask_top.setVisibility(View.INVISIBLE);
        mask_bottom.setVisibility(View.INVISIBLE);
        mask_left.setVisibility(View.INVISIBLE);
        mask_right.setVisibility(View.INVISIBLE);


        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        dmw = dm.widthPixels;
        dmh = dm.heightPixels;

        iv_crop_pull_lt.setLayoutParams(UI.getRelativeLayoutPararmW2(dmw, 40));
        iv_crop_pull_lb.setLayoutParams(UI.getRelativeLayoutPararmW2(dmw, 40));
        iv_crop_pull_rt.setLayoutParams(UI.getRelativeLayoutPararmW2(dmw, 40));
        iv_crop_pull_rb.setLayoutParams(UI.getRelativeLayoutPararmW2(dmw, 40));

        iv_crop_left90.setLayoutParams(UI.getLinearLayoutPararmW2LR(dmw, dmh, 36, 40, 20));
        iv_crop_right90.setLayoutParams(UI.getLinearLayoutPararmW2LR(dmw, dmh, 36, 20, 0));

        iv_crop_photo.setVisibility(View.VISIBLE);
        iv_crop_left90.setVisibility(View.VISIBLE);
        iv_crop_right90.setVisibility(View.VISIBLE);
        cropPullToFitFullCropArea();

        iv_crop_photo.postDelayed(new Runnable() {
            public void run() {
                InitLoadCropPhoto();
            }
        }, 150);
    }

    private void cropPullToFitFullCropArea() {
        crop_pull_frame_max_width = dmw - 2 * AppConstant.CROP_PADDING;

        crop_line_layout.setLayoutParams(UI.getRelativeLayoutPararmLRTB16_9True(AppConstant.CROP_PADDING - 7, AppConstant.CROP_PADDING - 7, AppConstant.CROP_PADDING - 7, AppConstant.CROP_PADDING - 7));
        crop_frame_layout.setLayoutParams(UI.getRelativeLayoutPararmWH16_9True(dmw));
        crop_pull_frame_layout.setLayoutParams(UI.getRelativeLayoutPararmWHLRTB16_9True(dmw, AppConstant.CROP_PADDING, AppConstant.CROP_PADDING, AppConstant.CROP_PADDING, AppConstant.CROP_PADDING));
        photo_crop_frame_layout.setLayoutParams(UI.getRelativeLayoutPararmWHLRTB16_9True(dmw, AppConstant.CROP_PADDING, AppConstant.CROP_PADDING, AppConstant.CROP_PADDING, AppConstant.CROP_PADDING));

        mask_top.setLayoutParams(UI.getRelativeLayoutPararmH_WFillTrue(AppConstant.CROP_PADDING));
        mask_bottom.setLayoutParams(UI.getRelativeLayoutPararmH_WFillTrue(AppConstant.CROP_PADDING));
        mask_left.setLayoutParams(UI.getRelativeLayoutPararmW_HFillTrue(AppConstant.CROP_PADDING));
        mask_right.setLayoutParams(UI.getRelativeLayoutPararmW_HFillTrue(AppConstant.CROP_PADDING));

        setPaddingMaskWhiteColor(true);
    }

    private void setPaddingMaskWhiteColor(boolean isWhite) {
        if (isWhite) {
            mask_top.setAlpha(1.0f);
            mask_bottom.setAlpha(1.0f);
            mask_left.setAlpha(1.0f);
            mask_right.setAlpha(1.0f);
            mask_top.setBackgroundColor(getResources().getColor(R.color.black));
            mask_bottom.setBackgroundColor(getResources().getColor(R.color.black));
            mask_left.setBackgroundColor(getResources().getColor(R.color.black));
            mask_right.setBackgroundColor(getResources().getColor(R.color.black));
        } else {
            mask_top.setAlpha(0.4f);
            mask_bottom.setAlpha(0.4f);
            mask_left.setAlpha(0.4f);
            mask_right.setAlpha(0.4f);
            mask_top.setBackgroundColor(getResources().getColor(R.color.black));
            mask_bottom.setBackgroundColor(getResources().getColor(R.color.black));
            mask_left.setBackgroundColor(getResources().getColor(R.color.black));
            mask_right.setBackgroundColor(getResources().getColor(R.color.black));
        }
    }

    private void InitLoadCropPhoto() {

        final float crop_view_width = dmw;
        final float crop_view_height = crop_view_width * 9 / 16;

        try {
            String tmpDir = Environment.getExternalStorageDirectory().toString() + "/XZLFile/Photo/";
            String originalFilePath = tmpDir + "/timg.jpg";
            BitmapModel bitmapModel = BitmapUtils.readBitmapModel(originalFilePath, AppConstant.CROP_PHOTO_BIG_OUTPUT_SIZE_PARAM);

            float outPutPhotoW = 480;
            float outPutPhotoH = 800;

            if (outPutPhotoW > 0 && outPutPhotoH > 0) {
                if (bitmapModel.bitmap != null) {
                    try {
                        AdjustParam mAdjustParam = new AdjustParam();
                        iv_crop_photo.setBitmap(true, bitmapModel.bitmap, 0, mAdjustParam, dmw, crop_view_width, crop_view_height, outPutPhotoW, outPutPhotoH);
                    } catch (Exception e) {
                    }

                    try {
                        iv_crop_photo.updateZoomScaleParam();

                        Double.parseDouble(iv_crop_photo.mAdjustParam.zoomScale);
                        Double.parseDouble(iv_crop_photo.mAdjustParam.centerOffsetX);
                        Double.parseDouble(iv_crop_photo.mAdjustParam.centerOffsetY);

                    } catch (Exception e) {
                        showInvalidDialogAndExit();
                    }
                    paddingMaskVisible();
                }
            } else {
                showInvalidDialogAndExit();
            }
        } catch (Exception e) {
            showInvalidDialogAndExit();
        }
    }

    private void paddingMaskVisible() {
        mask_top.setVisibility(View.VISIBLE);
        mask_bottom.setVisibility(View.VISIBLE);
        mask_left.setVisibility(View.VISIBLE);
        mask_right.setVisibility(View.VISIBLE);
    }

    private void showInvalidDialogAndExit() {
        DialogUtils.showDialog(FilmCropPhotoActivity.this, getString(R.string.prompt), getString(R.string.invalid_crop_photo_text), getString(R.string.i_know), getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                exitActivity();
            }
        }, false);
    }

    protected void initListener() {

        iv_crop_left90.setOnClickListener(v -> {
            iv_crop_photo.setRotate(-90.0f, false);
        });
        iv_crop_right90.setOnClickListener(v -> {
            iv_crop_photo.setRotate(90.0f, false);
        });

        iv_crop_pull_lt.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:// 手指压下屏幕
                        iv_crop_photo.mAdjustParam.isAdjusted = "1";
                        // tv_index.setVisibility(View.GONE);
                        crop_line_layout.setVisibility(View.VISIBLE);

                        setPaddingMaskWhiteColor(false);
                        break;
                    case MotionEvent.ACTION_MOVE:// 手指在屏幕移动，该 事件会不断地触发
                        crop_line_layout.setVisibility(View.VISIBLE);

                        if ((int) event.getRawX() <= crop_pull_frame_max_width * 2 / max_scale) {
                            crop_width = (dmw - AppConstant.CROP_PADDING) - (int) event.getRawX();
                            crop_height = crop_width * 9 / 16;

                            crop_leftMargin = (int) event.getRawX();
                            crop_topMargin = (dmw - AppConstant.CROP_PADDING) * 9 / 16 - crop_height;
                            crop_rightMargin = AppConstant.CROP_PADDING;
                            crop_bottomMargin = AppConstant.CROP_PADDING;

                            photo_crop_frame_layout.setLayoutParams(UI.getCropLayoutPararmWH16_9(crop_width, crop_height, crop_leftMargin, crop_topMargin, crop_rightMargin, crop_bottomMargin));
                            crop_pull_frame_layout.setLayoutParams(UI.getCropLayoutPararmWH16_9(crop_width, crop_height, crop_leftMargin, crop_topMargin, crop_rightMargin, crop_bottomMargin));

                            mask_top.setLayoutParams(UI.getRelativeLayoutPararmH_WFillTrue(crop_topMargin));
                            mask_bottom.setLayoutParams(UI.getRelativeLayoutPararmH_WFillTrue(crop_bottomMargin));
                            mask_left.setLayoutParams(UI.getRelativeLayoutPararmW_HFillTrue(crop_leftMargin));
                            mask_right.setLayoutParams(UI.getRelativeLayoutPararmW_HFillTrue(crop_rightMargin));
                        }

                        break;
                    case MotionEvent.ACTION_UP:// 手指离开屏
                        crop_line_layout.setVisibility(View.GONE);

                        int crop_org_w = dmw - 2 * AppConstant.CROP_PADDING;
                        int crop_org_h = crop_org_w * 9 / 16 + 3;

                        float scale = (float) crop_org_w / (float) photo_crop_frame_layout.getWidth();

                        // 参数为放缩时，按住的边角对角线保持固定的定点坐标
                        iv_crop_photo.setZoomScale(scale, (float) crop_org_w + (float) AppConstant.CROP_PADDING, crop_org_h);

                        cropPullToFitFullCropArea();

                        iv_crop_photo.invalidate();

                        break;
                }

                return true;
            }
        });

        iv_crop_pull_lb.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:// 手指压下屏幕
                        iv_crop_photo.mAdjustParam.isAdjusted = "1";
                        // tv_index.setVisibility(View.GONE);
                        crop_line_layout.setVisibility(View.VISIBLE);
                        setPaddingMaskWhiteColor(false);

                        break;
                    case MotionEvent.ACTION_MOVE:// 手指在屏幕移动，该 事件会不断地触发
                        crop_line_layout.setVisibility(View.VISIBLE);

                        if ((int) event.getRawX() <= crop_pull_frame_max_width * 2 / max_scale) {
                            crop_width = (dmw - AppConstant.CROP_PADDING) - (int) event.getRawX();
                            crop_height = crop_width * 9 / 16;

                            crop_leftMargin = (int) event.getRawX();
                            crop_topMargin = AppConstant.CROP_PADDING;
                            crop_rightMargin = AppConstant.CROP_PADDING;
                            crop_bottomMargin = (dmw - AppConstant.CROP_PADDING) * 9 / 16 - crop_height;

                            photo_crop_frame_layout.setLayoutParams(UI.getCropLayoutPararmWH16_9(crop_width, crop_height, crop_leftMargin, crop_topMargin, crop_rightMargin, crop_bottomMargin));
                            crop_pull_frame_layout.setLayoutParams(UI.getCropLayoutPararmWH16_9(crop_width, crop_height, crop_leftMargin, crop_topMargin, crop_rightMargin, crop_bottomMargin));

                            mask_top.setLayoutParams(UI.getRelativeLayoutPararmH_WFillTrue(crop_topMargin));
                            mask_bottom.setLayoutParams(UI.getRelativeLayoutPararmH_WFillTrue(crop_bottomMargin));
                            mask_left.setLayoutParams(UI.getRelativeLayoutPararmW_HFillTrue(crop_leftMargin));
                            mask_right.setLayoutParams(UI.getRelativeLayoutPararmW_HFillTrue(crop_rightMargin));

                        }

                        break;
                    case MotionEvent.ACTION_UP:// 手指离开屏
                        crop_line_layout.setVisibility(View.GONE);

                        int crop_org_w = dmw - 2 * AppConstant.CROP_PADDING;

                        float scale = (float) crop_org_w / (float) photo_crop_frame_layout.getWidth();

                        // 参数为放缩时，按住的边角对角线保持固定的定点坐标
                        iv_crop_photo.setZoomScale(scale, (float) crop_org_w + (float) AppConstant.CROP_PADDING, 0f + (float) AppConstant.CROP_PADDING);

                        cropPullToFitFullCropArea();

                        iv_crop_photo.invalidate();
                        break;
                }

                return true;
            }
        });

        iv_crop_pull_rt.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:// 手指压下屏幕
                        iv_crop_photo.mAdjustParam.isAdjusted = "1";
                        // tv_index.setVisibility(View.GONE);
                        crop_line_layout.setVisibility(View.VISIBLE);
                        setPaddingMaskWhiteColor(false);

                        break;
                    case MotionEvent.ACTION_MOVE:// 手指在屏幕移动，该 事件会不断地触发
                        crop_line_layout.setVisibility(View.VISIBLE);

                        if ((int) event.getRawX() >= crop_pull_frame_max_width / max_scale) {
                            crop_width = (int) event.getRawX();
                            crop_height = (int) event.getRawX() * 9 / 16;

                            crop_leftMargin = AppConstant.CROP_PADDING;
                            crop_topMargin = (dmw - AppConstant.CROP_PADDING) * 9 / 16 - crop_height;
                            crop_rightMargin = (dmw - AppConstant.CROP_PADDING) - (int) event.getRawX();
                            crop_bottomMargin = AppConstant.CROP_PADDING;

                            photo_crop_frame_layout.setLayoutParams(UI.getCropLayoutPararmWH16_9(crop_width, crop_height, crop_leftMargin, crop_topMargin, crop_rightMargin, crop_bottomMargin));
                            crop_pull_frame_layout.setLayoutParams(UI.getCropLayoutPararmWH16_9(crop_width, crop_height, crop_leftMargin, crop_topMargin, crop_rightMargin, crop_bottomMargin));

                            mask_top.setLayoutParams(UI.getRelativeLayoutPararmH_WFillTrue(crop_topMargin));
                            mask_bottom.setLayoutParams(UI.getRelativeLayoutPararmH_WFillTrue(crop_bottomMargin));
                            mask_left.setLayoutParams(UI.getRelativeLayoutPararmW_HFillTrue(crop_leftMargin));
                            mask_right.setLayoutParams(UI.getRelativeLayoutPararmW_HFillTrue(crop_rightMargin));
                        }

                        break;
                    case MotionEvent.ACTION_UP:// 手指离开屏
                        crop_line_layout.setVisibility(View.GONE);

                        int crop_org_w = dmw - 2 * AppConstant.CROP_PADDING;
                        int crop_org_h = crop_org_w * 9 / 16 + 3;

                        float scale = (float) crop_org_w / (float) photo_crop_frame_layout.getWidth();

                        // 参数为放缩时，按住的边角对角线保持固定的定点坐标
                        iv_crop_photo.setZoomScale(scale, 0f + (float) AppConstant.CROP_PADDING, 0f + crop_org_h);

                        cropPullToFitFullCropArea();

                        iv_crop_photo.invalidate();
                        break;
                }

                return true;
            }
        });

        iv_crop_pull_rb.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:// 手指压下屏幕
                        iv_crop_photo.mAdjustParam.isAdjusted = "1";
                        // tv_index.setVisibility(View.GONE);
                        crop_line_layout.setVisibility(View.VISIBLE);
                        setPaddingMaskWhiteColor(false);

                        break;
                    case MotionEvent.ACTION_MOVE:// 手指在屏幕移动，该 事件会不断地触发
                        crop_line_layout.setVisibility(View.VISIBLE);

                        if ((int) event.getRawX() >= crop_pull_frame_max_width / max_scale) {

                            crop_width = (int) event.getRawX();
                            crop_height = (int) event.getRawX() * 9 / 16;

                            crop_leftMargin = AppConstant.CROP_PADDING;
                            crop_topMargin = AppConstant.CROP_PADDING;
                            crop_rightMargin = (dmw - AppConstant.CROP_PADDING) - (int) event.getRawX();
                            crop_bottomMargin = (dmw - AppConstant.CROP_PADDING) * 9 / 16 - crop_height;

                            photo_crop_frame_layout.setLayoutParams(UI.getCropLayoutPararmWH16_9(crop_width, crop_height, crop_leftMargin, crop_topMargin, crop_rightMargin, crop_bottomMargin));
                            crop_pull_frame_layout.setLayoutParams(UI.getCropLayoutPararmWH16_9(crop_width, crop_height, crop_leftMargin, crop_topMargin, crop_rightMargin, crop_bottomMargin));

                            mask_top.setLayoutParams(UI.getRelativeLayoutPararmH_WFillTrue(crop_topMargin));
                            mask_bottom.setLayoutParams(UI.getRelativeLayoutPararmH_WFillTrue(crop_bottomMargin));
                            mask_left.setLayoutParams(UI.getRelativeLayoutPararmW_HFillTrue(crop_leftMargin));
                            mask_right.setLayoutParams(UI.getRelativeLayoutPararmW_HFillTrue(crop_rightMargin));
                        }

                        break;
                    case MotionEvent.ACTION_UP:// 手指离开屏
                        crop_line_layout.setVisibility(View.GONE);

                        int crop_org_w = dmw - 2 * AppConstant.CROP_PADDING;

                        float scale = (float) crop_org_w / (float) photo_crop_frame_layout.getWidth();

                        // 参数为放缩时，按住的边角对角线保持固定的定点坐标
                        iv_crop_photo.setZoomScale(scale, 0f + (float) AppConstant.CROP_PADDING, 0f + (float) AppConstant.CROP_PADDING);

                        cropPullToFitFullCropArea();
                        iv_crop_photo.invalidate();
                        break;
                }

                return true;
            }
        });
    }


    private void exitActivity() {
        finish();
        overridePendingTransition(R.anim.in_bottom_up, R.anim.out_up_bottom);
    }

}
