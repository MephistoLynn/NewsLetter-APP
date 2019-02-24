package com.newspaper.model.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mephisto- on 2016/10/17.
 */

public class NewsTop  implements Serializable{


    private String reason;

    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        private String stat;


        private List<DataBean> data;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

/*        "title":"农村最惹人烦的三种随礼，第三种无语了！",
                "date":"2016-10-18 05:31",
                "author_name":"缘来如此啊",
                "thumbnail_pic_s":"http:\/\/00.imgmini.eastday.com\/mobile\/20161018\/20161018053126_33f2f88ccc6755f081b9933f38b71b4b_1_mwpm_03200403.jpeg",
                "thumbnail_pic_s02":"http:\/\/00.imgmini.eastday.com\/mobile\/20161018\/20161018053126_33f2f88ccc6755f081b9933f38b71b4b_1_mwpl_05500201.jpeg",
                "thumbnail_pic_s03":"http:\/\/00.imgmini.eastday.com\/mobile\/20161018\/20161018053126_33f2f88ccc6755f081b9933f38b71b4b_1_mwpl_05500201.jpeg",
                "url":"http:\/\/mini.eastday.com\/mobile\/161018053126951.html?qid=juheshuju",
                "uniquekey":"161018053126951",
                "type":"头条",
                "realtype":"社会"
                */

        public static class DataBean {
            private String title;
            private String date;
            private String author_name;
            private String thumbnail_pic_s;
            private String thumbnail_pic_s02;
            private String thumbnail_pic_s03;
            private String url;
            private String uniquekey;
            private String type;
            private String realtype;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getAuthor_name() {
                return author_name;
            }

            public void setAuthor_name(String author_name) {
                this.author_name = author_name;
            }

            public String getThumbnail_pic_s() {
                return thumbnail_pic_s;
            }

            public void setThumbnail_pic_s(String thumbnail_pic_s) {
                this.thumbnail_pic_s = thumbnail_pic_s;
            }

            public String getThumbnail_pic_s02() {
                return thumbnail_pic_s02;
            }

            public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
                this.thumbnail_pic_s02 = thumbnail_pic_s02;
            }

            public String getThumbnail_pic_s03() {
                return thumbnail_pic_s03;
            }

            public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
                this.thumbnail_pic_s03 = thumbnail_pic_s03;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUniquekey() {
                return uniquekey;
            }

            public void setUniquekey(String uniquekey) {
                this.uniquekey = uniquekey;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getRealtype() {
                return realtype;
            }

            public void setRealtype(String realtype) {
                this.realtype = realtype;
            }
        }
    }
}
