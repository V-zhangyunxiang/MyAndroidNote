package com.owl.android_simple.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Description 跟着手指划线，并用 CornerPathEffect 使边缘更光滑
 *
 * @author zhangyunxiang Date 2022/1/21 17:35
 */
public class DrawLineView extends View {
  Paint mPaint = new Paint();
  PathEffect mPathEffect = new CornerPathEffect(200);
  Path mPath = new Path();

  public DrawLineView(Context context) {
    this(context, null);
  }

  public DrawLineView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    mPaint.setStrokeWidth(20);
    mPaint.setStyle(Paint.Style.STROKE);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getActionMasked()) {
      case MotionEvent.ACTION_DOWN:
        mPath.reset();
        mPath.moveTo(event.getX(), event.getY());
        break;
      case MotionEvent.ACTION_MOVE:
        mPath.lineTo(event.getX(), event.getY());
        break;
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_UP:
        break;
    }
    postInvalidate();
    return true;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // 绘制原始路径
    canvas.save();
    mPaint.setColor(Color.BLACK);
    mPaint.setPathEffect(null);
    canvas.drawPath(mPath, mPaint);
    canvas.restore();

    // 绘制带有效果的路径
    canvas.save();
    canvas.translate(getWidth() / 2, getHeight() / 2);
    mPaint.setColor(Color.RED);
    mPaint.setPathEffect(mPathEffect);
    canvas.drawPath(mPath, mPaint);
    canvas.restore();
  }
}
