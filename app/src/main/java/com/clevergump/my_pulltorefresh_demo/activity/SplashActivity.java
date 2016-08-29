package com.clevergump.my_pulltorefresh_demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.adapter.SplashAdapter;
import com.clevergump.my_pulltorefresh_demo.bean.SplashItemBeanWrapper;
import com.clevergump.my_pulltorefresh_demo.utils.AppUtils;

/**
 * @author clevergump
 */
public class SplashActivity extends AppCompatActivity {

    private ListView mLv;
    private SplashAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mLv = (ListView) findViewById(R.id.lv);
        mAdapter = new SplashAdapter(this);
        mLv.setAdapter(mAdapter);
    }

    private void initData() {
        for (int i=1; i<9; i++) {
            SplashItemBeanWrapper itemBeanWrapper = new SplashItemBeanWrapper(SplashItemBeanWrapper.TYPE_TITLE, "MainActivity"+i);
            mAdapter.addData(itemBeanWrapper);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initListener() {
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppUtils.launchApp(SplashActivity.this, new Intent(SplashActivity.this, getDestActClass(position)));
            }
        });
    }

    private Class<?> getDestActClass(int position) {
        switch (position) {
            case 0:
                return MainActivity1.class;
            case 1:
                return MainActivity2.class;
            case 2:
                return MainActivity3.class;
            case 3:
                return MainActivity4.class;
            case 4:
                return MainActivity5.class;
            case 5:
                return MainActivity6.class;
            case 6:
                return MainActivity7.class;
            case 7:
            default:
                return MainActivity8.class;
        }
    }
}