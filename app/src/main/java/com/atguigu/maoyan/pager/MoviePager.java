package com.atguigu.maoyan.pager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.adapter.MoviePagerAdapter;
import com.atguigu.maoyan.base.BasePager;
import com.atguigu.maoyan.base.MenuDetailBasePager;
import com.atguigu.maoyan.interfaces.MoviePagerChangeListener;
import com.atguigu.maoyan.menudetailpager.MovieOverseasPager;
import com.atguigu.maoyan.menudetailpager.MoviePlayingPager;
import com.atguigu.maoyan.menudetailpager.MovieWatingPager;
import com.atguigu.maoyan.utils.LogUtil;
import com.atguigu.maoyan.view.TitleTabBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/6/25.
 * 电影页面
 */
public class MoviePager extends BasePager implements TitleTabBar.TitleTabClickListener {
    private ViewPager viewpager;
    private MoviePagerAdapter adapter;
    private List<MenuDetailBasePager> basePagers;
    private TitleTabBar titleTabBar;

    public MoviePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("电影初始化了");


        View view = View.inflate(context, R.layout.movie_pager,null);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        titleTabBar = (TitleTabBar) view.findViewById(R.id.movie_topBar);

        fl_content.removeAllViews();
        fl_content.addView(view);
        //准备数据
        basePagers = new ArrayList<>();
        basePagers.add(new MoviePlayingPager(context));
        basePagers.add(new MovieWatingPager(context));
        basePagers.add(new MovieOverseasPager(context));
        //设置适配器
        adapter = new MoviePagerAdapter(basePagers);
        viewpager.setAdapter(adapter);

        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(0);

        titleTabBar.setTitleTabClickListener(this);
        viewpager.addOnPageChangeListener(new MoviePagerChangeListener(viewpager,titleTabBar){
            @Override
            public void focusedFragment(int selectPosition, int lastPosition) {
                super.focusedFragment(selectPosition, lastPosition);
                titleTabBar.setTitleState(selectPosition);
            }
        });

        //默认加载热映界面
//        basePagers.get(0).initData();



    }

    @Override
    public void callback(int index) {
        viewpager.setCurrentItem(index);
    }



}
