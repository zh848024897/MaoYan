package com.atguigu.maoyan.menudetailpager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.adapter.MovieOverseasPagerAdapter;
import com.atguigu.maoyan.base.MenuDetailBasePager;
import com.atguigu.maoyan.base.OverseasBasePager;
import com.atguigu.maoyan.pager.overseas.JapanPager;
import com.atguigu.maoyan.pager.overseas.KoreaPager;
import com.atguigu.maoyan.pager.overseas.UsaPager;
import com.atguigu.maoyan.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hp on 2016/6/27.
 */
public class MovieOverseasPager extends MenuDetailBasePager {
//    private TabPageIndicator tabpageindicator;
    private TabLayout tabpage_indicator;
    private ViewPager viewpager;
    private List<OverseasBasePager> basePagers;
    private List<String> tabDatas;

    public MovieOverseasPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.movieoverseas_pager, null);
        tabpage_indicator = (TabLayout) view.findViewById(R.id.tabpage_indicator);
////        tabpageindicator = (TabPageIndicator) view.findViewById(R.id.tabpageindicator);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("海外界面初始化了");

        //准备数据-页面
        basePagers = new ArrayList<>();
        basePagers.add(new UsaPager(context));
        basePagers.add(new KoreaPager(context));
        basePagers.add(new JapanPager(context));
        //页签页面数据
        tabDatas = Arrays.asList("美国", "韩国", "日本");

        //设置适配器
        viewpager.setAdapter(new MovieOverseasPagerAdapter(tabDatas,basePagers));


//        //关联ViewPager
//        tabpageindicator.setViewPager(viewpager);
//
//        //以后监听页面的变化，用TabPageIndicator监听页面的改变
//        tabpageindicator.setOnPageChangeListener(new MyOnPageChangeListener());
//        //默认第一项选中
//        tabpageindicator.setCurrentItem(0);

        //TabLayout关联ViewPager
        tabpage_indicator.setupWithViewPager(viewpager);
        //一定要设置设置滚动模式
//        tabpage_indicator.setTabMode(TabLayout.MODE_SCROLLABLE);
        //自定义Tab
//        for (int i = 0; i < tabpage_indicator.getTabCount(); i++) {
//            TabLayout.Tab tab = tabpage_indicator.getTabAt(i);
//            tab.setCustomView(getTabView(i));
//        }
    }

//    public View getTabView(int position){
//        View view = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
//        TextView tv= (TextView) view.findViewById(R.id.textView);
//        tv.setText(tabDatas.get(position));
//        ImageView img = (ImageView) view.findViewById(R.id.imageView);
//        img.setImageResource(R.drawable.show_icon_select);
//        return view;
//    }


    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
