package com.tommyputranto.myapplication.dagger;

import com.tommyputranto.myapplication.api.core.CoreApi;
import com.tommyputranto.myapplication.main.MainRequest;
import com.tommyputranto.myapplication.main.MainRequestImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tommy Dwi Putranto on 11/04/18.
 */

@Module
public class NetModule {
    @Singleton
    @Provides
    CoreApi provideApiClient() {
        return new CoreApi();
    }

    @Singleton
    @Provides
    MainRequest provideMainViewRequest(CoreApi api){
        return new MainRequestImpl(api);
    }
}