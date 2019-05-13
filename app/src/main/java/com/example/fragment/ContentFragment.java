package com.example.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.example.base.BaseFragment;
import com.example.base.BasePager;
import com.example.beijingnews.R;
import com.example.pager.GovaffairPager;
import com.example.pager.HomePager;
import com.example.pager.NewsCenterPager;
import com.example.pager.SettingPager;
import com.example.pager.SmartServicePager;
import com.example.utils.LogUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

// 去掉不必要的包 快捷键  Ctrl+Alt+O

public class ContentFragment extends BaseFragment {


    //2.注解的方式初始化控件
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;  //用注解的时候, viewPager 这个名字可以任意改 上面写注解,下面写属性


    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;


    /**
     * 装载页面的集合
     */
    private ArrayList<BasePager> basePagers;


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


        basePagers = new ArrayList<BasePager>();
        basePagers.add(new HomePager(context));         //添加主页面
        basePagers.add(new NewsCenterPager(context));   //新闻中心页面
        basePagers.add(new SmartServicePager(context)); //智慧服务页面
        basePagers.add(new GovaffairPager(context));    //政要页面
        basePagers.add(new SettingPager(context));      //设置页面


        //设置默认选中首页
        rg_main.check(R.id.rb_home);

    }
}




























