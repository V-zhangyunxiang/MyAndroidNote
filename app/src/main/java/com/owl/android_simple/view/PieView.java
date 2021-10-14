package com.owl.android_simple.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import com.owl.android_simple.data.PieData;
import java.util.List;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/1/29 16:33
 */
public class PieView extends View {

  private List<PieData> mData;
  private Paint mPaint = new Paint();
  private int mWidth, mHeight;
  private float mStartAngle;

  public PieView(Context context) {
    super(context);
  }

  public PieView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setAntiAlias(true);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mWidth = w;
    mHeight = h;
    Log.i("zyx", "width= " + mWidth + " height= " + mHeight);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (mData == null) {
      return;
    }
    // 将画布坐标原点移动到中心位置
    canvas.translate(mWidth / 2, mHeight / 2);
    float currentAngle = mStartAngle;
    float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
    RectF rectF = new RectF(-r, -r, r, r);
    for (PieData data : mData) {
      mPaint.setColor(data.color);
      data.angle = data.percentage * 360;
      canvas.drawArc(rectF, currentAngle, data.angle, true, mPaint);
      currentAngle += data.angle;
    }
  }

  private void setStartAngle(float startAngle) {
    this.mStartAngle = startAngle;
  }

  public void setData(List<PieData> pieDataList) {
    this.mData = pieDataList;
    invalidate();
  }
}
