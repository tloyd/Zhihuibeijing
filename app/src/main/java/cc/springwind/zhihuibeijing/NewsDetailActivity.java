package cc.springwind.zhihuibeijing;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * @author: HeFan
 * @date: 2016/9/2 18:39
 */
@ContentView(R.layout.activity_news_detail)
public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.ib_menu)
    private ImageButton ibMenu;

    @ViewInject(R.id.wb_news_detail)
    private WebView wbNewsDetail;

    @ViewInject(R.id.pb_news_loading)
    private ProgressBar pbNewsLoading;

    @ViewInject(R.id.ib_textsize)
    private ImageButton ibTextsize;

    @ViewInject(R.id.ib_share)
    private ImageButton ibShare;
    private int mCurrentSize = 2;
    private WebSettings settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_news_detail);

        x.view().inject(this);

        ibMenu.setImageResource(R.drawable.back);
        ibMenu.setOnClickListener(this);

        ibTextsize.setVisibility(View.VISIBLE);
        ibTextsize.setOnClickListener(this);

        ibShare.setVisibility(View.VISIBLE);
        ibShare.setOnClickListener(this);

        wbNewsDetail.loadUrl(getIntent().getStringExtra("url"));

        settings = wbNewsDetail.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);

        wbNewsDetail.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pbNewsLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pbNewsLoading.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 是否强制在当前页面加载跳转链接
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_menu:
                finish();
                break;
            case R.id.ib_share:
                showShare();
                break;
            case R.id.ib_textsize:
                showTextSize();
                break;
        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    private void showTextSize() {
        String[] sizeArray = new String[]{"超大", "大", "正常", "小", "超小"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择字体大小:")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (mCurrentSize) {
                            case 0:
                                settings.setTextSize(WebSettings.TextSize.LARGEST);
                                break;
                            case 1:
                                settings.setTextSize(WebSettings.TextSize.LARGER);
                                break;
                            case 2:
                                settings.setTextSize(WebSettings.TextSize.NORMAL);
                                break;
                            case 3:
                                settings.setTextSize(WebSettings.TextSize.SMALLER);
                                break;
                            case 4:
                                settings.setTextSize(WebSettings.TextSize.SMALLEST);
                                break;
                        }
                    }
                })
                .setSingleChoiceItems(sizeArray, mCurrentSize, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCurrentSize = which;
                    }
                })
                .show();
    }
}
