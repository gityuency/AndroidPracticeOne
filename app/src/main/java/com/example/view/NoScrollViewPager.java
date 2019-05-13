package com.example.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * 自定义不可以滑动的viewpager
 *
 * 在使用自定义类去替换已经存在的类的时候, 在这个类上面右键,然后选择 copy Reference
 *
 */
public class NoScrollViewPager extends ViewPager {

    /**
     * 通常在代码中实例化的时候用该方法
     * @param context
     */
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    /**
     * 在布局文件中使用该类的时候,实例化该类用该构造方法, 这个方法不能少,少的话会崩溃.
     * @param context
     * @param attrs
     */
    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 重写触摸事件,消费掉
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true; //true: 就是不接受触摸事件
    }
}
