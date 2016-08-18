package cc.springwind.zhihuibeijing.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import cc.springwind.zhihuibeijing.MainActivity;
import cc.springwind.zhihuibeijing.R;

/**
 * Created by HeFan on 2016/8/13.
 */
public class BasePager {

    protected Activity mActivity;
    public final View mContentView;
    protected ImageButton ibMenu;
    protected FrameLayout flContentBp;
    protected TextView tvTitle;

    public BasePager(Activity activity) {
        mActivity = activity;
        mContentView = initView();
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.base_pager, null);

        ibMenu = (ImageButton) view.findViewById(R.id.ib_menu);
        flContentBp = (FrameLayout) view.findViewById(R.id.fl_content_bp);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);

        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        return view;
    }

    public void initData() {
    }

    /**
     * 开关侧边栏
     */
    protected void toggle() {
        MainActivity mainUI = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        slidingMenu.toggle();
    }
}
