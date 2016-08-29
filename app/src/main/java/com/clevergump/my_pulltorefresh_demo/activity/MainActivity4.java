package com.clevergump.my_pulltorefresh_demo.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.clevergump.my_pulltorefresh_demo.activity.base.PtrActivity4;
import com.clevergump.my_pulltorefresh_demo.utils.DensityUtils;
import com.clevergump.my_pulltorefresh_demo.widget.header.PtrHeaderLayout2;

public class MainActivity4 extends PtrActivity4 {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected View getHeaderView() {
        View headerView = new PtrHeaderLayout2(this);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtils.dip2px(this, 60)));
        return headerView;
    }
}