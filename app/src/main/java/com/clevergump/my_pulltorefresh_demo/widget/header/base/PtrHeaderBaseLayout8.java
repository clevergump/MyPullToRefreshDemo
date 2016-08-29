package com.clevergump.my_pulltorefresh_demo.widget.header.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 下拉刷新头部 View 的基类
 *
 * @author clevergump
 */
public abstract class PtrHeaderBaseLayout8 extends RelativeLayout {

    protected View mContentView;

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
}