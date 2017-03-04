package com.bisonqin.learnrecyclerview.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bisonqin.learnrecyclerview.R;
import com.bisonqin.learnrecyclerview.bean.Girl;
import com.bisonqin.learnrecyclerview.ui.activity.LineActivity;
import com.bisonqin.learnrecyclerview.utils.ImageLoader;
import com.bisonqin.learnrecyclerview.utils.SnackbarUtil;

import java.util.List;

/**
 * Created by Basil on 2017/3/4.
 */

public class LineAdapter extends RecyclerView.Adapter<LineAdapter.MyViewHolder> implements View.OnClickListener {

    private LineActivity lineActivity;
    private List<Girl> datas;

    public LineAdapter(LineActivity lineActivity, List<Girl> datas) {
        this.lineActivity = lineActivity;
        this.datas = datas;
    }

    /**
     * 把View封装进ViewGolder中
     * @param parent
     * @param viewType
     * @return 返回一个自定义的ViewHolder
     */
    @Override
    public LineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(
                lineActivity).inflate(R.layout.line_meizi_item, parent,
                false);
        LineAdapter.MyViewHolder holder = new LineAdapter.MyViewHolder(view);

        view.setOnClickListener(this);

        return holder;
    }

    /**
     * 把数据装入ViewHolder的控件中
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(LineAdapter.MyViewHolder holder, int position) {

        holder.tv.setText("图" + position);
        //利用图片加载类加载图片
        ImageLoader.displayImage(lineActivity, datas.get(position).getUrl(), holder.iv);
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    @Override
    public void onClick(View v) {

        int position = lineActivity.getRecyclerview().getChildAdapterPosition(v);
        SnackbarUtil.ShortSnackbar(lineActivity.getCoordinatorLayout(),"点击第"+position+"个",SnackbarUtil.Info).show();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView iv;
        private TextView tv;
        public MyViewHolder(View view)
        {
            super(view);
            iv = (ImageView) view.findViewById(R.id.line_item_iv);
            tv=(TextView) view.findViewById(R.id.line_item_tv);
        }
    }

    public void addItem(Girl girl, int position) {
        datas.add(position, girl);
        notifyItemInserted(position);
        lineActivity.getRecyclerview().scrollToPosition(position);
    }

    public void removeItem(final int position) {
        final Girl removed= datas.get(position);
        datas.remove(position);
        notifyItemRemoved(position);
        SnackbarUtil.ShortSnackbar(lineActivity.getCoordinatorLayout(),
                "你删除了第" + position + "个item",
                SnackbarUtil.Warning).setAction("撤销",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addItem(removed, position);
                        SnackbarUtil.ShortSnackbar(lineActivity.getCoordinatorLayout(),
                                "撤销了删除第" + position + "个item",
                                SnackbarUtil.Confirm).show();
                    }
                }).setActionTextColor(Color.WHITE).show();
    }

}