package com.clevergump.my_pulltorefresh_demo.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.utils.DensityUtils;

/**
 * 基础版本3. 结合 PtrActivity3 使用, 实现的功能是: 提供了默认的 headerview 和 contentview, 当然也可以
 * 对二者进行自定义, 分别通过调用 {@link #setPtrHeaderView} 和 {@link #setPtrContentView} 方法来实现.
 *
 * @author clevergump
 */
public class PullToRefreshLayout3 extends LinearLayout {

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
    private ViewGroup mHeaderContainer;
    private ViewGroup mContentContainer;
    private View mHeaderView;
    private View mContentView;
    /**
     * headerview的高度
     */
    private int mHeaderHeight;
    private Scroller mScroller;
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


    public PullToRefreshLayout3(Context context) {
        this(context, null);
    }

    public PullToRefreshLayout3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout3(Context context, AttributeSet attrs, int defStyleAttr) {
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
        mPtrFrameView = View.inflate(mContext, R.layout.layout_ptr, this);
        mHeaderContainer = (ViewGroup) findViewById(R.id.vg_ptr_header);
        mContentContainer = (ViewGroup) findViewById(R.id.vg_ptr_content);

        mHeaderView = getPtrHeaderView();
        mContentView = getPtrContentView();
        mHeaderContainer.addView(mHeaderView);
        mContentContainer.addView(mContentView);
    }

    private void initData() {
        mScroller = new Scroller(mContext);
    }

    /**
     * 获取 headerview, 该方法需要设置 headerview 的 LayoutParams, 然后再返回.
     * @return
     */
    public View getPtrHeaderView(){
        View headerView = View.inflate(mContext, R.layout.ptr_header1, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(mContext, 240)));
        return headerView;
    }

    /**
     * 获取 contentview, 该方法需要设置 contentview 的 LayoutParams, 然后再返回.
     * @return
     */
    public View getPtrContentView(){
        TextView tv = new TextView(mContext);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setGravity(Gravity.CENTER);
        tv.setText("test");
        tv.setTextColor(Color.parseColor("#000000"));
        tv.setTextSize(40);
        return tv;
    }

    /**
     * 设置 headerview. 外界可用此方法传入自定义的 header view
     * @param headerView
     */
    public void setPtrHeaderView(View headerView){
        if (headerView == null) {
            return;
        }
        mHeaderContainer.removeAllViews();
        mHeaderContainer.addView(headerView);
    }

    /**
     * 设置可以下拉的内容view. 外界可用此方法传入自定义的 content view
     * @param contentView
     */
    public void setPtrContentView(View contentView){
        if (contentView == null) {
            return;
        }
        mContentView = contentView;
        mContentContainer.removeAllViews();
        mContentContainer.addView(mContentView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: changed = " + changed);
        if (changed && currState == STATE_FINISH_REFRESHING && mIsFirstLayout) {
            mHeaderHeight = mHeaderContainer.getMeasuredHeight();
            Toast.makeText(mContext, "header height = " + mHeaderHeight, Toast.LENGTH_SHORT).show();
//            scrollTo(0, mHeaderHeight);
            mIsFirstLayout = false;
        }
        super.onLayout(changed, l, t, r, b);
    }

    public void canPullDown(boolean canPullDown) {
        mCanPullDown = canPullDown;
    }

    /**
     * 平滑滚动
     *
     * @param destXCoor          目的地X坐标
     * @param destYCoor          目的地Y坐标
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
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}