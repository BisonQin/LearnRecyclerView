package com.bisonqin.learnrecyclerview.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.View;

import com.bisonqin.learnrecyclerview.Config;
import com.bisonqin.learnrecyclerview.R;
import com.bisonqin.learnrecyclerview.adapter.GridAdapter;
import com.bisonqin.learnrecyclerview.bean.Girl;
import com.bisonqin.learnrecyclerview.mvp.contract.Contract;
import com.bisonqin.learnrecyclerview.mvp.presenter.LinePresenter;
import com.bisonqin.learnrecyclerview.ui.base.MVPBaseActivity;
import com.bisonqin.learnrecyclerview.utils.SnackbarUtil;

import java.util.List;

/**
 * Created by Bison on 2017/3/3.
 */
public class StaggeredActivity extends MVPBaseActivity<Contract.View, LinePresenter> implements Contract.View{

    private static RecyclerView recyclerview;
    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ItemTouchHelper itemTouchHelper;
    private StaggeredGridLayoutManager mLayoutManager;

    private GridAdapter mAdapter;
    private List<Girl> girls;
    private int lastVisibleItem ;
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        setListener();

        mPresenter.getPage(1);
    }

    @Override
    protected LinePresenter createPresenter() {
        return new LinePresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_staggerde;
    }

    private void initView(){
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.staggered_coordinatorLayout);

        recyclerview = (RecyclerView)findViewById(R.id.staggered_recycler);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(mLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.staggered_swipe_refresh) ;
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
        swipeRefreshLayout.setProgressViewOffset(
                false,
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
    }

    private void setListener(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                mPresenter.getPage(1);
            }
        });

        itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags=0;
                if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager){
                    dragFlags= ItemTouchHelper.UP| ItemTouchHelper.DOWN| ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT;
                }
                return makeMovementFlags(dragFlags,0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from=viewHolder.getAdapterPosition();
                int to=target.getAdapterPosition();
                Girl moveItem= girls.get(from);
                girls.remove(from);
                girls.add(to,moveItem);
                mAdapter.notifyItemMoved(from,to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }
        });

        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +2>=mLayoutManager.getItemCount()) {
                    mPresenter.getPage(++ page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] positions= mLayoutManager.findLastVisibleItemPositions(null);
                lastVisibleItem = Math.max(positions[0],positions[1]);
            }
        });
    }

    @Override
    public void setRecyclerAdapter(List<Girl> data) {
        this.girls = data;
        if(null != recyclerview && null != itemTouchHelper) {
            recyclerview.setAdapter(mAdapter = new GridAdapter(StaggeredActivity.this, data));

            //设置点击监听
            mAdapter.setOnItemClickListener(new GridAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    int position=recyclerview.getChildAdapterPosition(view);
                    SnackbarUtil.ShortSnackbar(coordinatorLayout,"点击第"+position+"个",SnackbarUtil.Info).show();
                }

                @Override
                public void onItemLongClick(View view) {
                    itemTouchHelper.startDrag(recyclerview.getChildViewHolder(view));
                }
            });
            itemTouchHelper.attachToRecyclerView(recyclerview);
        }
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public void setRefreshStatus(boolean flag) {
        if(null != swipeRefreshLayout) {
            swipeRefreshLayout.setRefreshing(flag);
        }
    }

    @Override
    public boolean isAdapterNull() {
        return null == mAdapter;
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
