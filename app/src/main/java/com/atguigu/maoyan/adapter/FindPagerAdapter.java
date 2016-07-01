package com.atguigu.maoyan.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.domain.FindPagerBean;
import com.atguigu.maoyan.domain.FindVIewPagerBean;
import com.atguigu.maoyan.view.HorizontalScrollViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.xutils.common.util.DensityUtil;

import java.util.List;

/**
 * Created by hp on 2016/6/29.
 */
public class FindPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型：头
    private static final int TYPE_HEADER = 0;
    //item类型：style=2
    private static final int TYPE_TWO = 2;
    private static final int TYPE_THREE = 3;
    private static final int TYPE_FIVE = 5;
    private static final int TYPE_FOUR = 4;
    private final Context context;
    //RecyclerView数据
    private final FindPagerBean findPagerBean;
    private final List<FindPagerBean.DataBean.FeedsBean> feeds;
    //ViewPager数据
    private final FindVIewPagerBean findVIewPagerBean;
    private final List<FindVIewPagerBean.DataBean> datas;
    private FindViewPagerAdapter adapter;

    public FindPagerAdapter(Context context, FindPagerBean findPagerBean, FindVIewPagerBean findVIewPagerBean) {
        this.context = context;
        this.findPagerBean = findPagerBean;
        feeds = findPagerBean.getData().getFeeds();
        this.findVIewPagerBean = findVIewPagerBean;
        datas = findVIewPagerBean.getData();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        int realPosition = position - 1;
        if (feeds.get(realPosition).getStyle() == 2) {
            return TYPE_TWO;
        } else if (feeds.get(realPosition).getStyle() == 4) {
            return TYPE_FOUR;
        } else if (feeds.get(realPosition).getStyle() == 3) {
            return TYPE_THREE;
        } else if (feeds.get(realPosition).getStyle() == 5) {
            return TYPE_FIVE;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOUR) {
            View view = View.inflate(context, R.layout.item_find_style_four, null);
            return new ViewHolderFour(view);
        } else if (viewType == TYPE_THREE) {
            View view = View.inflate(context, R.layout.item_find_style_three, null);
            return new ViewHolderThree(view);
        } else if (viewType == TYPE_FIVE) {
            View view = View.inflate(context, R.layout.item_find_style_five, null);
            return new ViewHolderFive(view);
        } else if (viewType == TYPE_TWO) {
            View view = View.inflate(context, R.layout.item_find_style_two, null);
            return new ViewHolderTwo(view);
        } else if (viewType == TYPE_HEADER) {
            View view = View.inflate(context, R.layout.find_header, null);
            return new ViewHolderHeader(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int realPosition = position - 1;
        if (getItemViewType(position) == TYPE_FOUR) {
            ViewHolderFour viewHolderFour = (ViewHolderFour) holder;
            viewHolderFour.setData(feeds.get(realPosition));
        } else if (getItemViewType(position) == TYPE_THREE) {
            ViewHolderThree viewHolderThree = (ViewHolderThree) holder;
            viewHolderThree.setData(feeds.get(realPosition));
        } else if (getItemViewType(position) == TYPE_FIVE) {
            ViewHolderFive viewHolderFive = (ViewHolderFive) holder;
            viewHolderFive.setData(feeds.get(realPosition));
        } else if (getItemViewType(position) == TYPE_TWO) {
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.setData(feeds.get(realPosition));
        } else if (getItemViewType(position) == TYPE_HEADER) {
            ViewHolderHeader viewHolderHeader = (ViewHolderHeader) holder;
            viewHolderHeader.setData(datas);
        }
    }

    @Override
    public int getItemCount() {
        return feeds.size() + 1;
    }


    class ViewHolderHeader extends RecyclerView.ViewHolder {
        private HorizontalScrollViewPager hsviewpager;
        private LinearLayout ll_point_group;
        private RadioGroup rg_main;
        private RadioButton rb_topic;
        private RadioButton rb_message;
        private RadioButton rb_movie;
        private RadioButton rb_ticket;
        //上一次高亮显示的位置
        private int prePosition;
        private boolean isDrag = false;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            hsviewpager = (HorizontalScrollViewPager) itemView.findViewById(R.id.hsviewpager);
            ll_point_group = (LinearLayout) itemView.findViewById(R.id.ll_point_group);
            rg_main = (RadioGroup) itemView.findViewById(R.id.rg_main);
            rb_topic = (RadioButton) itemView.findViewById(R.id.rb_topic);
            rb_message = (RadioButton) itemView.findViewById(R.id.rb_message);
            rb_movie = (RadioButton) itemView.findViewById(R.id.rb_movie);
            rb_ticket = (RadioButton) itemView.findViewById(R.id.rb_ticket);
        }

        public void setData(List<FindVIewPagerBean.DataBean> feedsBean) {

            //发消息或者任务开始循环播放
            if (internalHander == null) {
                internalHander = new InternalHander();
            }

            //设置ViewPager适配器
            adapter = new FindViewPagerAdapter(context, feedsBean, internalHander, new InternalRunnable());
            hsviewpager.setAdapter(adapter);

            //把所有的点先移除
            ll_point_group.removeAllViews();
            //添加点
            for (int i = 0; i < feedsBean.size(); i++) {

                ImageView point = new ImageView(context);
                point.setBackgroundResource(R.drawable.point_bg_selector);
                if (i == 0) {
                    point.setEnabled(true);
                } else {
                    point.setEnabled(false);
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(5), DensityUtil.dip2px(5));
                if (i != 0) {
                    params.leftMargin = DensityUtil.dip2px(5);
                }
                point.setLayoutParams(params);

                //添加到显示布局里面
                ll_point_group.addView(point);

            }

            //监听ViewPager页面的变化
            hsviewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //1.把上一次的设置默认
                    ll_point_group.getChildAt(prePosition).setEnabled(false);
                    //2.把当前页面对应的点设置高亮
                    ll_point_group.getChildAt(position).setEnabled(true);

                    prePosition = position;
                }

                @Override
                public void onPageSelected(int position) {


                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                        isDrag = true;
                        internalHander.removeCallbacksAndMessages(null);
                    } else if (state == ViewPager.SCROLL_STATE_IDLE && isDrag) {
                        isDrag = false;
                        internalHander.removeCallbacksAndMessages(null);
                        internalHander.postDelayed(new InternalRunnable(), 4000);

                    } else if (state == ViewPager.SCROLL_STATE_SETTLING && isDrag) {
                        internalHander.removeCallbacksAndMessages(null);
                        internalHander.postDelayed(new InternalRunnable(), 4000);
                    }
                }
            });

            //移除所有的消息和任务
            internalHander.removeCallbacksAndMessages(null);
            //4秒后执行任务
            internalHander.postDelayed(new InternalRunnable(), 3000);

        }

        private InternalHander internalHander;

        public class InternalHander extends Handler {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                int item = (hsviewpager.getCurrentItem() + 1) % findVIewPagerBean.getData().size();
                hsviewpager.setCurrentItem(item);

                internalHander.postDelayed(new InternalRunnable(), 4000);
            }
        }

        public class InternalRunnable implements Runnable {

            @Override
            public void run() {
                internalHander.sendEmptyMessage(0);
            }
        }
    }

    class ViewHolderFour extends RecyclerView.ViewHolder {
        private TextView tv_group_title;
        private ImageView iv_group_first;
        private ImageView iv_group_second;
        private ImageView iv_group_third;
        private TextView tv_group_nickname;
        private TextView tv_group_comment;
        private TextView tv_group_viewcount;

        public ViewHolderFour(View itemView) {
            super(itemView);
            tv_group_title = (TextView) itemView.findViewById(R.id.tv_group_title);
            iv_group_first = (ImageView) itemView.findViewById(R.id.iv_group_first);
            iv_group_second = (ImageView) itemView.findViewById(R.id.iv_group_second);
            iv_group_third = (ImageView) itemView.findViewById(R.id.iv_group_third);
            tv_group_nickname = (TextView) itemView.findViewById(R.id.tv_group_nickname);
            tv_group_comment = (TextView) itemView.findViewById(R.id.tv_group_comment);
            tv_group_viewcount = (TextView) itemView.findViewById(R.id.tv_group_viewcount);
        }

        public void setData(FindPagerBean.DataBean.FeedsBean feedsBean) {
            tv_group_title.setText(feedsBean.getTitle());

            Glide.with(context).load(feedsBean.getImages().get(0).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//图片的缓存方式
                    .placeholder(R.drawable.backgroud_logo)// 加载过程中的图片
                    .error(R.drawable.backgroud_logo)// 加载失败的时候显示的图片
                    .into(iv_group_first); //请求成功后把图片设置到的控件
            Glide.with(context).load(feedsBean.getImages().get(1).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//图片的缓存方式
                    .placeholder(R.drawable.backgroud_logo)// 加载过程中的图片
                    .error(R.drawable.backgroud_logo)// 加载失败的时候显示的图片
                    .into(iv_group_second); //请求成功后把图片设置到的控件
            Glide.with(context).load(feedsBean.getImages().get(2).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//图片的缓存方式
                    .placeholder(R.drawable.backgroud_logo)// 加载过程中的图片
                    .error(R.drawable.backgroud_logo)// 加载失败的时候显示的图片
                    .into(iv_group_third); //请求成功后把图片设置到的控件

            tv_group_nickname.setText(feedsBean.getUser().getNickName());

            tv_group_comment.setText(feedsBean.getCommentCount() + "");

            tv_group_viewcount.setText(feedsBean.getViewCount() + "");

        }
    }

    class ViewHolderThree extends RecyclerView.ViewHolder {
        private TextView tv_group_title;
        private ImageView iv_group_first;
        private ImageView iv_group_second;
        private ImageView iv_group_third;
        private TextView tv_group_nickname;
        private TextView tv_group_comment;
        private TextView tv_group_viewcount;

        public ViewHolderThree(View itemView) {
            super(itemView);
            tv_group_title = (TextView) itemView.findViewById(R.id.tv_group_title);
            iv_group_first = (ImageView) itemView.findViewById(R.id.iv_group_first);
            iv_group_second = (ImageView) itemView.findViewById(R.id.iv_group_second);
            iv_group_third = (ImageView) itemView.findViewById(R.id.iv_group_third);
            tv_group_nickname = (TextView) itemView.findViewById(R.id.tv_group_nickname);
            tv_group_comment = (TextView) itemView.findViewById(R.id.tv_group_comment);
            tv_group_viewcount = (TextView) itemView.findViewById(R.id.tv_group_viewcount);
        }

        public void setData(FindPagerBean.DataBean.FeedsBean feedsBean) {
            tv_group_title.setText(feedsBean.getTitle());

            Glide.with(context).load(feedsBean.getImages().get(0).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//图片的缓存方式
                    .placeholder(R.drawable.backgroud_logo)// 加载过程中的图片
                    .error(R.drawable.backgroud_logo)// 加载失败的时候显示的图片
                    .into(iv_group_first); //请求成功后把图片设置到的控件
            Glide.with(context).load(feedsBean.getImages().get(1).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//图片的缓存方式
                    .placeholder(R.drawable.backgroud_logo)// 加载过程中的图片
                    .error(R.drawable.backgroud_logo)// 加载失败的时候显示的图片
                    .into(iv_group_second); //请求成功后把图片设置到的控件
            Glide.with(context).load(feedsBean.getImages().get(2).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//图片的缓存方式
                    .placeholder(R.drawable.backgroud_logo)// 加载过程中的图片
                    .error(R.drawable.backgroud_logo)// 加载失败的时候显示的图片
                    .into(iv_group_third); //请求成功后把图片设置到的控件

            tv_group_nickname.setText(feedsBean.getUser().getNickName());

            tv_group_comment.setText(feedsBean.getCommentCount() + "");

            tv_group_viewcount.setText(feedsBean.getViewCount() + "");
        }
    }

    class ViewHolderFive extends RecyclerView.ViewHolder {
        private ImageView iv_icon;
        private TextView tv_title;
        private TextView tv_nickName;
        private TextView tv_comment;
        private TextView tv_viewcount;

        public ViewHolderFive(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_nickName = (TextView) itemView.findViewById(R.id.tv_nickName);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_viewcount = (TextView) itemView.findViewById(R.id.tv_viewcount);
        }

        public void setData(FindPagerBean.DataBean.FeedsBean feedsBean) {
            Glide.with(context).load(feedsBean.getImages().get(0).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//图片的缓存方式
                    .placeholder(R.drawable.backgroud_logo)// 加载过程中的图片
                    .error(R.drawable.backgroud_logo)// 加载失败的时候显示的图片
                    .into(iv_icon); //请求成功后把图片设置到的控件

            tv_title.setText(feedsBean.getTitle());

            tv_nickName.setText(feedsBean.getUser().getNickName());

            tv_comment.setText(feedsBean.getCommentCount() + "");

            tv_viewcount.setText(feedsBean.getViewCount() + "");
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        private ImageView iv_icon;
        private TextView tv_title;
        private TextView tv_nickName;
        private TextView tv_comment;
        private TextView tv_viewcount;

        public ViewHolderTwo(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_nickName = (TextView) itemView.findViewById(R.id.tv_nickName);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
            tv_viewcount = (TextView) itemView.findViewById(R.id.tv_viewcount);
        }

        public void setData(FindPagerBean.DataBean.FeedsBean feedsBean) {
            Glide.with(context).load(feedsBean.getImages().get(0).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//图片的缓存方式
                    .placeholder(R.drawable.backgroud_logo)// 加载过程中的图片
                    .error(R.drawable.backgroud_logo)// 加载失败的时候显示的图片
                    .into(iv_icon); //请求成功后把图片设置到的控件

            tv_title.setText(feedsBean.getTitle());

            tv_nickName.setText(feedsBean.getUser().getNickName());

            tv_comment.setText(feedsBean.getCommentCount() + "");

            tv_viewcount.setText(feedsBean.getViewCount() + "");

        }
    }
}
