package com.atguigu.maoyan.menudetailpager;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.adapter.MovieWatingAdapter;
import com.atguigu.maoyan.base.MenuDetailBasePager;
import com.atguigu.maoyan.domain.MovieWatingBean;
import com.atguigu.maoyan.utils.CacheUtils;
import com.atguigu.maoyan.utils.Contants;
import com.atguigu.maoyan.utils.LogUtil;
import com.atguigu.maoyan.view.DividerItemDecoration;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by hp on 2016/6/27.
 */
public class MovieWatingPager extends MenuDetailBasePager {
    private RecyclerView recyclerview;
    private String url;
    private MovieWatingAdapter adapter;
    private MaterialRefreshLayout refresh;
    /**
     * 当前是第几页
     */
    private int curPage = 1;

    /**
     * 总页数
     */
    private int totalPage = 2;
    /**
     * 正常状态
     */
    private static final int STATE_NORMAL = 1;

    /**
     * 下拉刷新状态
     */
    private static final int STATE_REFRESH = 2;

    /**
     * 上拉刷新状态-加载更多状态
     */
    private static final int STATE_MORE = 3;

    /**
     * 当前状态
     */
    private int state = STATE_NORMAL;
    private MovieWatingBean movieWatingBean;
    private List<MovieWatingBean.DataBean.ComingBean> datas;

    public MovieWatingPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.moviewating_pager, null);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerview.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));

        refresh = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        refresh.setMaterialRefreshListener(new MyMaterialRefreshListener());
        return view;
    }

    class MyMaterialRefreshListener extends MaterialRefreshListener {

        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//            Toast.makeText(context, "下拉刷新", Toast.LENGTH_SHORT).show();
            //下拉刷新
            initRefresh();
        }

        @Override
        public void onfinish() {
            super.onfinish();
//            Toast.makeText(context, "刷新完成", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
//            super .onRefreshLoadMore(materialRefreshLayout);
            if (curPage < totalPage) {
                //上拉刷新
                initMore();
            } else {
                refresh.finishRefreshLoadMore();
                if (flag) {
                    flag = false;
                    Toast.makeText(context, "没有更多数据了", Toast.LENGTH_SHORT).show();
                    // 结束上拉刷新 ,即上拉刷新控件消失
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            flag = true;
                        }
                    }, 1000);
                }
            }
        }
    }

    private void initMore() {
        curPage = curPage + 1;
        state = STATE_MORE;
        getDataFromNet();
    }

    private void initRefresh() {
        curPage = 1;
        state = STATE_REFRESH;
        getDataFromNet();
    }

    private void normalShowData() {
        state = STATE_NORMAL;
        curPage = 1;
        String saveJson = CacheUtils.getString(context, url);
        if (!TextUtils.isEmpty(saveJson)) {
            processData(saveJson);
        }
        getDataFromNet();
    }

    //解决上拉加载更多弹出多次的bug
    private boolean flag = true;


    @Override
    public void initData() {
        super.initData();
        LogUtil.e("待映界面初始化了");

        url = Contants.WATING_URL;
        String saveJson = CacheUtils.getString(context, url);
        if (!TextUtils.isEmpty(saveJson)) {
            processData(saveJson);
        }

        normalShowData();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("联网成功");
                //缓存
                CacheUtils.putString(context, url, result);
                //解析和显示数据
                processData(result);
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

    private void processData(String json) {
        movieWatingBean = new Gson().fromJson(json, MovieWatingBean.class);
        datas = movieWatingBean.getData().getComing();
        showData();

    }

    private void showData() {
        switch (state) {
            case STATE_NORMAL://正常，正在加载数据 第一页
                recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                adapter = new MovieWatingAdapter(context, movieWatingBean);
                recyclerview.setAdapter(adapter);
                break;
            case STATE_REFRESH: //加载第一页，把原来的数据清空
                //1.把数据清空-适配器
                adapter.cleanData();
                //2.从新设置数据
                adapter.addData(datas);
                refresh.finishRefresh();
                break;
            case STATE_MORE://把得到的数据加载到原来的集合中
                //1.把数据添加到原来集合的末尾
                adapter.addData(adapter.getDate(), datas);
                //2.把状态还原
                refresh.finishRefreshLoadMore();
                break;
        }

    }
}
