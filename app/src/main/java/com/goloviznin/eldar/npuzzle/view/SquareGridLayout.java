package com.goloviznin.eldar.npuzzle.view;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;

public class SquareGridLayout extends GridLayout {

    public SquareGridLayout(Context context) {
        super(context);
    }

    public SquareGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int width = MeasureSpec.getSize(widthSpec);
        int height = MeasureSpec.getSize(heightSpec);
        int sizeSpec = (width > height) ? heightSpec : widthSpec;
        super.onMeasure(sizeSpec, sizeSpec);
    }
}
