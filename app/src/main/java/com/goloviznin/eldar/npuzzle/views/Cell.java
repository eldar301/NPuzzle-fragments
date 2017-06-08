package com.goloviznin.eldar.npuzzle.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;


public class Cell extends android.support.v7.widget.AppCompatButton {

    private final double textSizeMultiplier = 0.35f;

    public Cell(Context context) {
        super(context);
    }

    public Cell(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Cell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        adjustTextSize();
    }

    public void adjustTextSize() {
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setTextSize(TypedValue.COMPLEX_UNIT_PX, (float)(size * textSizeMultiplier));
    }
    
    public void setNumber(int number) {
        super.setText((number != 0) ? Integer.toString(number) : "");
    }

    public int getNumber() {
        String text = super.getText().toString();
        return (text.equals("") ? 0 : Integer.valueOf(text));
    }
}
