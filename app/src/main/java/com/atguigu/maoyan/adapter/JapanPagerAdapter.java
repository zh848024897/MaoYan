package com.atguigu.maoyan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.domain.OverseasJapanBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by hp on 2016/6/29.
 */
public class JapanPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final OverseasJapanBean overseasJapanBean;
    private final List<OverseasJapanBean.DataBean.HotBean> hots;

    public JapanPagerAdapter(Context context, OverseasJapanBean overseasJapanBean) {
        this.context = context;
        this.overseasJapanBean = overseasJapanBean;
        hots = overseasJapanBean.getData().getHot();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_overseas_korea,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.setData(context, hots.get(position));
    }

    @Override
    public int getItemCount() {
        return hots.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_icon;
        private TextView tv_name;
        private ImageView iv_3d;
        private TextView tv_wish;
        private TextView tv_scm;
        private TextView tv_star;
        private Button btn_look;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_3d = (ImageView) itemView.findViewById(R.id.iv_3d);
            tv_wish = (TextView) itemView.findViewById(R.id.tv_wish);
            tv_scm = (TextView) itemView.findViewById(R.id.tv_scm);
            tv_star = (TextView) itemView.findViewById(R.id.tv_star);
            btn_look = (Button) itemView.findViewById(R.id.btn_look);
        }

        public void setData(Context context, OverseasJapanBean.DataBean.HotBean hotBean) {
            String imgUrl = hotBean.getImg();
            imgUrl = imgUrl.replace("w.h", "168.110");
            Glide.with(JapanPagerAdapter.this.context).load(imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//图片的缓存方式
                    .placeholder(R.drawable.lh)// 加载过程中的图片
                    .error(R.drawable.lh)// 加载失败的时候显示的图片
                    .into(iv_icon); //请求成功后把图片设置到的控件

            tv_name.setText(hotBean.getNm());

            if (hotBean.getVer().contains("3D") && hotBean.getVer().contains("IMAX")) {

                iv_3d.setImageResource(R.drawable.rw);

            } else if (hotBean.getVer().contains("3D")) {
                iv_3d.setImageResource(R.drawable.rv);
            } else {
//                iv_3d.setVisibility(View.GONE);
            }

            if(hotBean.getSc() == 0){
                tv_wish.setText(hotBean.getWish() + "人想看");
            } else {
                tv_wish.setText(hotBean.getWish() + "分");
            }

            tv_scm.setText(hotBean.getCat());

            tv_star.setText("主演:" + hotBean.getStar());

        }
    }
}
