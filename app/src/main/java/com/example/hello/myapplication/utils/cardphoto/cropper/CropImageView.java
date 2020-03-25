package com.example.hello.myapplication.utils.cardphoto.cropper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.hello.myapplication.R;
import com.example.hello.myapplication.utils.cardphoto.listener.CropViewFinishListener;


public class CropImageView extends FrameLayout {

    private ImageView mImageView;
    private CropOverlayView mCropOverlayView;

    public CropImageView(@NonNull Context context) {
        super(context);
    }

    public CropImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.crop_image_view, this, true);
        mImageView = view.findViewById(R.id.img_crop);
        mCropOverlayView = view.findViewById(R.id.overlay_crop);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setImageBitmap(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
        mCropOverlayView.setBitmap(bitmap);
    }

    public void crop(CropViewFinishListener listener, boolean needStretch) {
        if (listener == null)
            return;
        mCropOverlayView.crop(listener, needStretch);
    }

}
