package com.atguigu.maoyan.pager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.atguigu.maoyan.R;
import com.atguigu.maoyan.adapter.CinamePagerAdapter;
import com.atguigu.maoyan.base.BasePager;
import com.atguigu.maoyan.parse.CinamePagerJsonBean;
import com.atguigu.maoyan.parse.GetAllDatasBean;
import com.atguigu.maoyan.utils.CacheUtils;
import com.atguigu.maoyan.utils.Contants;
import com.atguigu.maoyan.utils.LogUtil;
import com.atguigu.maoyan.view.DividerItemDecoration;
import com.atguigu.maoyan.volley.VolleyManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 2016/6/25.
 * 影院界面
 */
public class CinamePager extends BasePager {
    private String url;
    private RecyclerView recyclerview;
    private CinamePagerAdapter adapter;
    private List<CinamePagerJsonBean.Areas.AreasAddress> areasAddresses;
    private View view;

    public CinamePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        view = View.inflate(context, R.layout.ciname_pager, null);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerview.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));

        return super.initView();
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("影院初始化了");



        fl_content.removeAllViews();
        fl_content.addView(view);

        url = Contants.CINAME_URL;

        String saveJson = CacheUtils.getString(context,url);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }
        getDataFromNet();
    }

    private void getDataFromNet() {
        //请求文本
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                LogUtil.e("Volley联网成功=" + result);
                // 缓存数据
                CacheUtils.putString(context, url, result);
                // 解析和显示数据
                processData(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("Volley联网失败=" + volleyError.getMessage());
            }
        }) {
            //乱码解决
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed;
                try {
                    parsed = new String(response.data, "UTF-8");
                    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException var4) {
                    parsed = new String(response.data);
                }

                return super.parseNetworkResponse(response);
            }
        };
        //加入队列
        VolleyManager.getRequestQueue().add(request);
    }

    private void processData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String data = jsonObject.optString("data");
            GetAllDatasBean getAllDatasBean = new GetAllDatasBean();
            CinamePagerJsonBean allDatas = getAllDatasBean.getAllDatas(context, data);


            recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            areasAddresses = new ArrayList<>();
            for (int i = 0; i < allDatas.areas.size(); i++) {
                areasAddresses.addAll(allDatas.areas.get(i).areasAddresses);
            }
            adapter = new CinamePagerAdapter(context, areasAddresses);
            recyclerview.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
