package com.atguigu.maoyan.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.adapter.ContentFragmentAdapter;
import com.atguigu.maoyan.base.BaseFragment;
import com.atguigu.maoyan.base.BasePager;
import com.atguigu.maoyan.pager.CinamePager;
import com.atguigu.maoyan.pager.FindPager;
import com.atguigu.maoyan.pager.MoviePager;
import com.atguigu.maoyan.pager.SettingPager;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends BaseFragment {
    private ViewPager viewpager;
    private RadioGroup rg_main;
    //四个页面的集合
    private List<BasePager> basePagers;


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.content_fragment,null);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        rg_main = (RadioGroup) view.findViewById(R.id.rg_main);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //默认第一个RadioButton选中
        rg_main.check(R.id.rb_movie);

        //准备数据
        basePagers = new ArrayList<>();
        basePagers.add(new MoviePager(context));
        basePagers.add(new CinamePager(context));
        basePagers.add(new FindPager(context));
        basePagers.add(new SettingPager(context));

        //设置适配器
        viewpager.setAdapter(new ContentFragmentAdapter(basePagers));

        //设置RadioGroup中RadioButton的点击监听
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        //监听页面的变化
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

        //默认加载第一个页面数据
        basePagers.get(0).initData();
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //当页面选中时，初始化数据
            basePagers.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //设置RadioGroup中RadioButton的点击监听
    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_movie :
                    //第一个参数：显示ViewPager的第几页
                    //第二个参数：是否有默认的滑动的动画
                    viewpager.setCurrentItem(0,false);
                    break;
                case R.id.rb_ciname:
                    viewpager.setCurrentItem(1,false);
                    break;
                case R.id.rb_find:
                    viewpager.setCurrentItem(2,false);
                    break;
                case R.id.rb_my:
                    viewpager.setCurrentItem(3,false);
                    break;
            }
        }
    }
}
