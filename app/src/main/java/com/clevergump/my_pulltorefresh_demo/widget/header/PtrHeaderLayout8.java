package com.clevergump.my_pulltorefresh_demo.widget.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.clevergump.my_pulltorefresh_demo.PtrCommon;
import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.utils.DensityUtils;
import com.clevergump.my_pulltorefresh_demo.widget.header.base.PtrHeaderBaseLayout8;

/**
 * @author clevergump
 */
public class PtrHeaderLayout8 extends PtrHeaderBaseLayout8 {

    private static final int INDEX_PULL_TO_REFRESH = 0;
    private static final int INDEX_RELEASH_TO_REFRESH = 1;
    private static final int INDEX_REFRESHING = 2;

    private ImageView mIvRefreshing;
    private ImageView mIvArrow;
    private TextView mTvTips;

    private Animation mRefreshingAnim;
    private Animation mPullToRefreshAnim;
    private Animation mReleaseToRefreshAnim;

    private String[] mRefreshTipsArr;

    public PtrHeaderLayout8(Context context) {
        this(context, null);
    }

    public PtrHeaderLayout8(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrHeaderLayout8(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (mRefreshTipsArr == null) {
            mRefreshTipsArr = getContext().getResources().getStringArray(R.array.ptr_text_tips);
        }
    }

    @Override
    protected void initView() {
        mContentView = inflate(getContext(), R.layout.ptr_header3, this);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtils.dip2px(getContext(), 50)));
        mIvRefreshing = (ImageView) mContentView.findViewById(R.id.iv_refreshing);
        mIvArrow = (ImageView) mContentView.findViewById(R.id.iv_arrow);
        mTvTips = (TextView) mContentView.findViewById(R.id.tv_refresh_tips);
    }

    @Override
    public void onPullToRefresh() {
        if (mPullToRefreshAnim == null) {
            mPullToRefreshAnim = AnimationUtils.loadAnimation(getContext(), R.anim.pull_rotate);
        }
        mIvArrow.setVisibility(View.VISIBLE);
        mIvRefreshing.setVisibility(View.GONE);
        mIvArrow.startAnimation(mPullToRefreshAnim);
        mTvTips.setText(getRefreshTipText(PtrCommon.STATE_PULL_TO_REFRESH));
    }

    @Override
    public void onReleaseToRefresh() {
        if (mReleaseToRefreshAnim == null) {
            mReleaseToRefreshAnim = AnimationUtils.loadAnimation(getContext(), R.anim.releasel_rotate);
        }
        mIvArrow.setVisibility(View.VISIBLE);
        mIvRefreshing.setVisibility(View.GONE);
        mIvArrow.startAnimation(mReleaseToRefreshAnim);
        mTvTips.setText(getRefreshTipText(PtrCommon.STATE_RELEASH_TO_REFRESH));
    }

    @Override
    public void onRefreshing() {
        if (mRefreshingAnim == null) {
            mRefreshingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.refreshing_rotate);
        }
        // 由于设置了 mIvArrow 的2个动画 fillAfter = "true", 这样即使设置 mIvArrow 的 visibility 为 GONE
        // 也不会让该控件消失, 所以必须清除该动画.
        mIvArrow.clearAnimation();
        mIvArrow.setVisibility(View.GONE);
        mIvRefreshing.setVisibility(View.VISIBLE);
        mIvRefreshing.startAnimation(mRefreshingAnim);
        mTvTips.setText(getRefreshTipText(PtrCommon.STATE_REFRESHING));
    }

    @Override
    public void onReset() {
        mIvRefreshing.clearAnimation();
        mIvArrow.clearAnimation();
        mIvRefreshing.setVisibility(View.GONE);
        mIvArrow.setVisibility(View.VISIBLE);
        mTvTips.setText(getRefreshTipText(PtrCommon.STATE_RESET));
    }

    private CharSequence getRefreshTipText(int refreshState) {
        switch (refreshState) {
            case PtrCommon.STATE_PULL_TO_REFRESH:
            case PtrCommon.STATE_RESET:
                return mRefreshTipsArr[INDEX_PULL_TO_REFRESH];
            case PtrCommon.STATE_RELEASH_TO_REFRESH:
                return mRefreshTipsArr[INDEX_RELEASH_TO_REFRESH];
            case PtrCommon.STATE_REFRESHING:
                return mRefreshTipsArr[INDEX_REFRESHING];
        }
        return "";
    }
}