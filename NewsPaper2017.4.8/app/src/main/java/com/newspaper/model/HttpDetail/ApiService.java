package com.newspaper.model.HttpDetail;

import com.newspaper.model.Bean.NewsOther;
import com.newspaper.model.Bean.NewsTop;

import java.io.Serializable;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mephisto- on 2016/10/16.
 */

public interface ApiService {


    @GET("toutiao/index")
    Observable<NewsTop> getTopInfo(@Query("key") String key, @Query("type") String type);

    @GET("toutiao/index")
    Observable<NewsOther> getOtherInfo(@Query("key") String key, @Query("type") String type);
}
