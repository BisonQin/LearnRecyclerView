package com.bisonqin.learnrecyclerview.mvp.callback;

import com.bisonqin.learnrecyclerview.bean.Girl;

/**
 * Created by Basil on 2017/3/5.
 */

public interface RemoveItemCallback {

    void onSuccess(Girl girl);
    //void onFailed(int errorCode);
}
