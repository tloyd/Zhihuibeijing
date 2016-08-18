package cc.springwind.zhihuibeijing.domain;

import java.util.ArrayList;

/**
 * @author: HeFan
 * @date: 2016/8/16 14:00
 */
public class NewsTabBean {

    public int retcode;
    public DetailData data;

    public class DetailData {
        public String countcommenturl;
        public String more;
        public String title;
        public ArrayList<News> news;
        public ArrayList<Topic> topic;
        public ArrayList<TopNews> topnews;
    }

    public class News {
        public boolean comment;
        public String commentlist;
        public String commenturl;
        public int id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;
    }

    public class Topic {
        public String description;
        public int id;
        public String listimage;
        public int sort;
        public String title;
        public String url;
    }

    public class TopNews {
        public boolean comment;
        public String commentlist;
        public String commenturl;
        public int id;
        public String pubdate;
        public String title;
        public String topimage;
        public String type;
        public String url;
    }
}
