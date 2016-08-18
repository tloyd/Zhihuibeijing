package cc.springwind.zhihuibeijing.base.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import cc.springwind.zhihuibeijing.base.BaseMenuDetailPager;

/**
 * @author: HeFan
 * @date: 2016/8/15 13:52
 */
public class InteraDetailPager extends BaseMenuDetailPager {
    public InteraDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView view = new TextView(mActivity);
        view.setText("菜单详情页-互动");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);
        return view;
    }

}
