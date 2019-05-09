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
     *
     * 把光标移动到函数行的末尾, 然后按下  shit + option + g 就可以生成javadoc注释 前提是需要下载一个插件
     *
     * @param context 上下文
     * @param key     the key
     * @return the boolean
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("beijingxingwe", context.MODE_PRIVATE);
        sp.getBoolean(key, false);
        return true;
    }
}
