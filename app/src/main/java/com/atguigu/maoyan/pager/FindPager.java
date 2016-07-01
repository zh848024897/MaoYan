package com.atguigu.maoyan.pager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.adapter.FindPagerAdapter;
import com.atguigu.maoyan.base.BasePager;
import com.atguigu.maoyan.domain.FindPagerBean;
import com.atguigu.maoyan.domain.FindVIewPagerBean;
import com.atguigu.maoyan.utils.CacheUtils;
import com.atguigu.maoyan.utils.Contants;
import com.atguigu.maoyan.utils.LogUtil;
import com.atguigu.maoyan.view.DividerItemDecoration;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by hp on 2016/6/25.
 * 发现界面
 */
public class FindPager extends BasePager {
    private RecyclerView recyclerview;
    private String url;
    private View view;
    private FindPagerAdapter adapter;
    private String viewpager_url;
    //是顶部联网
    private static final String VIEWPAGER = "viewpager";
    //是RecyclerView联网
    private static final String RECYCLERVIEW = "recyclerview";
    private FindPagerBean findPagerBean;
    private FindVIewPagerBean findVIewPagerBean;

    public FindPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.find_pager,null);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerview.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));

        return super.initView();
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("发现初始化了");

        fl_content.removeAllViews();
        fl_content.addView(view);

        url = Contants.FIND_URL;
        String savaJson = CacheUtils.getString(context,url);
        if(!TextUtils.isEmpty(savaJson)){
            processData(savaJson,RECYCLERVIEW);
        }

        getDataFromNet();

        viewpager_url = Contants.FIND_VIEWPAGER_URL;
        String savaViewpagerJson = CacheUtils.getString(context,viewpager_url);
        if(!TextUtils.isEmpty(savaViewpagerJson)){
            processData(savaViewpagerJson,VIEWPAGER);
        }

        getViewpagerDataFromNet();
    }

    private void getViewpagerDataFromNet() {
        RequestParams params = new RequestParams(viewpager_url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("联网成功");
                CacheUtils.putString(context, viewpager_url, result);
                processData(result,VIEWPAGER);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("联网失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("联网成功");
                CacheUtils.putString(context, url, result);
                processData(result,RECYCLERVIEW);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("联网失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String json,String tag) {
        if(tag == RECYCLERVIEW){
            findPagerBean = new Gson().fromJson(json, FindPagerBean.class);
        } else if(tag == VIEWPAGER){
            findVIewPagerBean = new Gson().fromJson(json, FindVIewPagerBean.class);
        }

        if(findPagerBean != null && findVIewPagerBean != null){

            recyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            adapter = new FindPagerAdapter(context,findPagerBean,findVIewPagerBean);
            recyclerview.setAdapter(adapter);
        }
    }
}
