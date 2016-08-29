package com.clevergump.my_pulltorefresh_demo.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clevergump.my_pulltorefresh_demo.R;
import com.clevergump.my_pulltorefresh_demo.bean.SplashItemBeanWrapper;
import com.clevergump.my_pulltorefresh_demo.bean.SplashStringItem;
import com.clevergump.my_pulltorefresh_demo.utils.DensityUtils;

/**
 * @author clevergump
 */
public class SplashAdapter extends SuperAdapter<SplashItemBeanWrapper> {

    public SplashAdapter(Context mContext) {
        super(mContext);
    }

    /**
     * index : itemViewType
     */
    private int[] mItemLayoutIds = {
            R.layout.item_splash_title,
    };

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, mItemLayoutIds[getItemViewType(position)], null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setIndex(position);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        SplashItemBeanWrapper item = (SplashItemBeanWrapper) getItem(position);
        return item.mType;
    }

    @Override
    public int getViewTypeCount() {
        return SplashItemBeanWrapper.mTypes.length;
    }

    private class ViewHolder {
        private View mItemView;

        private SplashStringItem mTitleBean;

        public ViewHolder(View itemView) {
            this.mItemView = itemView;
        }

        public void setIndex(int position) {
            initView(position);
        }

        private void initView(int position) {
            int viewType = getItemViewType(position);
            Object itemBean = getItem(position);
            switch (viewType) {
                case SplashItemBeanWrapper.TYPE_TITLE:
                    if (mItemView instanceof TextView && itemBean instanceof SplashItemBeanWrapper) {
                        TextView tv = (TextView) mItemView;
                        Object actualBean = ((SplashItemBeanWrapper) itemBean).mBean;
                        if (actualBean instanceof CharSequence) {
                            tv.setText((CharSequence) actualBean);
                        }
                        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                DensityUtils.dip2px(mContext, 60)));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(DensityUtils.sp2px(mContext, 6));
                    }
                    break;
            }
        }
    }

}