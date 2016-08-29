package com.clevergump.my_pulltorefresh_demo.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.clevergump.my_pulltorefresh_demo.activity.base.PtrActivity8;
import com.clevergump.my_pulltorefresh_demo.widget.PullToRefreshLayout9;

import java.util.ArrayList;
import java.util.List;

public class MainActivity8 extends PtrActivity8 {

    private final String TAG = getClass().getSimpleName();
    private int count = 0;
    private ListView mLv;
    private List<String> mListData;
    private ArrayAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setCanPullDown(false);
        setOnRefreshListener(new PullToRefreshLayout9.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetDataTask().execute();
            }
        });
    }

    //    @Override
//    protected PtrHeaderBaseLayout8 getHeaderView() {
//        PtrHeaderBaseLayout8 h = new PtrHeaderLayout8_1(this);
//        h.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                DensityUtils.dip2px(this, 100)));
//        return h;
//    }

    @Override
    protected View getRefreshView() {
//        TextView tv = new TextView(this);
//        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        tv.setGravity(Gravity.CENTER);
//        tv.setText("CUSTOM CONTENT");
//        tv.setTextColor(Color.parseColor("#000000"));
//        tv.setTextSize(40);
//        tv.setBackgroundColor(Color.parseColor("#5500ff00"));
//        tv.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(MainActivity9.this, "long click", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity9.this, "click", Toast.LENGTH_SHORT).show();
//            }
//        });
//        return tv;

        mLv = new ListView(this);
//        mLv.setBackgroundColor(Color.parseColor("#33ffff00"));
        if (mListData == null) {
            mListData = new ArrayList<>();
        }
        mListData.clear();
        mListData.addAll(getData(5));
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mListData);
        mLv.setAdapter(mAdapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity8.this, "click item " + position, Toast.LENGTH_SHORT).show();
            }
        });
        return mLv;
    }

    private List<String> getData(int num) {
        if (num <= 0) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add("item " + (count++));
        }
        return list;
    }


    private class GetDataTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... params) {
            // 模拟网络请求的过程
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<String> list = new ArrayList<>(2);
            for(int dest = count + 2; count < dest; count++){
                list.add("刷新得到的 item " + count);
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<String> data) {
            if (mLv != null && data != null && data.size() > 0) {
                mListData.addAll(data);
                mAdapter.notifyDataSetChanged();
            }
            onRefreshComplete();
        }
    }
}