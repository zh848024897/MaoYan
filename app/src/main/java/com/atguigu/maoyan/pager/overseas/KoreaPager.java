package com.atguigu.maoyan.pager.overseas;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.adapter.KoreaPagerAdapter;
import com.atguigu.maoyan.base.OverseasBasePager;
import com.atguigu.maoyan.domain.OverseasKoreaBean;
import com.atguigu.maoyan.utils.CacheUtils;
import com.atguigu.maoyan.utils.Contants;
import com.atguigu.maoyan.utils.LogUtil;
import com.atguigu.maoyan.view.DividerItemDecoration;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by hp on 2016/6/28.
 */
public class KoreaPager extends OverseasBasePager {
    private RecyclerView recyclerview;
    private String url;
    private KoreaPagerAdapter adapter;

    public KoreaPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.korea_pager,null);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerview.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        url = Contants.OVERSEAS_KOREA_URL;
        String saveJson = CacheUtils.getString(context, url);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }

        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("联网成功");
                //缓存
                CacheUtils.putString(context, url, result);
                //处理并显示数据
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
        OverseasKoreaBean overseasKoreaBean = new Gson().fromJson(json, OverseasKoreaBean.class);

        recyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        adapter = new KoreaPagerAdapter(context,overseasKoreaBean);
        recyclerview.setAdapter(adapter);
    }
}
