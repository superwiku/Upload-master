package com.tommyputranto.myapplication;

import android.app.Application;

import com.google.gson.Gson;
import com.tommyputranto.myapplication.api.core.CoreApi;
import com.tommyputranto.myapplication.dagger.Injector;

/**
 * Created by Tommy Dwi Putranto on 11/04/18.
 */

public class CoreApp extends Application {
    public static CoreApi api = new CoreApi();
    public static Gson gson = new Gson();

    @Override
    public void onCreate() {
        super.onCreate();
        Injector.initialize(this);
    }
    public static CoreApi getApi(){
        return api;
    }

    public static Gson getGson(){
        return gson;
    }

    public static CoreApi getAPiCore(){
        api.CoreApi();
        return api;
    }

}
