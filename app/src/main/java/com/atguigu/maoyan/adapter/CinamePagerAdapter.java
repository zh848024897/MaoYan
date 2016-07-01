package com.atguigu.maoyan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atguigu.maoyan.R;
import com.atguigu.maoyan.parse.CinamePagerJsonBean;

import java.util.List;

/**
 * Created by hp on 2016/6/26.
 */
public class CinamePagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<CinamePagerJsonBean.Areas.AreasAddress> areasAddresses;

    public CinamePagerAdapter(Context context, List<CinamePagerJsonBean.Areas.AreasAddress> areasAddresses) {
        this.context = context;
        this.areasAddresses = areasAddresses;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_cinamepager, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_ciname_name.setText(areasAddresses.get(position).nm);
        viewHolder.tv_price.setText(areasAddresses.get(position).sellPrice + "");
        viewHolder.tv_address.setText(areasAddresses.get(position).addr);
    }

    @Override
    public int getItemCount() {
//        int size = 0;
//        for (int i = 0; i < allDatas.areas.size(); i++) {
//            size += allDatas.areas.get(i).areasAddresses.size();
//        }
        return areasAddresses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_ciname_name;
        private TextView tv_price;
        private TextView tv_price_follow;
        private TextView tv_address;
        private TextView tv_distance;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_ciname_name = (TextView) itemView.findViewById(R.id.tv_ciname_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_price_follow = (TextView) itemView.findViewById(R.id.tv_price_follow);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_distance = (TextView) itemView.findViewById(R.id.tv_distance);
        }
    }
}
