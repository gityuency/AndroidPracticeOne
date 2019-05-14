package com.example.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.example.activitys.MainActivity;
import com.example.adapter.ContentFragmentAdapter;
import com.example.base.BaseFragment;
import com.example.base.BasePager;
import com.example.beijingnews.R;
import com.example.pager.GovaffairPager;
import com.example.pager.HomePager;
import com.example.pager.NewsCenterPager;
import com.example.pager.SettingPager;
import com.example.pager.SmartServicePager;
import com.example.utils.LogUtil;
import com.example.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

// 去掉不必要的包 快捷键  Ctrl+Alt+O

public class ContentFragment extends BaseFragment {


    //2.注解的方式初始化控件
    @ViewInject(R.id.viewpager)
    private NoScrollViewPager viewPager;  //用注解的时候, viewPager 这个名字可以任意改 上面写注解,下面写属性


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


        //设置适配器
        viewPager.setAdapter(new ContentFragmentAdapter(basePagers));

        //设置radiogroup的选中状态的监听
        rg_main.setOnCheckedChangeListener(new MyOnCheckChagendListener());

        //监听某个页面被选中,初始化对应页面的数据
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        //设置默认选中首页
        rg_main.check(R.id.rb_home);

        basePagers.get(0).initData();  // 好像是因为 OnPageChangeListener 这个东西是在用户去改变之后才会触发, 所以这里需要 把 initData() 方法调用一次,否则 在首页看不到数据


        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE); //默认也不能滑动

    }


    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        /**
         * 当某个页面被选中的时候,回调的这个方法
         *
         * @param i 被选中页面的位置
         */
        @Override
        public void onPageSelected(int i) {
            basePagers.get(i).initData(); //调用被选中的页面的 initData()
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }


    /**
     * 下方按钮点击的
     */
    class MyOnCheckChagendListener implements RadioGroup.OnCheckedChangeListener {

        /**
         * @param group
         * @param checkedId 被选中的 radiobutton id
         */
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId) {  //对应切换各个页面

                case R.id.rb_home:
                    viewPager.setCurrentItem(0, false);  //fasel 去掉动画效果, true 就是有动画效果
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;

                case R.id.rb_newscenter:
                    viewPager.setCurrentItem(1, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;

                case R.id.rb_smartservice:
                    viewPager.setCurrentItem(2, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;

                case R.id.rb_govaffair:
                    viewPager.setCurrentItem(3, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;

                case R.id.rb_setting:
                    viewPager.setCurrentItem(4, false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
            }
        }
    }

    /**
     * 根据传入的参数 设置 侧滑菜单 是否可以滑动
     * 选中一段代码, 按住  Command + Alt + M 抽取出方法
     */
    private void isEnableSlidingMenu(int touchModel) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchModel);
    }

}




























