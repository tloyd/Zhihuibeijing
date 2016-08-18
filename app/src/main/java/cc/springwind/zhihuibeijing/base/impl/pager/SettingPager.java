package cc.springwind.zhihuibeijing.base.impl.pager;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cc.springwind.zhihuibeijing.R;
import cc.springwind.zhihuibeijing.base.BasePager;

/**
 * Created by HeFan on 2016/8/13.
 */
public class SettingPager extends BasePager {
    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        TextView textView = new TextView(mActivity);
        textView.setText("设置");
        textView.setTextSize(32);
        textView.setTextColor(mActivity.getResources().getColor(R.color.colorRed));
        textView.setGravity(Gravity.CENTER);
        flContentBp.addView(textView);

        tvTitle.setText("设置");
        ibMenu.setVisibility(View.GONE);
    }
}
