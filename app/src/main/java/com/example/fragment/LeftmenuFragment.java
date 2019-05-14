package com.example.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.activitys.MainActivity;
import com.example.base.BaseFragment;
import com.example.beijingnews.R;
import com.example.domain.NewsCenterPagerBean;
import com.example.utils.DensityUtil;
import com.example.utils.LogUtil;

import java.util.List;

// 左侧菜单的fragment
public class LeftmenuFragment extends BaseFragment {

    private ListView listView;

    private List<NewsCenterPagerBean.DataBean> data;

    private LeftMenuFragmentAdapter adapter;

    /**
     * 上一次点击的位置
     */
    private int prePosition;

    @Override
    public View initView() {

        LogUtil.e("左侧菜单页面被初始化了");


        listView = new ListView(context);
        listView.setPadding(0, DensityUtil.dip2px(context, 40), 0, 0);
        listView.setDividerHeight(0);                        //设置分割线高度是0, 这样就是隐藏了分割线   对比iOS隐藏分割线
        listView.setCacheColorHint(Color.TRANSPARENT);       // 点击之后取消变暗, 对比iOS取消cell默认选中样式
        listView.setSelector(android.R.color.transparent);   //这里取消点击的时候黄色高亮,  设置按下 listview 的item 不变色

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //1.记录点击的位置,变成红色
                prePosition = position;
                adapter.notifyDataSetChanged(); //这个就是刷新的方法  getCount() -> getView()    //tableview.reloadData();

                //2.关闭左侧菜单
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();  //在java里面,一个东西的显示与关闭,使用 toggle 这个单词  开就关, 关就开


                //3.切换到对应的详情页面

            }
        });



        return listView;
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("左侧菜单数据被初始化了");
    }

    /**
     * 接受数据
     *
     * @param data
     */
    public void setData(List<NewsCenterPagerBean.DataBean> data) {

        this.data = data;

        for (int i = 0; i < data.size(); i++) {
            LogUtil.e("循环打印数组 == " + data.get(i).getTitle());
        }


        adapter = new LeftMenuFragmentAdapter();

        //设置适配器,这里有数据
        listView.setAdapter(adapter);

    }


    class LeftMenuFragmentAdapter extends BaseAdapter {

        /// 这个方法返回个数
        @Override
        public int getCount() {
            return data.size();
        }

        /// 这个方法返回视图
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) View.inflate(context, R.layout.item_leftmenu, null);   // 这个layout是一个 TextView, 所以这里需要强转.
            textView.setText(data.get(position).getTitle());
            
            textView.setEnabled(position == prePosition);  //将当前点击的item设置为红色

            return textView;
        }


        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }
}































