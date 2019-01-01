package com.tommyputranto.myapplication.dagger;

import android.app.Application;

/**
 * Created by Tommy Dwi Putranto on 11/04/18.
 */

public class Injector {
    public static DiComponent component;

    public static DiComponent initialize(Application application) {
        component = DaggerDiComponent.builder()
                .appModule(new AppModule(application))
                .netModule(new NetModule())
                .build();
        return component;
    }
}


