package com.clevergump.my_pulltorefresh_demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clevergump.my_pulltorefresh_demo.R;

/**
 * 实现功能: 能显示出 headerview, 也能设置 headerview 的初始位置.
 *
 * @author clevergump
 */
public class PullToRefreshLayout1 extends LinearLayout {

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

    public PullToRefreshLayout1(Context context) {
        this(context, null);
    }

    public PullToRefreshLayout1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mContext = getContext();
        initView();
    }

    private void initView() {
        mPtrFrameView = View.inflate(mContext, R.layout.ptr_base1, this);
        mHeaderView = mPtrFrameView.findViewById(R.id.ptr_header);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: changed = " + changed);
        if (changed && currState == STATE_FINISH_REFRESHING && mIsFirstLayout) {
            mHeaderHeight = mHeaderView.getMeasuredHeight();
            Toast.makeText(mContext, "header height = " + mHeaderHeight, Toast.LENGTH_SHORT).show();
//            scrollTo(0, mHeaderHeight / 3 - 10);
//            scrollTo(0, -5);
//            scrollTo(0, mHeaderHeight);
            mIsFirstLayout = false;
        }
        super.onLayout(changed, l, t, r, b);
    }

    public void canPullDown(boolean canPullDown) {
        mCanPullDown = canPullDown;
    }

}