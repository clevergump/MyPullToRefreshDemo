package com.clevergump.my_pulltorefresh_demo.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.clevergump.my_pulltorefresh_demo.PtrCommon;
import com.clevergump.my_pulltorefresh_demo.activity.base.PtrActivity7;
import com.clevergump.my_pulltorefresh_demo.utils.DensityUtils;
import com.clevergump.my_pulltorefresh_demo.widget.header.PtrHeaderLayout8;
import com.clevergump.my_pulltorefresh_demo.widget.header.base.PtrHeaderBaseLayout8;

/**
 * 基础版本8:
 * 1. 重构. 将先前 headerview 中与下拉刷新状态相关的 get/set 逻辑移动到该类中, 由该类统一管理, headerview
 * 只负责命令的执行, 而不再负责命令的分发.
 * 2. 新增 {@link OnRefreshListener} 回调接口, 用于在 STATE_REFRESHING 状态下进行回调(通常是进行网络请求), 但请注意:
 * 在回调结束后, 需要调用 {@link #setState(int)} 方法, 也就是 {@link PtrActivity7#onRefreshComplete()} 方法以通知该控件.
 *
 * @author clevergump
 */
public class PullToRefreshLayout8 extends LinearLayout {

    private final String TAG = getClass().getSimpleName();
    /**
     * 控件随手指移动的距离比例. 即: 控件移动的距离 = 手指移动的距离 / 该比例
     */
    private static final float SCROLL_PROPORTION = 2.0f;
    protected Context mContext;
    /**
     * 是否允许下拉
     */
    private boolean mCanPullDown = true;

    /**
     * 刷新的监听器
     */
    private OnRefreshListener mOnRefreshListener;
    /**
     * headerview的高度
     */
    private int mHeaderHeight;
    private Scroller mScroller;
    /**
     * 当前状态.
     */
    private int mCurrState = PtrCommon.STATE_RESET;
    private boolean mIsFirstLayout = true;
    private float mLastY;
    private int mScaledTouchSlop;
    private PtrHeaderBaseLayout8 mHeaderView;


    public PullToRefreshLayout8(Context context) {
        this(context, null);
    }

