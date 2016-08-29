package com.clevergump.my_pulltorefresh_demo.activity;

import android.os.Bundle;

import com.clevergump.my_pulltorefresh_demo.activity.base.PtrActivity2;

public class MainActivity2 extends PtrActivity2 {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    protected View getHeaderView() {
//        View headerView = View.inflate(this, R.layout.ptr_header2, null);
//        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                DensityUtils.dip2px(this, 60)));
//        return headerView;
//    }
}