package com.example.goingonce.Common;

import android.app.Application;

public class ApplicationContext extends Application {
    private static ApplicationContext mContext;
    private static String str;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
    }

    public static  ApplicationContext getContext(){
        return mContext;
    }

    public static String getDuration(){
        return str;
    }

    public static void setDuration(String duration){
        str=duration;
    }
}
