package com.bisonqin.learnrecyclerview.mvp.callback;

import com.bisonqin.learnrecyclerview.bean.Girl;

import java.util.List;

/**
 * Created by Basil on 2017/3/4.
 */

public interface GetPageCallback {

    void setRefreshStatus(boolean flag);
    void operateAdapter(List<Girl> data);
}
