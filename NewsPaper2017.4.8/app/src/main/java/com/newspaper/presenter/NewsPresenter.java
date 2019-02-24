package com.newspaper.presenter;

import android.util.Log;

import com.newspaper.model.Bean.NewsOther;
import com.newspaper.model.Bean.NewsTop;
import com.newspaper.model.HttpDetail.HttpParams;
import com.newspaper.model.Service.Http.HttpMethods;

import rx.Subscriber;

/**
 * Created by mephisto- on 2016/10/18.
 */

public class NewsPresenter {


    private IEvent infoEvent;


    private NewsPresenter(IEvent infoEvent) {
        this.infoEvent = infoEvent;
    }


    /* public void getInfo(String type) {
         subscriber = new Subscriber<T>() {
             @Override
             public void onCompleted() {
                 Toast.makeText(MyApplication.getContext(), "请求完成", Toast.LENGTH_SHORT).show();
             }

             @Override
             public void onError(Throwable e) {
                 Toast.makeText(MyApplication.getContext(), "访问失败" + e, Toast.LENGTH_SHORT).show();

             }

             @Override
             public void onNext(T t) {

 //                infoEvent.getInfo(t);
             }


         };
         HttpMethods.getInstance().getNewsInfo((Subscriber<Object>) subscriber, HttpParams.KEY, type);
     }
 */
    public void getTop() {
        Subscriber<NewsTop> subscriber = new Subscriber<NewsTop>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                infoEvent.onError(e);
//                Toast.makeText(MyApplication.getContext(), "访问失败" + e, Toast.LENGTH_SHORT).show();
                Log.e("error", e.toString());
            }
            @Override
            public void onNext(NewsTop newsTop) {
                if (newsTop.getError_code() == 0)
                    infoEvent.getTop(newsTop.getResult().getData());
                else
//                    Toast.makeText(MyApplication.getContext(), "访问失败", Toast.LENGTH_SHORT).show();
                    infoEvent.onError(null);
            }
        };
        HttpMethods.getInstance().getNewsTop(subscriber, HttpParams.TOP);

    }

    public void getOther(String Type) {
        Subscriber<NewsOther> subscriber = new Subscriber<NewsOther>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                infoEvent.onError(null);
//                Toast.makeText(MyApplication.getContext(), "访问失败" + e, Toast.LENGTH_SHORT).show();
                Log.e("error", e.toString());
            }

            @Override
            public void onNext(NewsOther newsOther) {
                if (newsOther.getError_code() == 0)
                    infoEvent.getOther(newsOther.getResult().getData());
                else
                    infoEvent.onError(null);
//                    Toast.makeText(MyApplication.getContext(), "访问失败", Toast.LENGTH_SHORT).show();

            }


        };
        HttpMethods.getInstance().getNewsOther(subscriber, Type);
    }


    public static NewsPresenter getInstance(IEvent iEvent) {
        return SingletonHolder.Instance(iEvent);
    }

    //单例
    private static class SingletonHolder {
        private static NewsPresenter Instance(IEvent iEvent) {
            return new NewsPresenter(iEvent);
        }
    }
}
