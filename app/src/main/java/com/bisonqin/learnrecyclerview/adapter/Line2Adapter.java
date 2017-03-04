package com.bisonqin.learnrecyclerview.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bisonqin.learnrecyclerview.R;
import com.bisonqin.learnrecyclerview.bean.Girl;
import com.bisonqin.learnrecyclerview.ui.activity.LineActivity2;
import com.bisonqin.learnrecyclerview.utils.ImageLoader;
import com.bisonqin.learnrecyclerview.utils.SnackbarUtil;

import java.util.List;

/**
 * Created by Basil on 2017/3/4.
 */

public class Line2Adapter extends RecyclerView.Adapter<Line2Adapter.MyViewHolder> {

    private LineActivity2 lineActivity2;
    private List<Girl> datas;

    public Line2Adapter(LineActivity2 lineActivity2, List<Girl> datas) {
        this.lineActivity2 = lineActivity2;
        this.datas = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(
                lineActivity2).inflate(R.layout.line_meizi_item2, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if(holder.itemView.getScrollX()!=0){
            ((HorizontalScrollView)holder.itemView).fullScroll(View.FOCUS_UP);//如果item的HorizontalScrollView没在初始位置，则滚动回顶部
        }
        holder.ll.setMinimumWidth(lineActivity2.getScreenwidth());//设置LinearLayout宽度为屏幕宽度
        holder.tv.setText("图"+position);

        ImageLoader.displayImage(lineActivity2, datas.get(position).getUrl(), holder.iv);

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarUtil.ShortSnackbar(lineActivity2.getCoordinatorLayout(),
                        "点击第"+position+"个",
                        SnackbarUtil.Info).show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView iv;
        private TextView tv;
        private LinearLayout ll;
        public MyViewHolder(View view)
        {
            super(view);
            iv = (ImageView) view.findViewById(R.id.line_item_iv);
            tv=(TextView) view.findViewById(R.id.line_item_tv);
            ll=(LinearLayout) view.findViewById(R.id.ll);
        }
    }

    public void addItem(Girl girl, int position) {
        datas.add(position, girl);
        notifyItemInserted(position);
        lineActivity2.getRecyclerview().scrollToPosition(position);
    }

    public void removeItem(final int position) {
        final Girl removed = datas.get(position);
        datas.remove(position);
        notifyItemRemoved(position);
        SnackbarUtil.ShortSnackbar(lineActivity2.getCoordinatorLayout(),
                "你删除了第" + position + "个item",
                SnackbarUtil.Warning).setAction("撤销", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(removed, position);
                SnackbarUtil.ShortSnackbar(lineActivity2.getCoordinatorLayout(), "撤销了删除第" + position + "个item", SnackbarUtil.Confirm).show();
            }
        }).setActionTextColor(Color.WHITE).show();
    }

    public void removeItem(Girl girl) {
        int position = datas.indexOf(girl);
        datas.remove(position);
        notifyItemRemoved(position);
    }

}