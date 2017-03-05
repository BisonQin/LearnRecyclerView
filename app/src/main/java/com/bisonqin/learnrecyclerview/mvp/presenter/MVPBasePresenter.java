package com.bisonqin.learnrecyclerview.mvp.presenter;

/**
 * MVP框架的简单封装 P处理层
 * Created by Basil on 2017/3/4.
 */

public abstract class MVPBasePresenter<V> {

    protected V mView;             //持有View层的引用

    public void attachView(V mView) {
        this.mView = mView;
    }

    public void dettachView() {
        mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }
}
