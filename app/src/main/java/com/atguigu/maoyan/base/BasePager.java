package com.atguigu.maoyan.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.atguigu.maoyan.R;

/**
 * Created by hp on 2016/6/25.
 */
public class BasePager {
    public Context context;
    public View rootView;
    public FrameLayout fl_content;
    public TextView tv_title;

    public BasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    public View initView() {
        View view = View.inflate(context, R.layout.base_pager,null);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        return view;
    }

    public void initData(){

    }
}
