package com.example.beijingnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.activitys.GuideActivity;
import com.example.activitys.MainActivity;
import com.example.utils.CacheUtils;

public class SplashActivity extends Activity {

    ///静态常量  先连同冒号一起选中字符串, 然后按下 command + option + c 抽取字符串到类
    public static final String START_MAIN = "start_main";
    ///根页面的布局
    private RelativeLayout rl_splahs_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rl_splahs_root = findViewById(R.id.rl_splahs_root);


        //开场的三个动画
        //渐变动画 缩放动画 旋转动画

        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(500); //播放时间
        aa.setFillAfter(true);

        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(500);
        sa.setFillAfter(true);

        RotateAnimation ra = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(500);
        ra.setFillAfter(true);

        AnimationSet set = new AnimationSet(true); //这个地方要写成true 才有动画效果,教程里面写成的是false,但是也有动画效果
        //添加三个动画没有先后顺序 同时播放动画
        set.addAnimation(aa);
        set.addAnimation(ra);
        set.addAnimation(sa);
        set.setDuration(2000); //优先执行这个时间

        rl_splahs_root.startAnimation(set);

        set.setAnimationListener(new MyAnimationListioner());

    }

    ///自定义的类
    class MyAnimationListioner implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

            //判断是否进入过主页面,


            // 使用 option + enter 组合键打开提示,然后直接创建方法
            boolean isStartMain = CacheUtils.getBoolean(SplashActivity.this, START_MAIN);

            Intent intent;

            if (isStartMain) {
                //如果 进入过主页面,直接进入主页面,
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                //如果没有进入过主页面,先进入引导页
                intent = new Intent(SplashActivity.this, GuideActivity.class);
            }

            startActivity(intent);

            finish();

            Toast.makeText(SplashActivity.this, "开场动画播放完成了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


}
