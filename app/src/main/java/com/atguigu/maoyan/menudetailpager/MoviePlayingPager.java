package com.atguigu.maoyan.menudetailpager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.adapter.MoviePlayingAdapter;
import com.atguigu.maoyan.base.MenuDetailBasePager;
import com.atguigu.maoyan.domain.MoviePlayingBean;
import com.atguigu.maoyan.domain.MoviePlayingInnerBean;
import com.atguigu.maoyan.utils.CacheUtils;
import com.atguigu.maoyan.utils.Contants;
import com.atguigu.maoyan.utils.LogUtil;
import com.atguigu.maoyan.view.DividerItemDecoration;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by hp on 2016/6/27.
 */
public class MoviePlayingPager extends MenuDetailBasePager {
    private RecyclerView recyclerview;
    private String url;
    private MoviePlayingAdapter adapter;
    private String topUrl;
    //是顶部联网
    private static final String VIEWPAGER = "viewpager";
    //是RecyclerView联网
    private static final String RECYCLERVIEW = "recyclerview";
    private MoviePlayingInnerBean moviePlayingInnerBean;
    private MoviePlayingBean moviePlayingBean;

    public MoviePlayingPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.movie_playing_pager, null);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerview.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("热映界面初始化了");



        url = Contants.PLAYING_URL;
        String saveJson = CacheUtils.getString(context, url);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson,RECYCLERVIEW);
        }

        getDataFromNet();

        topUrl = Contants.PLAYING_VIEWPAGER_URL;

        String saveTopJson = CacheUtils.getString(context,topUrl);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveTopJson,VIEWPAGER);
        }

        getTopDataFromNet();
    }

    private void getTopDataFromNet() {
        RequestParams params = new RequestParams(topUrl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("联网成功");

                //缓存数据
                CacheUtils.putString(context, topUrl, result);

                //处理数据并显示
                processData(result,VIEWPAGER);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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

                //缓存数据
                CacheUtils.putString(context, url, result);

                //处理数据并显示
                processData(result,RECYCLERVIEW);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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

        if(tag == VIEWPAGER){
            moviePlayingInnerBean = new Gson().fromJson(json, MoviePlayingInnerBean.class);
        }else {

            moviePlayingBean = new Gson().fromJson(json, MoviePlayingBean.class);
        }
        //设置布局管理器
        if(moviePlayingBean != null && moviePlayingInnerBean != null){

            //recyclerview.removeAllViews();
            recyclerview.setLayoutManager(
                    new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            adapter =
                    new MoviePlayingAdapter(context, moviePlayingBean,moviePlayingInnerBean);

            recyclerview.setAdapter(adapter);

            //RecyclerView添加横线
            //横线要在initView()中添加，如果在initData()中,每次刷新都会添加横线，会把布局撑大
//            recyclerview.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        }
    }
}
