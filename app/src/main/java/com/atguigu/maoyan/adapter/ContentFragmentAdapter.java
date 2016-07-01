package com.atguigu.maoyan.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.atguigu.maoyan.base.BasePager;

import java.util.List;

/**
 * Created by hp on 2016/6/25.
 */
public class ContentFragmentAdapter extends PagerAdapter {
    private final List<BasePager> basePagers;

    public ContentFragmentAdapter(List<BasePager> basePagers) {
        this.basePagers = basePagers;
    }

    @Override
    public int getCount() {
        return basePagers.size();
    }
    //相当于ListView适配器中的getView
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager basePager = basePagers.get(position);
        View view = basePager.rootView;
//        basePager.initData();//当页面被选中时再加载数据
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
