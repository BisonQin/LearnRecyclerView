package com.bisonqin.learnrecyclerview.ui.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.bisonqin.learnrecyclerview.R;
import com.bisonqin.learnrecyclerview.mvp.presenter.MVPBasePresenter;
import com.bisonqin.learnrecyclerview.receiver.NetChangeObserver;
import com.bisonqin.learnrecyclerview.receiver.NetStateReceiver;
import com.bisonqin.learnrecyclerview.utils.NetStateUtils;

/**
 * 封装的MVPBaseActivity类
 * Created by Basil on 2016/9/21.
 */

public abstract class MVPBaseActivity<V, P extends MVPBasePresenter<V>> extends AppCompatActivity {

    protected P mPresenter;
    protected Toolbar mToolbar;
    /**
     * 网络观察者
     */
    protected NetChangeObserver mNetChangeObserver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNetChangeObserver();
        //允许为空，不是所有都要实现MVP模式
        mPresenter = createPresenter();
        if(null != mPresenter) {
            mPresenter.attachView((V) this);
        }

        setContentView(provideContentViewId());//布局

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar); //把Toolbar当做ActionBar给设置
            if (canBack()) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null)
                    actionBar.setDisplayHomeAsUpEnabled(true);//设置ActionBar一个返回箭头，主界面没有，次级界面有
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mPresenter != null && !mPresenter.isViewAttached()) {
            mPresenter.attachView((V) this);
        }
    }

    private void initNetChangeObserver() {
        // 网络改变的一个回掉类
        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetStateUtils.NetType type) {

            }

            @Override
            public void onNetDisConnect() {
                Toast.makeText(getBaseContext(), "网络未连接", Toast.LENGTH_SHORT).show();
            }
        };

        //开启广播去监听 网络 改变事件
        NetStateReceiver.registerObserver(mNetChangeObserver);
    }

    @Override
    protected void onDestroy() {
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
        if(mPresenter != null) {
            mPresenter.dettachView();           //防止Presenter层一直持有View层引用导致内存泄露
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 此时android.R.id.home即为返回箭头
        if (item.getItemId() == android.R.id.home) {
            //onBackPressed();
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 判断当前 Activity 是否允许返回
     * 主界面不允许返回，次级界面允许返回
     *
     * @return false
     */
    public boolean canBack() {
        return false;
    }

    /**
     * 判断子Activity是否需要刷新功能
     *
     * @return false
     */
    public boolean isSetRefresh() {
        return false;
    }

    protected abstract P createPresenter();

    abstract protected int provideContentViewId();//用于引入布局文件

}