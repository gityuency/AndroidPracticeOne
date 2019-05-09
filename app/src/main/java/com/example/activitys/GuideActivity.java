package com.example.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.beijingnews.R;

import java.util.ArrayList;

/**
 * The type Guide activity.
 */
public class GuideActivity extends Activity {

    private ViewPager viewpager;
    private Button btn_start_main;
    private LinearLayout ll_point_group;

    private ArrayList<ImageView> imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewpager = findViewById(R.id.viewpager);
        btn_start_main = findViewById(R.id.btn_start_main);
        ll_point_group = findViewById(R.id.ll_point_group);

        //准备数据
        int[] ids = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3,};

        imageViews = new ArrayList<ImageView>();

        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            //设置背景
            imageView.setBackgroundResource(ids[i]);
            //添加到集合中
            imageViews.add(imageView);
        }


        //设置viewpager的适配器
        viewpager.setAdapter(new MyPagerAdapter());


    }


    /**
     * The type My pager adapter.
     */
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
