package com.example.menudetailpager.tabledetailpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

public class TabDetailPager extends MenuDetailBasePager {

    private final NewsCenterPagerBean.DataBean.ChildrenBean childrenBean;

    private ViewPager viewpager;

    private TextView tv_title;

    private LinearLayout ll_point_group;

    private ListView listview;


    /**
     * xUtils 也需要设置默认的图片 这个是设置选项
     */
    private ImageOptions imageOptions;

    ///顶部轮播图部分的数据
    private List<TabDetailPagerBean.TopnewsBean> topNews;

    /**
     * 下方Listview 的数据集合
     */
    private List<TabDetailPagerBean.NewsBean> news;

    /**
     * 列表的适配器
     */
    private TabDetailPagerListAdapter adapter;


    public TabDetailPager(Context context, NewsCenterPagerBean.DataBean.ChildrenBean childrenBean) {
        super(context);

        this.childrenBean = childrenBean;

        //设置默认的图片 的一系列参数
        imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(context, 100), DensityUtil.dip2px(context, 100)).setRadius(DensityUtil.dip2px(context, 5)).setCrop(true).setImageScaleType(ImageView.ScaleType.CENTER_CROP).setLoadingDrawableId(R.drawable.news_pic_default).setFailureDrawableId(R.drawable.news_pic_default).build();

    }

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.tabdetail_pager, null);
        listview = view.findViewById(R.id.listview);


        View topNewsView = View.inflate(context, R.layout.topnews, null);
        viewpager = topNewsView.findViewById(R.id.viewpager);
        tv_title = topNewsView.findViewById(R.id.tv_title);
        ll_point_group = topNewsView.findViewById(R.id.ll_point_group);


        //把顶部轮播图部分视图,以头的方式添加到ListView中
        listview.addHeaderView(topNewsView);   //UITableView 的 Header

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


        //设置listview的适配器
        news = bean.getNews();


        //设置listview的适配器
        adapter = new TabDetailPagerListAdapter();
        listview.setAdapter(adapter);

    }

    /**
     * 适配器
     */
    class TabDetailPagerListAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {

            //返回一个视图

            return null;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {   //这个函数的作用就是返回 cell  iOS 里面的

            ViewHolder viewHolder;

            if (convertView == null) {

                convertView = View.inflate(context, R.layout.item_tabdetail_pager, null);

                viewHolder = new ViewHolder();
                viewHolder.iv_icon = convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
                viewHolder.tv_time = convertView.findViewById(R.id.tv_time);

                convertView.setTag(viewHolder);  //设置Tag保存这个东西

            } else {

                viewHolder = (ViewHolder) convertView.getTag();
            }


            //根据位置得到数据
            TabDetailPagerBean.NewsBean newsBean = news.get(position);

            viewHolder.tv_title.setText(newsBean.getTitle());

            viewHolder.tv_time.setText(newsBean.getPubdate());


            //x.image().bind(viewHolder.iv_icon, newsBean.getListimage(),imageOptions); //加上了图片的默认设置参数


            //另一种图片的请求方式 Glide
            Glide.with(context).load(newsBean.getListimage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.iv_icon);


            return convertView;
            //return null;
        }
    }

    //然后就是写一个Viewholder  这个class 要使用 static
    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;
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
