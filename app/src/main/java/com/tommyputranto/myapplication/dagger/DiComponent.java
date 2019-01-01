package com.tommyputranto.myapplication.dagger;

import com.tommyputranto.myapplication.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Tommy Dwi Putranto on 11/04/18.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        NetModule.class
})
public interface DiComponent {
    void inject(MainActivity mainActivity);
}