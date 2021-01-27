package com.owl.android_simple.animate;

import android.animation.TypeEvaluator;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/1/22 16:34
 */
public class PointEvaluator implements TypeEvaluator<Object> {

  @Override
  public Object evaluate(float fraction, Object startValue, Object endValue) {
    Point startPoint = (Point) startValue;
    Point endPoint = (Point) endValue;
    float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
    float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
    return new Point(x, y);
  }
}
