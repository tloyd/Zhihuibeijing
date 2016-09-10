package cc.springwind.zhihuibeijing.global;

/**
 * Created by HeFan on 2016/8/12.
 */
public class Constants {
    // SharedPreference的KEY
    public static final String IS_FIRST_USE = "is_first_use"; // 是否第一次使用
    // 访问服务器的URL
    public static final String SERVER_URL = "http://192.168.1.113:8080/zhbj";
    public static final String CATEGORIES_URL = SERVER_URL + "/categories.json"; // 新闻分类的URL
    public static final String READ_IDS = "read_ids";
    public static final String PHOTO_URL = SERVER_URL + "/photos/photos_1.json";
}
