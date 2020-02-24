package com.example.hello.myapplication.common.bean;

import java.util.ArrayList;

public class MedicPlanBean {

    public long timestamp;
    public int searchBy;
    public String result;
    public int medicineId;// 更改服药计划时使用
    public ArrayList<PlanBean> plans = new ArrayList<PlanBean>();
}
