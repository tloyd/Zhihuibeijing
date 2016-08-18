package cc.springwind.zhihuibeijing;

import android.app.Application;

import org.xutils.x;

/**
 * Created by HeFan on 2016/8/14.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
