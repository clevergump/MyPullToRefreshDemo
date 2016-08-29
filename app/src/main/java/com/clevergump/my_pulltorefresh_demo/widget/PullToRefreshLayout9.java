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
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.clevergump.my_pulltorefresh_demo.PtrCommon;
import com.clevergump.my_pulltorefresh_demo.utils.DensityUtils;
import com.clevergump.my_pulltorefresh_demo.widget.header.PtrHeaderLayout8;
import com.clevergump.my_pulltorefresh_demo.widget.header.base.PtrHeaderBaseLayout8;

/**
 * 下拉刷新框架的核心布局.
 *
 * 基础版本9.
 *
 * 实现的功能:
 * 1. 对 AbsListView 的下拉刷新, 目前只测试了 ListView 是ok的, 还未测试 GridView.
 * 2. 正在刷新时, 禁止继续下拉.
 * 3. 提供 {@link #setCanPullDown(boolean)} 方法, 可供设置是否允许下拉.
 *
 * 存在的bug:
 * 1. 当内容View是一个 ListView 并且随着 ListView item 的不断增加, 当增加到需要滑动才能看到最后一个 item时,
 * 最后一个 item 显示不全, 大概显示该 item高度的一半(需进一步验证是不是一半, 例如: 可以设置不同样式的 item,
 * 然后为最后一个 item 分别设置不同样式的view, 看看能够显示出来的高度是不是也是各自样式View的一半高度)
 *
 * @author clevergump
 */
public class PullToRefreshLayout9 extends LinearLayout {

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
    private float mInterceptLastY;
    private int mScaledTouchSlop;
    private PtrHeaderBaseLayout8 mHeaderView;
    private View mContentView;


    public PullToRefreshLayout9(Context context) {
        this(context, null);
    }

    public PullToRefreshLayout9(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout9(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mContentView = getPtrContentView();
        addView(mHeaderView, 0);
        addView(mContentView, 1);
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
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(mContext, 60)));
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
        removeView(mHeaderView);
        mHeaderView = headerView;
        addView(mHeaderView, 0);
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
//        removeView(getChildAt(1));
//        addView(contentView, 1);
        removeView(mContentView);
        mContentView = contentView;
        addView(mContentView, 1);
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
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: changed = " + changed);
        if (changed && mCurrState == PtrCommon.STATE_RESET && mIsFirstLayout) {
            mHeaderHeight = mHeaderView.getMeasuredHeight();
            Toast.makeText(mContext, "header height = " + mHeaderHeight, Toast.LENGTH_SHORT).show();
//            smoothScrollTo(0, -mHeaderHeight, 8000);
            scrollTo(0, mHeaderHeight);

            // 设置contentview 的高度是屏幕的高度, 原先的高度是 (屏幕高度-headerview高度).
            LinearLayout.LayoutParams clp = (LayoutParams) mContentView.getLayoutParams();
            clp.height = DensityUtils.getScreenHeightPixels(mContext);
            mContentView.setLayoutParams(clp);

//            LinearLayout.LayoutParams clp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT);
//            mContentView.setLayoutParams(clp);

            // 下面的设置无效.
//            ViewGroup.LayoutParams clp = getChildAt(1).getLayoutParams();
//            clp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            invalidate();
            mIsFirstLayout = false;
        }
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * 设置是否允许下拉
     * @param canPullDown
     */
    public void setCanPullDown(boolean canPullDown) {
        mCanPullDown = canPullDown;
    }

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
//        if(!isContentViewVerticallyScrollable()) {
//            return true;
//        }
        // mContentView 的内容上边缘相对于mContentView控件上边缘的y坐标.
//        int contentTopCoor = getContentTopCoor();

