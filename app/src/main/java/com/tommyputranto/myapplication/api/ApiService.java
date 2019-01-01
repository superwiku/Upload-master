package com.tommyputranto.myapplication.api;

import com.tommyputranto.myapplication.api.dao.SuccessDao;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Tommy Dwi Putranto on 11/04/18.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("exec")
    Observable<SuccessDao> postUpload(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("email") String email,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("foto") String foto,
            @Field("sheet") String sheet);
}
