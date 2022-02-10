package com.owl.android_simple.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Description
 *
 * @author zhangyunxiang Date 2022/2/8 16:16
 */
public class RotateRectView extends View {
  private int viewWidth;
  private int viewHeight;
  private Paint mPaint;
  private SweepGradient sweepGradient;
  private Matrix matrix;
  private float degree;
  private RectF rectF;
  ValueAnimator valueAnimator;

  public RotateRectView(Context context) {
    super(context);
    initView();
  }

  public RotateRectView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initView();
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    viewWidth = w;
    viewHeight = h;
    Log.i("zyx", "width= " + viewWidth + "\n" + "height= " + viewHeight);
  }

  private void initView() {
    mPaint = new Paint();
    mPaint.setDither(true);
    mPaint.setColor(Color.BLACK);
    mPaint.setAntiAlias(true);
    mPaint.setStyle(Style.STROKE);
    sweepGradient =
        new SweepGradient(
            viewWidth / 2,
            viewHeight / 2,
            new int[] {Color.argb(200, 200, 0, 0), Color.argb(10, 30, 0, 0)},
            null);
    matrix = new Matrix();
    rectF = new RectF(dip2px(15), dip2px(15), dip2px(50), dip2px(50));
  }

  public void setDegrees(float degrees) {
    this.degree = degrees;
    postInvalidate();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    matrix.postRotate(degree, viewWidth / 2, viewHeight / 2);
    sweepGradient.setLocalMatrix(matrix);
    mPaint.setShader(sweepGradient);
    if (degree != 0) {
      // canvas.concat(matrix);
      canvas.drawRoundRect(rectF, dip2px(10), dip2px(10), mPaint);
      // canvas.drawCircle(viewWidth / 2, viewHeight / 2, dip2px(40), mPaint);
      // canvas.drawLine(0, viewHeight / 2, viewWidth / 2, viewHeight / 2, mPaint);
    }
    if (degree >= 360) {
      degree = 0;
    }
    Log.i("zyx", "degree= " + degree);
  }

  public int dip2px(float dpValue) {
    final float scale = this.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }
}
