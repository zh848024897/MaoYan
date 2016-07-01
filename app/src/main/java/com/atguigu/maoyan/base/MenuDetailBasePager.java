package com.atguigu.maoyan.base;

import android.content.Context;
import android.view.View;

/**
 * Created by hp on 2016/6/27.
 */
public abstract class MenuDetailBasePager{
    public Context context;
    //用来显示页面
    public View rootView;

    public MenuDetailBasePager(Context context) {
        this.context = context;
        this.rootView = initView();
    }

    public abstract View initView();

    public void initData(){

    }
}
