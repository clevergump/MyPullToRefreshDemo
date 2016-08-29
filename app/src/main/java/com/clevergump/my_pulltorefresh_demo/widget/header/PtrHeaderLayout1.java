package com.clevergump.my_pulltorefresh_demo.widget.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.utils.DensityUtils;
import com.clevergump.my_pulltorefresh_demo.widget.header.base.PtrHeaderBaseLayout7;

/**
 * 基础版本7. 实现的功能: 正在刷新时, 让 headerview 显示刷新的动画效果.
 * 刷新结束后, 让 headerview 取消掉刷新的动画效果.
 *
 * @author clevergump
 */
public class PtrHeaderLayout1 extends PtrHeaderBaseLayout7 {

    private Animation mRefreshingAnim;

    public PtrHeaderLayout1(Context context) {
        this(context, null);
    }

    public PtrHeaderLayout1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrHeaderLayout1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        mContentView = inflate(getContext(), R.layout.ptr_header1, this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtils.dip2px(getContext(), 240)));
    }

    @Override
    public void refreshing() {
        if (mRefreshingAnim == null) {
            mRefreshingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.refreshing_alpha);
        }
        startAnimation(mRefreshingAnim);
    }

    @Override
    public void finishRefreshing() {
        clearAnimation();
    }

//    @Override
//    public void onRefreshing() {
//        Toast.makeText(getContext(), "开始刷新...", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onReset() {
//        Toast.makeText(getContext(), "结束刷新...", Toast.LENGTH_SHORT).show();
//    }
}