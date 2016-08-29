package com.clevergump.my_pulltorefresh_demo.widget.header.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author clevergump
 */
public abstract class PtrHeaderBaseLayout8 extends RelativeLayout {

    protected View mContentView;
//    private int mCurrState = PtrCommon.STATE_RESET;

    public PtrHeaderBaseLayout8(Context context) {
        this(context, null);
    }

    public PtrHeaderBaseLayout8(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrHeaderBaseLayout8(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected abstract void initView();

    public abstract void onPullToRefresh();

    public abstract void onReleaseToRefresh();

    public abstract void onRefreshing();

    public abstract void onReset();

//    /**
//     * 改变刷新状态
//     * @param state
//     */
//    public void setState(int state) {
//        mCurrState = state;
//        switch (state) {
//            case PtrCommon.STATE_PULL_TO_REFRESH:
//                Log.i("PullToRefreshLayout7_1", "---- PULL_TO_REFRESH");
//                onPullToRefresh();
//                break;
//            case PtrCommon.STATE_RELEASH_TO_REFRESH:
//                Log.i("PullToRefreshLayout7_1", "---- RELEASH_TO_REFRESH");
//                onReleaseToRefresh();
//                break;
//            case PtrCommon.STATE_REFRESHING:
//                Log.i("PullToRefreshLayout7_1", "---- REFRESHING");
//                onRefreshing();
//                break;
//            case PtrCommon.STATE_RESET:
//                Log.i("PullToRefreshLayout7_1", "---- RESET");
//                onReset();
//                break;
//        }
//    }

//    /**
//     * 获取当前状态
//     * @return
//     */
//    public int getState() {
//        return mCurrState;
//    }

}