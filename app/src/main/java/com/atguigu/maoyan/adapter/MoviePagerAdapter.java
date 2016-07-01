package com.atguigu.maoyan.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.atguigu.maoyan.base.MenuDetailBasePager;

import java.util.List;

/**
 * Created by hp on 2016/6/27.
 * 电影界面适配器
 */
public class MoviePagerAdapter extends PagerAdapter {
    private final List<MenuDetailBasePager> basePagers;

    public MoviePagerAdapter(List<MenuDetailBasePager> basePagers) {
        this.basePagers = basePagers;
    }

    @Override
    public int getCount() {
        return basePagers.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MenuDetailBasePager menuDetailBasePager = basePagers.get(position);
        View view = menuDetailBasePager.rootView;
        //在此加载数据，会默认加载两个页面，如果不在此加载数据，在页面选中时加载数据
        //则ViewPager切换时，每次页面都会到头部，而不是之前滑动到的位置
        menuDetailBasePager.initData();
        //加入容器，不能忘
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