    public PullToRefreshLayout8(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout8(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mHeaderView = getPtrHeaderView();
        View contentView = getPtrContentView();
        addView(mHeaderView, 0);
        addView(contentView, 1);
    }

    private void initData() {
        mScroller = new Scroller(mContext);
        mScaledTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
    }

    /**
     * 获取 headerview, 该方法需要设置 headerview 的 LayoutParams, 然后再返回.
     *
     * @return
     */
    public PtrHeaderBaseLayout8 getPtrHeaderView() {
        PtrHeaderBaseLayout8 headerView = new PtrHeaderLayout8(mContext);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(mContext, 120)));
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
    public void setPtrHeaderView(PtrHeaderBaseLayout8 headerView) {
        if (headerView == null) {
            return;
        }
        removeView(getChildAt(0));
        mHeaderView = headerView;
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

    /**
     * 设置上拉, 下拉监听器
     *
     * @param listener
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        if (listener == null) {
            return;
        }
        mOnRefreshListener = listener;
        mCanPullDown = true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: changed = " + changed);
        if (changed && mCurrState == PtrCommon.STATE_RESET && mIsFirstLayout) {
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
     * @param destXCoor      目的地X坐标
     * @param destYCoor      目的地Y坐标
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
//      getTop()是控件上边缘的位置, scroll只会改变控件内容的位置, 不会改变控件自身的位置, 所以不能用 getTop().
//      int headerTop = getChildAt(0).getTop();
//      getScrollY() = 0 - headerTop;
        int headerTop = getHeaderTopCoor();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = currY;
                break;
            case MotionEvent.ACTION_MOVE:
                // 手指移动的距离
                float dy = currY - mLastY;
                if (Math.abs(dy) < mScaledTouchSlop) {
                    return true;
                }
                // 允许下拉, 且当前手指向下移动
                if (mCanPullDown && dy > 0) {
                    // scrollBy()的2个参数分别等于各自坐标的变化量的负值.
                    scrollBy(0, -floatToInt(dy / SCROLL_PROPORTION));
                    Log.i(TAG, "header top = " + getHeaderTopCoor());
                    if ((mCurrState == PtrCommon.STATE_PULL_TO_REFRESH || mCurrState == PtrCommon.STATE_RESET)
                            && getHeaderTopCoor() >= 0) {
                        setState(PtrCommon.STATE_RELEASH_TO_REFRESH);
                    }
                }
                // 手指向上移动
                else if (dy < 0) {
                    Log.d(TAG, "headerTop = " + headerTop);
                    // headerview 先前已被下拉了一段距离
                    if (headerTop > -mHeaderHeight) {
                        // 上边缘的目的地Y坐标  dy / SCROLL_PROPORTION 是控件移动的距离
                        float headerDestTop = Math.max(headerTop + dy / SCROLL_PROPORTION, -mHeaderHeight);
                        // scrollTo()的2个参数含义分别是: 控件的左/上边缘坐标 - 控件内容的左/上边缘坐标
                        int destScrollY = floatToInt(0 - headerDestTop);
                        scrollTo(0, destScrollY);
                        Log.i(TAG, "header top = " + getHeaderTopCoor());
                        if (mCurrState == PtrCommon.STATE_RELEASH_TO_REFRESH && getHeaderTopCoor() <= 0) {
                            setState(PtrCommon.STATE_PULL_TO_REFRESH);
                        }
                    }
                }
                mLastY = currY;
                break;
            case MotionEvent.ACTION_UP:
                // 如果 headerview 不是完全显示, 则直接不进行网络请求, 直接回弹到 headerview 刚好完全隐藏的位置
                if (headerTop < 0) {
                    scrollToInitialPosition(1000);
                    setState(PtrCommon.STATE_RESET);
                }
                // 如果 headerview 已经完全显示, 则需要模拟网络请求的过程.
                else {
                    setState(PtrCommon.STATE_REFRESHING);
                    // 平滑回弹到 headerview 的上边缘刚好与屏幕上边缘重合的位置, 此后模拟网络请求的过程.
                    smoothScrollTo(0, 0, 500);
                    Toast.makeText(mContext, "开始请求数据", Toast.LENGTH_SHORT).show();
//                    getHandler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            setState(PtrCommon.STATE_RESET);
//                        }
//                    }, 3000);
                    if (mOnRefreshListener != null) {
                        mOnRefreshListener.onRefresh();
                    }
                }
                break;
        }
        return true;
    }

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

    private void onPullToRefresh() {
        mHeaderView.onPullToRefresh();
    }

    private void onReleaseToRefresh() {
        mHeaderView.onReleaseToRefresh();
    }

    private void onRefreshing() {
        mHeaderView.onRefreshing();
    }

    private void onReset() {
        // 模拟网络请求过程结束后, 回弹到 headerview 刚好完全隐藏的位置.
        scrollToInitialPosition(1000);
        mHeaderView.onReset();
    }

    private void scrollToInitialPosition(int durationMillis) {
        smoothScrollTo(0, -mHeaderHeight, durationMillis);
    }

    /**
     * 获取该控件内容的上边缘 y坐标. 注意: 获取的不是控件自身上边缘的y坐标.
     *
     * @return
     */
    private int getHeaderTopCoor() {
        return 0 - getScrollY();
    }

    /**
     * float 转 int
     *
     * @param floatValue
     * @return
     */
    private int floatToInt(float floatValue) {
        return (int) (floatValue + 0.5f);
    }

    public interface OnRefreshListener {
        /**
         * 当从顶部下拉时的回调
         */
        void onRefresh();
    }
}