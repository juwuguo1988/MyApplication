package com.example.hello.myapplication.counter.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hello.myapplication.R;
import com.example.hello.myapplication.common.bean.UserGoodBean;
import com.example.hello.myapplication.counter.adapter.GoodDisplayAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juwuguo on 2020-03-11.
 */
public class DisplayCounterActivity extends AppCompatActivity {
    private EditText et_good_name, et_good_dose, et_good_unit;
    private Button btn_start_count;
    private ListView lv_good_display;
    private List<UserGoodBean> oldUserGoodBeans;
    private List<UserGoodBean> newUserGoodBeans = new ArrayList<>();
    private GoodDisplayAdapter mGoodDisplayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_counter_layout);
        findViewById();
        initData();
        initView();
        setListener();
    }

    private void findViewById() {
        et_good_name = findViewById(R.id.et_good_name);
        et_good_dose = findViewById(R.id.et_good_dose);
        et_good_unit = findViewById(R.id.et_good_unit);
        btn_start_count = findViewById(R.id.btn_start_count);
        lv_good_display = findViewById(R.id.lv_good_display);
    }

    private void initData() {
        oldUserGoodBeans = getIntent().getParcelableArrayListExtra("dataList");
        newUserGoodBeans.addAll(oldUserGoodBeans);
    }


    private void initView() {
        mGoodDisplayAdapter = new GoodDisplayAdapter(this, newUserGoodBeans, false);
        lv_good_display.setAdapter(mGoodDisplayAdapter);
    }

    private void setListener() {
        btn_start_count.setOnClickListener(v -> {
            String name = et_good_name.getText().toString().trim();
            String dosage = et_good_dose.getText().toString().trim();
            String unit = et_good_unit.getText().toString().trim();
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(dosage) && !TextUtils.isEmpty(unit)) {
                UserGoodBean resultBean = null;
                for (UserGoodBean userGoodBean : oldUserGoodBeans) {
                    if (TextUtils.equals(userGoodBean.getGoodName(), name)) {
                        resultBean = userGoodBean;
                        break;
                    }
                }
                if (resultBean != null) {
                    if (TextUtils.equals(dosage, "0") || TextUtils.equals(dosage, "0.") || Double.parseDouble(dosage) < 0) {
                        Toast.makeText(this, "请输入正确的剂量", Toast.LENGTH_LONG).show();
                    } else {
                        double result = new BigDecimal(dosage).divide(new BigDecimal(resultBean.getGoodDosage())).doubleValue();
                        for (UserGoodBean userGoodBean : newUserGoodBeans) {
                            double resultDosage = new BigDecimal(result).multiply(new BigDecimal(userGoodBean.getGoodDosage())).doubleValue();
                            userGoodBean.setGoodDosage(String.valueOf(resultDosage));
                        }
                        mGoodDisplayAdapter.notifyDataSetChanged();
                    }

                } else {
                    Toast.makeText(this, "未找到此物品!!!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "请正确填写!!!", Toast.LENGTH_LONG).show();
            }

        });

    }


}
