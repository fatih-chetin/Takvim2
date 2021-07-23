package com.example.takvim;

import android.app.Application;

import com.backendless.Backendless;

public class ApplicationClass extends Application
{
    public static final String APPLICATION_ID = "CE3AE7DC-0A03-AC3A-FF4F-502E07D66600";
    public static final String API_KEY = "1FBF4DBD-FD4F-4318-A021-AC5F33D309A5";
    public static final String SERVER_URL = "https://api.backendless.com";
    @Override
    public void onCreate() {
        super.onCreate();
//        Backendless.setUrl(SERVER_URL);
        Backendless.initApp( this,
                APPLICATION_ID,
                API_KEY );

    }
}
