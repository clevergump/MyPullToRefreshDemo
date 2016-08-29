package com.clevergump.my_pulltorefresh_demo.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.clevergump.my_pulltorefresh_demo.activity.base.PtrActivity7;
import com.clevergump.my_pulltorefresh_demo.widget.PullToRefreshLayout8;
import com.clevergump.my_pulltorefresh_demo.widget.header.PtrHeaderLayout8;
import com.clevergump.my_pulltorefresh_demo.widget.header.base.PtrHeaderBaseLayout8;

public class MainActivity7 extends PtrActivity7 {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setOnRefreshListener(new PullToRefreshLayout8.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetDataTask().execute();
            }
        });
    }

    @Override
    protected PtrHeaderBaseLayout8 getHeaderView() {
        PtrHeaderBaseLayout8 headerView = new PtrHeaderLayout8(this);
//        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                DensityUtils.dip2px(this, 60)));
        return headerView;
    }

    private class GetDataTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            // 模拟网络请求的过程
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "网络请求得到的数据";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            onRefreshComplete();
        }
    }
}