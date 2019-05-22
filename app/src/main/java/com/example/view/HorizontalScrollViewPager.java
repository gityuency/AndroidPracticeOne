package com.example.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 水平方向滑动的滚动式图
 */
public class HorizontalScrollViewPager extends ViewPager {


    public HorizontalScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public HorizontalScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    //判断滑动方向, 在 X 和 Y 轴 在滑动的绝对值谁大,就是在什么方向滑动

    /**
     * 起始坐标 X
     */
    private float startX;

    /**
     * 起始坐标 Y
     */
    private float startY;


    /**
     * 顶部新闻轮播图事件处理 要在  dispatchTouchEvent 方法中并且在按下的时候做  getParent().requestDisallowInterceptTouchEvent(true);
     *
     * 竖直方向滑动 getParent().requestDisallowInterceptTouchEvent(true);
     * 水平方向滑动
     * 1 - 当我们滑动到Viewpage的第0个页面, 并且是从左往右,设置为false , 这样可以滑出左侧菜单
     * 2 - 当我们滑动到viewpager的最后一个页面,并且是从右到左滑动, getParent().requestDisallowInterceptTouchEvent(false);
     * 3 - 其他的情况,设置为true
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        //请求父层视图不拦截当前控件的事件
        getParent().requestDisallowInterceptTouchEvent(true);


        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:

                //记录其实坐标
                startX = ev.getX();
                startY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:

                //来到新的坐标

                float endX = ev.getX();
                float endY = ev.getY();

                //计算偏移量
                float distanceX = endX - startX;
                float distanceY = endY - startY;

                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    //水平方向滑动

                    if (getCurrentItem() == 0 && distanceX > 0) {  //最左边的滑动
                        getParent().requestDisallowInterceptTouchEvent(false);

                    } else if (getCurrentItem() == (getAdapter().getCount() - 1) && distanceX < 0) {  //最右边的滑动
                        getParent().requestDisallowInterceptTouchEvent(false);

                    } else {  //其他中间部分
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }

                } else {
                    //数值方向滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }


                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;

        }


        return super.dispatchTouchEvent(ev);
    }
}
