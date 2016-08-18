package cc.springwind.zhihuibeijing.base.impl.menu;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;

import cc.springwind.zhihuibeijing.MainActivity;
import cc.springwind.zhihuibeijing.R;
import cc.springwind.zhihuibeijing.base.BaseMenuDetailPager;
import cc.springwind.zhihuibeijing.base.impl.TabDetailPager;
import cc.springwind.zhihuibeijing.domain.NewsBean;

/**
 * @author: HeFan
 * @date: 2016/8/15 13:52
 */
@ContentView(R.layout.detail_pager_news_menu)
public class NewsMenuDetailPager extends BaseMenuDetailPager implements View.OnClickListener {

    //    @ViewInject(R.id.vp_new_menu_dpnm)
    private ViewPager vpNewMenuDpnm;
    //    @ViewInject(R.id.indicator_news_menu_dpnm)
    private TabPageIndicator indicator;
    private ImageButton ibIndicatorDpnm;

    private final ArrayList<NewsBean.NewsChild> mChildren;
    private ArrayList<TabDetailPager> mTabPagerList;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsBean.NewsChild> children) {
        super(activity);
        mChildren = children;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.detail_pager_news_menu, null);
        vpNewMenuDpnm = (ViewPager) view.findViewById(R.id.vp_new_menu_dpnm);
        indicator = (TabPageIndicator) view.findViewById(R.id.indicator_news_menu_dpnm);
        ibIndicatorDpnm = (ImageButton) view.findViewById(R.id.ib_indicator_dpnm);
//        x.view().inject(view);
        return view;
    }

    @Override
    public void initData() {
        mTabPagerList = new ArrayList<>();
        for (int i = 0; i < mChildren.size(); i++) {
            TabDetailPager pager = new TabDetailPager(mActivity, mChildren.get(i));
            mTabPagerList.add(pager);
        }

        vpNewMenuDpnm.setAdapter(new MenuDetailAdapter());

        indicator.setViewPager(vpNewMenuDpnm);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    setSlidingMenuEnable(true);
                } else {
                    setSlidingMenuEnable(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ibIndicatorDpnm.setOnClickListener(this);
    }

    /**
     * 设置首页与设置页无法滑动侧边栏
     *
     * @param enable
     */
    private void setSlidingMenuEnable(boolean enable) {
        SlidingMenu slidingMenu = ((MainActivity) mActivity).getSlidingMenu();
        if (enable)
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        else
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_indicator_dpnm:
                vpNewMenuDpnm.setCurrentItem(vpNewMenuDpnm.getCurrentItem() + 1);
                break;
        }
    }

    class MenuDetailAdapter extends PagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            return mChildren.get(position).title;
        }

        @Override
        public int getCount() {
            return mTabPagerList.size();
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
            TabDetailPager pager = mTabPagerList.get(position);
            View view = pager.mRootView;
            container.addView(view);
            pager.initData();
            return view;
        }
    }

}
