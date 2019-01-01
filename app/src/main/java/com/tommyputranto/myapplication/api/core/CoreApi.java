package com.tommyputranto.myapplication.api.core;

import com.tommyputranto.myapplication.api.ApiService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tommy Dwi Putranto on 11/04/18.
 */


public class CoreApi {
    //MARK: BASE URL FROM GOOGLE SCRIPT
    public static final String BASE_URL = "https://script.google.com/macros/s/AKfycbwWc7iXfv2M2ff_Cnarh7bY6hUkEpbHUNUMYBNMEVXd7zlZWzZb/";


    ApiService apiService;
    public void CoreApi() {
        Retrofit retrofit;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ApiInterceptor())
                .readTimeout(300, TimeUnit.SECONDS)
                .connectTimeout(300, TimeUnit.SECONDS)
                .addInterceptor(new MyInterceptor())
                .addInterceptor(logging)
                .build();
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BASE_URL)
                .build();


        apiService = retrofit.create(ApiService.class);

    }


    private class MyInterceptor implements Interceptor {

        public MyInterceptor() {

        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            System.out.println("CALL API: " + original.url());
            Request.Builder reqBuilder = original.newBuilder();
            Request request;
            request = reqBuilder.build();
            Response response = chain.proceed(request);
            String rawJson = response.body().string();
            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), rawJson)).build();
        }
    }

    public ApiService getApiService() {
        return apiService;
    }
}