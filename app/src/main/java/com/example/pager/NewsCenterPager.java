package com.example.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.activitys.MainActivity;
import com.example.base.BasePager;
import com.example.domain.NewsCenterPagerBean;
import com.example.fragment.LeftmenuFragment;
import com.example.utils.Constants;
import com.example.utils.LogUtil;
import com.example.utils.YuencyFakeDataTool;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class NewsCenterPager extends BasePager {


    /**
     * 左侧菜单对应的数据集合
     */
    private List<NewsCenterPagerBean.DataBean> data;


    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();


        // 显示左上角的按钮图标
        ib_menu.setVisibility(View.VISIBLE);


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

                String fakeJson = YuencyFakeDataTool.getJson("newscenter.json", context);
                LogUtil.e("使用三方库联网请求成功!" + fakeJson + "转入自定义数据进行解析");

                processData(fakeJson);
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

    /**
     * 解析数据和显示数据
     *
     * @param result
     */
    private void processData(String result) {

        //这里导入了一个jar包, 把这个包复制,然后粘贴到libs文件夹上面,然后右键,选择 add as library

        NewsCenterPagerBean bean = new Gson().fromJson(result, NewsCenterPagerBean.class);  //创建一个 Gson 对象, 然后使用 fromJson ,然后传入参数, json 字符串 和 javabean

        String title = bean.getData().get(0).getChildren().get(1).getTitle();

        data = bean.getData();

        //在这里解析了新闻数据,要在这里把新闻数据传递到 左侧菜单,  先要找到 这个类所在的Activity,然后通过Activity 使用tag 来找到 fragment, 传递数据
        MainActivity mainActivity = (MainActivity) context;

        //得到左侧菜单
        LeftmenuFragment leftmenuFragment = mainActivity.getLeftmenuFragment();

        //把数据传递给左侧菜单
        leftmenuFragment.setData(data);

        LogUtil.e("解析得到bean的标题" + title);

    }


}




































