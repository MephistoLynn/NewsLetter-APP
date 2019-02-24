package com.newspaper.model.DataBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by mephisto- on 2017/4/6.
 */
@Entity
public class CollectBean {
    @Id(autoincrement = true)
    private Long id;
    private String title;
    private String url;
    @Generated(hash = 210402398)
    public CollectBean(Long id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }
    @Generated(hash = 420494524)
    public CollectBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
