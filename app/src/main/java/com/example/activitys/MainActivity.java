package com.example.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.example.beijingnews.R;
import com.example.fragment.ContentFragment;
import com.example.fragment.LeftmenuFragment;
import com.example.utils.DensityUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    public static final String MAIN_CONTENT_TAG = "main_content_tag";
    public static final String LEFTMENU_TAG = "leftmenu_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Android中布局内容被底部系统导航栏遮挡, 在Activity中调用如下代码就可以解决  系统说,也可以通过 App Theme 来设置
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


        //初始化侧滑菜单
        initSlidingMenu();


        // 初始化Fragment
        initFragment();
    }


    private void initSlidingMenu() {

        //设置左侧菜单
        setBehindContentView(R.layout.activity_leftmenu);

        //3.设置右侧菜单
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setSecondaryMenu(R.layout.activity_rightmenu);

        //4.设置显示的模式 左侧菜单+主页  左侧菜单+主页+右侧菜单  主页+右侧菜单
        slidingMenu.setMode(SlidingMenu.LEFT); //LEFT_RIGHT

        //5.设置滑动模式  滑动边缘 全屏滑动 不可以滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //6.设置主页占据的宽度
        slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this, 200));
    }


    private void initFragment() {

        //1.得到FragmentManager 使用V4的  getSupportFragmentManager();   不要用这个: getFragmentManager()
        FragmentManager fm = getSupportFragmentManager();

        //2.开启事务
        FragmentTransaction ft = fm.beginTransaction();

        //3.替换  这个new出来的 ContentFragment 在当前Activity里面, 所以这里面我们定义的属性 public Activity context; 是可以取到的
        ft.replace(R.id.fl_main_content, new ContentFragment(), MAIN_CONTENT_TAG);  //主页
        ft.replace(R.id.fl_leftmenu, new LeftmenuFragment(), LEFTMENU_TAG);  //左侧菜单

        //4.提交
        ft.commit();

    }

    /**
     * 得到左侧菜单的 Fragment
     */
    public LeftmenuFragment getLeftmenuFragment() {

        //FragmentManager fragmentManager = getSupportFragmentManager();
        //return (LeftmenuFragment) fragmentManager.findFragmentByTag(LEFTMENU_TAG);

        return (LeftmenuFragment) getSupportFragmentManager().findFragmentByTag(LEFTMENU_TAG);
    }

    /**
     * 得到正文的fragment
     * @return
     */
    public ContentFragment getContentFragment() {

        return (ContentFragment) getSupportFragmentManager().findFragmentByTag(MAIN_CONTENT_TAG);
    }
}





























