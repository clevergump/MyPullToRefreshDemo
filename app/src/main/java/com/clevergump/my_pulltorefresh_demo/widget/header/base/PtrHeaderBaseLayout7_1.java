package com.clevergump.my_pulltorefresh_demo.widget.header.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.clevergump.my_pulltorefresh_demo.PtrCommon;

/**
 * 下拉刷新头部 View 的基类
 *
 * @author clevergump
 */
public abstract class PtrHeaderBaseLayout7_1 extends RelativeLayout {

    protected View mContentView;
    private int mCurrState = PtrCommon.STATE_RESET;

    public PtrHeaderBaseLayout7_1(Context context) {
        this(context, null);
    }

    public PtrHeaderBaseLayout7_1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrHeaderBaseLayout7_1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected abstract void initView();

    protected abstract void onPullToRefresh();

    protected abstract void onReleaseToRefresh();

    protected abstract void onRefreshing();

    protected abstract void onReset();

    /**
     * 改变刷新状态
     * @param state
     */
    public void setState(int state) {
        mCurrState = state;
        switch (state) {
            case PtrCommon.STATE_PULL_TO_REFRESH:
                Log.i("PullToRefreshLayout7_1", "---- PULL_TO_REFRESH");
                onPullToRefresh();
                break;
            case PtrCommon.STATE_RELEASH_TO_REFRESH:
                Log.i("PullToRefreshLayout7_1", "---- RELEASH_TO_REFRESH");
                onReleaseToRefresh();
                break;
            case PtrCommon.STATE_REFRESHING:
                Log.i("PullToRefreshLayout7_1", "---- REFRESHING");
                onRefreshing();
                break;
            case PtrCommon.STATE_RESET:
                Log.i("PullToRefreshLayout7_1", "---- RESET");
                onReset();
                break;
        }
    }

    /**
     * 获取当前状态
     * @return
     */
    public int getState() {
        return mCurrState;
    }

}