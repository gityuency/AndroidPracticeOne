package com.example.base;

import android.content.Context;
import android.view.View;

/**
 * 详情页面的基类
 */
public abstract class MenuDetailBasePager {

    /**
     * 上下文
     */
    public final Context context;


    /**
     * 详情页面的根视图
     */
    public View rootView;


    public MenuDetailBasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    /**
     * 这里创建的是一个抽象的方法,让子类去实现, 抽象方法没有方法体,并且这个类要被标记为抽象类
     * 强制子类实现页面, 每个子类有不同的亚敏
     *
     * @return
     */
    public abstract View initView();


    /**
     * 子页面需要绑定数据, 联网请求数据的时候,重写该方法
     */
    public void initData() {

    }

}






































