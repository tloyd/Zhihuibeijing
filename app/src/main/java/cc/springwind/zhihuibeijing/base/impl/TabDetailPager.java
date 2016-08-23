package cc.springwind.zhihuibeijing.base.impl;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cc.springwind.zhihuibeijing.R;
import cc.springwind.zhihuibeijing.base.BaseMenuDetailPager;
import cc.springwind.zhihuibeijing.domain.NewsBean;
import cc.springwind.zhihuibeijing.domain.NewsTabBean;
import cc.springwind.zhihuibeijing.global.Constants;
import cc.springwind.zhihuibeijing.utils.CacheUtil;
import cc.springwind.zhihuibeijing.utils.LogUtil;
import cc.springwind.zhihuibeijing.view.TopNewsViewPager;

/**
 * @author: HeFan
 * @date: 2016/8/15 22:49
 */
public class TabDetailPager extends BaseMenuDetailPager {

    private final NewsBean.NewsChild mNewsChild;
    private View view;
    private View headerView; // ListView的头布局
    private String mUrl;
    private ListView lvNewsDpt;
    private TopNewsViewPager vpTopDpt;
    private TextView tvTitleDpt;
    private NewsTabBean mbean;
    private ArrayList<NewsTabBean.TopNews> mTopnews;
    private CirclePageIndicator indicatorTopnewsDpt;
    private ArrayList<NewsTabBean.News> mNewsList;
    private NewsAdapter mNewsAdapter;

    public TabDetailPager(Activity activity, NewsBean.NewsChild newsChild) {
        super(activity);
        mNewsChild = newsChild;
        mUrl = Constants.SERVER_URL + mNewsChild.url;
    }

    @Override
    public View initView() {
        // 要给帧布局填充布局对象

        view = View.inflate(mActivity, R.layout.detail_pager_tab, null);
        lvNewsDpt = (ListView) view.findViewById(R.id.lv_news_dpt);
        headerView = View.inflate(mActivity, R.layout.list_item_header, null);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180);
//        headerView.setLayoutParams(params);
//        LayoutInflater lif = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        headerView = lif.inflate(R.layout.list_item_header, lvNewsDpt, false);
        vpTopDpt = (TopNewsViewPager) headerView.findViewById(R.id.vp_top_dpt);
        tvTitleDpt = (TextView) headerView.findViewById(R.id.tv_title_dpt);
        indicatorTopnewsDpt = (CirclePageIndicator) headerView.findViewById(R.id.indicator_topnews_dpt);
        lvNewsDpt.addHeaderView(headerView); // 没数据的时候就要添加了
        return view;
    }

    @Override
    public void initData() {
        LogUtil.error("-->>", "initData");
        String cache = CacheUtil.getCache(mActivity, mUrl);
        if (!cache.isEmpty()) {
            processData(cache);
        }
        getDataFromServer();
    }

    private void getDataFromServer() {
        x.http().get(new RequestParams(mUrl), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                json = result;
                processData(result);
                CacheUtil.setCache(mActivity, mUrl, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                LogUtil.error("-->>", "onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
                LogUtil.error("-->>", "onCancelled");
            }

            @Override
            public void onFinished() {
                LogUtil.error("-->>", "onFinished");
            }
        });
    }

    public void processData(String json) {
        Gson gson = new Gson();
        mbean = gson.fromJson(json, NewsTabBean.class);
        mTopnews = mbean.data.topnews;
        vpTopDpt.setAdapter(new TopAdapter());
        indicatorTopnewsDpt.setViewPager(vpTopDpt);
        indicatorTopnewsDpt.setSnap(true); // 设置快照显示方式
        indicatorTopnewsDpt.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tvTitleDpt.setText(mbean.data.topnews.get(position).title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tvTitleDpt.setText(mTopnews.get(0).title);
        indicatorTopnewsDpt.onPageSelected(0); // 设置在页面初始化时,指示器指示第0个

        mNewsList = mbean.data.news;
        mNewsAdapter = new NewsAdapter();
        lvNewsDpt.setAdapter(mNewsAdapter);

    }

    class NewsAdapter extends BaseAdapter {
        private final ImageOptions.Builder builder;

        public NewsAdapter() {
            builder = new ImageOptions.Builder();
            builder.setFailureDrawableId(R.drawable.topnews_item_default)
                    .setLoadingDrawableId(R.drawable.topnews_item_default);
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public NewsTabBean.News getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_item_news, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            NewsTabBean.News item = getItem(position);
            viewHolder.tvNewsTitleLin.setText(item.title);
            viewHolder.tvNewsDateLin.setText(item.pubdate);
            x.image().bind(viewHolder.ivNewsLin, item.listimage, builder.build());
            return convertView;
        }


    }

    static class ViewHolder {
        @InjectView(R.id.iv_news_lin)
        ImageView ivNewsLin;
        @InjectView(R.id.tv_news_title_lin)
        TextView tvNewsTitleLin;
        @InjectView(R.id.tv_news_date_lin)
        TextView tvNewsDateLin;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    /**
     * 头条滚动新文的适配器
     */
    class TopAdapter extends PagerAdapter {

        private final ImageOptions.Builder builder;

        public TopAdapter() {
            builder = new ImageOptions.Builder();
            builder.setFailureDrawableId(R.drawable.topnews_item_default)
                    .setLoadingDrawableId(R.drawable.topnews_item_default);
        }

        @Override
        public int getCount() {
            return mTopnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(mActivity);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            x.image().bind(view, mTopnews.get(position).topimage, builder.build());
            container.addView(view);
            return view;
        }
    }
}
