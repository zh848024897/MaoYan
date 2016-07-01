package com.atguigu.maoyan.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.domain.MoviePlayingInnerBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by hp on 2016/6/27.
 * 热映界面中ViewPager适配器
 */
public class MoviePlayingInnerAdapter extends PagerAdapter {
    private final Context context;
    private final List<MoviePlayingInnerBean.DataBean> datas;

    public MoviePlayingInnerAdapter(Context context, List<MoviePlayingInnerBean.DataBean> datas) {
        this.context = context;
        this.datas = datas;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        int realPosition = position%datas.size();
        ImageView imageView = new ImageView(context);
        imageView.setBackgroundResource(R.drawable.lh);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        Log.e("TAG", "url======" + datas.get(position).getImgUrl());
        Glide.with(context).load(datas.get(realPosition).getImgUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)//图片的缓存方式
                .placeholder(R.drawable.lh)// 加载过程中的图片
                .error(R.drawable.lh)// 加载失败的时候显示的图片
                .into(imageView); //请求成功后把图片设置到的控件

        container.addView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "ViewPager点击" + position, Toast.LENGTH_SHORT).show();
            }
        });
        return imageView;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
