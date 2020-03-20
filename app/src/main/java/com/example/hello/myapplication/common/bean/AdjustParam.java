package com.example.hello.myapplication.common.bean;

import java.io.Serializable;

//素材是否被用户手动调整（如果被调整，服务器按照客户端传的坐标旋转来做，如果没有被调整，服务器自动人脸识别）

public class AdjustParam implements Serializable{

	// 0代表剪裁画面的gridview需要刷新，1代表剪裁画面的gridview不需要刷新 (此变量不需要上传至服务器，仅剪裁画面使用)
	public String needGridViewRefresh = "0";
		
	// 素材是否被用户手动调整（如果被调整，服务器按照客户端传的坐标旋转来做，如果没有被调整，服务器自动人脸识别）
	// 0代表未调整，1代表调整
	public String isAdjusted = "0";

	// 旋转角度是度数，逆时针为正，一周为2*PI，小数点后保留8位 默认为0
	public String rotateScale = "0";

	// 图片最终的尺寸相对图片原始尺寸的缩放系数。小数点后保留8位 默认为0
	public String zoomScale = "0";

	// 用来记录图片的中心相对于裁剪框的中心的偏移量（裁剪框按照1920*1080计算）

	// 图片的中心点相对于裁剪框的横向中心偏移，右为x正 默认为0
	public String centerOffsetX = "0";

	// 图片的中心点相对于裁剪框的纵向中心偏移，上为y正 默认为0
	public String centerOffsetY = "0";

	
	@Override
	public String toString() {
		
		return "{AdjustParam {isAdjusted:" + isAdjusted + ",rotateScale:"
				+ rotateScale + ",zoomScale:" + zoomScale + ",centerOffsetX:"
				+ centerOffsetX + ",centerOffsetY:" + centerOffsetY+"}";
	}
}
