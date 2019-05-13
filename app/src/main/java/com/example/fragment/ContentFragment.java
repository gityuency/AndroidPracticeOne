package com.example.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.example.base.BaseFragment;
import com.example.beijingnews.R;
import com.example.utils.LogUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

// 去掉不必要的包 快捷键  Ctrl+Alt+O

public class ContentFragment extends BaseFragment {


    //2.注解的方式初始化控件
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;  //用注解的时候, viewPager 这个名字可以任意改 上面写注解,下面写属性


    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;


    @Override
    public View initView() {

        LogUtil.e("正文页面被初始化了");

        View view = View.inflate(context, R.layout.content_fragment, null);

        //传统的初始化方法
        //viewPager = view.findViewById(R.id.viewpager);
        //rg_main = view.findViewById(R.id.rg_main);

        //叫做使用注解的方式来初始化控件
        //1.把视图注入到框架中,让 ContentFragment.this 和 view 关联起来
        x.view().inject(ContentFragment.this, view);



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
