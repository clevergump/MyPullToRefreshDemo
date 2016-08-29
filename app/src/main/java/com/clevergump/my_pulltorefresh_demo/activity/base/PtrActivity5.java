package com.clevergump.my_pulltorefresh_demo.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.widget.PullToRefreshLayout7;
import com.clevergump.my_pulltorefresh_demo.widget.header.base.PtrHeaderBaseLayout7;

/**
 * @author clevergump
 */
public class PtrActivity5 extends AppCompatActivity {

    protected PullToRefreshLayout7 mPtrLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr7);
        initView();
    }

    private void initView() {
        mPtrLayout = (PullToRefreshLayout7) findViewById(R.id.vg_ptr);
        PtrHeaderBaseLayout7 headerView = getHeaderView();
        if (headerView != null) {
            mPtrLayout.setPtrHeaderView(headerView);
        }
    }

    /**
     * 默认是空实现, 这样就使用的是控件内部自带的 headerview.
     * @return
     */
    protected PtrHeaderBaseLayout7 getHeaderView() {
        return null;
    }
}