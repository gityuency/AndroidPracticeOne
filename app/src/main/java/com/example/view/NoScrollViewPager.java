package com.example.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * 自定义不可以滑动的viewpager
 * <p>
 * 在使用自定义类去替换已经存在的类的时候, 在这个类上面右键,然后选择 copy Reference
 */
public class NoScrollViewPager extends ViewPager {

    /**
     * 通常在代码中实例化的时候用该方法
     *
     * @param context
     */
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    /**
     * 在布局文件中使用该类的时候,实例化该类用该构造方法, 这个方法不能少,少的话会崩溃.
     *
     * @param context
     * @param attrs
     */
    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 重写触摸事件,消费掉
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true; //true: 就是不接受触摸事件
    }


    /*

    onInterceptTouchEvent() 的机制总结, 就是

    1.down事件首先会传递到onInterceptTouchEvent()方法
    2.如果该viewGroup的onInterceptTouchEvent()在接受到down事件处理完成后return false, 那么后续的move up 等事件将继续会先传递给该ViewGroup,之后才和down事件一样传递给最终目标的onInterceptTouchEvent()
    3.如果该viewGroup的onInterceptTouchEvent()在接受到down事件处理完成后return true, 那么后续的move up 等事件将不再传递给onInterceptTouchEvent(),而是和down事件一样传递给ViewGroup的onInterceptTouchEvent()处理,注意,目标view将接受不到任何事件.
    4.如果最终需要处理事件的view的onTouchEvent返回了false,那么该事件将被传递至上一层次的onTouchEvent()处理.
    5.如果最终需要处理事件的View的onTouchEvent()返回了true,那么后续事件将可以继续传递给该View的onTouchEvent()事件.

    解决这个问题,

    在 NoScrollViewPager修改和添加方法
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;

    }
}










