package com.rebolt.ark.popularmoviesone.activity;


import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by pakhi on 20-08-2016.
 */
public class RecyclerViewTouch implements RecyclerView.OnItemTouchListener{

    private OnItemClickListener mlistner;

    public interface OnItemClickListener{
        public void onItemClick(View view,int position);
    }

    GestureDetector mgestureDetector;

    public RecyclerViewTouch(Context context, OnItemClickListener listener) {
        mlistner = listener;
        mgestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mlistner != null && mgestureDetector.onTouchEvent(e)) {
            mlistner.onItemClick(childView, rv.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
