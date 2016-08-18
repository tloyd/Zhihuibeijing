package cc.springwind.zhihuibeijing.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import cc.springwind.zhihuibeijing.MainActivity;
import cc.springwind.zhihuibeijing.R;
import cc.springwind.zhihuibeijing.base.BasePager;
import cc.springwind.zhihuibeijing.base.impl.pager.GovPager;
import cc.springwind.zhihuibeijing.base.impl.pager.HomePager;
import cc.springwind.zhihuibeijing.base.impl.pager.NewsPager;
import cc.springwind.zhihuibeijing.base.impl.pager.ServicePager;
import cc.springwind.zhihuibeijing.base.impl.pager.SettingPager;

/**
 * Created by HeFan on 2016/8/13.
 */
public class MainContentFragment extends BaseFragment {

    private ViewPager vpContentFmc;
    private RadioGroup rgTab;
    private ArrayList<BasePager> mPagerList;

    @Override
    public View getView() {
        View view = View.inflate(mActivity, R.layout.fragment_main_content, null);
        vpContentFmc = (ViewPager) view.findViewById(R.id.vp_content_fmc);
        rgTab = (RadioGroup) view.findViewById(R.id.rg_tab);
        return view;
    }

    @Override
    public void initData() {
        mPagerList = new ArrayList<>();

        mPagerList.add(new HomePager(mActivity));
        mPagerList.add(new NewsPager(mActivity));
        mPagerList.add(new ServicePager(mActivity));
        mPagerList.add(new GovPager(mActivity));
        mPagerList.add(new SettingPager(mActivity));

        vpContentFmc.setAdapter(new ContentAdapter());
        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home_fmc:
                        vpContentFmc.setCurrentItem(0, false);
                        break;
                    case R.id.rb_news_fmc:
                        vpContentFmc.setCurrentItem(1, false);
                        break;
                    case R.id.rb_service_fmc:
                        vpContentFmc.setCurrentItem(2, false);
                        break;
                    case R.id.rb_gov_fmc:
                        vpContentFmc.setCurrentItem(3, false);
                        break;
                    case R.id.rb_setting_fmc:
                        vpContentFmc.setCurrentItem(4, false);
                        break;
                }
            }
        });

        mPagerList.get(0).initData();
        setSlidingMenuEnable(false);

        vpContentFmc.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPagerList.get(position).initData();

                if (position == 0 || position == mPagerList.size() - 1) {
                    setSlidingMenuEnable(false);
                } else {
                    setSlidingMenuEnable(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    public NewsPager getNewsPager() {
        return (NewsPager) mPagerList.get(1);
    }

    class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagerList.get(position);
            View view = pager.mContentView;
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
