package com.bisonqin.learnrecyclerview.ui.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.bisonqin.learnrecyclerview.Config;
import com.bisonqin.learnrecyclerview.R;
import com.bisonqin.learnrecyclerview.adapter.LineAdapter;
import com.bisonqin.learnrecyclerview.bean.Girl;
import com.bisonqin.learnrecyclerview.mvp.callback.RemoveItemCallback;
import com.bisonqin.learnrecyclerview.mvp.callback.AddItemCallback;
import com.bisonqin.learnrecyclerview.mvp.contract.Contract;
import com.bisonqin.learnrecyclerview.mvp.presenter.LinePresenter;
import com.bisonqin.learnrecyclerview.ui.base.MVPBaseActivity;
import com.bisonqin.learnrecyclerview.utils.SnackbarUtil;

import java.util.Collections;
import java.util.List;

/**
 * Created by Basil on 2017/3/4.
 */

public class LineActivity extends MVPBaseActivity<Contract.View, LinePresenter> implements Contract.View{

    private static RecyclerView recyclerview;
    private CoordinatorLayout coordinatorLayout;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int lastVisibleItem ;
    private int page = 1;
    private ItemTouchHelper itemTouchHelper;
    private int screenwidth;
    private LineAdapter mAdapter;
    private List<Girl> data;

    public static RecyclerView getRecyclerview() {
        return recyclerview;
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }

    public boolean isAdapterNull() {
        return mAdapter == null;
    }

    @Override
    protected LinePresenter createPresenter() {
        return new LinePresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_line;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        setListener();

        mPresenter.getPage(1);              //获取第一页

        //获取屏幕宽度
        WindowManager wm = (WindowManager) LineActivity.this
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        screenwidth =outMetrics.widthPixels;
    }

    private void initView(){
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.line_coordinatorLayout);

        recyclerview = (RecyclerView)findViewById(R.id.line_recycler);
        mLayoutManager = new LinearLayoutManager(this);

        recyclerview.setLayoutManager(mLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.line_swipe_refresh) ;
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
        swipeRefreshLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }

    private void setListener(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.getPage(page);
            }
        });

        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags=0,swipeFlags=0;
                if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){
                    dragFlags= ItemTouchHelper.UP| ItemTouchHelper.DOWN| ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT;
                }else if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
                    dragFlags= ItemTouchHelper.UP| ItemTouchHelper.DOWN;
                    //设置侧滑方向为从左到右和从右到左都可以
                    swipeFlags = ItemTouchHelper.START| ItemTouchHelper.END;
                }
                return makeMovementFlags(dragFlags,swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(data,from,to);
                mAdapter.notifyItemMoved(from, to);

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                mAdapter.removeItem(position, new RemoveItemCallback() {
                    @Override
                    public void onSuccess(final Girl girl) {
                        SnackbarUtil.ShortSnackbar(coordinatorLayout,
                                "你删除了第" + position + "个item",
                                SnackbarUtil.Warning).setAction("撤销",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mAdapter.addItem(position, girl, new AddItemCallback() {
                                            @Override
                                            public void onSuccess() {
                                                SnackbarUtil.ShortSnackbar(coordinatorLayout,
                                                        "撤销了删除第" + position + "个item",
                                                        SnackbarUtil.Confirm).show();
                                                recyclerview.scrollToPosition(position);
                                            }
                                        });
                                    }
                                }).setActionTextColor(Color.WHITE).show();
                    }
                });
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if(actionState== ItemTouchHelper.ACTION_STATE_DRAG){
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                viewHolder.itemView.setAlpha(1- Math.abs(dX)/screenwidth);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });

        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                //               滑动状态停止并且剩余两个item时自动加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +2>=mLayoutManager.getItemCount()) {
                    mPresenter.getPage(++page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //                获取加载的最后一个可见视图在适配器的位置。
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public void setRecyclerAdapter(List<Girl> data) {
        this.data = data;
        if(null != recyclerview && null != itemTouchHelper) {
            //recyclerview.setAdapter(mAdapter = new LineAdapter(LineActivity.this, data));
            recyclerview.setAdapter(mAdapter = new LineAdapter(getBaseContext(), data, R.layout.line_meizi_item, coordinatorLayout));
            itemTouchHelper.attachToRecyclerView(recyclerview);
        }
    }

    @Override
    public void setRefreshStatus(boolean flag) {
        if(null != swipeRefreshLayout) {
            swipeRefreshLayout.setRefreshing(flag);
        }
    }

    @Override
    public void operatedAdapter(int operationCode) {
        switch (operationCode) {
            case Config.NOTIFY_DATA_SET_CHANGE:
                mAdapter.notifyDataSetChanged();
                break;
        }
    }
}
