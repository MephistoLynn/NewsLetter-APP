package com.newspaper.presenter;


import com.newspaper.model.Bean.NewsOther;
import com.newspaper.model.Bean.NewsTop;

import java.util.List;

/**
 * Created by mephisto- on 2016/10/18.
 */

public interface IEvent {
    //   void getInfo(T t);
    void getTop(List<NewsTop.ResultBean.DataBean> listTop);

    void getOther(List<NewsOther.ResultBean.DataBean> listOther);

    void onError(Throwable e);

}
