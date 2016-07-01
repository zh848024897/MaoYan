package com.atguigu.maoyan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.domain.OverseasUsaBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by hp on 2016/6/29.
 */
public class UsaPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型：正常
    private static final int TYPE_NORMAL = 0;
    //item类型：底部
    private static final int TYPE_FOOTER = 2;
    private final Context context;
    private final OverseasUsaBean overseasUsaBean;
    private final List<OverseasUsaBean.DataBean.ComingBean> comings;

    public UsaPagerAdapter(Context context, OverseasUsaBean overseasUsaBean) {
        this.context = context;
        this.overseasUsaBean = overseasUsaBean;
        comings = overseasUsaBean.getData().getComing();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_NORMAL){
            View itemView = View.inflate(context, R.layout.item_overseas_usa,null);
            return new ViewHolder(itemView);
        }else if(viewType == TYPE_FOOTER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_overseas_usa_footer, parent, false);
            return new ViewHolderFooter(itemView);
        }
            return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.setData(context, comings.get(position));

        }else {
        }
    }

    @Override
    public int getItemCount() {
        return comings.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == comings.size()){
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
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

        public void setData(Context context, OverseasUsaBean.DataBean.ComingBean comingBean) {
            String imgUrl = comingBean.getImg();
            imgUrl = imgUrl.replace("w.h", "168.110");
            Glide.with(context).load(imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//图片的缓存方式
                    .placeholder(R.drawable.lh)// 加载过程中的图片
                    .error(R.drawable.lh)// 加载失败的时候显示的图片
                    .into(iv_icon); //请求成功后把图片设置到的控件

            tv_name.setText(comingBean.getNm());

            if(comingBean.getVer() != null){
                if (comingBean.getVer().contains("3D") && comingBean.getVer().contains("IMAX")) {

                    iv_3d.setImageResource(R.drawable.rw);

                } else if (comingBean.getVer().contains("3D")) {
                    iv_3d.setImageResource(R.drawable.rv);
                } else {
//                iv_3d.setVisibility(View.GONE);
                }
            }

            tv_wish.setText(comingBean.getWish() + "人想看");

            tv_scm.setText(comingBean.getOverseaTime() + "美国上映");

            tv_star.setText("主演:" + comingBean.getStar());

        }
    }

    class ViewHolderFooter extends RecyclerView.ViewHolder{
        private TextView tv_name;
        public ViewHolderFooter(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
