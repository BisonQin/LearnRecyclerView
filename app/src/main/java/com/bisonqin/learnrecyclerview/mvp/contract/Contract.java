package com.bisonqin.learnrecyclerview.mvp.contract;

import com.bisonqin.learnrecyclerview.bean.Girl;

import java.util.List;

/**
 * Contract其实就是一个包涵了Presenter和View的接口，Presenter实现的逻辑层方法，
 * View实现的UI层的方法都能在Contract接口中一目了然的看明白，
 * 具体的Presenter和View的实现类都是通过实现Contract接口来完成。
 *
 * Created by Basil on 2017/3/4.
 */

public interface Contract {

    interface View {
        //TODO 主要是View层的接口
        void setRecyclerAdapter(List<Girl> data);
        void setRefreshStatus(boolean flag);
        boolean isAdapterNull();
        void operatedAdapter(int operationCode);
    }

    interface Presenter {
        //TODO 主要是Presenter的接口
        void getPage(int page);
    }
}
