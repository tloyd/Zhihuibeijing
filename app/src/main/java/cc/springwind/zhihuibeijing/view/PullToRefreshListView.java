package cc.springwind.zhihuibeijing.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import cc.springwind.zhihuibeijing.R;

/**
 * @author: HeFan
 * @date: 2016/8/27 10:53
 */
public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener {

    ImageView ivArrowPtrh;
    ProgressBar pbPtrh;
    TextView tvTitlePtrh;
    TextView tvTimePtrh;
    private View mFooterView;
    private View mHeaderView;
    private RotateAnimation upAnimation;
    private RotateAnimation downAnimation;
    private int measuredHeight;
    private int mCurrentState = STATE_PULL_TO_REFRESH;
    private int startY = -1;
    // 3. 定义成员变量,接收监听对象
    private OnRefreshListener listener;

    public static final int STATE_PULL_TO_REFRESH = 0;
    public static final int STATE_RELEASE_TO_REFRESH = 1;
    public static final int STATE_REFRESHING = 2;
    private int footerHeight;
    private boolean isLoad;


    public PullToRefreshListView(Context context) {
        this(context, null);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFooterView();
    }

    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.pull_to_refresh_header, null);
        addHeaderView(mHeaderView);
        mHeaderView.measure(0, 0);
        measuredHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -measuredHeight, 0, 0);

        tvTitlePtrh = (TextView) mHeaderView.findViewById(R.id.tv_title_ptrh);
        ivArrowPtrh = (ImageView) mHeaderView.findViewById(R.id.iv_arrow_ptrh);
        pbPtrh = (ProgressBar) mHeaderView.findViewById(R.id.pb_ptrh);
        tvTimePtrh = (TextView) mHeaderView.findViewById(R.id.tv_time_ptrh);

        setCurrentTime();
        initAnimation();
    }

    private void initFooterView() {
        mFooterView = View.inflate(getContext(), R.layout.pull_to_refresh_footer, null);
        this.addFooterView(mFooterView);
        mFooterView.measure(0, 0);
        footerHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0, -footerHeight, 0, 0);
        this.setOnScrollListener(this);
    }

    private void initAnimation() {
        upAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                .RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(200);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                .RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(200);
        downAnimation.setFillAfter(true);
    }

    private void setCurrentTime() {
        tvTimePtrh.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = (int) ev.getY();
                }

                if (mCurrentState == STATE_REFRESHING) {
                    break;
                }

                int endY = (int) ev.getY();

                int disY = endY - startY;
                int firstVisiblePosition = getFirstVisiblePosition();
                if (firstVisiblePosition == 0 && disY > 0) {
                    int paddingTop = disY - measuredHeight;
                    mHeaderView.setPadding(0, paddingTop, 0, 0);
                    if (paddingTop > 0 && mCurrentState != STATE_RELEASE_TO_REFRESH) {
                        mCurrentState = STATE_RELEASE_TO_REFRESH;
                        refreshState();
                    } else if (paddingTop < 0 && mCurrentState != STATE_PULL_TO_REFRESH) {
                        mCurrentState = STATE_PULL_TO_REFRESH;
                        refreshState();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (mCurrentState == STATE_RELEASE_TO_REFRESH) {
                    mCurrentState = STATE_REFRESHING;
                    refreshState();
                    mHeaderView.setPadding(0, 0, 0, 0);

                    // 4. 进行回调
                    if (listener != null) {
                        listener.onRefresh();
                    }

                } else if (mCurrentState == STATE_PULL_TO_REFRESH) {
                    mHeaderView.setPadding(0, -measuredHeight, 0, 0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void refreshState() {
        switch (mCurrentState) {
            case STATE_PULL_TO_REFRESH:
                tvTitlePtrh.setText("下拉刷新!");
                ivArrowPtrh.clearAnimation();
                ivArrowPtrh.setVisibility(VISIBLE);
                ivArrowPtrh.setAnimation(downAnimation);
                pbPtrh.setVisibility(INVISIBLE);
                break;
            case STATE_REFRESHING:
                tvTitlePtrh.setText("正在刷新!");
                ivArrowPtrh.clearAnimation();
                ivArrowPtrh.setVisibility(INVISIBLE);
                pbPtrh.setVisibility(VISIBLE);
                break;
            case STATE_RELEASE_TO_REFRESH:
                tvTitlePtrh.setText("松开刷新!");
                ivArrowPtrh.setVisibility(VISIBLE);
                ivArrowPtrh.clearAnimation();
                ivArrowPtrh.setAnimation(upAnimation);
                pbPtrh.setVisibility(INVISIBLE);
                break;
        }
    }

    // 2. 暴露接口,设置监听
    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    public void onRefreshComplete(boolean flag) {
        if (!isLoad) {
            mHeaderView.setPadding(0, -measuredHeight, 0, 0);
            mCurrentState = STATE_PULL_TO_REFRESH;
            tvTitlePtrh.setText("下拉刷新!");
            pbPtrh.setVisibility(INVISIBLE);
            ivArrowPtrh.setVisibility(VISIBLE);
            if (flag) {
                setCurrentTime();
            }
        } else {
            mFooterView.setPadding(0, -footerHeight, 0, 0);//隐藏布局
            isLoad = false;
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            int lastVisiblePosition = getLastVisiblePosition();
            if (lastVisiblePosition == getCount() - 1 && !isLoad) {
                isLoad = true;
                mFooterView.setPadding(0, 0, 0, 0);
                setSelection(getCount() - 1);
                if (listener != null)
                    listener.onLoadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    // 1. 设置回调接口
    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();
    }
}
