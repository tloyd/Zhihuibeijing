package cc.springwind.zhihuibeijing.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * create by HEFAN at 2016-08-12 15:02:07
 */
public class PrefsUtil {
    private static SharedPreferences sp;

    /**
     * 写入字符串变量至SharedPreferences中
     *
     * @param ctx
     * @param key
     * @param value
     */
    public static void putString(Context ctx, String key, String value) {
        //(存储节点文件名称,读写方式)
        if (sp == null)
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    /**
     * 从SharedPreferences中读取字符串变量
     *
     * @param ctx
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context ctx, String key, String defValue) {
        //(存储节点文件名称,读写方式)
        if (sp == null)
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    /**
     * 写入boolean变量至sp中
     *
     * @param ctx   上下文环境
     * @param key   存储节点名称
     * @param value 存储节点的值 boolean
     */
    public static void putBoolean(Context ctx, String key, boolean value) {
        //(存储节点文件名称,读写方式)
        if (sp == null)
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 读取boolean标示从sp中
     *
     * @param ctx      上下文环境
     * @param key      存储节点名称
     * @param defValue 没有此节点默认值
     * @return 默认值或者此节点读取到的结果
     */
    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        //(存储节点文件名称,读写方式)
        if (sp == null)
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 删除某个节点
     *
     * @param ctx
     * @param key
     */
    public static void remove(Context ctx, String key) {
        if (sp == null)
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    /**
     * 从SharedPreference读取int值
     *
     * @param ctx
     * @param key
     * @param i
     * @return
     */
    public static int getInt(Context ctx, String key, int i) {
        if (sp == null)
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt(key, i);
    }

    /**
     * 写入int变量至SharedPreference中
     *
     * @param ctx
     * @param key
     * @param i
     */
    public static void putInt(Context ctx, String key, int i) {
        if (sp == null)
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt(key, i).commit();
    }
}
