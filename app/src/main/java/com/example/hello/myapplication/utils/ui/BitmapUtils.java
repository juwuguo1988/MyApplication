package com.example.hello.myapplication.utils.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.hello.myapplication.common.bean.BitmapModel;

public class BitmapUtils {
	private Context mContext;
	public static final int CROP_BIG_PICTURE = 1003;
	public static final int TAKE_BIG_PICTURE = 1002;
	/**
	 * 设置封面
	 */
	public static final int SET_FRONT_PAGE = 1004;
	private static final String IMAGE_UNSPECIFIED = "image/*";

	public BitmapUtils(Context context) {
		this.mContext = context;
	}

	/**
	 * uri转换成bitmap
	 * 
	 * @param uri
	 * @return
	 */
	public Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	/**
	 * bitmap转换成流
	 * 
	 * @param bm
	 * @return
	 */
	public static InputStream Bitmap2IS(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.JPEG, 100, baos);
		InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
		return sbs;
	}

	/**
	 * 调用系统相册并截图intent参数
	 * 
	 * @param intent
	 * @param imageUri
	 * @return
	 */
	public static Intent setMediaIntent(Intent intent, Uri imageUri) {
		intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType(IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", false);
		return intent;
	}

	public static Intent setMediaIntent() {
		Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
		getImage.addCategory(Intent.CATEGORY_OPENABLE);
		getImage.setType("image/*");
		return getImage;
	}

	public static Intent setMediaIntent(Intent intent, Uri imageUri, String babyId) {
		intent.setType(IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 100);
		intent.putExtra("outputY", 300);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", false);
		return intent;
	}

	/**
	 * 调用系统相机
	 * 
	 * @param intent
	 * @param imageUri
	 * @return
	 */
	public static Intent setCameraIntent(Intent intent, Uri imageUri) {
		intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		return intent;
	}

	public static BitmapModel readBitmapModel(String srcPath, int photoAmiSize) {

		BitmapModel model = new BitmapModel();
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bitmap为空
		newOpts.inJustDecodeBounds = false;

		// final int outW = newOpts.outWidth;
		// final int outH = newOpts.outHeight;

		int degree = getPhotoDegree(srcPath);

		// 处理后大图最高像素数接近1136x852，小图最小尺寸接近316x212，取316x316
		int photoAimSize = photoAmiSize;// 1704;//最终图片的目标尺寸，为1136的1.5倍
		int photoFactSize = newOpts.outWidth;// 实际得到的图片的尺寸，初始值取高和宽中最大的
		if (newOpts.outWidth < newOpts.outHeight) {
			photoFactSize = newOpts.outHeight;
		}
		int sampleSize = 1;// be=1表示不缩放
		while (true) {
			if (photoFactSize / sampleSize > photoAimSize)
				sampleSize = sampleSize * 2;
			else
				break;
		}
		newOpts.inSampleSize = sampleSize;// 设置缩放比例
		newOpts.inPreferredConfig = Config.ARGB_8888;
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 当系统内存不够时候图片自动被回收
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		try {
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		} catch (OutOfMemoryError e) {
			try {
				System.gc();
				newOpts.inSampleSize = sampleSize * 2;
				bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
			} catch (OutOfMemoryError e1) {
				System.gc();
				newOpts.inSampleSize = sampleSize * 2;
				// OutOfMemoryError
				bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
			}
		}

		if (degree != 0) {
			// 旋转图片
			Matrix m = new Matrix();
			m.postRotate(degree);
			try {
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
			} catch (OutOfMemoryError e) {
				try {
					System.gc();
					m.preScale(0.9F, 0.9F);
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
				} catch (OutOfMemoryError e1) {
					System.gc();
					m.preScale(0.8F, 0.8F);
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
				}
			}
		}

		model.bitmap = bitmap;

		if (degree == 90 || degree == 270) {
			model.mediaWidth = String.valueOf(newOpts.outHeight);
			model.mediaHeight = String.valueOf(newOpts.outWidth);
		} else {
			model.mediaWidth = String.valueOf(newOpts.outWidth);
			model.mediaHeight = String.valueOf(newOpts.outHeight);
		}

		return model;
	}

	// 获取图片的宽高
	public static BitmapModel readBitmapModelWH(String srcPath) {
		BitmapModel model = new BitmapModel();
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(srcPath, options);

		model.mediaWidth = String.valueOf(options.outWidth);
		model.mediaHeight = String.valueOf(options.outHeight);

		return model;
	}

	public static int getPhotoDegree(String srcPath) {
		// 因为有些图片默认是旋转的，所以这里进行校正
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(srcPath);
		} catch (IOException e) {
			e.printStackTrace();
			exif = null;
		}
		if (exif != null) {
			// 读取图片中相机方向信息
			int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

			// 计算旋转角度
			switch (ori) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			default:
				degree = 0;
				break;
			}
		}

		return degree;
	}

	public static void processCompressPhoto(Bitmap mBitmap, String newPath, String extType) throws Exception {
		int bmpQuality = 100;

		CompressFormat mCompressFormat = CompressFormat.JPEG;

		if (extType.equals("png")) {
			mCompressFormat = CompressFormat.PNG;
		} else if (extType.equals("jpg")) {
			mCompressFormat = CompressFormat.JPEG;
		}

		try {
			File file = new File(newPath);
			FileOutputStream out = new FileOutputStream(file);

			if (mBitmap.compress(mCompressFormat, bmpQuality, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (OutOfMemoryError e) {

		}

		mBitmap.recycle();
		mBitmap = null;
	}

	public static void processSaveResizePhoto(String srcPath, String newPath, int resizeParam, String extType) throws Exception {
		BitmapModel bitmapMode = readBitmapModel(srcPath, resizeParam);

		processCompressPhoto(bitmapMode.bitmap, newPath, extType);
	}

	// 从文件调入bitmap
	public static Bitmap loadBitmapFile(String srcPath) {
		try {
			if (new File(srcPath).exists() == false)
				return null;

			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			newOpts.inPreferredConfig = Config.ARGB_8888;// null;//bitmap.getConfig();// 该模式是默认的,可不设
			newOpts.inPurgeable = true;// 同时设置才会有效
			newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收
			newOpts.inJustDecodeBounds = false;
			return BitmapFactory.decodeFile(srcPath, newOpts);
		} catch (OutOfMemoryError e) {

		}
		return null;
	}

	// 从文件调入bitmap
	public static Bitmap loadBitmapFileRGB565(String srcPath) {
		try {
			if (new File(srcPath).exists() == false)
				return null;

			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			newOpts.inPreferredConfig = Config.RGB_565;// null;//bitmap.getConfig();// 该模式是默认的,可不设
			newOpts.inPurgeable = true;// 同时设置才会有效
			newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收
			newOpts.inJustDecodeBounds = false;
			return BitmapFactory.decodeFile(srcPath, newOpts);
		} catch (OutOfMemoryError e) {

		} catch (Exception e) {

		}
		return null;
	}

	@SuppressLint("NewApi")
	public static Bitmap compressImage(Bitmap image, String newPath, double maxSize) {
		Bitmap bitmap = null;
		File file = new File(newPath);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			bitmap = imageZoom(image, maxSize);
			if (bitmap.compress(CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (OutOfMemoryError e) {

		}
		return bitmap;
	}

	@SuppressLint("NewApi")
	public static Bitmap compressPNGImage(Bitmap image, String newPath, double maxSize) {
		Bitmap bitmap = image;
		File file = new File(newPath);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			// bitmap = imageZoom(image, maxSize);
			if (bitmap.compress(CompressFormat.PNG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (OutOfMemoryError e) {

		}
		return bitmap;
	}

	private static Bitmap imagePNGZoom(Bitmap bitMap, double maxSize) {
		Bitmap bitmap2 = null;
		// 图片允许最大空间 单位：KB
		// 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitMap.compress(CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		try {
			baos.close();
			baos = null;
		} catch (IOException e) {

			e.printStackTrace();
		}
		// 将字节换成KB
		double mid = b.length / 1024;
		// 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			// 获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			// 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			if (maxSize == 20 && mid < 40) {// 如果是20k小图的时候判断 如果原图小于40K则不压缩
				bitmap2 = bitMap;
			} else {
				bitmap2 = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i), bitMap.getHeight() / Math.sqrt(i));
			}
		} else {
			bitmap2 = bitMap;
		}
		return bitmap2;
	}

	private static Bitmap imageZoom(Bitmap bitMap, double maxSize) {
		Bitmap bitmap2 = null;
		// 图片允许最大空间 单位：KB
		// 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitMap.compress(CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		try {
			baos.close();
			baos = null;
		} catch (IOException e) {

			e.printStackTrace();
		}
		// 将字节换成KB
		double mid = b.length / 1024;
		// 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			// 获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			// 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			if (maxSize == 20 && mid < 40) {// 如果是20k小图的时候判断 如果原图小于40K则不压缩
				bitmap2 = bitMap;
			} else {
				bitmap2 = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i), bitMap.getHeight() / Math.sqrt(i));
			}
		} else {
			bitmap2 = bitMap;
		}
		return bitmap2;
	}

	/***
	 * 图片的缩放方法
	 * 
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
		// wrightBitmapToFile(bitmap, AppConstant.BBJ_FILE_PATH_SDCARD+System.currentTimeMillis(), 500);
		// if(bgimage != null && !bgimage.isRecycled()){
		// bgimage.recycle();
		// }
		return bitmap;
	}

	public static Bitmap myCompress(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 80, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		try {
			baos.close();
			baos = null;
		} catch (IOException e2) {

			e2.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		try {
			isBm.close();
			isBm = null;
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		if (image != null && !image.isRecycled()) {
			image.recycle();
			image = null;
		}
		return bitmap;

	}

	public static int[] getWidthAndHeightByBitmap(String path) {
		int info[] = new int[2];
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);// 此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		info[0] = w;
		info[1] = h;
		return info;
	}

	public static float[] getWidthAndHeightFloatByBitmap(String path) {
		float info[] = new float[2];
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);// 此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		info[0] = w;
		info[1] = h;
		return info;
	}

	// 只被ChangeAlbumAct用到，应该是没有用了
	public static void writeBitmapToFile(String srcPath, String newPath, double maxSize) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		// 处理后大图最高像素数接近1136，小图最小尺寸为212
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		newOpts.inPreferredConfig = Config.RGB_565;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		// 因为有些图片默认是旋转的，所以这里进行校正后再复制
		int digree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(srcPath);
		} catch (IOException e) {
			e.printStackTrace();
			exif = null;
		}
		if (exif != null) {
			// 读取图片中相机方向信息
			int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
			// 计算旋转角度
			switch (ori) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				digree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				digree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				digree = 270;
				break;
			default:
				digree = 0;
				break;
			}
		}
		if (digree != 0) {
			// 旋转图片
			Matrix m = new Matrix();
			m.postRotate(digree);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
		}
		compressImage(bitmap, newPath, maxSize);// 压缩好比例大小后再进行质量压缩
	}

	public static void wrightBitmapToFile(Bitmap bitmap, String newPath, double maxSize) {
		compressImage(bitmap, newPath, maxSize);// 压缩好比例大小后再进行质量压缩
	}

	public static Bitmap processOrginalPhoto(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bitmap为空
		newOpts.inJustDecodeBounds = false;

		int degree = getPhotoDegree(srcPath);

		int tempW = newOpts.outWidth;
		int tempH = newOpts.outHeight;

		if (degree == 90 || degree == 270) {
			newOpts.outWidth = tempH;
			newOpts.outHeight = tempW;
		} else {
			newOpts.outWidth = tempW;
			newOpts.outHeight = tempH;
		}

		// 处理后大图最高像素数接近1136x852，小图最小尺寸接近316x212，取316x316
		int photoAimSize = 800;// 1704;//最终图片的目标尺寸，为1136的1.5倍
		int photoFactSize = newOpts.outWidth;// 实际得到的图片的尺寸，初始值取高和宽中最大的
		if (newOpts.outWidth < newOpts.outHeight) {
			photoFactSize = newOpts.outHeight;
		}

		int sampleSize = 1;// be=1表示不缩放
		while (true) {
			if (photoFactSize / sampleSize > photoAimSize)
				sampleSize = sampleSize * 2;
			else
				break;
		}

		newOpts.inSampleSize = sampleSize;// 设置缩放比例
		newOpts.inPreferredConfig = Config.ARGB_8888;// Config.RGB_565;//null;//bitmap.getConfig();// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		if (degree != 0) {
			// 旋转图片
			Matrix m = new Matrix();
			m.postRotate(degree);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
		}

		if (bitmap == null) {
			return null;
		}
		return myCompress(bitmap);
	}

	public static Bitmap decodeSampledBitmapFrombyte(byte[] bit, int reqWidth, int reqHeight) {
		// First decode with inJustDecodeBounds=true to check dimensions
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(bit, 0, bit.length, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(bit, 0, bit.length, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	public static void rotateImage(final int degree, String imagePath) {
		// Rotaciona a imagem em 90 graus
		Bitmap photo = BitmapFactory.decodeFile(imagePath);
		// wrightBitmapToFile(photo, imagePath+"p", 200);
		Matrix matrix = new Matrix();
		matrix.setRotate(degree, (float) photo.getWidth() / 2, (float) photo.getHeight() / 2);
		Bitmap bitmap;
		try {
			bitmap = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
		} catch (OutOfMemoryError error) {
			try {
				matrix.preScale(0.9F, 0.9F);
				bitmap = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
			} catch (OutOfMemoryError e1) {
				matrix.preScale(0.8F, 0.8F);
				bitmap = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
			}
		}
		wrightBitmapToFile(bitmap, imagePath, 200);
		bitmap.recycle();
		// return Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
	}

	//
	//
	//
	public static Bitmap resizeImage(Bitmap bitmap, float inSampleSize) {
		if (bitmap == null || bitmap.isRecycled()) {
			return null;
		}
		// load the origial Bitmap
		// Bitmap bitmapOrg = bitmap;
		Bitmap resizedBitmap = null;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		try {
			// resize the Bitmap
			matrix.postScale(inSampleSize, inSampleSize);
			resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		} catch (OutOfMemoryError e) {

			try {
				inSampleSize -= 0.1;
				matrix.postScale(inSampleSize, inSampleSize);
				resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
			} catch (OutOfMemoryError e1) {

				try {
					inSampleSize -= 0.1;
					matrix.postScale(inSampleSize, inSampleSize);
					resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
				} catch (OutOfMemoryError e2) {

					try {
						inSampleSize -= 0.1;
						matrix.postScale(inSampleSize, inSampleSize);
						resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
					} catch (OutOfMemoryError e3) {

						return null;
					}
				}
			}
		}
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
		return resizedBitmap;
	}

	/**
	 * 图片按比例大小压缩方法(根据Bitmap图片压缩)
	 */
	public static Bitmap compress(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 质量压缩方法
	 */
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	public static Bitmap getimage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	public static Bitmap getViewBitmap(View v) {
		v.clearFocus();
		v.setPressed(false);

		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);

		// Reset the drawing cache background color to fully transparent
		// for the duration of this operation
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);

		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap == null) {
			return null;
		}

		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

		// Restore the view
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);

		return bitmap;
	}
}