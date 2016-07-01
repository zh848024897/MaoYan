package com.atguigu.maoyan.pager;

import android.content.Context;
import android.view.View;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.base.BasePager;
import com.atguigu.maoyan.utils.LogUtil;

/**
 * Created by hp on 2016/6/25.
 * 设置界面
 */
public class SettingPager extends BasePager {
    public SettingPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("设置初始化了");

        View view = View.inflate(context, R.layout.setting_pager,null);

        fl_content.removeAllViews();
        fl_content.addView(view);
    }
}
