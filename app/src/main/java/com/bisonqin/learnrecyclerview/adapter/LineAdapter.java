package com.bisonqin.learnrecyclerview.adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bisonqin.learnrecyclerview.R;
import com.bisonqin.learnrecyclerview.bean.Girl;
import com.bisonqin.learnrecyclerview.mvp.callback.RemoveItemCallback;
import com.bisonqin.learnrecyclerview.mvp.callback.AddItemCallback;
import com.bisonqin.learnrecyclerview.utils.ImageLoader;
import com.bisonqin.learnrecyclerview.utils.SnackbarUtil;

import java.util.List;

/**
 * Created by Basil on 2017/3/5.
 */

public class LineAdapter extends BaseRecyclerViewAdapter<Girl> {

    private Context mContext;
    private List<Girl> datas;
    private CoordinatorLayout layout;

    public LineAdapter(Context context, List<Girl> datas, int layoutId, CoordinatorLayout layout) {
        super(context, datas, layoutId);
        this.mContext = context;
        this.datas = datas;
        this.layout = layout;
    }

    @Override
    protected void bindData(BaseViewHolder holder, Girl data, final int position) {
        TextView tv = holder.getView(R.id.line_item_tv);
        ImageView iv = holder.getView(R.id.line_item_iv);
        LinearLayout linearLayout = holder.getView(R.id.linearLayout);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackbarUtil.ShortSnackbar(layout,
                        "点击第" + position + "个",
                        SnackbarUtil.Info).show();
            }
        });

        tv.setText("图" + position);
        ImageLoader.displayImage(mContext, datas.get(position).getUrl(), iv);           //利用图片加载库加载图片
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
