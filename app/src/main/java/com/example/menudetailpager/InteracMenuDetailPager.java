package com.example.menudetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.base.MenuDetailBasePager;
import com.example.utils.LogUtil;


/**
 * 互动详情页面
 */
public class InteracMenuDetailPager extends MenuDetailBasePager {

    private TextView textView;

    public InteracMenuDetailPager(Context context) {
        super(context);
    }


    @Override
    public View initView() {

        //2.联网请求,得到数据,创建视图
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);

        return textView;
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("互动 页面被初始化了............");

        textView.setText("互动 页面的内容");

    }
}
