package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.beijingnews.R;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * 刷新控件的高度
     */
    private int pullDownRefreshHeight;


    /**
     * 下拉刷新
     * 大写  command + shift + u
     */
    public static final int PULL_DOWN_REFRESH = 0;

    /**
     * 手松刷新
     */
    public static final int RELEASE_REFRESH = 1;

    /**
     * 正在刷新
     */
    public static final int REFRESHING = 2;


    /**
     * 当前的刷新状态
     */
    private int CURRENTSTATUS = PULL_DOWN_REFRESH;


    /**
     * 底部的视图,加载更多
     */
    private View footView;

    /**
     * 底部视图的高度
     */
    private int footerViewHeight;


    /**
     * 是否已经加载更多
     */
    private boolean isLoadMore = false;

    /**
     * 顶部轮播图部分
     */
    private View topNewsView;


    /**
     * listView在Y轴上的坐标
     */
    private int listViewOnScreenY = -1;


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
        initAnimation();
        initFootView(context);
    }

    private void initFootView(Context context) {

        footView = View.inflate(context, R.layout.refresh_footer, null);
        footView.measure(0, 0); //先测量
        footerViewHeight = 500; // footView.getMeasuredHeight();    //然后拿到测量的数值

        footView.setPadding(0, -footerViewHeight, 0, 0);

        //添加底部的视图
        addFooterView(footView);


        //监听listView滚动,滚动到最底部的时候,去干活
        setOnScrollListener(new MyOnScrollListener());

    }

    /**
     * 添加顶部轮播图
     *
     * @param topNewsView
     */
    public void addTopNewsView(View topNewsView) {
        if (topNewsView != null) {
            this.topNewsView = topNewsView;
            headerView.addView(topNewsView);
        }
    }


    class MyOnScrollListener implements OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

            //当静止  或者 是惯性滚动的时候,
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE || scrollState == OnScrollListener.SCROLL_STATE_FLING) {
                //并且是最后一条可见,
                if (getLastVisiblePosition() >= getCount() - 1) {

                    //显示加载更多的布局
                    footView.setPadding(8, 8, 8, 8);  //这里写 8  是因为在布局的时候, padding 设置的值是 8

                    //状态改变
                    isLoadMore = true;

                    //回调接口
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onLoadMore();
                    }
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }


    private Animation upAnimation;

    private Animation downAnimation;

    private void initAnimation() {

        upAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180, -360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);

    }

    private void initHeadView(Context context) {

        headerView = (LinearLayout) View.inflate(context, R.layout.refresh_header, null);

        ll_pull_down_refresh = headerView.findViewById(R.id.ll_pull_down_refresh);

        iv_arrow = headerView.findViewById(R.id.iv_arrow);

        pb_status = headerView.findViewById(R.id.pb_status);

        tv_status = headerView.findViewById(R.id.tv_status);

        tv_time = headerView.findViewById(R.id.tv_time);


        ll_pull_down_refresh.measure(0, 0); //这里面两个参数传递0, 这两个参数可能对测量时没有影响的
        pullDownRefreshHeight = ll_pull_down_refresh.getMeasuredHeight(); //拿到测量的高度


        //需要得到线性布局的高,把这个高度设置top padding
        ll_pull_down_refresh.setPadding(0, -pullDownRefreshHeight, 0, 0);


        //添加头
        addHeaderView(headerView);  // 这么写也可以 RefreshListView.this.addHeaderView(headerView)

    }


    private float startY = -1;

    /**
     * 重写这个方法.
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:

                //记录其实坐标
                startY = ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = ev.getY();
                }


                //判断顶部轮播图是否完全显示,完全显示才会刷新
                boolean isDisplayTopViews = isDisplayTopoNews();
                if (!isDisplayTopViews) {
                    break;

                }


                if (CURRENTSTATUS == REFRESHING) {
                    break;

                }


                //2.来到新的坐标
                float endY = ev.getY();

                //3.计算滑动的距离
                float distanceY = endY - startY;

                if (distanceY > 0) {  //下拉
                    int paddingTop = (int) (-pullDownRefreshHeight + distanceY);


                    if (paddingTop < 0 && CURRENTSTATUS != PULL_DOWN_REFRESH) {
                        //下拉刷新状态
                        CURRENTSTATUS = PULL_DOWN_REFRESH;
                        //更新状态
                        refreshViewState();

                    } else if (paddingTop > 0 && CURRENTSTATUS != RELEASE_REFRESH) {
                        //手松刷新状态
                        CURRENTSTATUS = RELEASE_REFRESH;
                        //更新状态
                        refreshViewState();

                    }


                    ll_pull_down_refresh.setPadding(0, paddingTop, 0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:

                startY = -1;

                if (CURRENTSTATUS == PULL_DOWN_REFRESH) {

                    ll_pull_down_refresh.setPadding(0, -pullDownRefreshHeight, 0, 0);

                } else if (CURRENTSTATUS == RELEASE_REFRESH) {

                    //正在刷新
                    CURRENTSTATUS = REFRESHING;

                    refreshViewState();

                    ll_pull_down_refresh.setPadding(0, 0, 0, 0);


                    //回调接口
                    if (mOnRefreshListener != null) {  //需要判断这个东西是否为空,否则容易引起崩溃
                        mOnRefreshListener.onPullDownRefresh();
                    }

                }

                break;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 顶部轮播图是否已经完全显示
     */
    private boolean isDisplayTopoNews() {


        if (topNewsView != null) {

            //得到listvuew在屏幕上的坐标
            int[] location = new int[2];

            if (listViewOnScreenY == -1) {
                getLocationOnScreen(location);
                listViewOnScreenY = location[1];
            }

            //2得到顶部轮播图在屏幕上的坐标
            topNewsView.getLocationOnScreen(location);
            int topNewsViewOnScreenY = location[1];

            //if (listViewOnScreenY <= topNewsViewOnScreenY) {
            //    return true;
            //} else {
            //    return false;
            //    }

            return listViewOnScreenY <= topNewsViewOnScreenY;

        } else {

            return true;
        }
    }

    private void refreshViewState() {

        switch (CURRENTSTATUS) {

            case PULL_DOWN_REFRESH:

                iv_arrow.startAnimation(downAnimation);
                tv_status.setText("下拉刷新.....");

                break;
            case RELEASE_REFRESH:

                iv_arrow.startAnimation(upAnimation);
                tv_status.setText("手松刷新.....");

                break;
            case REFRESHING:

                tv_status.setText("正在刷新.....");
                pb_status.setVisibility(VISIBLE);
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(GONE);
                break;
        }
    }

    /**
     * 设置刷新结束 当联网成功和失败的回调
     * 用于刷新状态的还原
     */
    public void setOnRefreshFinish(boolean b) {

        if (isLoadMore) {
            //加载更多
            isLoadMore = false;
            //隐藏副局
            footView.setPadding(0, -footerViewHeight, 0, 0);


        } else {

            //下拉刷新

            tv_status.setText("下拉刷新---");
            CURRENTSTATUS = PULL_DOWN_REFRESH;
            iv_arrow.clearAnimation();
            pb_status.setVisibility(GONE);
            iv_arrow.setVisibility(VISIBLE);


            //隐藏下拉刷新控件
            ll_pull_down_refresh.setPadding(0, -pullDownRefreshHeight, 0, 0);


            if (b) {  //设置最新的更新时间
                tv_time.setText("上次更新时间" + getSystemTime());
            }
        }


    }

    /**
     * 得到当前安卓系统的时间
     */
    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }


    //定义接口实现刷新回调
    public interface OnRefreshListener {

        /**
         * 当下拉刷新的时候回调这个方法
         */
        public void onPullDownRefresh();


        /**
         * 当上拉加载的时候回调这个方法
         */
        public void onLoadMore();

    }


    private OnRefreshListener mOnRefreshListener;


    /**
     * 设置监听刷新  由外籍设置
     */
    public void setOnRefreshListener(OnRefreshListener l) {
        this.mOnRefreshListener = l;

    }


}






































