package com.atguigu.maoyan;

import android.app.Application;

import com.atguigu.maoyan.volley.VolleyManager;

import org.xutils.x;

/**
 * Created by hp on 2016/6/25.
 */
public class MaoYanApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VolleyManager.init(this);

        x.Ext.init(this);
        x.Ext.setDebug(true);

    }
}
