package cc.springwind.zhihuibeijing.base.impl;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

import cc.springwind.zhihuibeijing.R;
import cc.springwind.zhihuibeijing.base.BaseMenuDetailPager;
import cc.springwind.zhihuibeijing.domain.NewsBean;
import cc.springwind.zhihuibeijing.domain.NewsTabBean;
import cc.springwind.zhihuibeijing.global.Constants;
import cc.springwind.zhihuibeijing.utils.CacheUtil;
import cc.springwind.zhihuibeijing.utils.LogUtil;

/**
 * @author: HeFan
 * @date: 2016/8/15 22:49
 */
public class TabDetailPager extends BaseMenuDetailPager {

    private final NewsBean.NewsChild mNewsChild;
    private View view;
    private String mUrl;
    private ListView lvNewsDpt;
    private ViewPager vpTopDpt;
    private NewsTabBean mbean;
    private ArrayList<NewsTabBean.TopNews> mTopnews;

    public TabDetailPager(Activity activity, NewsBean.NewsChild newsChild) {
        super(activity);
        mNewsChild = newsChild;
        mUrl = Constants.SERVER_URL + mNewsChild.url;
    }

    @Override
    public View initView() {
        // 要给帧布局填充布局对象
        view = View.inflate(mActivity, R.layout.detail_pager_tab, null);
        vpTopDpt = (ViewPager) view.findViewById(R.id.vp_top_dpt);
        lvNewsDpt = (ListView) view.findViewById(R.id.lv_news_dpt);
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
    }

    class TopAdapter extends PagerAdapter {

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
            ImageOptions.Builder builder = new ImageOptions.Builder();
            builder.setFailureDrawableId(R.drawable.topnews_item_default)
                    .setLoadingDrawableId(R.drawable.topnews_item_default);
            x.image().bind(view, mTopnews.get(position).topimage, builder.build());

            container.addView(view);
            return view;
        }
    }
}
