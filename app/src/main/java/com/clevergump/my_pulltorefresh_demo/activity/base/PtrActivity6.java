package com.clevergump.my_pulltorefresh_demo.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.widget.PullToRefreshLayout7_1;
import com.clevergump.my_pulltorefresh_demo.widget.header.base.PtrHeaderBaseLayout7_1;

/**
 * @author clevergump
 */
public class PtrActivity6 extends AppCompatActivity {

    protected PullToRefreshLayout7_1 mPtrLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr7_1);
        initView();
    }

    private void initView() {
        mPtrLayout = (PullToRefreshLayout7_1) findViewById(R.id.vg_ptr);
        PtrHeaderBaseLayout7_1 headerView = getHeaderView();
        if (headerView != null) {
            mPtrLayout.setPtrHeaderView(headerView);
        }
    }

    /**
     * 默认是空实现, 这样就使用的是控件内部自带的 headerview.
     * @return
     */
    protected PtrHeaderBaseLayout7_1 getHeaderView() {
        return null;
    }
}