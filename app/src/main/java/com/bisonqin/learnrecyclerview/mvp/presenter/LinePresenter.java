package com.bisonqin.learnrecyclerview.mvp.presenter;

import com.bisonqin.learnrecyclerview.Config;
import com.bisonqin.learnrecyclerview.bean.Girl;
import com.bisonqin.learnrecyclerview.mvp.callback.GetPageCallback;
import com.bisonqin.learnrecyclerview.mvp.contract.Contract;
import com.bisonqin.learnrecyclerview.mvp.model.Model;

import java.util.List;

/**
 * Created by Basil on 2017/3/4.
 */

public class LinePresenter extends MVPBasePresenter<Contract.View> implements Contract.Presenter{

    private Model mModel;           //Presenter层持有对Model层的引用

    public LinePresenter() {
        this.mModel = new Model();
    }

    @Override
    public void getPage(int page) {
        mModel.getPage(page, new GetPageCallback() {
            @Override
            public void setRefreshStatus(boolean flag) {
                mView.setRefreshStatus(flag);
            }

            @Override
            public void operateAdapter(List<Girl> data) {
                if(null == mView){
                    return;
                }
                if(mView.isAdapterNull()) {
                    mView.setRecyclerAdapter(data);
                }else {
                    mView.operatedAdapter(Config.NOTIFY_DATA_SET_CHANGE);
                }
            }
        });
    }
}
