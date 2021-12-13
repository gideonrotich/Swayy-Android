package com.example.swayy;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

/**
 * This project file is owned by DevMwarabu, johnmwarabuchone@gmail.com.
 * Created on 12/13/21. Copyright (c) 2021 DevMwarabu
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }
}
