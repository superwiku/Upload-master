package com.tommyputranto.myapplication.dagger;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tommy Dwi Putranto on 11/04/18.
 */


@Module
public class AppModule {

    Application mApplication;


    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return mApplication;
    }

    @Singleton
    @Provides
    Gson provideGson() {
        return new Gson();
    }

}

