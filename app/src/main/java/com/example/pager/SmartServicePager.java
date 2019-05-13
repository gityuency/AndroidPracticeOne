package com.example.pager;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.base.BasePager;

public class SmartServicePager extends BasePager {


    public SmartServicePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();

        //1.设置标题
        tv_title.setText("智慧末刃页面");

        //2.联网请求,得到数据,创建视图
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);

        //3.把子视图添加到 basepager 中的 framelayout中
        fl_content.addView(textView);


        //4.绑定数据
        textView.setText("智慧末刃页面内容");

    }

}
