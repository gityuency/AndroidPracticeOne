package com.example.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.activitys.MainActivity;
import com.example.base.BasePager;
import com.example.base.MenuDetailBasePager;
import com.example.domain.NewsCenterBeanByHand;
import com.example.domain.NewsCenterPagerBean;
import com.example.fragment.LeftmenuFragment;
import com.example.menudetailpager.InteracMenuDetailPager;
import com.example.menudetailpager.NewsMenuDetailPager;
import com.example.menudetailpager.PhotosMenuDetailPager;
import com.example.menudetailpager.TopicMenuDetailPager;
import com.example.utils.CacheUtils;
import com.example.utils.Constants;
import com.example.utils.LogUtil;
import com.example.utils.YuencyFakeDataTool;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class NewsCenterPager extends BasePager {


    /**
     * 左侧菜单对应的数据集合
     */
    private List<NewsCenterPagerBean.DataBean> data;


    public NewsCenterPager(Context context) {
        super(context);
    }

    /**
     * 详情页面的集合
     */
    private ArrayList<MenuDetailBasePager> detailBasePagers;

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


        // 先去找缓存, 缓存有数据,先加载缓存里面的数据, 先把UI显示出来,  然后再去网络请求,把请求到的最新的数据再写上去, 这个流程可以接受.


        //在联网请求之前获取网络数据
        String saveJson = CacheUtils.getString(context,Constants.NEWSCENTER_PAGER_URL);
        if (!TextUtils.isEmpty(saveJson)) {    //这里的判断包含了  ""  和  null   的这样的情况?
            processData(saveJson);  // 这个用于加载数据
        }

        getDataFromNet();

    }

    /**
     * 使用 xUtils3 联网请求数据  联网请求这个东西是需要配置 网络请求权限的
     */
    private void getDataFromNet() {


        final RequestParams params = new RequestParams(Constants.NEWSCENTER_PAGER_URL);  //指定参数


        x.http().get(params, new Callback.CommonCallback<String>() {  //使用 公共的回调 CommonCallback

            @Override
            public void onSuccess(String result) {

                String fakeJson = YuencyFakeDataTool.getJson("newscenter.json", context);
                LogUtil.e("使用三方库联网请求成功!" + fakeJson + "转入自定义数据进行解析");


                //缓存数据 使用网址作为了 key
                CacheUtils.putString(context,Constants.NEWSCENTER_PAGER_URL,fakeJson);

                processData(fakeJson);  // 这个用于加载数据

                parseJsonUseAndroidAPI(fakeJson);  //这个是练习用的原生解析
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


        //添加详情页面
        detailBasePagers = new ArrayList<>();  //在new 的时候已经可以不用指定泛型的类型了?
        detailBasePagers.add(new NewsMenuDetailPager(context,data.get(0)));
        detailBasePagers.add(new TopicMenuDetailPager(context));
        detailBasePagers.add(new PhotosMenuDetailPager(context));
        detailBasePagers.add(new InteracMenuDetailPager(context));


        //把数据传递给左侧菜单
        leftmenuFragment.setData(data);

        LogUtil.e("解析得到bean的标题" + title);


    }


    /**
     * 根据位置切换详情页面
     *
     * @param position
     */
    public void switchPager(int position) {

        //1.标题
        tv_title.setText(data.get(position).getTitle());

        //2.移除之前的内容,
        fl_content.removeAllViews(); //


        //3.添加新的内容
        MenuDetailBasePager detailBasePager = detailBasePagers.get(position);
        View rootView = detailBasePager.rootView;
        detailBasePager.initData();  //初始化数据

        fl_content.addView(rootView);

    }


    /**
     * 使用Android系统自带API进行数据解析
     *
     * @param fakeJson
     */
    private void parseJsonUseAndroidAPI(String fakeJson) {

        /*
        // 自定义的对象,自己写一个json的model类
        NewsCenterBeanByHand beanByHand = new Gson().fromJson(fakeJson, NewsCenterBeanByHand.class);
        String title = beanByHand.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("自定义的鹅json **********************" + title);
         */


        NewsCenterBeanByHand bean2 = new NewsCenterBeanByHand();

        try {

            JSONObject object = new JSONObject(fakeJson);  //这里要用大写的 JSONObject. 小写的那个转不了

            // 使用 optInt() 不会报错崩溃,只是会没有内容而已
            int retcode = object.optInt("retcode");    //如果某天服务器不返回这个 retcode 字段, object.getInt("retcode") 这个方法就会崩溃

            bean2.setRetcode(retcode);  //给字段赋值,解析成功

            JSONArray data = object.optJSONArray("data");
            if (data != null && data.length() > 0) {


                List<NewsCenterBeanByHand.DetailPagerData> detailPagerDatas = new ArrayList<>();   // List 是抽象的类, ArrayList 是一个方法可以用来初始化?

                // 设置列表数据
                bean2.setData(detailPagerDatas);


                //for 循环,解析 每一条数据
                for (int i = 0; i < data.length(); i++) {  //这里的data是数组, 有 length  没有 size


                    JSONObject jsonObject = (JSONObject) data.get(i);  //这里也可以用opt, 但是因为上面已经做了空和长度的判断,这里直接用 get 也没问题

                    NewsCenterBeanByHand.DetailPagerData data1 = new NewsCenterBeanByHand.DetailPagerData();

                    detailPagerDatas.add(data1); //添加到集合中

                    int id = jsonObject.optInt("id");
                    data1.setId(id);
                    int type = jsonObject.optInt("type");
                    data1.setType(type);
                    String title = jsonObject.optString("title");
                    data1.setTitle(title);
                    String url = jsonObject.optString("url");
                    data1.setUrl(url);
                    String url1 = jsonObject.optString("url1");
                    data1.setUrl1(url1);
                    String excurl = jsonObject.optString("excurl");
                    data1.setExcurl(excurl);
                    String weekurl = jsonObject.optString("weekurl");
                    data1.setWeekurl(weekurl);


                    JSONArray children = jsonObject.optJSONArray("children");

                    if (children != null && children.length() > 0) {

                        List<NewsCenterBeanByHand.DetailPagerData.ChildrenData> childrenDataList = new ArrayList<>();

                        data1.setChildren(childrenDataList);


                        for (int j = 0; j < children.length(); j++) {

                            JSONObject childrenItem = (JSONObject) data.get(j);

                            NewsCenterBeanByHand.DetailPagerData.ChildrenData childrenData = new NewsCenterBeanByHand.DetailPagerData.ChildrenData();

                            childrenDataList.add(childrenData);

                            int childId = childrenItem.optInt("id");
                            childrenData.setId(childId);
                            int childtype = childrenItem.optInt("type");
                            childrenData.setType(childtype);
                            String childtitle = childrenItem.optString("title");
                            childrenData.setTitle(childtitle);
                            String childurl = childrenItem.optString("url");
                            childrenData.setUrl(childurl);


                        }
                    }

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        bean2.getData().get(1).getChildren().get(1).getTitle();

    }


}




































