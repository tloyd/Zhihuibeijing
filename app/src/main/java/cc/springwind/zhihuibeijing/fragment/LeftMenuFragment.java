package cc.springwind.zhihuibeijing.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import cc.springwind.zhihuibeijing.MainActivity;
import cc.springwind.zhihuibeijing.R;
import cc.springwind.zhihuibeijing.base.impl.pager.NewsPager;
import cc.springwind.zhihuibeijing.domain.NewsBean;

/**
 * Created by HeFan on 2016/8/13.
 */
public class LeftMenuFragment extends BaseFragment {
    private ListView lvMenuFlm;
    private ArrayList<NewsBean.NewsData> mDataList;
    private int mPosition;
    private MenuAdapter mAdapter;

    @Override
    public View getView() {
        View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        lvMenuFlm = (ListView) view.findViewById(R.id.lv_menu_flm);
        return view;
    }

    @Override
    public void initData() {

    }

    /**
     * 传入数据设置侧边栏listview
     *
     * @param data
     */
    public void setMenuData(ArrayList<NewsBean.NewsData> data) {
        mPosition = 0;
        mDataList = data;
        mAdapter = new MenuAdapter();
        lvMenuFlm.setAdapter(mAdapter);
        lvMenuFlm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                mAdapter.notifyDataSetChanged();
                toggle();

                setNewsDetailPager(position);
            }
        });
    }

    private void setNewsDetailPager(int position) {
        MainActivity mainActivity = (MainActivity) mActivity;
        MainContentFragment fragment = mainActivity.getMainContentFragment();
        NewsPager pager = fragment.getNewsPager();
        pager.setCurrentDetailPosition(position);
    }

    /**
     * 关闭侧边栏
     */
    private void toggle() {
        SlidingMenu slidingMenu = ((MainActivity) mActivity).getSlidingMenu();
        slidingMenu.toggle();
    }

    class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public NewsBean.NewsData getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NewsBean.NewsData item = getItem(position);
            View view = View.inflate(mActivity, R.layout.list_item_left_menu, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_menu_lilm);
            textView.setText(item.title);
            if (mPosition == position) {
                textView.setEnabled(true);
            } else {
                textView.setEnabled(false);
            }
            return view;
        }
    }
}
