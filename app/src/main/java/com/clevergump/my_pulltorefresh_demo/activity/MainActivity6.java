package com.clevergump.my_pulltorefresh_demo.activity;

import android.os.Bundle;

import com.clevergump.my_pulltorefresh_demo.activity.base.PtrActivity6;
import com.clevergump.my_pulltorefresh_demo.widget.header.base.PtrHeaderBaseLayout7_1;
import com.clevergump.my_pulltorefresh_demo.widget.header.PtrHeaderLayout7_1;

public class MainActivity6 extends PtrActivity6 {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected PtrHeaderBaseLayout7_1 getHeaderView() {
        PtrHeaderBaseLayout7_1 headerView = new PtrHeaderLayout7_1(this);
//        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                DensityUtils.dip2px(this, 60)));
        return headerView;
    }
}