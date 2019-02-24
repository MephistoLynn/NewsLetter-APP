package com.newspaper.model.Bean;

/**
 * Created by mephisto- on 2016/10/31.
 */

public class DeliverBean {
    private String title;
    private String url;


    public DeliverBean(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public DeliverBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
