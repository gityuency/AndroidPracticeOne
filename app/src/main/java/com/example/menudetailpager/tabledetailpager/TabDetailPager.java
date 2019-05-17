package com.example.menudetailpager.tabledetailpager;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.example.base.MenuDetailBasePager;
import com.example.beijingnews.R;
import com.example.domain.NewsCenterPagerBean;
import com.example.domain.TabDetailPagerBean;
import com.example.utils.CacheUtils;
import com.example.utils.Constants;
import com.example.utils.LogUtil;
import com.example.utils.YuencyFakeDataTool;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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

    }
}
