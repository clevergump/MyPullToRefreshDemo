package com.clevergump.my_pulltorefresh_demo.widget.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.utils.DensityUtils;
import com.clevergump.my_pulltorefresh_demo.widget.header.base.PtrHeaderBaseLayout7;

/**
 * @author clevergump
 */
public class PtrHeaderLayout2 extends PtrHeaderBaseLayout7 {

    private ImageView mIvRefreshing;
    private Animation mRefreshingAnim;

    public PtrHeaderLayout2(Context context) {
        this(context, null);
    }

    public PtrHeaderLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrHeaderLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        mContentView = inflate(getContext(), R.layout.ptr_header2, this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtils.dip2px(getContext(), 60)));
        mIvRefreshing = (ImageView) mContentView.findViewById(R.id.iv_refreshing);
    }

    @Override
    public void refreshing() {
        if (mRefreshingAnim == null) {
            mRefreshingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.refreshing_rotate);
        }
        mIvRefreshing.startAnimation(mRefreshingAnim);
    }

    @Override
    public void finishRefreshing() {
        mIvRefreshing.clearAnimation();
    }
}