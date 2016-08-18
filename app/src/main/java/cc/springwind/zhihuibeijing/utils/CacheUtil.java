package cc.springwind.zhihuibeijing.utils;

import android.content.Context;

/**
 * @author: HeFan
 * @date: 2016/8/14 17:07
 */
public class CacheUtil {
    public static void setCache(Context context, String key, String cache) {
        PrefsUtil.putString(context, key, cache);
    }

    public static String getCache(Context context, String key) {
        // 也可以用文件来作为缓存
        return PrefsUtil.getString(context, key, "");
    }
}
