package com.clevergump.my_pulltorefresh_demo.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.activity.base.PtrActivity3;
import com.clevergump.my_pulltorefresh_demo.utils.DensityUtils;

public class MainActivity3 extends PtrActivity3 {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected View getHeaderView() {
        View headerView = View.inflate(this, R.layout.ptr_header2, null);
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtils.dip2px(this, 60)));
        return headerView;
    }
}