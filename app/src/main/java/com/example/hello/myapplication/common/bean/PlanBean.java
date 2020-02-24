package com.example.hello.myapplication.common.bean;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Juwuguo on 2017/11/14.
 */


public class PlanBean implements Serializable, Comparable<PlanBean> {

    private static final long serialVersionUID = 1L;
    private Long mid = null;
    @SerializedName(value = "id")
    private String planId;// 服药计划id;
    private int takeAt;// 提醒时间
    private int cycleDays;// 服药计划频率（单位：天数, 默认为0-每天服药，1-隔一天一服，-1 - 只吃一次的）
    private String medicineId;// 药品id
    private int count;// 单次服药数量
    private int positionNo;// 药仓号
    private int dosage;// 单次服药剂量
    private int zone;// 早中晚区分 0:早晨 1:中午 2:晚上
    private String medicineName;// 药品名
    private Long started;// 服药计划开始时间
    private String ended;// 服药计划开始时间
    private Long middleTime;// takeAt+昨天的date或者今天的date拼接而来，用于判断是否生成一条服药历史
    private boolean isSelected;// 已选，未选
    private String dosageUnit;//药品单位
    private int medicineVia;//药品信息来源（1 是扫码 2是手工输入 默认2）
    private String medicineHash;//药品信息的哈希值
    private Long remindFirstAt;//首次提醒时间
    private String dosageFormUnit;//剂型单位
    private String commodityName;//药品商品名称
    private String ingredient;//药品成分
    private String boxUuid;//药盒唯一信息
    private String planSeqWithBox;//服药计划在药盒中的id
    private String category;//药品类型


    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public int getTakeAt() {
        return takeAt;
    }

    public void setTakeAt(int takeAt) {
        this.takeAt = takeAt;
    }

    public int getCycleDays() {
        return cycleDays;
    }

    public void setCycleDays(int cycleDays) {
        this.cycleDays = cycleDays;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPositionNo() {
        return positionNo;
    }

    public void setPositionNo(int positionNo) {
        this.positionNo = positionNo;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public Long getStarted() {
        return started;
    }

    public void setStarted(Long started) {
        this.started = started;
    }

    public String getEnded() {
        return ended;
    }

    public void setEnded(String ended) {
        this.ended = ended;
    }

    public Long getMiddleTime() {
        return middleTime;
    }

    public void setMiddleTime(Long middleTime) {
        this.middleTime = middleTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getDosageUnit() {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit) {
        this.dosageUnit = dosageUnit;
    }

    public int getMedicineVia() {
        return medicineVia;
    }

    public void setMedicineVia(int medicineVia) {
        this.medicineVia = medicineVia;
    }

    public String getMedicineHash() {
        return medicineHash;
    }

    public void setMedicineHash(String medicineHash) {
        this.medicineHash = medicineHash;
    }

    public Long getRemindFirstAt() {
        return remindFirstAt;
    }

    public void setRemindFirstAt(Long remindFirstAt) {
        this.remindFirstAt = remindFirstAt;
    }

    public String getDosageFormUnit() {
        return dosageFormUnit;
    }

    public void setDosageFormUnit(String dosageFormUnit) {
        this.dosageFormUnit = dosageFormUnit;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }


    @Override
    public int compareTo(PlanBean another) {
        int reData = 0;
        try {
            if (this.takeAt < another.takeAt) {
                return -1;
            } else if (this.takeAt > another.takeAt) {
                return 1;
            } else if (this.takeAt == another.takeAt) {
                if (this.positionNo < another.positionNo) {
                    reData = -1;
                } else {
                    reData = 1;
                }
            }
            return reData;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getBoxUuid() {
        return this.boxUuid;
    }

    public void setBoxUuid(String boxUuid) {
        this.boxUuid = boxUuid;
    }

    public String getPlanSeqWithBox() {
        return this.planSeqWithBox;
    }

    public void setPlanSeqWithBox(String planSeqWithBox) {
        this.planSeqWithBox = planSeqWithBox;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
