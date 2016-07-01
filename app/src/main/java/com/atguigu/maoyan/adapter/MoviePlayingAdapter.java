package com.atguigu.maoyan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.activity.MoviePlayingItemActivity;
import com.atguigu.maoyan.domain.MoviePlayingBean;
import com.atguigu.maoyan.domain.MoviePlayingInnerBean;
import com.atguigu.maoyan.utils.Contants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by hp on 2016/6/27.
 * 热映界面适配器
 */
public class MoviePlayingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //分类型：头
    private static final int TYPE_HEADER = 0;
    //分类型：正常item
    private static final int TYPE_NORMAL = 1;
    //分类型：特别的item
    private static final int TYPE_NO_NORMAL = 2;
    private final Context context;
    //recyclerview数据
    private final MoviePlayingBean moviePlayingBean;
    private List<MoviePlayingBean.DataBean.MoviesBean> movies;
    //viewpager数据
    private final MoviePlayingInnerBean moviePlayingInnerBean;
    //热映界面-ListView-item点击
    private String item_url = Contants.PLAYING_CLICK_URL;
    private int id;


    public MoviePlayingAdapter(final Context context, MoviePlayingBean moviePlayingBean, MoviePlayingInnerBean moviePlayingInnerBean) {
        this.context = context;
        this.moviePlayingBean = moviePlayingBean;
        movies = moviePlayingBean.getData().getMovies();
        this.moviePlayingInnerBean = moviePlayingInnerBean;

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                
                if (getItemViewType(position) == TYPE_NORMAL) {
                    if(position == 1){
                        id = movies.get(position - 1).getId();
                    } else {
                        id = movies.get(position - 2).getId();
                    }
                    String real_item_url = item_url.replace("246188",id + "");
                    Intent intent = new Intent(context,MoviePlayingItemActivity.class);
                    intent.putExtra("url",real_item_url);
                    context.startActivity(intent);

                } else if (getItemViewType(position) == TYPE_HEADER) {



                } else if (getItemViewType(position) == TYPE_NO_NORMAL) {

                    Toast.makeText(context, "影评被点击了", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moviepager, parent, false);
            return new ViewHolder(itemView);

        } else if (viewType == TYPE_HEADER) {
            View itemView = View.inflate(context, R.layout.item_moviepager_header, null);
            return new ViewHolderHeader(itemView);
        } else if (viewType == TYPE_NO_NORMAL) {
            View itemView = View.inflate(context, R.layout.item_moviepager_no_normal, null);
            return new ViewHolderNoNormal(itemView);
        }

        return null;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ViewPager viewpager = (ViewPager) msg.obj;
            int item = viewpager.getCurrentItem() + 1;
            viewpager.setCurrentItem(item);

            //延迟发消息
            handler.removeCallbacksAndMessages(null);
            Message message = Message.obtain();
            message.obj = viewpager;
            handler.sendMessageDelayed(message, 3000);
        }
    };

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

//        movies = moviePlayingBean.getData().getMovies();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }

            }
        });

        if (getItemViewType(position) == TYPE_HEADER) {
            ViewHolderHeader viewHolderHeader = (ViewHolderHeader) holder;
            viewHolderHeader.viewpager.setAdapter(new MoviePlayingInnerAdapter(context, moviePlayingInnerBean.getData()));
            //设置监听ViewPager页面的改变
//            viewHolderHeader.viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

            //设置中间位置
            int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % (moviePlayingInnerBean.getData().size());//要保证imageViews的整数倍
            viewHolderHeader.viewpager.setCurrentItem(item);
            //发消息
            handler.removeCallbacksAndMessages(null);
            Message message = Message.obtain();
            message.obj = viewHolderHeader.viewpager;
            handler.sendMessageDelayed(message, 3000);
        } else if (getItemViewType(position) == TYPE_NO_NORMAL) {

            return;
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            ViewHolder viewHolder = (ViewHolder) holder;
            //position的位置发生了改变，但集合数据中下标没变
            if (position == 1) {
                viewHolder.setData(movies.get(position - 1), context);
            } else {
                viewHolder.setData(movies.get(position - 2), context);
            }

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == 2) {
            return TYPE_NO_NORMAL;
        }
//        return super.getItemViewType(position);
        return TYPE_NORMAL;
    }


    @Override
    public int getItemCount() {
        return moviePlayingBean.getData().getMovies().size() + 2;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_icon;
        private TextView tv_name;
        private ImageView iv_3d;
        private TextView tv_score_wish;
        private TextView tv_scm;
        private TextView tv_showinfo;
        private Button btn_buy;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_3d = (ImageView) itemView.findViewById(R.id.iv_3d);
            tv_score_wish = (TextView) itemView.findViewById(R.id.tv_score_wish);
            tv_scm = (TextView) itemView.findViewById(R.id.tv_scm);
            tv_showinfo = (TextView) itemView.findViewById(R.id.tv_showinfo);
            btn_buy = (Button) itemView.findViewById(R.id.btn_buy);
        }

        public void setData(MoviePlayingBean.DataBean.MoviesBean moviesBean,Context context) {

            Glide.with(context).load(moviesBean.getImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//图片的缓存方式
//                .placeholder(R.drawable.lh)// 加载过程中的图片
                    .error(R.drawable.lh)// 加载失败的时候显示的图片
                    .into(iv_icon); //请求成功后把图片设置到的控件

            tv_name.setText(moviesBean.getNm());

            if(moviesBean.isValue3d() && moviesBean.isImax()){

                iv_3d.setImageResource(R.drawable.rw);

            } else if(moviesBean.isValue3d()){
                iv_3d.setImageResource(R.drawable.rv);
            }else {
                iv_3d.setVisibility(View.INVISIBLE);
            }

            if(moviesBean.getSc() == 0){
                tv_score_wish.setText(moviesBean.getWish() + "人想看");
                btn_buy.setText("预售");
            }else {
                tv_score_wish.setText(moviesBean.getSc() + "分");
                btn_buy.setText("购票");
            }

            tv_scm.setText(moviesBean.getScm());
            tv_showinfo.setText(moviesBean.getShowInfo());
        }
    }

    class ViewHolderHeader extends RecyclerView.ViewHolder {
        private RelativeLayout rl_seach;
        private ViewPager viewpager;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            rl_seach = (RelativeLayout) itemView.findViewById(R.id.rl_seach);
            viewpager = (ViewPager) itemView.findViewById(R.id.viewpager);

            rl_seach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "搜索点击", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    class ViewHolderNoNormal extends RecyclerView.ViewHolder {

        public ViewHolderNoNormal(View itemView) {
            super(itemView);
        }
    }

    //监听
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
