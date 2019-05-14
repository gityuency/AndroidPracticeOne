package com.example.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.example.base.BaseFragment;
import com.example.domain.NewsCenterPagerBean;
import com.example.utils.LogUtil;

import java.util.List;

// 左侧菜单的fragment
public class LeftmenuFragment extends BaseFragment {

    private TextView textView;

    private List<NewsCenterPagerBean.DataBean> data;

    @Override
    public View initView() {

        LogUtil.e("左侧菜单页面被初始化了");

        textView = new TextView(context);  //这里需要用到context, 从哪里拿?  getContext()
        textView.setTextSize(23);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("左侧菜单数据被初始化了");
        textView.setText("菜单页面");
    }

    /**
     * 接受数据
     * @param data
     */
    public void setData(List<NewsCenterPagerBean.DataBean> data) {

        this.data = data;

        for (int i = 0; i<data.size(); i++ ){
            LogUtil.e("循环打印数组 == " + data.get(i).getTitle());
        }
    }
}
