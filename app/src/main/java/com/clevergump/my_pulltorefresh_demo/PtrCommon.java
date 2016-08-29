package com.clevergump.my_pulltorefresh_demo;

/**
 * @author clevergump
 */
public class PtrCommon {
    /**
     * 还未开始刷新或结束刷新的状态
     */
    public static final int STATE_RESET = 0;
    /**
     * 下拉刷新的状态
     */
    public static final int STATE_PULL_TO_REFRESH = 1;
    /**
     * 释放刷新的状态
     */
    public static final int STATE_RELEASH_TO_REFRESH = 2;
    /**
     * 正在刷新的状态
     */
    public static final int STATE_REFRESHING = 3;
}