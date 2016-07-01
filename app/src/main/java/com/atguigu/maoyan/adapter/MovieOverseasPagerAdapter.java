package com.atguigu.maoyan.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.atguigu.maoyan.base.OverseasBasePager;

import java.util.List;

/**
 * Created by hp on 2016/6/28.
 */
public class MovieOverseasPagerAdapter extends PagerAdapter {
    private final List<String> tabDatas;
    private final List<OverseasBasePager> basePagers;

    public MovieOverseasPagerAdapter(List<String> tabDatas, List<OverseasBasePager> basePagers) {
        this.tabDatas = tabDatas;
        this.basePagers = basePagers;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return tabDatas.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        OverseasBasePager basePager = basePagers.get(position);
        View rootView = basePager.rootView;
        basePager.initData();
        container.addView(rootView);
        return rootView;
    }

    @Override
    public int getCount() {
        return basePagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
