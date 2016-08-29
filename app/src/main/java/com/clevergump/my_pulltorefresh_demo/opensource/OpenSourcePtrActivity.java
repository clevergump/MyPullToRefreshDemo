package com.clevergump.my_pulltorefresh_demo.opensource;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.clevergump.my_pulltorefresh_demo.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author clevergump
 */
public class OpenSourcePtrActivity extends AppCompatActivity {

    private PullToRefreshListView mPtrLv;
    private int count = 0;
    private ArrayAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_os_ptr);

        mPtrLv = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getData(10));
        mPtrLv.setAdapter(mAdapter);
        mPtrLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });
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
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getData(3);
        }

        @Override
        protected void onPostExecute(List<String> result) {
            // Call onRefreshComplete when the list has been refreshed.
            if (result != null) {
                mAdapter.addAll(result);
                mAdapter.notifyDataSetChanged();
            }
            mPtrLv.onRefreshComplete();
        }
    }
}