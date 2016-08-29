package com.clevergump.my_pulltorefresh_demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author clevergump
 */
public abstract class SuperAdapter<T> extends BaseAdapter {

    protected List<T> mItems = new ArrayList<>();

    protected Context mContext;

    protected LayoutInflater mLayoutInflater;

    public SuperAdapter(Context mContext) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void addData(T t) {
        if (t != null) {
            mItems.add(t);
        }
    }

    /**
     * 在原有数据基础上添加新数据集合, 原有数据未删除. 如需删除原有数据, 请先调用 {@link #clear()} 方法
     * 然后再调用该方法
     *
     * @param appendedData
     */
    public void addAll(List<T> appendedData) {
        if (appendedData != null && appendedData.size() > 0) {
            mItems.addAll(appendedData);
        }
    }

    public void clear(){
        mItems.clear();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}