package com.softisland.searchdemo.app;

import android.app.Application;
import android.content.Context;

import com.softisland.searchdemo.entity.MyObjectBox;

import io.objectbox.BoxStore;

public class MyApplication extends Application {

    private static Context context;
    private static BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        context= this;
        boxStore= MyObjectBox.builder().androidContext(this).build();
    }

    public static Context getContext(){
        return context;
    }
    public static BoxStore getBoxStore(){
        return boxStore;
    }
}
