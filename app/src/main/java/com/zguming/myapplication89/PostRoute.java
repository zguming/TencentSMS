package com.zguming.myapplication89;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/3/22 0022.
 */
//?sdkappid=1400045389&random=7226249334
public interface PostRoute {
    //@Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("sendsms")
    //@POST("send")
    Call<Tl> postFlyRoute(@Query("sdkappid") String sdkappid,@Query("random") String random,@Body RequestBody route);//传入的参数为RequestBody
    //Call<Gh> postFlyRoute(@Body RequestBody route);//传入的参数为RequestBody
}
