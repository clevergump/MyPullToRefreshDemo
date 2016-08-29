package com.clevergump.my_pulltorefresh_demo.activity;

import android.os.Bundle;

import com.clevergump.my_pulltorefresh_demo.activity.base.PtrActivity5;
import com.clevergump.my_pulltorefresh_demo.widget.header.base.PtrHeaderBaseLayout7;
import com.clevergump.my_pulltorefresh_demo.widget.header.PtrHeaderLayout2;

public class MainActivity5 extends PtrActivity5 {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected PtrHeaderBaseLayout7 getHeaderView() {
        PtrHeaderBaseLayout7 headerView = new PtrHeaderLayout2(this);
//        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                DensityUtils.dip2px(this, 60)));
        return headerView;
    }
}