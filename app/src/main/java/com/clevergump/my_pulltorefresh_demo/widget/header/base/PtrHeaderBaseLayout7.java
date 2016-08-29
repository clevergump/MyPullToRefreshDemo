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
public abstract class PtrHeaderBaseLayout7 extends RelativeLayout {

    protected View mContentView;

    public PtrHeaderBaseLayout7(Context context) {
        this(context, null);
    }

    public PtrHeaderBaseLayout7(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrHeaderBaseLayout7(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected abstract void initView();

    public abstract void refreshing();

    public abstract void finishRefreshing();

}