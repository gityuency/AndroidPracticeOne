package com.example.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.base.BasePager;

import java.util.ArrayList;

/**
 * 这个类就是把 ContentFragment 里面的适配器类给抽出来, 因为同一个代码文件里的代码过多,
 */
public class ContentFragmentAdapter extends PagerAdapter {

    private final ArrayList<BasePager> basePagers;

    public ContentFragmentAdapter(ArrayList<BasePager> basePagers) {

        this.basePagers = basePagers;
    }

    @Override
    public int getCount() {
        return basePagers.size();
    }

    /**
     * 这个东西就相当于创建每一个view 所以在这里函数里面,我们需要return 出去一个 view
     * <p>
     * 这个viewpager初始化的时候就会默认创建2个视图,在点击的时候回动态创建下一个视图和销毁不需要的视图, 这个方法是经常被调用的
     *
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        BasePager pager = basePagers.get(position);  //各个页面的实例
        View rootView = pager.rootView;   //各个页面

        //调用各个页面的initData方法
        //pager.initData(); //初始化数据  放在这里会有重复调用,重复调用的嫌疑

        //添加到容器中
        container.addView(rootView);

        return rootView; //最后把这个view返回出去
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);

        //super.destroyItem(container, position, object); //删除这句话
    }
}