package com.example.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//这个是基类  left 和 content Fragment 会继承自这个类
public abstract class BaseFragment extends Fragment { //这里继承的是 V4 包里面的 Fragment

    /// 因为这些东西都是要放到 MainActivity里面,所以,这个类型可以直接写成 Activity
    public Activity context;


    // 这个类写完了之后需重写下面这三个方法   onCreate   onCreateView   onActivityCreated

    /**
     * 当 Fragment 被创建的时候回回调这个方法  Fragment 是系统帮我们创建的
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();  //取得当前的上下文,

    }

    /**
     * 当视图被创建的时候回调
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return 创建了视图
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //这个方法 比 onActivityCreated 优先执行

        //return super.onCreateView(inflater, container, savedInstanceState); 这一句就不要了,返回自己创建的对象,重新写一个方法
        return initView();

    }

    /**
     * 这是一个抽象的方法  如果一个类里面有抽象方法, 那么这个类也应该被修饰成抽象类  抽象方法不能有方法体, 就是说没有大括号,只有定义
     * 让子类实现自己的视图达到自己特有的效果
     *
     * @return
     */
    public abstract View initView();

    /**
     * 当Activity被创建之后回调
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData(); //这个方法用于给视图里写入数据
    }

    /**
     * 1.如果子页面没有数据,可以去联网获取数据,并且绑定到 initView 初始化的视图上
     * 2.如果有数据,就直接绑定到 initView 初始化的视图上
     */
    public void initData() {  //这方法让子类重写

    }

}
