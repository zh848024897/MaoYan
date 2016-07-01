package com.atguigu.maoyan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.domain.MovieWatingBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by hp on 2016/6/28.
 * 待映界面适配器
 */
public class MovieWatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_NORMAL = 2;
    private final Context context;
    private final MovieWatingBean movieWatingBean;
    private final List<MovieWatingBean.DataBean.ComingBean> datas;
    //图片资源
    private int[] imgIds;

    private LayoutInflater mInflater;

    public MovieWatingAdapter(Context context, MovieWatingBean movieWatingBean) {
        this.context = context;
        this.movieWatingBean = movieWatingBean;
        datas = movieWatingBean.getData().getComing();

        initData();

        mInflater = LayoutInflater.from(context);
    }

    private void initData() {
        imgIds = new int[]{R.drawable.gridview,R.drawable.ame};
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){
            View view = View.inflate(context,R.layout.item_moviewating_header,null);
            return new ViewHolderHeader(view);
        }else if(viewType == TYPE_NORMAL){

            View itemView = View.inflate(context, R.layout.item_moviewating, null);
            return new ViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){

            ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.setData(context, movieWatingBean.getData().getComing().get(position));
        } else if(getItemViewType(position) == TYPE_HEADER){
            ViewHolderHeader viewHolderHeader = (ViewHolderHeader) holder;
            viewHolderHeader.setData();
        }
    }

    @Override
    public int getItemCount() {
        return movieWatingBean.getData().getComing().size() + 1;
    }

    /**
     * 清空数据
     */
    public void cleanData() {
        datas.clear();
        notifyItemRangeRemoved(0,datas.size());
    }

    /**
     * 添加数据
     * @param data
     */
    public void addData(List<MovieWatingBean.DataBean.ComingBean> data) {
        addData(0, data);
    }

    /**
     * 得到多少条数据
     * @return
     */
    public int getDate() {
        return datas.size();

    }

    /**
     * 添加数据到指定位置
     * @param positon
     * @param data
     */
    public void addData(int positon, List<MovieWatingBean.DataBean.ComingBean> data) {
        if(data != null && data.size() >0 ){
            datas.addAll(positon,data);
            notifyItemRangeChanged(positon,datas.size());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date;
        private ImageView iv_icon;
        private TextView tv_name;
        private ImageView iv_3d;
        private TextView tv_wish;
        private TextView tv_scm;
        private TextView tv_star;
        private Button btn_presell;
        private TextView tv_tickets;
        private LinearLayout ll_special;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_3d = (ImageView) itemView.findViewById(R.id.iv_3d);
            tv_wish = (TextView) itemView.findViewById(R.id.tv_wish);
            tv_scm = (TextView) itemView.findViewById(R.id.tv_scm);
            tv_star = (TextView) itemView.findViewById(R.id.tv_star);
            btn_presell = (Button) itemView.findViewById(R.id.btn_presell);
            tv_tickets = (TextView) itemView.findViewById(R.id.tv_tickets);
            ll_special = (LinearLayout) itemView.findViewById(R.id.ll_special);
        }

        public void setData(Context context, MovieWatingBean.DataBean.ComingBean comingBean) {
            tv_date.setText(comingBean.getRt());
            String imgUrl = comingBean.getImg();
            imgUrl = imgUrl.replace("w.h", "168.110");
            Glide.with(context).load(imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//图片的缓存方式
                    .placeholder(R.drawable.lh)// 加载过程中的图片
                    .error(R.drawable.lh)// 加载失败的时候显示的图片
                    .into(iv_icon); //请求成功后把图片设置到的控件

            tv_name.setText(comingBean.getNm());

            if (comingBean.getVer().contains("3D") && comingBean.getVer().contains("IMAX")) {

                iv_3d.setImageResource(R.drawable.rw);

            } else if (comingBean.getVer().contains("3D")) {
                iv_3d.setImageResource(R.drawable.rv);
            } else {
//                iv_3d.setVisibility(View.GONE);
            }

            tv_wish.setText(comingBean.getWish() + "人想看");

            tv_scm.setText(comingBean.getScm());

            tv_star.setText("主演:" + comingBean.getStar());

            if(comingBean.getHeadLinesVO() != null && comingBean.getHeadLinesVO().size() > 0){
                ll_special.setVisibility(View.VISIBLE);
                tv_tickets.setText(comingBean.getHeadLinesVO().get(0).getTitle());
            } else {
                ll_special.setVisibility(View.GONE);
            }
        }
    }

    class ViewHolderHeader extends RecyclerView.ViewHolder{
        private LinearLayout ll_gallery_first;
        private LinearLayout ll_gallery_second;
        public ViewHolderHeader(View itemView) {
            super(itemView);
            ll_gallery_first = (LinearLayout) itemView.findViewById(R.id.ll_gallery_first);
            ll_gallery_second = (LinearLayout) itemView.findViewById(R.id.ll_gallery_second);
        }

        public void setData() {
            for (int i = 0; i < 10; i++)
            {

                View view = mInflater.inflate(R.layout.item_moviepager_gallery_first,
                        ll_gallery_first, false);
                ImageView img = (ImageView) view
                        .findViewById(R.id.iv_gallery);
                img.setImageResource(imgIds[0]);
                img.setScaleType(ImageView.ScaleType.FIT_XY);

                ll_gallery_first.addView(view);
            }

            for (int i = 0; i < 10; i++)
            {

                View view = mInflater.inflate(R.layout.item_moviepager_gallery_second,
                        ll_gallery_second, false);
                ImageView img = (ImageView) view
                        .findViewById(R.id.iv_gallery_second);
                img.setImageResource(imgIds[1]);
                img.setScaleType(ImageView.ScaleType.FIT_XY);

                ll_gallery_second.addView(view);
            }
        }
    }

}
