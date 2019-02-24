package com.newspaper.model.HttpDetail;

/**
 * Created by mephisto- on 2016/10/17.
 */

public class HttpParams {
    /*    新闻类型：
            top(头条，默认),shehui(社会),guonei(国内),
         guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),
         keji(科技),caijing(财经),shishang(时尚)
    */
    public static final String paramTYPE = "TYPE";
    public static final String[] paramTypes = {"shehui", "guonei", "yule", "tiyu", "top", "junshi", "keji", "caijing", "shishang"};

    public static final String[] categories = {"社会", "国内", "娱乐", "体育", "头条", "军事", "科技", "财经", "时尚"};

    public static final String shehui = "shehui";
    public static final String guonei = "guonei";
    public static final String guoji = "guoji";
    public static final String yule = "yule";
    public static final String tiyu = "tiyu";
    public static final String junshi = "junshi";
    public static final String keji = "keji";
    public static final String caijing = "caijing";
    public static final String shishang = "shishang";

    public static final String TOP = "top";
    public static final String KEY = "da5014e8de28b54cb1c46cef3b1fddc4";

}
