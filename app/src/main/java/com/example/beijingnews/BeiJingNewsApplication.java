package com.example.beijingnews;

import android.app.Application;

import org.xutils.x;

/**
 * 代表整个软件, 这个类是我们自己创建的 是整个程序的入口?
 *
 * 这个东西需要在 App清单里面配置 android:name=".BeiJingNewsApplication" 配置完了之后, BeiJingNewsApplication 这个名字就不是灰色
 */
public class BeiJingNewsApplication extends Application {


    /**
     * 所有组件被创建之前之执行
     */
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.setDebug(true);
        x.Ext.init(this);  //这个this 指的就是  BeiJingNewsApplication

    }
}
