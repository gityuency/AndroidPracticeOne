package com.example.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.beijingnews.SplashActivity;
import com.example.beijingnews.R;
import com.example.utils.CacheUtils;

import java.util.ArrayList;

/**
 * The type Guide activity.
 */
public class GuideActivity extends Activity {

    private ViewPager viewpager;
    private Button btn_start_main;
    private LinearLayout ll_point_group;

    private ImageView iv_red_point;

    /// 两红点的间距
    private int leftmax;

    private ArrayList<ImageView> imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewpager = findViewById(R.id.viewpager);
        btn_start_main = findViewById(R.id.btn_start_main);
        ll_point_group = findViewById(R.id.ll_point_group);
        iv_red_point = findViewById(R.id.iv_red_point);  //红点的移动就是距离左边的margin动态改变


        //准备数据
        int[] ids = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3,};

        imageViews = new ArrayList<ImageView>();

        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            //设置背景
            imageView.setBackgroundResource(ids[i]);
            //添加到集合中
            imageViews.add(imageView);

            //创建点, 添加到线性布局里面
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);

            // 这里的宽高 单位是像素
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
            if (i != 0) { //不包括第0个, 所有的点距离左边50个像素
                params.leftMargin = 50;
            }
            point.setLayoutParams(params);

            ll_point_group.addView(point);  //添加到线性布局里面

        }

        //设置viewpager的适配器
        viewpager.setAdapter(new MyPagerAdapter());

        //根据view的生命周期, 当视图执行到 onLayout 或者 onDraw 的时候,视图的宽和高,边距就都偶有了
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyGlobalLayoutLinstner());

        //获得屏幕屏幕滑动的百分比
        viewpager.addOnPageChangeListener(new MyPageChangeListener());

        //设置按钮的点击事件
        btn_start_main.setOnClickListener(new MyButtonClickListener());

    }

    //按钮点击事件的类
    class MyButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            //1.保存曾经进入过的参数
            CacheUtils.putBoolean(GuideActivity.this, SplashActivity.START_MAIN, true);

            //2.跳转到主页面
            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(intent);

            //3.关闭引导页面
            finish();

        }
    }


    // 页面滑动的时候,
    class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * 当前页面会毁掉这个方法
         *
         * @param i  当前滑动页面的位置
         * @param v  当前页面滑动的百分比
         * @param i1 滑动的像素
         */
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            //两点间移动的距离 = 屏幕滑动百分比 * 间距

            // 直接这么用就没有计算点滑动的正确位置, 因为当第一页滑动的时候, 百分比这个参数就会 再次从0开始.
            //int leftMargin = (int) (v * leftmax);  //请注意,强制类型转换的运算优先级是大于乘法优先级的,  (int) v * leftmax 这个表达式结果永远是 0

            Log.i("A", "fjdkasljfas");

            //两点间滑动距离对应的坐标 = 原来的其实位置 + 两点间移动的距离
            int leftMargin = (int) (i * leftmax + v * leftmax);
            // parama.leftMargin = 两点间滑动距离对应的坐标

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();

            params.leftMargin = leftMargin;

            iv_red_point.setLayoutParams(params);

        }

        /**
         * 当页面被选中的时候回调的方法
         *
         * @param i 被选中页面对应的位置
         */
        @Override
        public void onPageSelected(int i) {
            //在最后一个页面显示按钮,其余的页面不显示按钮
            if (i == imageViews.size() - 1) {
                btn_start_main.setVisibility(View.VISIBLE);
            } else {
                btn_start_main.setVisibility(View.GONE);
            }
        }

        /**
         * 当viewpager页面滑动状态发生改变的时候 好像是有三种状态
         *
         * @param i
         */
        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }


    //页面布局的时候
    class MyGlobalLayoutLinstner implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {

            Log.i("执行次数", "onGlobalLayout");

            // 这个方法执行不止一次,需要移除, 这个移除的函数貌似对低版本不支持,根据编辑器提示修改成if
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                iv_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this); //这里的 this 值得是 MyGlobalLayoutLinstner.this
            }

            //间距 = 第一个红点距离左边的距离 - 第0个红点距离左边的距离
            leftmax = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();

        }
    }


    //分页视图的需要的代理方法 返回数据源
    class MyPagerAdapter extends PagerAdapter {  //这个类需要重写的方法有四个

        /// 返回数据的总个数
        @Override
        public int getCount() {
            return imageViews.size();
        }

        /**
         * 作用 getView
         *
         * @param container 就是 viewpager
         * @param position  要创建页面的位置
         * @return 返回和创建当前页面的关系值
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;   //在这里可以随我返回  view 或者是 position
            // return  position;
            //return super.instantiateItem(container, position);
        }


        /**
         * @param view 当前创建的视图
         * @param o    上面 instantiateItem 返回的结果
         * @return
         */

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

            //通常将参数进行比较, true 就是同一个值, false 就不是同一个值

            return view == o; //上面的函数直接返回view, 这么写就可以了, 这里的 o 指的就是View

            // return view == imageViews.get(Integer.parseInt((String) o));  如果上面的那个函数返回的结果是 position, 那么这里判断相等就要这么写
        }


        /**
         * 先按下  /** 然后按下回车键, 就可以生成这样的注释
         * 一进来默认创建2个页面,最多创建3个页面
         *
         * @param container 这个就是 viewpager
         * @param position  要销毁页面的位置
         * @param object    要销毁的页面
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            // super.destroyItem(container, position, object);  这里的 super 这一句 要 去掉!! !
            container.removeView((View) object);
        }

    }

}


