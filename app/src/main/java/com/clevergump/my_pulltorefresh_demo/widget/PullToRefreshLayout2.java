package com.clevergump.my_pulltorefresh_demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.clevergump.my_pulltorefresh_demo.R;

/**
 * 下拉刷新框架的核心布局.
 *
 * 基础版本2. 添加平滑滚动的代码.
 *
 * @author clevergump
 */
public class PullToRefreshLayout2 extends LinearLayout {

    private final String TAG = getClass().getSimpleName();
    protected Context mContext;
    private View mPtrFrameView;
    /**
     * 是否允许下拉
     */
    private boolean mCanPullDown = true;
    /**
     * 头部view. 未刷新或刷新结束后需要隐藏.
     */
    private View mHeaderView;
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
    /**
     * 是否是第一次layout操作. 如果是, 则需要手动隐藏 headerview.
     */
    private boolean mIsFirstLayout = true;
    /**
     * headerview的高度
     */
    private int mHeaderHeight;

    public PullToRefreshLayout2(Context context) {
        this(context, null);
    }

    public PullToRefreshLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mContext = getContext();
        initView();
        initData();
    }

    private void initView() {
        mPtrFrameView = View.inflate(mContext, R.layout.ptr_base1, this);
        mHeaderView = mPtrFrameView.findViewById(R.id.ptr_header);
    }

    private void initData() {
        mScroller = new Scroller(mContext);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: changed = " + changed);
        if (changed && currState == STATE_FINISH_REFRESHING && mIsFirstLayout) {
            mHeaderHeight = mHeaderView.getMeasuredHeight();
//            Toast.makeText(mContext, "header height = " + mHeaderHeight, Toast.LENGTH_SHORT).show();
//            scrollTo(0, mHeaderHeight);
            smoothScrollTo(0, -mHeaderHeight, 5000);
            mIsFirstLayout = false;
        }
        super.onLayout(changed, l, t, r, b);
    }

    public void canPullDown(boolean canPullDown) {
        mCanPullDown = canPullDown;
    }

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
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }


}