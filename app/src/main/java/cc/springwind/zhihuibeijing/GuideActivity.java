package cc.springwind.zhihuibeijing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cc.springwind.zhihuibeijing.global.Constants;
import cc.springwind.zhihuibeijing.utils.PrefsUtil;

/**
 * Created by HeFan on 2016/8/12.
 */
public class GuideActivity extends AppCompatActivity {
    @InjectView(R.id.vp_guide_ag)
    ViewPager vpGuideAg;
    @InjectView(R.id.btn_start_ag)
    Button btnStartAg;
    @InjectView(R.id.ll_indicator_ag)
    LinearLayout llIndicatorAg;
    @InjectView(R.id.iv_indicator_ag)
    ImageView ivIndicatorAg;

    private int[] mImageIds = new int[]{
            R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3
    };
    private List<ImageView> mImageViewList;
    private int mPointDistance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        ButterKnife.inject(this);

        initData();

        vpGuideAg.setAdapter(new GuideAdapter());

        vpGuideAg.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivIndicatorAg.getLayoutParams();
                int leftMargin = (int) (mPointDistance * positionOffset + mPointDistance * position);
                params.leftMargin = leftMargin;
                ivIndicatorAg.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mImageViewList.size() - 1) {
                    btnStartAg.setVisibility(View.VISIBLE);
                } else {
                    btnStartAg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        ivIndicatorAg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ivIndicatorAg.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mPointDistance = llIndicatorAg.getChildAt(1).getLeft() - llIndicatorAg.getChildAt(0).getLeft();
            }
        });
    }

    private void initData() {
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(mImageIds[i]);
            mImageViewList.add(imageView);

            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_gray);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0)
                params.leftMargin = 16;
            point.setLayoutParams(params);
            llIndicatorAg.addView(point);
        }
    }

    @OnClick(R.id.btn_start_ag)
    public void onClick() {
        PrefsUtil.putBoolean(getApplicationContext(), Constants.IS_FIRST_USE, false);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mImageViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
