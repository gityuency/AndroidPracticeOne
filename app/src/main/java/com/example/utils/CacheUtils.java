package com.example.utils;

import android.content.Context;
import android.content.SharedPreferences;

/// 缓存软件的一些参数和数据

/**
 * 一个缓存的工具类
 */
public class CacheUtils {

    /**
     * 得到缓存值
     * <p>
     * 把光标移动到函数行的末尾, 然后按下  shit + option + g 就可以生成javadoc注释 前提是需要下载一个插件
     *
     * @param context 上下文
     * @param key     the key
     * @return the boolean
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("beijingxingwe", context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     * 保存参数, 曾经看到过引导页
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("beijingxingwe", context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }


    /**
     * 缓存网络请求的数据
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("beijingxingwe", context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }


    /**
     * 获取网络请求的数据
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("beijingxingwe", context.MODE_PRIVATE);
        return sp.getString(key, "");  //这里不要使用null, 在其他地方用返回值的时候会有崩溃的几率
    }
}
