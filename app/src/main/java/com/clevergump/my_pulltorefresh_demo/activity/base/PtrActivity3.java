package com.clevergump.my_pulltorefresh_demo.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.widget.PullToRefreshLayout5;

/**
 * @author clevergump
 */
public class PtrActivity3 extends AppCompatActivity {

    protected PullToRefreshLayout5 mPtrLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr5);
        initView();
    }

    private void initView() {
        mPtrLayout = (PullToRefreshLayout5) findViewById(R.id.vg_ptr);
        View headerView = getHeaderView();
        if (headerView != null) {
            mPtrLayout.setPtrHeaderView(headerView);
        }
    }

    /**
     * 默认是空实现, 这样就使用的是控件内部自带的 headerview.
     * @return
     */
    protected View getHeaderView() {
        return null;
    }
}