        float currY = ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.i(TAG, "contentTopCoor = " + contentTopCoor);
                mInterceptLastY = currY;
                return false;
            case MotionEvent.ACTION_MOVE:
                if (isContentViewVerticallyScrollable()) {
                    if (canScrollUp()) {
                        return false;
                    }
                    float dy = currY - mInterceptLastY;
                    // |dy| >= mScaledTouchSlop
                    // 手指向下移动
                    // 注意: 一旦拦截, 后续就不再调用该方法了, 而是直接调用该控件自己的 super.dispatchTouchEvent().
                    if (dy > 0 && mCanPullDown) {
                        return true;
                    }
                    // 手指向上移动
                    // 让 mContentView 内部滚动. 记下允许滚动时候的y坐标, 以便于计算下一个 |dy| 与 mScaledTouchSlop的比较.
                    mInterceptLastY = currY;
                    return false;
                } else {
                    float dy = currY - mInterceptLastY;
                    if (Math.abs(dy) < mScaledTouchSlop) {
                        return false;
                    }
                    // |dy| >= mScaledTouchSlop
                    // 手指向下移动
                    if (dy > 0) {
                        return true;
                    }
                }
            case MotionEvent.ACTION_UP:
                // 不拦截, 这样子View就可以响应 click 事件
                return false;
        }
        return false;
    }

    /**
     * content View 的内容在垂直方向上是否可以滚动
     * @return
     */
    private boolean isContentViewVerticallyScrollable() {
        return mContentView instanceof ScrollView || mContentView instanceof AbsListView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currY = event.getRawY();
//      getTop()是控件上边缘的位置, scroll只会改变控件内容的位置, 不会改变控件自身的位置, 所以不能用 getTop().
//      int headerTop = mHeaderView.getTop();
//      getScrollY() = 0 - headerTop;
        int headerTop = getHeaderTopCoor();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                // 手指移动的距离
                float dy = currY - mLastY;
                if (Math.abs(dy) < mScaledTouchSlop) {
                    return true;
                }
                // 允许下拉, 且当前手指向下移动
                if (dy > 0 && mCurrState != PtrCommon.STATE_REFRESHING) {
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
                break;
            case MotionEvent.ACTION_UP:
                // 如果 headerview 不是完全显示, 则直接不进行网络请求, 直接回弹到 headerview 刚好完全隐藏的位置
                if (headerTop < 0) {
                    scrollToInitialPosition(1000);
                    setState(PtrCommon.STATE_RESET);
                }
                // 如果 headerview 已经完全显示, 则需要模拟网络请求的过程.
                else {
                    if (mOnRefreshListener != null && mCurrState == PtrCommon.STATE_RELEASH_TO_REFRESH) {
                        mOnRefreshListener.onRefresh();
                        Toast.makeText(mContext, "onRefresh", Toast.LENGTH_SHORT).show();
                    }
                    setState(PtrCommon.STATE_REFRESHING);
                    // 平滑回弹到 headerview 的上边缘刚好与屏幕上边缘重合的位置, 此后模拟网络请求的过程.
                    smoothScrollTo(0, 0, 500);
                }
                break;
        }
        mLastY = currY;
        return true;
    }

    /**
     * 改变刷新状态
     *
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
     * content View的内容是否已滚动到顶部. return true表示内容还未滚动到顶部, false otherwise.
     * @return
     */
    private boolean canScrollUp() {

        if (mContentView instanceof AbsListView && ((AbsListView) mContentView).getChildCount() > 0) {
            AbsListView absLv = (AbsListView) mContentView;
            if (absLv.getFirstVisiblePosition() > 0) {
                return true;
            }
            // first visiblie child is position 0
            final View firstVisibleChild = absLv.getChildAt(0);
            if (firstVisibleChild != null) {
                // 初始打印 firstVisibleChild.getTop() = 0, absLv.getTop() = mHeaderHeight
//                Log.d(TAG, "firstVisibleChild.getTop() = " + firstVisibleChild.getTop()
//                        +", absLv.getTop() = " + absLv.getTop());
                return firstVisibleChild.getTop() < 0;
            }

//            return canScrollVertically(-1);


//            try {
//                Method method = AbsListView.class.getDeclaredMethod("canScrollUp", null);
//                method.setAccessible(true);
//                boolean result = (boolean) method.invoke(absLv, null);
//                return result;
//            } catch (NoSuchMethodException e) {
//                boolean canScrollUp;
//                // 0th element is not visible
//                canScrollUp = absLv.getFirstVisiblePosition() > 0;
//
//                // ... Or top of 0th element is not visible
//                if (!canScrollUp) {
//                    if (getChildCount() > 0) {
//                        View child = getChildAt(0);
//                        canScrollUp = child.getTop() < absLv.getListPaddingTop();
//                    }
//                }
//                return canScrollUp;
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }

//            boolean canScrollUp;
//            // 0th element is not visible
//            canScrollUp = absLv.getFirstVisiblePosition() > 0;
//
//            // ... Or top of 0th element is not visible
//            if (!canScrollUp) {
//                if (getChildCount() > 0) {
//                    View child = getChildAt(0);
//                    canScrollUp = child.getTop() < absLv.getListPaddingTop();
//                    Log.w(TAG, "child.getTop() = " + child.getTop() + ", absLv.getListPaddingTop() = " + absLv.getListPaddingTop());
//                }
//            }
//            return canScrollUp;
        }

        return true;
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