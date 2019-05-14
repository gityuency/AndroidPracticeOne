package com.example.pager;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.base.BasePager;
import com.example.utils.Constants;
import com.example.utils.LogUtil;
import com.example.utils.YuencyFakeDataTool;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NewsCenterPager extends BasePager {


    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();

        //1.设置标题
        tv_title.setText("新闻中心");

        //2.联网请求,得到数据,创建视图
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);

        //3.把子视图添加到 basepager 中的 framelayout中
        fl_content.addView(textView);

        //4.绑定数据
        textView.setText("新闻中心");


        getDataFromNet();

    }

    /**
     * 使用 xUtils3 联网请求数据  联网请求这个东西是需要配置 网络请求权限的
     */
    private void getDataFromNet() {


        RequestParams params = new RequestParams(Constants.NEWSCENTER_PAGER_URL);  //指定参数


        x.http().get(params, new Callback.CommonCallback<String>() {  //使用 公共的回调 CommonCallback

            @Override
            public void onSuccess(String result) {


                String s = YuencyFakeDataTool.getJson("newscenter.json", context);
                LogUtil.e("使用三方库联网请求成功!" + s);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                LogUtil.e("使用三方库联网请求 失败!" + ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {

                LogUtil.e("使用三方库联网请求 取消!" + cex.getMessage());


            }

            @Override
            public void onFinished() {

                LogUtil.e("使用三方库联网请求 结束!");
            }

        });

    }


}




































