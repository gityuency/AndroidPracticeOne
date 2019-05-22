package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.beijingnews.R;

/**
 * 自定义下拉刷新 ListView
 */
public class RefreshListView extends ListView {


    /**
     * 下拉刷新和顶部轮播图
     */
    private LinearLayout headerView;

    /**
     * 下拉刷新控件
     */
    private View ll_pull_down_refresh;

    /**
     * 箭头
     */
    private ImageView iv_arrow;

    /**
     * 进度条
     */
    private ProgressBar pb_status;

    /**
     * 刷新文字
     */
    private TextView tv_status;

    /**
     * 时间
     */
    private TextView tv_time;

    /// 生成了三个构造方法  然后就是第一个构造方法调用第二个构造方法,第二个构造方法调用第三个构造方法
    public RefreshListView(Context context) {
        //super(context);
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        //super(context, attrs);
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initHeadView(context);
    }

    private void initHeadView(Context context) {

        headerView = (LinearLayout) View.inflate(context, R.layout.refresh_header, null);

        ll_pull_down_refresh = headerView.findViewById(R.id.ll_pull_down_refresh);

        iv_arrow = headerView.findViewById(R.id.iv_arrow);

        pb_status = headerView.findViewById(R.id.pb_status);

        tv_status = headerView.findViewById(R.id.tv_status);

        tv_time = headerView.findViewById(R.id.tv_time);



        //添加头
        addHeaderView(headerView);  // 这么写也可以 RefreshListView.this.addHeaderView(headerView)




    }


}
