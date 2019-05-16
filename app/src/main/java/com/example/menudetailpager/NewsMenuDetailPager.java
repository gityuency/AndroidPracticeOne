package com.example.menudetailpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.base.MenuDetailBasePager;
import com.example.beijingnews.R;
import com.example.domain.NewsCenterPagerBean;
import com.example.menudetailpager.tabledetailpager.TabDetailPager;
import com.example.utils.LogUtil;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * 新闻详情页面
 */
public class NewsMenuDetailPager extends MenuDetailBasePager {


    @ViewInject(R.id.viewpager)  //这里后面不能有分号, 这个意思是这句话没写完?
    private ViewPager viewPager;


    @ViewInject(R.id.tabPageIndicator)
    private TabPageIndicator tabPageIndicator;


    @ViewInject(R.id.ib_tab_next)
    private ImageButton ib_tab_next;


    /**
     * 页签页面的数据集合
     */
    private List<NewsCenterPagerBean.DataBean.ChildrenBean> children;


    /**
     * 页签页面的的集合
     */
    private ArrayList<TabDetailPager> tabDetailPagers;


    public NewsMenuDetailPager(Context context, NewsCenterPagerBean.DataBean dataBean) {
        super(context);

        children = dataBean.getChildren();

    }

    @Override
    public View initView() {

        final View view = View.inflate(context, R.layout.newsmenu_detail_pager, null);   // newsmenu_detail_pager 这个布局文件名字里面不能有大写的字母

        x.view().inject(NewsMenuDetailPager.this, view); //使用这个含有两个参数的方法, 传入一个上下文 和 这个实例化出来的view

        //按钮
        ib_tab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });


        return view;

    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("新闻详情页面被初始化了... .........");

        tabDetailPagers = new ArrayList<>();

        //设置viewpager的是适配器  准备新闻详情页面的数据
        for (int i = 0; i < children.size(); i++) {
            tabDetailPagers.add(new TabDetailPager(context, children.get(i)));
        }

        viewPager.setAdapter(new MyNewsMenuDetailPagerAdapter());


        //这个三方库 TabPageIndicator 一定要在 设置了适配器之后, 才能使用初始化的代码,否则会崩溃    TabPageIndicator 和 ViewPager 关联
        tabPageIndicator.setViewPager(viewPager);

        //主页以后监听页面的变化, TabPageIndicator 监听页面的变化



    }


    class MyNewsMenuDetailPagerAdapter extends PagerAdapter {

        /**
         * 这个函数返回上方 indicator 的标题 这个标题应该是被第三方截获了
         * @param position
         * @return
         */
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            //在这里返回视图
            TabDetailPager tabDetailPager = tabDetailPagers.get(position);
            View rootView = tabDetailPager.rootView;
            tabDetailPager.initData();  //初始化数据
            container.addView(rootView); //不要忘了 添加视图
            return rootView;  //返回这个视图
        }


        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            container.removeView((View) object);  //在这里移除这个

            //super.destroyItem(container, position, object); 这一句注释掉
        }
    }


}
