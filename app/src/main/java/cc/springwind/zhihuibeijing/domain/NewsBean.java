package cc.springwind.zhihuibeijing.domain;

import java.util.ArrayList;

/**
 * Created by HeFan on 2016/8/14.
 */
public class NewsBean {
    public int retcode;
    public ArrayList<Integer> extend;
    public ArrayList<NewsData> data;

    @Override
    public String toString() {
        return "NewsBean{" +
                "data=" + data +
                '}';
    }

    public class NewsData {
        public int id;
        public int type;
        public String title;
        public ArrayList<NewsChild> children;

        @Override
        public String toString() {
            return "NewsData{" +
                    "title='" + title + '\'' +
                    ", children=" + children +
                    '}';
        }
    }

    public class NewsChild {
        public int id;
        public int type;
        public String title;
        public String url;

        @Override
        public String toString() {
            return "NewsChild{" +
                    "title='" + title + '\'' +
                    '}';
        }
    }
}
