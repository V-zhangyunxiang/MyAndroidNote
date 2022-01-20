package com.owl.android_simple.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.owl.android_simple.R;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/12/1 11:31
 */
public class VoiceEndFireProgressView extends View {
  private Paint mPaint;
  private RectF backgroundRectF;
  private RectF foregroundRectF;
  private float percentage;
  private int backgroundColor;
  private int foregroundColor;

  public VoiceEndFireProgressView(Context context) {
    super(context);
    initPaint(context);
  }

  public VoiceEndFireProgressView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initPaint(context);
  }

  public VoiceEndFireProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
  }

  private void initPaint(Context context) {
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setDither(true);
    backgroundRectF = new RectF();
    foregroundRectF = new RectF();
    backgroundColor = ContextCompat.getColor(context, R.color.live_white_30_opacity);
    foregroundColor = ContextCompat.getColor(context, R.color.live_fe7e1d);
  }

  public void setViewData(int currentProgress) {
    percentage = (float) currentProgress / 100;
    invalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPaint.setColor(backgroundColor);
    backgroundRectF.set(0, 0, getWidth(), getHeight());
    canvas.drawRoundRect(backgroundRectF, dip2px(5), dip2px(5), mPaint);

    float completeWidth = getWidth() * percentage;
    foregroundRectF.set(0, 0, completeWidth, getHeight());
    mPaint.setColor(foregroundColor);
    canvas.drawRoundRect(foregroundRectF, dip2px(5), dip2px(5), mPaint);
  }

  public int dip2px(float dpValue) {
    final float scale = this.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }
}
