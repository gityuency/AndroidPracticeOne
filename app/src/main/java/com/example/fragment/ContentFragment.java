package com.example.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.example.base.BaseFragment;
import com.example.beijingnews.R;
import com.example.utils.LogUtil;

// 去掉不必要的包 快捷键  Ctrl+Alt+O

public class ContentFragment extends BaseFragment {

    private ViewPager viewPager;
    private RadioGroup rg_main;

    @Override
    public View initView() {

        LogUtil.e("正文页面被初始化了");

        View view = View.inflate(context, R.layout.content_fragment, null);

        viewPager = view.findViewById(R.id.viewpager);

        rg_main = view.findViewById(R.id.rg_main);

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("正文数据被初始化了");

        //设置默认选中首页
        rg_main.check(R.id.rb_home);

    }
}
