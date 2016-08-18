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
public class HomePager extends BasePager {
    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        TextView textView = new TextView(mActivity);
        textView.setText("首页");
        textView.setTextSize(32);
        textView.setTextColor(mActivity.getResources().getColor(R.color.colorRed));
        textView.setGravity(Gravity.CENTER);
        flContentBp.addView(textView);

        tvTitle.setText("首页");

        ibMenu.setVisibility(View.GONE);
    }
}
