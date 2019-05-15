package com.example.menudetailpager.tabledetailpager;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.base.MenuDetailBasePager;
import com.example.domain.NewsCenterPagerBean;

public class TabDetailPager extends MenuDetailBasePager {

    private final NewsCenterPagerBean.DataBean.ChildrenBean childrenBean;

    private TextView textView;

    public TabDetailPager(Context context, NewsCenterPagerBean.DataBean.ChildrenBean childrenBean) {
        super(context);

        this.childrenBean = childrenBean;
    }

    @Override
    public View initView() {

        textView = new TextView(context);
        textView.setText("不是我的");
        textView.setTextSize(60);
        textView.setGravity(Gravity.CENTER);

        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        
        textView.setText(childrenBean.getTitle());

    }
}
