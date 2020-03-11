package com.example.hello.myapplication.counter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hello.myapplication.R;
import com.example.hello.myapplication.common.bean.UserGoodBean;
import com.example.hello.myapplication.counter.adapter.GoodDisplayAdapter;

import java.util.ArrayList;

/**
 * Created by juwuguo on 2020-03-11.
 */
public class InputCounterActivity extends AppCompatActivity {
    private EditText et_good_name, et_good_dose, et_good_unit;
    private Button btn_add, btn_create_counter;
    private ListView lv_good_display;
    private ArrayList<UserGoodBean> userGoodBeans = new ArrayList<>();
    private GoodDisplayAdapter mGoodDisplayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_counter_layout);
        findViewById();
        setListener();
    }

    private void findViewById() {
        et_good_name = findViewById(R.id.et_good_name);
        et_good_dose = findViewById(R.id.et_good_dose);
        et_good_unit = findViewById(R.id.et_good_unit);
        btn_add = findViewById(R.id.btn_add);
        btn_create_counter = findViewById(R.id.btn_create_counter);
        lv_good_display = findViewById(R.id.lv_good_display);
        mGoodDisplayAdapter = new GoodDisplayAdapter(this, userGoodBeans, true);
        lv_good_display.setAdapter(mGoodDisplayAdapter);
    }


    private void setListener() {
        btn_add.setOnClickListener(v -> {
            String name = et_good_name.getText().toString().trim();
            String dosage = et_good_dose.getText().toString().trim();
            String unit = et_good_unit.getText().toString().trim();
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(dosage) && !TextUtils.isEmpty(unit)) {
                UserGoodBean userGoodBean = new UserGoodBean();
                userGoodBean.setGoodName(name);
                userGoodBean.setGoodDosage(dosage);
                userGoodBean.setGoodUnit(unit);
                userGoodBeans.add(userGoodBean);
                mGoodDisplayAdapter.notifyDataSetChanged();
                et_good_name.setText("");
                et_good_dose.setText("");
                et_good_unit.setText("");
            } else {
                Toast.makeText(this, "请正确填写!!!", Toast.LENGTH_LONG).show();
            }

        });

        btn_create_counter.setOnClickListener(v -> {
            Intent intent = new Intent(this, DisplayCounterActivity.class);
            intent.putParcelableArrayListExtra("dataList", userGoodBeans);
            startActivity(intent);
        });

    }


}
