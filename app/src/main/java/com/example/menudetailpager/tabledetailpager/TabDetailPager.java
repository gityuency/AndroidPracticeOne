package com.example.menudetailpager.tabledetailpager;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.base.MenuDetailBasePager;
import com.example.beijingnews.R;
import com.example.domain.NewsCenterPagerBean;

public class TabDetailPager extends MenuDetailBasePager {

    private final NewsCenterPagerBean.DataBean.ChildrenBean childrenBean;


    public TabDetailPager(Context context, NewsCenterPagerBean.DataBean.ChildrenBean childrenBean) {
        super(context);

        this.childrenBean = childrenBean;
    }

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.tabdetail_pager, null);

        return view;
    }

    @Override
    public void initData() {
        super.initData();



    }
}
