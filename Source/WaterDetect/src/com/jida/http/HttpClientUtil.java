package com.jida.http;

import com.jida.http.okhttp.OkHttpClientManager;
import com.jida.http.okhttp.OkHttpResultCallback;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by chenyuye on 17/11/30.
 */

public class HttpClientUtil {

    private static HttpClientUtil mInstance;
    public static HttpClientUtil getInstance() {
        if (mInstance == null){
            synchronized (HttpClientUtil.class) {
                if (mInstance == null) {
                    mInstance = new HttpClientUtil();
                }
            }
        }
        return mInstance;
    }


    public Response getSync(String url) throws IOException {
        return OkHttpClientManager.getInstance().getSync(url);
    }


    public String getSyncString(String url) throws IOException {
        return OkHttpClientManager.getInstance().getSyncString(url);
    }

    public void getAsyn(String url, OkHttpResultCallback callback) {
        OkHttpClientManager.getInstance().getAsyn(url, callback);
    }

    public Response postSync(String url, OkHttpClientManager.Param... params) throws IOException {
        return OkHttpClientManager.getInstance().postSync(url, params);
    }

    public String postSyncString(String url, OkHttpClientManager.Param... params) throws IOException {
        return OkHttpClientManager.getInstance().postSyncString(url, params);
    }

    public void postAsyn(String url, final OkHttpResultCallback callback, OkHttpClientManager.Param... params) {
        OkHttpClientManager.getInstance().postAsyn(url, callback, params);
    }


    public void postAsyn(String url, final OkHttpResultCallback callback,
                                Map<String, String> params) {
        OkHttpClientManager.getInstance().postAsyn(url, callback, params);
    }
}
