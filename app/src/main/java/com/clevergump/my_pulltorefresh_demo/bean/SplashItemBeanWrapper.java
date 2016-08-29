package com.clevergump.my_pulltorefresh_demo.bean;

/**
 * @author clevergump
 */
public class SplashItemBeanWrapper<T> {

    public static final int TYPE_TITLE = 0;
//    public static final int TYPE_ROOM = 1;
//    public static final int TYPE_ROOM_MORE = 2;
//    public static final int TYPE_DIVIDER = 3;
//    public static final int TYPE_USER_TITLE = 4;
//    public static final int TYPE_USER = 5;

    public static int[] mTypes = {
            TYPE_TITLE,
//            TYPE_ROOM,
//            TYPE_ROOM_MORE,
//            TYPE_DIVIDER,
//            TYPE_USER_TITLE,
//            TYPE_USER
    };

    /**
     * 可以取 TYPE_ROOM_TITLE, TYPE_ROOM, TYPE_ROOM_MORE, TYPE_DIVIDER, TYPE_USER_TITLE, TYPE_USER
     */
    public int mType;

    /**
     *  可以取以下类型: SearchRoomTitle, LiveRoom, SearchRoomMore, SearchDivider, SearchUserTitle, SearchUser
     */
    public T mBean;

    public SplashItemBeanWrapper(int type, T bean) {
        this.mType = type;
        this.mBean = bean;
    }
}