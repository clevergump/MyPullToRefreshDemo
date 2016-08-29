package com.clevergump.my_pulltorefresh_demo.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.clevergump.my_pulltorefresh_demo.PtrCommon;
import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.widget.PullToRefreshLayout8;
import com.clevergump.my_pulltorefresh_demo.widget.header.base.PtrHeaderBaseLayout8;

/**
 * @author clevergump
 */
public class PtrActivity7 extends AppCompatActivity {

    private PullToRefreshLayout8 mPtrLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr8);
        initView();
    }

    private void initView() {
        mPtrLayout = (PullToRefreshLayout8) findViewById(R.id.vg_ptr);
        PtrHeaderBaseLayout8 headerView = getHeaderView();
        if (headerView != null) {
            mPtrLayout.setPtrHeaderView(headerView);
        }
    }

    /**
     * 默认是空实现, 这样就使用的是控件内部自带的 headerview.
     *
     * @return
     */
    protected PtrHeaderBaseLayout8 getHeaderView() {
        return null;
    }

    /**
     * 刷新完成后, 需要主动调用该方法, 以通知控件结束刷新.
     */
    protected void onRefreshComplete() {
        mPtrLayout.setState(PtrCommon.STATE_RESET);
    }

    protected void setOnRefreshListener(PullToRefreshLayout8.OnRefreshListener listener){
        mPtrLayout.setOnRefreshListener(listener);
    }
}