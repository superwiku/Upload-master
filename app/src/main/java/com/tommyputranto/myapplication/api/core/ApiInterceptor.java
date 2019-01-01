package com.tommyputranto.myapplication.api.core;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tommy Dwi Putranto on 11/04/18.
 */

public class ApiInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest= chain.request();
        Request requestWithuserAgent= originalRequest.newBuilder().build();
        return chain.proceed(requestWithuserAgent);
    }
}
