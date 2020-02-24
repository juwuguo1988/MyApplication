package com.example.hello.myapplication.utils.request.inteface;

import com.example.hello.myapplication.common.bean.SucMedicPlanBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by hello on 18/5/24.
 */

public interface HttpRequestService {

    @GET("patient/plan")
    Observable<SucMedicPlanBean> getAllMedicPlan();
}
