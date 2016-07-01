package com.atguigu.jsontest;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by hp on 2016/6/26.
 */
public class MyBaseAdapter<T> extends BaseAdapter {
    private List<T> mDatas;

    // 通过构造函数來获取到数据
    public MyBaseAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDatas != null) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mDatas != null) {
            return position;
        }
        return 0;
    }

    // 这个方法由子类复写
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;

    }
}
