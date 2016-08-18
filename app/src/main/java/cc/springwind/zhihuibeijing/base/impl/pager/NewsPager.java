package cc.springwind.zhihuibeijing.base.impl.pager;

import android.app.Activity;
import android.view.View;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import cc.springwind.zhihuibeijing.MainActivity;
import cc.springwind.zhihuibeijing.base.BaseMenuDetailPager;
import cc.springwind.zhihuibeijing.base.BasePager;
import cc.springwind.zhihuibeijing.base.impl.menu.InteraDetailPager;
import cc.springwind.zhihuibeijing.base.impl.menu.NewsMenuDetailPager;
import cc.springwind.zhihuibeijing.base.impl.menu.PhotosDetailPager;
import cc.springwind.zhihuibeijing.base.impl.menu.TopicDetailPager;
import cc.springwind.zhihuibeijing.domain.NewsBean;
import cc.springwind.zhihuibeijing.fragment.LeftMenuFragment;
import cc.springwind.zhihuibeijing.global.Constants;
import cc.springwind.zhihuibeijing.utils.CacheUtil;
import cc.springwind.zhihuibeijing.utils.LogUtil;

/**
 * Created by HeFan on 2016/8/13.
 */
public class NewsPager extends BasePager {

    private NewsBean mBean;
    private String json;
    private ArrayList<BaseMenuDetailPager> mDetailPagerList;

    public NewsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        ibMenu.setVisibility(View.VISIBLE);

        json = CacheUtil.getCache(mActivity, Constants.CATEGORIES_URL);
        if (json.isEmpty()) {
            getDataFromServer();
        } else {
            processData(json);
        }
    }

    private void getDataFromServer() {
        x.http().get(new RequestParams(Constants.CATEGORIES_URL), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                json = result;
                processData(json);
                CacheUtil.setCache(mActivity, Constants.CATEGORIES_URL, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.log("TAG-->>:", this, "onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.log("TAG-->>:", this, "onCancelled");
            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void processData(String json) {
        Gson gson = new Gson();
        mBean = gson.fromJson(json, NewsBean.class);

        MainActivity mainActivity = (MainActivity) mActivity;
        LeftMenuFragment fragment = mainActivity.getLeftMenuFragment();

        fragment.setMenuData(mBean.data);

        mDetailPagerList = new ArrayList<>();
        mDetailPagerList.add(new NewsMenuDetailPager(mActivity, mBean.data.get(0).children));
        mDetailPagerList.add(new TopicDetailPager(mActivity));
        mDetailPagerList.add(new PhotosDetailPager(mActivity));
        mDetailPagerList.add(new InteraDetailPager(mActivity));

        setCurrentDetailPosition(0);
    }

    public void setCurrentDetailPosition(int position) {
        flContentBp.removeAllViews();
        BaseMenuDetailPager pager = mDetailPagerList.get(position);
        flContentBp.addView(pager.mRootView);

        pager.initData();

        tvTitle.setText(mBean.data.get(position).title);
    }
}
