package com.bisonqin.learnrecyclerview.adapter;

import android.content.Context;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bisonqin.learnrecyclerview.R;
import com.bisonqin.learnrecyclerview.bean.Girl;
import com.bisonqin.learnrecyclerview.mvp.callback.AddItemCallback;
import com.bisonqin.learnrecyclerview.mvp.callback.RemoveItemCallback;
import com.bisonqin.learnrecyclerview.ui.activity.LineActivity2;
import com.bisonqin.learnrecyclerview.utils.ImageLoader;
import com.bisonqin.learnrecyclerview.utils.SnackbarUtil;

import java.util.List;

/**
 * Created by Basil on 2017/3/5.
 */

public class Line2Adapter extends BaseRecyclerViewAdapter<Girl>{

    private LineActivity2 activity;
    private List<Girl> datas;

    public Line2Adapter(Context context, List<Girl> datas, int layoutId, LineActivity2 activity) {
        super(context, datas, layoutId);
        this.activity = activity;
        this.datas = datas;
    }

    @Override
    protected void bindData(BaseViewHolder holder, Girl data, final int position) {
        ImageView iv = holder.getView(R.id.line_item_iv);
        TextView tv = holder.getView(R.id.line_item_tv);
        LinearLayout linearLayout = holder.getView(R.id.ll);

        if(holder.itemView.getScrollX()!=0){
            ((HorizontalScrollView)holder.itemView).fullScroll(View.FOCUS_UP);//如果item的HorizontalScrollView没在初始位置，则滚动回顶部
        }
        linearLayout.setMinimumWidth(activity.getScreenwidth());//设置LinearLayout宽度为屏幕宽度
        tv.setText("图"+position);

        ImageLoader.displayImage(activity, datas.get(position).getUrl(), iv);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarUtil.ShortSnackbar(activity.getCoordinatorLayout(),
                        "点击第" + position + "个",
                        SnackbarUtil.Info).show();
            }
        });
    }

    public void removeItem(final int position, final RemoveItemCallback callback) {
        Girl removed = datas.get(position);     //移除之前先保存移除的item
        datas.remove(position);                 //从Adapter中移除该item
        notifyItemRemoved(position);            //提示有变化
        callback.onSuccess(removed);           //移除成功,把移除的数据返回，看是否还需要
    }

    public void addItem(int position, Girl girl, final AddItemCallback callback) {
        datas.add(position, girl);              //把上一个移除的重新插入
        notifyItemInserted(position);
        callback.onSuccess();                   //插入成功
    }
}
