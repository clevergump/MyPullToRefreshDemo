package com.clevergump.my_pulltorefresh_demo.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author clevergump
 */
public class TestViewGroup extends LinearLayout {


    public TestViewGroup(Context context) {
        super(context);
    }

    public TestViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d("test", "----------DOWN------------------------------");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("test", "----------MOVE------------------------------");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("test", "----------UP--------------------------------");
                break;
        }
        Log.d("test", "ViewGroup ---- dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("test", "ViewGroup ---- onInterceptTouchEvent");
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                return false;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("test", "ViewGroup ---- onTouchEvent");
        return true;
    }
}