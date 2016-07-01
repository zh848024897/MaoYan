package com.atguigu.maoyan.base;

import android.content.Context;
import android.view.View;

/**
 * Created by hp on 2016/6/28.
 */
public abstract class OverseasBasePager {
    /**
     * 上下文
     */
    public Context context;

    /**
     * 视图，有各个子页面实例化的结果
     */
    public View rootView;

//    public boolean isInitData = false;

    public OverseasBasePager(Context context) {
        this.context = context;
        rootView = initView();//调用的时候孩子的initView();
//        isInitData = false;

    }

    public abstract View initView();


    public void initData() {

    }
}
