package com.clevergump.my_pulltorefresh_demo.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.utils.DensityUtils;
import com.clevergump.my_pulltorefresh_demo.widget.PullToRefreshLayout3;

/**
 * @author clevergump
 */
public class PtrActivity1 extends AppCompatActivity {

    protected PullToRefreshLayout3 mPtrLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr3);
        initView();
    }

    private void initView() {
        mPtrLayout = (PullToRefreshLayout3) findViewById(R.id.vg_ptr);
        mPtrLayout.setPtrHeaderView(getHeaderView());
    }

    protected View getHeaderView() {
        View headerView = View.inflate(this, R.layout.ptr_header2, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtils.dip2px(this, 80)));
        return headerView;
    }


}