package com.bisonqin.learnrecyclerview.net;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * OkHttpClient请求构建类
 * Created by Bison on 2017/3/3.
 */
public class MyOkhttp {

    public static OkHttpClient client = new OkHttpClient();

    public static String get(String url){
        try {
            client.newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS);

            //用建造者模式构建请求
            Request request = new Request
                                .Builder()
                                .url(url)
                                .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
