package com.newspaper.model.Service.Http;

import com.newspaper.model.HttpDetail.ApiService;
import com.newspaper.model.HttpDetail.HttpParams;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liukun on 16/3/9.
 */
public class HttpMethods {

    private static final String URL = "http://v.juhe.cn/";

    private static final int DEFAULT_TIMEOUT = 7;

    private Retrofit retrofit;
    private ApiService apiService;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取TOP新闻信息
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param type       新闻种类
     */
    public void getNewsTop(Subscriber subscriber, String type) {
        apiService.getTopInfo(HttpParams.KEY, type)

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);


    }

    /**
     * 获取Other新闻信息
     *
     * @param subscriber 由调用者传过来的观察者对象
     * @param type       新闻种类
     */
    public void getNewsOther(Subscriber subscriber, String type) {
        apiService.getOtherInfo(HttpParams.KEY, type)

                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


    }


}


