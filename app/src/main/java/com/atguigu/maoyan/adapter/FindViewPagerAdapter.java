package com.atguigu.maoyan.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.domain.FindVIewPagerBean;

import org.xutils.x;

import java.util.List;

/**
 * Created by hp on 2016/6/30.
 */
public class FindViewPagerAdapter extends PagerAdapter {

    private final List<FindVIewPagerBean.DataBean> feedsBean;
    private final Context context;
    private final FindPagerAdapter.ViewHolderHeader.InternalHander internalHander;
    private final FindPagerAdapter.ViewHolderHeader.InternalRunnable internalRunnable;

    public FindViewPagerAdapter(Context context, List<FindVIewPagerBean.DataBean> feedsBean, FindPagerAdapter.ViewHolderHeader.InternalHander internalHander, FindPagerAdapter.ViewHolderHeader.InternalRunnable internalRunnable) {
        this.context = context;
        this.feedsBean = feedsBean;
        this.internalHander = internalHander;
        this.internalRunnable = internalRunnable;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(R.drawable.backgroud_logo);
        x.image().bind(imageView, feedsBean.get(position).getImgUrl());

        container.addView(imageView);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //把消息移除
                        internalHander.removeCallbacksAndMessages(null);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        //把消息移除
                        internalHander.removeCallbacksAndMessages(null);
                        break;

                    case MotionEvent.ACTION_UP:
                        //把消息移除
                        internalHander.removeCallbacksAndMessages(null);
                        internalHander.postDelayed(internalRunnable,4000);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                return true;
            }
        });

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return feedsBean.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
