package com.clevergump.my_pulltorefresh_demo.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.clevergump.my_pulltorefresh_demo.PtrCommon;
import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.widget.PullToRefreshLayout9;
import com.clevergump.my_pulltorefresh_demo.widget.header.base.PtrHeaderBaseLayout8;

/**
 * 具有下拉刷新功能的 Activity
 *
 * @author clevergump
 */
public class PtrActivity8 extends AppCompatActivity {

    private PullToRefreshLayout9 mPtrLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr9);
        initView();
    }

    private void initView() {
        mPtrLayout = (PullToRefreshLayout9) findViewById(R.id.vg_ptr);
        PtrHeaderBaseLayout8 headerView = getHeaderView();
        if (headerView != null) {
            mPtrLayout.setPtrHeaderView(headerView);
        }

        View refreshView = getRefreshView();
        if (refreshView != null) {
            mPtrLayout.setPtrContentView(refreshView);
        }
    }

    /**
     * 获取下拉刷新的自定义 headerview. 空实现表示使用控件内部自带的.
     *
     * @return
     */
    protected PtrHeaderBaseLayout8 getHeaderView() {
        return null;
    }

    /**
     * 获取下拉刷新的自定义内容view. 空实现表示使用控件内部自带的.
     *
     * @return
     */
    protected View getRefreshView(){
        return null;
    }

    /**
     * 刷新完成后, 需要主动调用该方法, 以通知控件结束刷新.
     */
    protected void onRefreshComplete() {
        mPtrLayout.setState(PtrCommon.STATE_RESET);
    }

    protected void setOnRefreshListener(PullToRefreshLayout9.OnRefreshListener listener){
        mPtrLayout.setOnRefreshListener(listener);
    }

    protected void setCanPullDown(boolean canPullDown){
        mPtrLayout.setCanPullDown(canPullDown);
    }
}