package cc.springwind.zhihuibeijing.base;

import android.app.Activity;
import android.view.View;

import cc.springwind.zhihuibeijing.MainActivity;

/**
 * @author: HeFan
 * @date: 2016/8/15 13:49
 */
public abstract class BaseMenuDetailPager {
    protected MainActivity mActivity;
    public final View mRootView;

    public BaseMenuDetailPager(Activity activity) {
        mActivity = (MainActivity) activity;
        mRootView = initView();
    }

    public abstract View initView();

    public void initData() {

    }
}
