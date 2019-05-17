package com.example.menudetailpager.tabledetailpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.base.MenuDetailBasePager;
import com.example.beijingnews.R;
import com.example.domain.NewsCenterPagerBean;
import com.example.domain.TabDetailPagerBean;
import com.example.utils.CacheUtils;
import com.example.utils.Constants;
import com.example.utils.DensityUtil;
import com.example.utils.LogUtil;
import com.example.utils.YuencyFakeDataTool;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class TabDetailPager extends MenuDetailBasePager {

    private final NewsCenterPagerBean.DataBean.ChildrenBean childrenBean;

    private ViewPager viewpager;

    private TextView tv_title;

    private LinearLayout ll_point_group;

    private ListView listview;

    ///顶部轮播图部分的数据
    private List<TabDetailPagerBean.TopnewsBean> topNews;


    public TabDetailPager(Context context, NewsCenterPagerBean.DataBean.ChildrenBean childrenBean) {
        super(context);

        this.childrenBean = childrenBean;
    }

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.tabdetail_pager, null);


        viewpager = view.findViewById(R.id.viewpager);

        tv_title = view.findViewById(R.id.tv_title);

        ll_point_group = view.findViewById(R.id.ll_point_group);

        listview = view.findViewById(R.id.listview);


        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //把之前缓存的数据取出来
        String savedJson = CacheUtils.getString(context, Constants.NEWSCENTER_DETAIL_PAGER);
        if (!TextUtils.isEmpty(savedJson)) {
            processData(savedJson);  //解析数据
        }

        getDataFromNet();
    }

    private void getDataFromNet() {

        RequestParams params = new RequestParams(Constants.NEWSCENTER_DETAIL_PAGER);

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                // 这里弄个假数据数据
                String fakeJson = YuencyFakeDataTool.getJson("tabdetailpagerbean.json", context);

                //缓存数据
                CacheUtils.putString(context, Constants.NEWSCENTER_DETAIL_PAGER, fakeJson);

                processData(fakeJson);  //解析数据

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 解析数据
     */
    private void processData(String jsonString) {

        TabDetailPagerBean bean = new Gson().fromJson(jsonString, TabDetailPagerBean.class);

        LogUtil.e("*** 打印请求得到的数据 ****************************" + bean.getTopnews().get(1).getTitle());

        topNews = bean.getTopnews();

        //设置Viewpager的适配器
        viewpager.setAdapter(new TabDetailPagerTopNewsAdapter());

        //添加红点,
        addPoint();

        //监听页面的改变 设置红点变化和文本变化
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
        tv_title.setText(topNews.get(prePosition).getTitle());  //默认的时候后显示 第 0 个, 这个需要提前写,因为不在


    }

    /**
     * 添加红点
     */
    private void addPoint() {

        ll_point_group.removeAllViews();  //移除所有的子控件

        for (int i = 0; i < topNews.size(); i++) {

            ImageView imageView = new ImageView(context);

            imageView.setBackgroundResource(R.drawable.point_selector);  //设置背景选择器

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 8), DensityUtil.dip2px(context, 8));

            if (i == 0) {
                imageView.setEnabled(true);
            } else {
                imageView.setEnabled(false);
                params.leftMargin = DensityUtil.dip2px(context, 8);
            }

            imageView.setLayoutParams(params);

            ll_point_group.addView(imageView);
        }
    }

    /**
     * 记录之前的点的位置
     */
    private int prePosition = 0;

    /// 适配器的监听类,用于更改轮播图的文本
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            //设置文本
            String title = topNews.get(i).getTitle();
            tv_title.setText(title);

            //红点高亮 把之前的变成灰色,把当前的设置成为高亮
            ll_point_group.getChildAt(prePosition).setEnabled(false);
            ll_point_group.getChildAt(i).setEnabled(true);
            prePosition = i;

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }


    class TabDetailPagerTopNewsAdapter extends PagerAdapter {


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {  //差不多就是一个banner图,

            ImageView imageView = new ImageView(context);
            //这是默认图片
            imageView.setBackgroundResource(R.drawable.home_scroll_default);
            //拉伸图片 X Y 轴都拉伸
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            container.addView(imageView); // 图片添加到容器(viewpager)中

            TabDetailPagerBean.TopnewsBean bean = topNews.get(position);

            String url = bean.getTopimage();

            //联网请求图片
            x.image().bind(imageView, url);  // 这个东西就相当于 SDWebImage

            return imageView;
        }

        @Override
        public int getCount() {
            return topNews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            //super.destroyItem(container, position, object);
        }
    }


}
