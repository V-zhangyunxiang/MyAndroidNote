package com.owl.android_simple.animate;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.BounceInterpolator;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/1/22 16:47
 */
public class MyTypeEvaluatorView extends View {

  public MyTypeEvaluatorView(Context context) {
    super(context);
  }

  public MyTypeEvaluatorView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setColor(Color.BLUE);
  }

  public MyTypeEvaluatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public static final float RADIUS = 50f;

  private Point currentPoint;

  private Paint mPaint;

  @Override
  protected void onDraw(Canvas canvas) {
    if (currentPoint == null) {
      currentPoint = new Point(RADIUS, RADIUS);
      drawCircle(canvas);
      // startAnimation();
      startInterpolatorAnimation();
    } else {
      drawCircle(canvas);
    }
  }

  private void drawCircle(Canvas canvas) {
    float x = currentPoint.getX();
    float y = currentPoint.getY();
    canvas.drawCircle(x, y, RADIUS, mPaint);
  }

  /** 属性动画不光能控制 View，非 View 对象也能控制；也不局限于 伸缩，透明，位移，旋转，其它变化也能 */
  private void startAnimation() {
    Point startPoint = new Point(RADIUS, RADIUS);
    Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
    ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
    anim.addUpdateListener(
        animation -> {
          currentPoint = (Point) animation.getAnimatedValue();
          invalidate();
        });
    anim.setDuration(5000);
    anim.start();
  }

  private void startInterpolatorAnimation() {
    Point startPoint = new Point(getWidth() / 2, RADIUS);
    Point endPoint = new Point(getWidth() / 2, getHeight() - RADIUS);
    ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
    anim.addUpdateListener(
        animation -> {
          currentPoint = (Point) animation.getAnimatedValue();
          invalidate();
        });
    // 默认加速器是先加速后减速
    // AccelerateInterpolator(float factor)持续加速，factor 加速因子，会放大先慢后快的速度
    anim.setInterpolator(new BounceInterpolator());
    anim.setRepeatCount(-1);
    anim.setDuration(5000);
    anim.start();
  }

  public void ViewPropertyAnimator() {
    // 会创建并返回一个 ViewPropertyAnimator 的实例，之后的调用的所有方法，设置的所有属性都是通过这个实例完成的
    // ViewPropertyAnimator 会隐式启动动画，也可显式调用 start
    ViewPropertyAnimator viewPropertyAnimator = this.animate();
    viewPropertyAnimator.x(500).y(500).setDuration(5000).setInterpolator(new BounceInterpolator());
    viewPropertyAnimator.setListener(
        new AnimatorListener() {
          @Override
          public void onAnimationStart(Animator animation) {}

          @Override
          public void onAnimationEnd(Animator animation) {}

          @Override
          public void onAnimationCancel(Animator animation) {}

          @Override
          public void onAnimationRepeat(Animator animation) {}
        });
  }
}
