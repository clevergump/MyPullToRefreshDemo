package com.clevergump.my_pulltorefresh_demo.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.utils.DensityUtils;

/**
 * 下拉刷新框架的核心布局.
 *
 * 基础版本5. 实现的功能: 初始只能下拉, 随后控件能跟随随手指一起上下移动, 但下拉后再向上移动, 最多只能
 * 移动到 headerview 刚好隐藏起来的位置.
 *
 * @author clevergump
 */
public class PullToRefreshLayout5 extends LinearLayout {

    private final String TAG = getClass().getSimpleName();
    protected Context mContext;
    /**
     * 是否允许下拉
     */
    private boolean mCanPullDownFromFront = true;
    /**
     * headerview的高度
     */
    private int mHeaderHeight;
    private Scroller mScroller;
    /**
     * 下拉刷新的状态
     */
    private static final int STATE_PULL_TO_REFRESH = 1;
    /**
     * 释放刷新的状态
     */
    private static final int STATE_RELEASH_TO_REFRESH = 2;
    /**
     * 正在刷新的状态
     */
    private static final int STATE_REFRESHING = 3;
    /**
     * 结束刷新或还未开始刷新的状态
     */
    private static final int STATE_FINISH_REFRESHING = 0;
    /**
     * 当前状态.
     */
    private int currState = STATE_FINISH_REFRESHING;
    private boolean mIsFirstLayout = true;
    private float mDownY;
    private float mLastY;


    public PullToRefreshLayout5(Context context) {
        this(context, null);
    }

    public PullToRefreshLayout5(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout5(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        mContext = getContext();
        initView();
        initData();
    }

    private void initView() {
        View headerView = getPtrHeaderView();
        View contentView = getPtrContentView();
        addView(headerView, 0);
        addView(contentView, 1);
    }

    private void initData() {
        mScroller = new Scroller(mContext);
    }

    /**
     * 获取 headerview, 该方法需要设置 headerview 的 LayoutParams, 然后再返回.
     *
     * @return
     */
    public View getPtrHeaderView() {
        View headerView = View.inflate(mContext, R.layout.ptr_header1, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(mContext, 240)));
        return headerView;
    }

    /**
     * 获取 contentview, 该方法需要设置 contentview 的 LayoutParams, 然后再返回.
     *
     * @return
     */
    public View getPtrContentView() {
        TextView tv = new TextView(mContext);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setGravity(Gravity.CENTER);
        tv.setText("test");
        tv.setTextColor(Color.parseColor("#000000"));
        tv.setTextSize(40);
        tv.setBackgroundColor(Color.parseColor("#ffff00"));
        return tv;
    }

    /**
     * 设置 headerview. 外界可用此方法传入自定义的 header view
     *
     * @param headerView
     */
    public void setPtrHeaderView(View headerView) {
        if (headerView == null) {
            return;
        }
        removeView(getChildAt(0));
        addView(headerView, 0);
    }

    /**
     * 设置可以下拉的内容view. 外界可用此方法传入自定义的 content view
     *
     * @param contentView
     */
    public void setPtrContentView(View contentView) {
        if (contentView == null) {
            return;
        }
        removeView(getChildAt(1));
        addView(contentView, 1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: changed = " + changed);
        if (changed && currState == STATE_FINISH_REFRESHING && mIsFirstLayout) {
            mHeaderHeight = getChildAt(0).getMeasuredHeight();
            Toast.makeText(mContext, "header height = " + mHeaderHeight, Toast.LENGTH_SHORT).show();
//            smoothScrollTo(0, -mHeaderHeight, 8000);
            scrollTo(0, mHeaderHeight);

            // 设置contentview 的高度是屏幕的高度, 原先的高度是 (屏幕高度-headerview高度).
            ViewGroup.LayoutParams clp = getChildAt(1).getLayoutParams();
            int screenHeight = DensityUtils.getScreenHeightPixels(mContext);
            clp.height = screenHeight;
            getChildAt(1).setLayoutParams(clp);
            // 下面的设置无效. why?
//            ViewGroup.LayoutParams clp = getChildAt(1).getLayoutParams();
//            clp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            invalidate();
            mIsFirstLayout = false;
        }
        super.onLayout(changed, l, t, r, b);
    }

//    public void canPullDown(boolean canPullDownFromFront) {
//        mCanPullDownFromFront = canPullDownFromFront;
//    }

    /**
     * 平滑滚动
     *
     * @param destXCoor          目的地X坐标
     * @param destYCoor          目的地Y坐标
     * @param durationMillis 滚动用时(毫秒数)
     */
    private void smoothScrollTo(int destXCoor, int destYCoor, int durationMillis) {
        // (0, 0) 是这个控件自身的左, 上两条边的坐标.
        // 根据公式: getScrollX() = 0 - currXCoor  getScrollY() = 0 - currYCoor 可得
        int currXCoor = 0 - getScrollX();
        int currYCoor = 0 - getScrollY();
        mScroller.startScroll(getScrollX(), getScrollY(), currXCoor - destXCoor, currYCoor - destYCoor, durationMillis);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currY = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = currY;
                mLastY = currY;
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                float dy = currY - mLastY;
                // 允许下拉, 且当前确实是在下拉
                if (mCanPullDownFromFront && dy > 0) {
                    scrollBy(0, -(int) dy);
                }
                // 上拉
                else if (dy < 0) {
//                    getTop()是控件的位置, scroll只会改变内容的位置, 不会改变控件自身的位置, 所以不能用 getTop().
//                    int headerTop = getChildAt(0).getTop();
                    int headerTop = -getScrollY();
                    Log.d(TAG, "headerTop = " + headerTop);
                    // headerview 先前已被下拉了一段距离
                    if (headerTop > -mHeaderHeight) {
                        // 上边缘的目的地Y坐标
                        float headerDestTop = Math.max(headerTop + dy, -mHeaderHeight);
                        // scrollTo()的2个参数含义分别是: 控件的左/上边缘坐标 - 控件内容的左/上边缘坐标
                        scrollTo(0, 0 - (int) headerDestTop);
                    }
                }
                mLastY = currY;
                break;
        }
        return true;
    }
}