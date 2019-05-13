package com.example.base;


import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.beijingnews.R;

/**
 * 公共类
 */
public class BasePager {

    /**
     * 上下文 (MainActivity) 这个东西子类会用的上, 用 public
     */
    public final Context context;

    /**
     * 需要用到的view
     */
    public View rootView;

    /**
     * 显示标题
     */
    public TextView tv_title;

    /**
     * 点击侧滑
     */
    public ImageButton ib_menu;

    /**
     * 加载各个子页面的
     */
    public FrameLayout fl_content;


    public BasePager(Context context) {
        this.context = context;

        // 构造方法一执行,视图 就被初始化了
        rootView = initView();
    }


    /**
     * 用于初始化公共部分的视图,并且初始化加载子视图的FrameLayout
     *
     * @return
     */
    private View initView() {

        //基类的页面
        View view = View.inflate(context, R.layout.base_pager, null);

        tv_title = view.findViewById(R.id.tv_title);
        ib_menu = view.findViewById(R.id.ib_menu);
        fl_content = view.findViewById(R.id.fl_content);

        return view;
    }


    /**
     * 初始化数据,当子类需要初始化数据的时候,或者绑定数据,联网请求数据的时候,重写这个方法
     */
    public void initData() {

    }


}
