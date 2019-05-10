package com.example.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.example.base.BaseFragment;
import com.example.utils.LogUtil;

public class ContentFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {

        LogUtil.e("正文页面被初始化了");

        textView = new TextView(context);  //这里需要用到context, 从哪里拿?  getContext()
        textView.setTextSize(23);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("正文数据被初始化了");
        textView.setText("正文页面");
    }
}
