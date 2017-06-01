package com.githang.stepview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 表示步骤的自定义控件
 *
 * @author Geek_Soledad (msdx.android@qq.com)
 * @since 2016-12-01
 */
public class StepView extends View {
    private static final int START_STEP = 1;

    private final List<String> mSteps = new ArrayList<>();
    private int mCurrentStep = START_STEP;

    private int mCircleColor;
    private int mTextColor;
    private int mSelectedColor;
    private int mFillRadius;
    private int mStrokeWidth;
    private int mLineWidth;
    private int mDrawablePadding;

    private Paint mPaint;

    public StepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StepView, 0, R.style.StepView);
        mCircleColor = ta.getColor(R.styleable.StepView_svCircleColor, 0);
        mTextColor = ta.getColor(R.styleable.StepView_svTextColor, 0);
        mSelectedColor = ta.getColor(R.styleable.StepView_svSelectedColor, 0);
        mFillRadius = ta.getDimensionPixelSize(R.styleable.StepView_svFillRadius, 0);
        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.StepView_svStrokeWidth, 0);
        mLineWidth = ta.getDimensionPixelSize(R.styleable.StepView_svLineWidth, 0);
        mDrawablePadding = ta.getDimensionPixelSize(R.styleable.StepView_svDrawablePadding, 0);
        final int textSize = ta.getDimensionPixelSize(R.styleable.StepView_svTextSize, 0);
        ta.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Paint.Align.CENTER);

        if (isInEditMode()) {
            String[] steps = {"Step 1", "Step 2", "Step 3"};
            setSteps(Arrays.asList(steps));
        }
    }

    public void setSteps(List<String> steps) {
        mSteps.clear();
        if (steps != null) {
            mSteps.addAll(steps);
        }
        selectedStep(START_STEP);
    }

    public void selectedStep(int step) {
        final int selected = step < START_STEP ?
                START_STEP : (step > mSteps.size() ? mSteps.size() : step);
        mCurrentStep = selected;
        invalidate();
    }

    public int getCurrentStep() {
        return mCurrentStep;
    }

    public int getStepCount() {
        return mSteps.size();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST) {
            final int fontHeight = (int) Math.ceil(mPaint.descent() - mPaint.ascent());
            height = getPaddingTop() + getPaddingBottom() + (mFillRadius + mStrokeWidth) * 2
                    + mDrawablePadding + fontHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int stepSize = mSteps.size();
        if (stepSize == 0) {
            return;
        }
        final int width = getWidth();

        final float ascent = mPaint.ascent();
        final float descent = mPaint.descent();
        final int fontHeight = (int) Math.ceil(descent - ascent);
        final int halfFontHeightOffset = -(int)(ascent + descent) / 2;
        final int bigRadius = mFillRadius + mStrokeWidth;
        final int startCircleY = getPaddingTop() + bigRadius;
        final int childWidth = width / stepSize;
        for (int i = 1; i <= stepSize; i++) {
            drawableStep(canvas, i, halfFontHeightOffset, fontHeight, bigRadius,
                    childWidth * i - childWidth / 2, startCircleY);
        }
        final int halfLineLength = childWidth / 2 - bigRadius;
        for (int i = 1; i < stepSize; i++) {
            final int lineCenterX = childWidth * i;
            drawableLine(canvas, lineCenterX - halfLineLength,
                    lineCenterX + halfLineLength, startCircleY);
        }
    }

    private void drawableStep(Canvas canvas, int step, int halfFontHeightOffset, int fontHeight,
                              int bigRadius, int circleCenterX, int circleCenterY) {
        final String text = mSteps.get(step - 1);
        final boolean isSelected = step == mCurrentStep;

        if (isSelected) {
            mPaint.setStrokeWidth(mStrokeWidth);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(mCircleColor);
            canvas.drawCircle(circleCenterX, circleCenterY, mFillRadius + mStrokeWidth / 2, mPaint);

            mPaint.setColor(mSelectedColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(circleCenterX, circleCenterY, mFillRadius, mPaint);
        } else {
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mCircleColor);
            canvas.drawCircle(circleCenterX, circleCenterY, bigRadius, mPaint);
        }

        mPaint.setFakeBoldText(true);
        mPaint.setColor(Color.WHITE);
        String number = String.valueOf(step);
        canvas.drawText(number, circleCenterX, circleCenterY + halfFontHeightOffset, mPaint);

        mPaint.setFakeBoldText(false);
        mPaint.setColor(isSelected ? mSelectedColor : mTextColor);
        canvas.drawText(text, circleCenterX,
                circleCenterY + bigRadius + mDrawablePadding + fontHeight / 2, mPaint);
    }

    private void drawableLine(Canvas canvas, int startX, int endX, int centerY) {
        mPaint.setColor(mCircleColor);
        mPaint.setStrokeWidth(mLineWidth);
        canvas.drawLine(startX, centerY, endX, centerY, mPaint);
    }
}
