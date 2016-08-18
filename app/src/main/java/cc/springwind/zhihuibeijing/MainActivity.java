package cc.springwind.zhihuibeijing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import cc.springwind.zhihuibeijing.fragment.LeftMenuFragment;
import cc.springwind.zhihuibeijing.fragment.MainContentFragment;

/**
 * Created by HeFan on 2016/8/12.
 */
public class MainActivity extends SlidingFragmentActivity {

    public static final String TAG_LEFT_MENU = "TAG_LEFT_MENU";
    public static final String TAG_MAIN = "TAG_MAIN";
    private FragmentTransaction mFt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBehindContentView(R.layout.menu_left);

        SlidingMenu menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN); //设置触摸模式为全屏触摸
        menu.setBehindOffset(650);
        menu.setFadeDegree(0.35f);

        initFragment();
    }

    private void initFragment() {
        mFt = getSupportFragmentManager().beginTransaction();
        mFt.replace(R.id.fl_menu_lm, new LeftMenuFragment(), TAG_LEFT_MENU);
        mFt.replace(R.id.fl_main_am, new MainContentFragment(), TAG_MAIN);
        mFt.commit();
    }

    /**
     * @return 返回侧边栏的Fragment
     */
    public LeftMenuFragment getLeftMenuFragment() {
        return (LeftMenuFragment) getSupportFragmentManager().findFragmentByTag(TAG_LEFT_MENU);
    }

    /**
     * @return 返回主页面的Fragment
     */
    public MainContentFragment getMainContentFragment() {
        return (MainContentFragment) getSupportFragmentManager().findFragmentByTag(TAG_MAIN);
    }
}
