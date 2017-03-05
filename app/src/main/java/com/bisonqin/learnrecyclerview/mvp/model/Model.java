package com.bisonqin.learnrecyclerview.mvp.model;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.bisonqin.learnrecyclerview.bean.Girl;
import com.bisonqin.learnrecyclerview.mvp.callback.GetPageCallback;
import com.bisonqin.learnrecyclerview.net.MyOkhttp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Basil on 2017/3/4.
 */

public class Model {

    private List<Girl> girls;

    public void getPage(final int page, final GetPageCallback callback) {

        new AsyncTask<String, Integer, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                callback.setRefreshStatus(true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if(!TextUtils.isEmpty(s)){

                    JSONObject jsonObject;
                    Gson gson=new Gson();
                    String jsonData=null;

                    try {
                        jsonObject = new JSONObject(s);
                        jsonData = jsonObject.getString("results");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(girls == null|| girls.size() == 0){
                        girls = gson.fromJson(jsonData, new TypeToken<List<Girl>>() {}.getType());
                        Girl pages=new Girl();
                        pages.setPage(page);
                        girls.add(pages);
                    }else{
                        List<Girl> more= gson.fromJson(jsonData, new TypeToken<List<Girl>>() {}.getType());
                        girls.addAll(more);
                        Girl pages=new Girl();
                        pages.setPage(page);
                        girls.add(pages);
                    }

                    callback.operateAdapter(girls);
                }
                callback.setRefreshStatus(false);
            }

            @Override
            protected String doInBackground(String... strings) {
                return MyOkhttp.get(strings[0]);                //发起请求
            }
        }.execute("http://gank.io/api/data/福利/10/" + page);
    }
}
