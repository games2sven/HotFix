package com.highgreat.sven.hotfix;

import android.app.Application;
import android.content.Context;

import com.highgreat.sven.hotfix.utils.FixDexUtils;

import androidx.multidex.MultiDex;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        FixDexUtils.loadFixedDex(this);
    }
}
