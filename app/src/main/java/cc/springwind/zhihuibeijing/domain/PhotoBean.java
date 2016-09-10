package cc.springwind.zhihuibeijing.domain;

import java.util.ArrayList;

/**
 * @author: HeFan
 * @date: 2016/9/8 13:21
 */
public class PhotoBean {

    public PhotosData data;

    public class PhotosData {
        public ArrayList<PhotoNews> news;
    }

    public class PhotoNews {
        public int id;
        public String listimage;
        public String title;
    }
}
