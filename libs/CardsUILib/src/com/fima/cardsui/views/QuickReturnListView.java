package com.fima.cardsui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class QuickReturnListView extends ListView {

    private int mItemOffsetY[];
    private boolean scrollIsComputed = false;
    private int mHeight;

    public QuickReturnListView(Context context) {
        super(context);
    }

    public QuickReturnListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getListHeight() {
        return mHeight;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void computeScrollY() {
        mHeight = 0;
        try {
            int mItemCount = getAdapter().getCount();
            if (mItemOffsetY == null) {
                mItemOffsetY = new int[mItemCount];
            }
            for (int i = 0; i < mItemCount; ++i) {
                View view = getAdapter().getView(i, null, this);
                if (view != null) {
                    view.measure(
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                }
                mItemOffsetY[i] = mHeight;
                mHeight += view != null ? view.getMeasuredHeight() : 0;
            }
            scrollIsComputed = true;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public boolean scrollYIsComputed() {
        return scrollIsComputed;
    }

    public int getComputedScrollY() {
        int pos, nScrollY, nItemY = 0;
        View view;
        pos = getFirstVisiblePosition();
        view = getChildAt(0);
        if (view != null) nItemY = view.getTop();
        nScrollY = mItemOffsetY[pos] - nItemY;
        return nScrollY;
    }
}