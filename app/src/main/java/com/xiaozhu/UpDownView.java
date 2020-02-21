package com.xiaozhu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description
 *
 * @author zhangyunxiang
 * Date 2020-02-19 14:46
 */
public class UpDownView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mTop = 0;
    private float mBottom = dip2px(110);

    public UpDownView(Context context) {
        super(context);
    }

    public UpDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UpDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint.setColor(Color.parseColor("#D0D0D0"));
        mPaint.setStyle(Paint.Style.FILL);
    }

    public float getTopProgress() {
        return mTop;
    }

    /**
     * 递减左上角 Y 轴坐标
     */
    public void setTopProgress(float topProgress) {
        this.mTop = dip2px(topProgress);
        invalidate();
    }

    public float getBottomProgress() {
        return mBottom;
    }

    /**
     * 递减右下角 Y 轴坐标
     */
    public void setBottomProgress(float bottomProgress) {
        this.mBottom = dip2px(bottomProgress);
        invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int left = dip2px(44);
        int right = dip2px(58);
        canvas.drawRoundRect(left, mTop, right, mBottom, 50, 50, mPaint);
    }

    public int dip2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
