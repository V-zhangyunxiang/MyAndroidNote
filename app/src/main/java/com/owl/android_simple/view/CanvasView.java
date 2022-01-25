package com.owl.android_simple.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PathEffect;
import android.graphics.Picture;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.graphics.drawable.PictureDrawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import com.owl.android_simple.R;
import java.util.Locale;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/1/28 14:56
 */
public class CanvasView extends View {
  private static final String TAG = "zyx";
  Paint mPaint = new Paint();
  // Paint mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
  Picture mPicture = new Picture();
  private int mWidth;
  private int mHeight;
  private String mText;
  private int mTextSize;

  // 在 new CanvasView() 时调用
  public CanvasView(Context context) {
    super(context);
    initPaint();
  }
  // 在 layout 文件声明时调用，View 的属性值来自 AttributeSet 的值
  public CanvasView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    TypedArray typedArray =
        context.getTheme().obtainStyledAttributes(attrs, R.styleable.CanvasView, 0, 0);
    try {
      mText = typedArray.getString(R.styleable.CanvasView_text);
      mText = typedArray.getString(R.styleable.CanvasView_textSize);
    } finally {
      typedArray.recycle();
    }
    initPaint();
  }
  // 提供了默认的 defStyleAttr 用于指定基本的属性，允许 View 有自己基础的风格，默认值 0
  // 如 com.android.internal.R.attr.buttonStyle
  public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }
  // 多了一种提供 View 默认属性的一种方式，代码中传入 R.style.XX 就可以了。默认值 0，这个参数只有 defStyleAttr 为 0 的时候才会生效
  public CanvasView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  /*
   * 设置笔颜色
   *
   * 1.基础颜色-ColorFilter-Xfermode
   *
   * 2.直接设置颜色的 API 用来给图形和文字设置颜色
   *
   *   2.1 setColorFilter() 用来基于颜色进行过滤处理
   *   2.2 setXfermode() 用来处理源图像和 View 已有内容的关系
   */
  private void initPaint() {
    Log.i("zyx", "initPaint");
    // 五种颜色表示方法
    mPaint.setColor(Color.BLACK);
    // Color.argb(127, 255, 0, 255);
    // 0xff00ff00;
    // mPaint.setColor(Color.parseColor("#009688"));
    // mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    // 渲染模式
    // STROKE                //描边
    // FILL                  //填充
    // FILL_AND_STROKE       //描边加填充，表示会加上描边的宽度
    mPaint.setStyle(Style.FILL);
    // 描边宽度
    mPaint.setStrokeWidth(10);
    // 抗锯齿
    mPaint.setAntiAlias(true);
    // 设置文本字体大小
    mPaint.setTextSize(50);
    // 设置字体
    mPaint.setTypeface(Typeface.SERIF);
    // mPaint.setTypeface(
    //  Typeface.createFromAsset(getContext().getAssets(), "futura_bold_italic_font.ttf"));
    // 设置粗体
    mPaint.setFakeBoldText(false);
    // 设置文字对齐方式,该设置影响 drawText x 坐标的位置,x 根据对齐方式而定，默认是 left，指在文字的左边
    mPaint.setTextAlign(Align.LEFT);
    // 设置绘制使用的 locale
    mPaint.setTextLocale(Locale.CHINA);
    // 设置删除线
    mPaint.setStrikeThruText(false);
    // 设置下划线
    mPaint.setUnderlineText(false);
    // 设置文字横向错切角度,文字倾斜度
    mPaint.setTextSkewX(0);
    // 设置文字横向缩放，也就是每个字符变胖变瘦
    // 值> 1.0 会使文本更宽。值 <1.0 将缩小文本范围
    mPaint.setTextScaleX(1);
    // 设置字符间距,负值会拉紧文本
    mPaint.setLetterSpacing(0);
    // 获取推荐的行距
    mPaint.getFontSpacing();
    // 获取 paint 的 FontMetrics
    FontMetrics fontMetrics = mPaint.getFontMetrics();
    // fontMetrics.top;
    // fontMetrics.bottom;
    // fontMetrics.ascent;
    // fontMetrics.descent;
    // 抖动
    mPaint.setDither(true);
    // 双线性过滤
    mPaint.setFilterBitmap(true);
    // 重置 paint 的所有属性
    // mPaint.reset();
    // 将目标 paint 属性复制到当前 paint 中
    // mPaint.set(new Paint());
    // 批量设置 flags，相当于依次调用它们的 set 方法，多次调用只有最新的一次会生效，不建议使用
    // mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
  }

  /**
   * 定义: 测量 View 的大小
   *
   * <p>如果对 View 的宽高进行修改了，不要调用 super.onMeasure(widthMeasureSpec, heightMeasureSpec)，要调用
   * setMeasuredDimension(widthSize,heightSize) 这个函数
   *
   * <p>MeasureSpec 一个 int 有 32 bit，将它的高 2 位用来代表测量模式 Mode，低 30 位用来代表数值大小 Size
   *
   * <p>MeasureSpec.EXACTLY 表示父控件已经确切的指定了子 View 的大小(数值或 match_parent)
   *
   * <p>MeasureSpec.AT_MOST 表示子 View 大小由自身内容决定，但不能超过父 View 大小(wrap_content)
   *
   * <p>MeasureSpec.UNSPECIFIED 默认值，父控件没有给子 View 任何限制，子 View 可以设置为任意大小
   *
   * <p>MeasureSpec.makeMeasureSpec(int size, int mode)) 将 Mode 和 Size 组合成一个 measureSpec 数值
   *
   * <p>public static int resolveSize(int size , int measureSpec) 系统提供判断测量模式的代码，size
   * 表示你希望的宽或高，返回计算后的宽或高
   *
   * @param widthMeasureSpec
   * @param heightMeasureSpec
   */
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //    // 父 View 推荐的宽高
    //    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    //    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    //    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    //    int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    //    // 自身内容宽高
    //    int contentW;
    //    int contentH;
    //    // resultW 最终设置的宽，resultH 最终设置的高
    //    int resultW = widthSize;
    //    int resultH = heightSize;
    //
    //    contentW = (int) mPaint.measureText(mText);
    //    contentW += getPaddingLeft() + getPaddingRight();
    //
    //    contentH = mTextSize;
    //    contentH += getPaddingTop() + getPaddingBottom();
    //
    //    if (widthMode == MeasureSpec.AT_MOST) {
    //      if (!TextUtils.isEmpty(mText)) {
    //        resultW = Math.min(contentW, widthSize);
    //        resultH = Math.min(contentH, heightSize);
    //      }
    //    } else if (widthMode == MeasureSpec.EXACTLY) {
    //      // 与默认值相同
    //    } else {
    //      resultW = contentW;
    //      resultH = contentH;
    //    }
    //    setMeasuredDimension(resultW, resultH);
    //
    //    // measureViewGroup(widthMeasureSpec, heightMeasureSpec);
  }

  // ViewGroup 中的 onMeasure() 调用了 View.measure() 而 View.measure() 调用了 View.onMeasure()
  private void measureViewGroup(int widthMeasureSpec, int heightMeasureSpec) {
    // 父 View 推荐的宽高
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    // 自身内容宽高
    int contentW;
    int contentH;
    // resultW 最终设置的宽，resultH 最终设置的高
    int resultW = widthSize;
    int resultH = heightSize;

    contentW = getPaddingLeft() + getPaddingRight();
    contentH = getPaddingTop() + getPaddingBottom();
    // 下面的设置需要继承 ViewGroup
    // 对子元素进行尺寸的测量
    // measureChildren(widthMeasureSpec, heightMeasureSpec);
    //    MarginLayoutParams layoutParams;
    //    for (int i = 0; i < getChildCount(); i++) {
    //      View child = getChildAt(i);
    //      layoutParams = (MarginLayoutParams) child.getLayoutParams();
    //      // 子元素不可见时，不参与布局，因此不需要将其尺寸计算在内
    //      if (child.getVisibility() == View.GONE) {
    //        continue;
    //      }
    //      contentW += child.getMeasuredWidth() + layoutParams.leftMargin +
    // layoutParams.rightMargin;
    //      contentH += child.getMeasuredHeight() + layoutParams.topMargin +
    // layoutParams.bottomMargin;
    //    }

    if (widthMode == MeasureSpec.AT_MOST) {
      if (!TextUtils.isEmpty(mText)) {
        resultW = Math.min(contentW, widthSize);
        resultH = Math.min(contentH, heightSize);
      }
    } else if (widthMode == MeasureSpec.EXACTLY) {
      // 与默认值相同
    } else {
      resultW = contentW;
      resultH = contentH;
    }
    setMeasuredDimension(resultW, resultH);
  }

  /**
   * View 的大小不仅由 View 本身控制，而且受父控件的影响，所以我们在确定 View 大小的时候最好使用系统提供的 onSizeChanged 回调函数
   *
   * @param w 宽
   * @param h 高
   * @param oldw 上一次的宽
   * @param oldh 上一次的高
   */
  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    Log.i("zyx", "onSizeChanged");
    super.onSizeChanged(w, h, oldw, oldh);
    mWidth = w;
    mHeight = h;
  }

  /**
   * 定义: 确定子 View 布局位置
   *
   * @param changed
   * @param left
   * @param top
   * @param right
   * @param bottom
   */
  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    //    int topStart = getPaddingTop();
    //    int leftStart = getPaddingLeft();
    //    int childW;
    //    int childH;
    //    MarginLayoutParams layoutParams;
    //    for (int i = 0; i < getChildCount(); i++) {
    //      View child = getChildAt(i);
    //      layoutParams = (MarginLayoutParams) child.getLayoutParams();
    //
    //      // 子元素不可见时，不参与布局，因此不需要将其尺寸计算在内
    //      if (child.getVisibility() == View.GONE) {
    //        continue;
    //      }
    //
    //      childW = child.getMeasuredWidth();
    //      childH = child.getMeasuredHeight();
    //
    //      leftStart += layoutParams.leftMargin;
    //      topStart += layoutParams.topMargin;
    //
    //      // 确定子 View 位置
    //      child.layout(leftStart, topStart, leftStart + childW, topStart + childH);
    //      // 下一个 View 的左上角坐标
    //      leftStart += childW + layoutParams.rightMargin;
    //      topStart += childH + layoutParams.bottomMargin;
    //    }
  }

  /*
   * 定义: 绘制内容
   *
   * Android 绘制都是按顺序的，先绘制的内容会被后绘制的内容覆盖
   *
   * 在继承已有控件的基础上添加绘制代码时，需要考虑代码的绘制顺序
   *
   * 1.背景(发生在 drawBackground 方法中，但此方法是 private 的，一般在 XML 或者 Java 代码中设置)
   *
   * 2.主体(onDraw)
   *
   * 3.子 View(dispatchDraw)
   *
   * 4.滑动条(XML 或者 setScrollBar 设置)
   *
   * 5 前景(XML 或者 setForeground 设置)
   *
   * 也可以重写 draw 方法，将绘制代码设置在 super.draw 前或者后
   */

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawPathAddArc(canvas);
  }

  // 绘制子 View
  @Override
  protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
  }

  // 绘制前景色
  @Override
  public void onDrawForeground(Canvas canvas) {
    super.onDrawForeground(canvas);
  }

  // 绘制颜色
  private void drawColor(Canvas canvas) {
    canvas.drawColor(Color.BLUE);
  }

  // 绘制点
  private void drawPoint(Canvas canvas) {
    canvas.drawPoint(200, 200, mPaint);
    // 绘制一组点，每 2 个组成一个点
    canvas.drawPoints(new float[] {500, 500, 500, 600, 500, 700}, mPaint);
  }

  // 绘制一条直线
  private void drawLine(Canvas canvas) {
    canvas.drawLine(300, 300, 500, 600, mPaint);
    // 绘制一组线，每 2 个点的坐标确定一条线
    canvas.drawLines(new float[] {100, 200, 200, 200, 100, 300, 200, 300}, mPaint);
  }

  // 绘制矩形，左上角和右下角坐标确定矩形范围
  // Rect 和 RectF 的区别: rect 是 int 的，rectF 是 float 的
  private void drawRect(Canvas canvas) {
    // 第一种
    // canvas.drawRect(200, 500, 600, 700, mPaint);
    // 第二种
    Rect rect = new Rect();
    rect.set(200, 500, 600, 700);
    canvas.drawRect(rect, mPaint);
  }

  // 绘制圆角矩形
  // 30,30 表示圆弧的两个半径，30,30 这个点到 X,Y 轴包围的范围就是圆弧的角度
  private void drawRoundRect(Canvas canvas) {
    // 第一种
    canvas.drawRoundRect(100, 100, 800, 400, 30, 30, mPaint);
    // 第二种
    RectF rectF = new RectF(100, 100, 800, 400);
    canvas.drawRoundRect(rectF, 30, 30, mPaint);
  }

  // 绘制椭圆
  private void drawOval(Canvas canvas) {
    // 第一种
    canvas.drawOval(100, 100, 800, 400, mPaint);
    // 第二种
    RectF rectF = new RectF(100, 100, 700, 400);
    canvas.drawOval(rectF, mPaint);
  }

  // 绘制圆
  // cx(300)、cy(400) 表示圆心坐标，radius(200) 表示半径
  private void drawCircle(Canvas canvas) {
    canvas.drawCircle(300, 400, 200, mPaint);
  }

  /* 绘制圆弧
     矩形的中心点为旋转起始点，顺时针旋转为正角度方向(sweepAngle取值范围是 [-360, 360) 度)
     startAngle 开始角度
     sweepAngle 扫过角度
     userCenter 表示是否使用中心点。true 表示起点和终点同时与中心点相连的区域，false 表示起点终点两者相连的区域
  */
  private void drawArc(Canvas canvas) {
    // 例一
    // 绘制背景矩形
    RectF rectF = new RectF(100, 100, 800, 400);
    mPaint.setColor(Color.GRAY);
    canvas.drawRect(rectF, mPaint);
    // 绘制圆弧
    mPaint.setColor(Color.BLUE);
    canvas.drawArc(rectF, 0, 90, false, mPaint);

    // 例二
    RectF rectF2 = new RectF(100, 600, 800, 900);
    // 绘制背景矩形
    mPaint.setColor(Color.GRAY);
    canvas.drawRect(rectF2, mPaint);
    // 绘制圆弧
    mPaint.setColor(Color.BLUE);
    canvas.drawArc(rectF2, 0, 90, true, mPaint);
  }

  // 所有的画布操作都只影响后续的绘制，对之前已经绘制过的内容没有影响

  // 位移，将画布圆心移动到指定位置。位移是基于当前位置移动，而不是每次基于 View 左上角的 (0,0) 点移动
  private void canvasTranslate(Canvas canvas) {
    mPaint.setColor(Color.BLACK);
    canvas.translate(200, 200);
    canvas.drawCircle(0, 0, 100, mPaint);

    mPaint.setColor(Color.BLUE);
    canvas.translate(200, 200);
    canvas.drawCircle(0, 0, 100, mPaint);
  }

  // 缩放，缩放的中心默认为 坐标原点(画布已经位移到布局中央)
  // public void scale (float sx, float sy)
  // public void scale (float sx, float sy, float px, float py)
  // sx，sy 表示缩放比例，默认 1 无变化，>1 表示放大，小于 1 表示缩小；当缩放比例为负数时会根据缩放中心进行对折翻转
  // px，py 表示缩放中心坐标
  private void canvasScale(Canvas canvas) {
    canvas.translate(getWidth() / 2, getHeight() / 2);
    RectF rect = new RectF(0, -400, 400, 0); // 矩形区域
    mPaint.setColor(Color.BLACK); // 绘制黑色矩形
    canvas.drawRect(rect, mPaint);

    // 例一
    // 先根据缩放中心(坐标原点)缩放到原来的 0.5 倍，然后分别按照 x 轴和 y 轴进行翻转
    canvas.scale(-0.5f, -0.5f, 200, 0); // 画布缩放，缩放中心向右偏移了 200
    mPaint.setColor(Color.BLUE); // 绘制蓝色矩形
    canvas.drawRect(rect, mPaint);

    // 例二
    // 缩放也是可以叠加的，缩放比例相乘
    mPaint.setStyle(Style.STROKE);
    canvas.translate(getWidth() / 2, getHeight() / 2);
    RectF rect2 = new RectF(-400, -400, 400, 400); // 矩形区域
    for (int i = 0; i <= 20; i++) {
      canvas.scale(0.9f, 0.9f);
      canvas.drawRect(rect2, mPaint);
    }
  }

  // 旋转
  // public void rotate (float degrees)
  // public void rotate (float degrees, float px, float py)
  // 默认的旋转中心 View 的坐标原点(画布已经位移到布局中央)
  private void canvasRotate(Canvas canvas) {
    // 示例一
    canvas.translate(getWidth() / 2, getHeight() / 2);
    RectF rect = new RectF(0, -400, 400, 0); // 矩形区域
    mPaint.setColor(Color.BLACK); // 绘制黑色矩形
    canvas.drawRect(rect, mPaint);

    canvas.rotate(180); // 旋转180度
    mPaint.setColor(Color.BLUE); // 绘制蓝色矩形
    canvas.drawRect(rect, mPaint);

    // 示例二
    canvas.translate(getWidth() / 2, getHeight() / 2);
    RectF rect2 = new RectF(0, -400, 400, 0); // 矩形区域
    mPaint.setColor(Color.BLACK); // 绘制黑色矩形
    canvas.drawRect(rect, mPaint);

    canvas.rotate(180, 200, 0); // 旋转180度，且旋转中心向右偏移 200 个单位
    mPaint.setColor(Color.BLUE); // 绘制蓝色矩形
    canvas.drawRect(rect2, mPaint);

    // 示例三
    mPaint.setStyle(Style.STROKE);
    canvas.translate(getWidth() / 2, getHeight() / 2);
    canvas.drawCircle(0, 0, 400, mPaint); // 绘制两个圆形
    canvas.drawCircle(0, 0, 380, mPaint);
    for (int i = 0; i <= 360; i += 10) { // 绘制圆形之间的连接线
      canvas.drawLine(0, 380, 0, 400, mPaint);
      canvas.rotate(10);
    }
  }

  // 错切(skew)，特殊类型的线性变换
  // public void skew (float sx, float sy)
  // sx 将画布在 x 方向倾斜相应的角度，sx 为倾斜角度的 tan 值
  // sy 将画布在 y 方向倾斜相应的角度，sy 为倾斜角度的 tan 值
  private void canvasSkew(Canvas canvas) {
    canvas.translate(getWidth() / 2, getHeight() / 2);
    RectF rect = new RectF(0, 0, 200, 200);
    mPaint.setColor(Color.BLACK); // 绘制黑色矩形
    canvas.drawRect(rect, mPaint);

    canvas.skew(1, 0); // 水平错切 <- 45度
    mPaint.setStyle(Style.STROKE);
    mPaint.setColor(Color.BLUE); // 绘制蓝色矩形
    canvas.drawRect(rect, mPaint);
  }

  // 通过 save + restore 组合避免对画布的操作影响之后的代码
  private void saveRestore() {
    //    save();      //保存状态
    //    ...          //具体操作
    //    restore();   //回滚到之前的状态
  }

  /*
   * 使用 Picture 前最好关闭硬件加速，以免引起不必要的问题；可以把 Picture 看作是一个录制 Canvas 操作的录像机。
   *
   * beginRecording 和 endRecording 是成对使用的，一个开始录制，一个是结束录制，两者之间的操作将会存储在 Picture 中, 并不会显示在屏幕中。在
   * endRecording 后不应再操作 canvas。
   *
   * 需要重复绘制多次时，可以先用它录制下来，之后直接把录制的 Picture 绘制出来就可以了，直接绘制 Picture 会比再绘制一遍内容快一些。
   */
  private void initDrawPicture() {
    Log.i("zyx", "initRecording");
    // beginRecording(int width, int height)，width height 并不是绘制范围，而是与 drawPicture 设置的 RectF 来控制缩放比例的
    Canvas canvas = mPicture.beginRecording(0, 0);
    // 创建一个画笔
    Paint paint = new Paint();
    paint.setColor(Color.BLUE);
    paint.setStyle(Paint.Style.FILL);
    // 位移
    canvas.translate(250, 250);
    // 绘制一个圆
    canvas.drawCircle(0, 0, 100, paint);
    mPicture.endRecording();
  }

  private void StartDrawPicture(Canvas canvas) {
    // 让存储在 picture 中的 canvas 绘制出来，有 3 种方法

    // 1.调用 mPicture.draw(canvas)，会影响Canvas的状态，一般不使用
    mPicture.draw(canvas);

    // 2.canvas.drawPicture 使用 Canvas 提供的 drawPicture 方法绘制
    canvas.drawPicture(mPicture);
    canvas.drawPicture(
        mPicture, new RectF(0, 0, 50, 200)); // 此处的 Rect 与 beginRecording 处传入的宽高相除，得到宽和高的缩放比例

    // 3.将 Picture 包装成为 PictureDrawable，使用 PictureDrawable 的 draw 方法绘制
    PictureDrawable drawable = new PictureDrawable(mPicture);
    // 设置绘制区域，图形超出部分会被忽略
    drawable.setBounds(0, 0, 250, 500);
    // 绘制
    drawable.draw(canvas);
  }

  /*
   * 三种 bitmap 获取方式
   *
   * BitmapFactory.decodeResource(getResources(), R.drawable.wallhaven_doe);
   *
   * BitmapFactory.decodeFile("/sdcard/bitmap.png");
   *
   * BitmapFactory.decodeStream();
   */
  private void drawBitmap(Canvas canvas) {
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallhaven_doe);
    // 第一种
    // public void drawBitmap (Bitmap bitmap, Matrix matrix, Paint paint);
    // matrix、paint 是对图片做一些转变，正常加载图片的话 new 对应实例传入即可
    canvas.drawBitmap(bitmap, new Matrix(), new Paint());

    // 第二种，指定了图片左上角的坐标(与坐标原点的距离)
    // public void drawBitmap (Bitmap bitmap, float left, float top, Paint paint);
    canvas.drawBitmap(bitmap, 20, 30, new Paint());

    // 第三种，Rect src 指定绘制图片的区域，Rect dst 指定图片在屏幕上显示(绘制)的区域
    // 直白的解释，就是可自定义图片的一部分展示在特定区域，根据显示区域大小图片会自动缩放
    // public void drawBitmap (Bitmap bitmap, Rect src, Rect dst, Paint paint);
    // 显示图片左上角的四分之一
    Rect src1 = new Rect(0, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
    // 显示图片的全部
    Rect src2 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    // 设置图片在屏幕上的显示区域
    Rect dst = new Rect(0, mHeight / 2, mWidth, mHeight);
    // 绘制图片
    canvas.drawBitmap(bitmap, src1, dst, new Paint());
  }

  /*
   * 测量文字的宽度并返回
   *
   * measureText 测量出来的宽度总是比 getTextBounds 大一点
   *
   * measureText 测量的是文字占用的宽度
   *
   * getTextBounds 测量的是文字显示的宽度，而文字本身是带有内边距的
   */
  public void measureText(Canvas canvas) {
    String str = "Hello World";
    float width = mPaint.measureText(str);
  }

  /*
   * 获取文字的显示范围
   *
   * text 要显示的文字; start 和 end 分别是文字的起始和结束位置; bounds 是存储文字显示范围的对象，方法在测算完成之后会把结果写进 bounds
   *
   * getTextBounds(String text, int start, int end, Rect bounds)
   *
   */
  public void getTextBounds(Canvas canvas) {
    String str = "Hello World";
    Rect rect = new Rect();
    mPaint.getTextBounds(str, 0, str.length(), rect);
    // 对齐方式 left 前提下，此时 rect 保存的坐标是以文字左下角坐标为原点计算的
    Log.i(
        "zyx",
        "left= "
            + rect.left
            + "\n"
            + "top= "
            + rect.top
            + "\n"
            + "right= "
            + rect.right
            + "\n"
            + "bottom= "
            + rect.bottom);
    rect.set(
        rect.left + (mWidth / 2),
        rect.top + (mHeight / 2),
        rect.right + (mWidth / 2),
        rect.bottom + (mHeight / 2));
    canvas.drawRect(rect, mPaint);
  }

  /*
   * 绘制文字
   *
   * public void drawText (String text, float x, float y, Paint paint)
   *
   * public void drawText(String text, int start, int end, float x, float y, Paint paint)
   */
  private void drawText(Canvas canvas) {
    String str = "中华人民共和国";
    // float x, float y 简单理解为字体的左下角坐标
    canvas.drawText(str, 200, 500, mPaint);

    // int start, int end，截取 [x,y) 区间的字符串
    canvas.drawText(str, 1, 3, mWidth / 2, mHeight / 2, mPaint);
  }

  /*
  * StaticLayout 换行绘制文字
  *
  * 与 drawText 相比，StaticLayout 能在 View 到达最大宽度后自动折行，也能识别 \n 换行符
  *
  * width 是文字区域的宽度，文字到达这个宽度后就会自动换行，align 是文字的对齐方向
  *
  * spacingmult 是行间距的倍数，通常情况下填 1 就好，spacingadd 是行间距的额外增加值，通常情况下填 0 就好
  *
  * includepad  是指是否在文字上下添加额外的空间，来避免某些过高的字符的绘制出现越界
  *
  * StaticLayout(CharSequence source, TextPaint paint,
                       int width,
                       Alignment align, float spacingmult, float spacingadd,
                       boolean includepad)
  */
  private void drawWrapText(Canvas canvas) {
    String str = "a\nbc\ndefghi\njklm\nnopqrst\nuvwx\nyz";
    String text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
    String text2 = "中华人民共和国";
    TextPaint textPaint = new TextPaint();
    textPaint.setColor(Color.BLACK);
    textPaint.setStyle(Style.FILL);
    textPaint.setAntiAlias(true);
    textPaint.setTextSize(25);
    StaticLayout staticLayout =
        new StaticLayout(text2, textPaint, mWidth / 2, Alignment.ALIGN_NORMAL, 1, 0, true);
    staticLayout.draw(canvas);
  }

  /*
   * 线帽 用于指定线段开始和结束时的效果
   *
   * BUTT   无线帽，默认类型
   *
   * ROUND  在开头和结尾分别添加一个半圆
   *
   * SQUARE 在开头和结尾分别添加半个正方形
   *
   * Cap 也会影响到点的绘制，在 Round 的状态下绘制的点是圆的
   *
   */
  public void setStrokeCap(Canvas canvas) {
    mPaint.setStrokeWidth(60);

    mPaint.setStrokeCap(Cap.BUTT);
    canvas.drawLine(500, 700, 1000, 700, mPaint);
    mPaint.setStrokeCap(Cap.SQUARE);
    canvas.drawLine(500, 900, 1000, 900, mPaint);
    mPaint.setStrokeCap(Cap.ROUND);
    canvas.drawLine(500, 1100, 1000, 1100, mPaint);
  }

  /*
   * 两条连接起来的线段拐角显示方式
   *
   * MITER 尖角，默认
   * BEVEL 平角
   * ROUND 圆角
   *
   * 如果夹角足够小，接近于零，那么交点位置就会在延长线上无限远的位置。 为了避免这种情况，如果连接模式为 MITER(尖角)，当连接角度小于一定程度时会自动将连接模式转换为 BEVEL(平角)，
   * 默认当夹角小于 28.96 时，尖角就会变成平角， 通过 setStrokeMiter 设置延长线的最大值修改阈值，默认为 4
   *
   */
  public void setStrokeJoin(Canvas canvas) {
    // mPaint.setStrokeMiter(4);
    mPaint.setStyle(Style.STROKE);
    mPaint.setStrokeJoin(Join.BEVEL);
    Path path = new Path();
    path.moveTo(mWidth / 2, mHeight / 2);
    path.lineTo((mWidth / 2) + 500, mHeight / 2);
    path.lineTo((mWidth / 2) + 500, (mHeight / 2) + 600);
    path.lineTo((mWidth / 2), (mHeight / 2) + 600);
    canvas.drawPath(path, mPaint);
  }

  /*
  * 在绘制之前修改几何路径，它可以实现划线，自定义填充效果和自定义笔触效果
  *
  * CornerPathEffect   圆角效果，将尖角替换为圆角，CornerPathEffect 可以让手绘轨迹更加圆润
  *
  * DashPathEffect(float intervals[], float phase) 虚线效果，用于各种虚线效果
  * intervals[] 用于控制虚线显示长度和隐藏长度，它必须为偶数(且至少为 2 个)，按照[显示长度，隐藏长度，显示长度，隐藏长度]的顺序来显示
  * phase 偏移量，控制第一条线（实线）向左偏移的长度
  *
  * DiscretePathEffect(float segmentLength, float deviation) 让路径分段随机偏移
  * segmentLength 分段长度
  * deviation     偏移距离
  *
  * PathDashPathEffect(Path shape, float advance, float phase, Style style) Path 虚线效果，虚线中的间隔使用 Path 代替

  * SumPathEffect     两个 PathEffect 效果组合，同时绘制两种效果

    ComposePathEffect 两个 PathEffect 效果叠加，先使用效果 1，之后使用效果 2，有先后顺序
  */
  public void setCornerPathEffect(Canvas canvas) {
    PathEffect cornerPathEffect = new CornerPathEffect(20); // 20 指圆角的半径
    PathEffect dashPathEffect = new DashPathEffect(new float[] {10, 10, 20, 10}, 0);
    PathEffect discretePathEffect = new DiscretePathEffect(10, 30);

    mPaint.setPathEffect(dashPathEffect);
    Path path = new Path();
    path.moveTo(mWidth / 2, mHeight / 2);
    path.lineTo((mWidth / 2) + 500, mHeight / 2);
    path.lineTo((mWidth / 2) + 500, (mHeight / 2) + 600);
    path.lineTo((mWidth / 2), (mHeight / 2) + 600);
    canvas.drawPath(path, mPaint);
  }

  /*
   图片背后添加阴影
   setShadowLayer(float radius, float dx, float dy, @ColorInt int shadowColor)
   radius 模糊半径，越大越模糊，越小越清晰，默认 0
   dx     阴影的横向偏移距离，正值向右偏移，负值向左偏移
   dy     阴影的纵向偏移距离，正值向下偏移，负值向上偏移
   color  绘制阴影的画笔颜色

   清除阴影层，使用 mPaint.clearShadowLayer()
  */
  public void setShadowLayer(Canvas canvas) {
    mPaint.setShadowLayer(10, 0, 0, Color.RED);
    String text = "Hello World";
    canvas.drawText(text, 80, 300, mPaint);
  }

  /*
   * 在绘制层上方的附加效果
   *
   * MaskFilter 有两种： BlurMaskFilter 和 EmbossMaskFilter
   *
   * BlurMaskFilter   模糊效果
   * BlurMaskFilter(float radius, BlurMaskFilter.Blur style)
   * radius 模糊的范围
   * style  模糊类型
   *        NORMAL 内外模糊，SOLID 内部加粗、外部模糊，INNER 内部模糊，OUTER 外部模糊
   *
   * EmbossMaskFilter 浮雕效果
   * EmbossMaskFilter(float[] direction, float ambient, float specular, float blurRadius)
   */
  public void setMaskFilter(Canvas canvas) {
    mPaint.setMaskFilter(new BlurMaskFilter(50, Blur.OUTER));
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallhaven_doe);
    canvas.drawBitmap(bitmap, 100, 100, mPaint);
  }

  // shader 着色器，它在图形绘制过程中返回一段段颜色值，通过调用 Paint.setShader() 方法，可以将它的子类安装进画笔
  /*
  * BitmapShader 图片渲染器
  *   BitmapShader (Bitmap bitmap, Shader.TileMode tileX, Shader.TileMode tileY)
  *   bitmap 纹理图片，tileX X 方向轴的 tiling mode，tileY Y方向轴的 tiling mode
  *   Shader.TileMode CLAMP
  *      贴图的纹理本身小于要绘制的区域，那么超出部分将会以边缘的颜色填充
      Shader.TileMode MIRROR
         以镜像的方式在 X 和 Y 方向复制
      Shader.TileMode REPEAT
         将图片纹理沿 X、Y 轴进行复制
  */
  private void drawBitmapShader(Canvas canvas) {
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.drawable_test_girl);
    Shader shader = new BitmapShader(bitmap, TileMode.MIRROR, TileMode.REPEAT);
    int radius = getWidth() <= getHeight() ? getWidth() / 2 : getHeight() / 2;
    mPaint.setShader(shader);
    canvas.drawCircle(mWidth / 2, mHeight / 2, radius, mPaint);
  }

  // shader 与文字混合，像是文字贴到了文字上
  private void drawBitmapShaderAndText(Canvas canvas) {
    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.diqiu);
    Shader bitmapShader = new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
    mPaint.setTextSize(200.0f);
    mPaint.setColor(Color.RED);
    mPaint.setTypeface(Typeface.DEFAULT_BOLD);
    mPaint.setShader(bitmapShader);
    canvas.drawText("小狗狗", 0, getHeight() / 2, mPaint);
  }

  /*
  * LinearGradient 线性渐变
  * LinearGradient (float x0,
               float y0,
               float x1,
               float y1,
               int color0,
               int color1,
               Shader.TileMode tile)
    x0 和 y0 是颜色渐变的起点坐标
    x1 和 y1 是颜色渐变的终点坐标
    color0 是起点颜色值
    color1 是终点颜色值
    tile TileMode 类型
  *
  *
  * */
  private void drawLinearGradientShader(Canvas canvas) {
    Shader linearGradient =
        new LinearGradient(
            0,
            0,
            getWidth(),
            0,
            Color.parseColor("#faf84d"),
            Color.parseColor("#CC423C"),
            Shader.TileMode.CLAMP);
    mPaint.setShader(linearGradient);
    canvas.drawRect(0, 0, getWidth(), getHeight() / 2, mPaint);
  }

  /*
   LinearGradient (float x0,
                float y0,
                float x1,
                float y1,
                int[] colors,
                float[] positions,
                Shader.TileMode tile)
   colors    颜色数组
   positions 大小范围从 0.0 到 1.0，0.0 代表起点位置，1.0 代表终点位置，且只能从小到大。
   positions 为 null 时，colors 平分展示
  *
  * */
  private void drawLinearGradientShaderTwo(Canvas canvas) {
    Shader linearGradient =
        new LinearGradient(
            0,
            0,
            getWidth(),
            0,
            new int[] {
              Color.parseColor("#faf84d"),
              Color.parseColor("#003449"),
              Color.parseColor("#808080"),
              Color.parseColor("#CC423C")
            },
            new float[] {0.0f, 0.6f, 0.8f, 1.0f},
            Shader.TileMode.CLAMP);
    mPaint.setShader(linearGradient);
    canvas.drawRect(0, 0, getWidth(), getHeight() / 2, mPaint);
  }
  /*
    RadialGradient 环行渲染器，由中心向四周辐射
    RadialGradient (float centerX,
                float centerY,
                float radius,
                int centerColor,
                int edgeColor,
                Shader.TileMode tileMode)
    centerX  圆心的 X 坐标
    centerY  圆心的 Y 坐标
    radius   圆的半径
    centerColor  中心颜色
    edgeColor    边缘颜色
    tileMode

   RadialGradient (float centerX,
                float centerY,
                float radius,
                int[] colors,
                float[] stops,
                Shader.TileMode tileMode)
   colors、stops 同 LinearGradient 含义一致
  */
  private void drawRadialGradient(Canvas canvas) {
    int h = getHeight();
    int w = getWidth();
    Shader radialGradient =
        new RadialGradient(
            w / 2,
            h / 2,
            w / 2,
            Color.parseColor("#faf84d"),
            Color.parseColor("#CC423C"),
            Shader.TileMode.CLAMP);
    mPaint.setShader(radialGradient);
    canvas.drawRect(0, 0, w, h, mPaint);
  }

  /*
  * SweepGradient 扫描渐变
  * SweepGradient (float cx,
               float cy,
               int color0,
               int color1)
     cx，cy 圆心坐标
     color0 是起始颜色
     color1 是终止颜色

     SweepGradient (float cx,
                float cy,
                int[] colors,
                float[] positions)
  * */
  private void drawSweepGradient(Canvas canvas) {
    int h = getHeight();
    int w = getWidth();
    Shader sweepGradient = new SweepGradient(w / 2, h / 2, Color.RED, Color.BLUE);
    mPaint.setShader(sweepGradient);
    canvas.drawRect(0, 0, w, h, mPaint);
  }
  //  ComposeShader composeShader = new ComposeShader(); 组合两个 shader 样式，通过 Xfermode 规则组合到一起

  /* path 路径
      使用 Path 不仅能够绘制简单图形，也可以制比较复杂的图形
      根据路径绘制文本和剪裁画布都会用到 Path

     lineTo       从一个点到另一个点之间的连线，如果没有进行过操作则起点为坐标原点
     moveTo       改变下次操作的起点
     setLastPoint 替换上一次操作的坐标点位置
     close        连接当前最后一个点和最初的点，最终形成一个封闭的图形
  */
  private void drawPathBase(Canvas canvas) {
    canvas.translate(mWidth / 2, mHeight / 2);
    Path path = new Path();
    path.lineTo(200, 200);
    path.lineTo(200, 0);
    path.close();
    canvas.drawPath(path, mPaint);
  }

  /*
   * addXxx
   *
   * 圆形 public void addCircle(float x, float y, float radius, Path.Direction dir)
   *
   * 椭圆 public void addOval(RectF oval, Path.Direction dir)
   *
   * 矩形 public void addRect(RectF rect, Path.Direction dir)
   *
   * 圆角矩形 addRoundRect(RectF rect, float rx, float ry, Path.Direction dir)
   *
   * Direction.CW  顺时针
   * Direction.CCW 逆时针
   */
  private void drawPathAddXXX(Canvas canvas) {
    canvas.translate(mWidth / 2, mHeight / 2); // 移动坐标系到屏幕中心
    Path path = new Path();
    path.addRect(-200, -200, 300, 200, Direction.CCW);
    canvas.drawPath(path, mPaint);

    /* 单纯使用 Direction.CW/CCW 看不出区别，配合 setLastPoint 就能看出区别 */
    canvas.translate(mWidth / 2, mHeight / 2); // 移动坐标系到屏幕中心
    Path path2 = new Path();
    path.addRect(-200, -200, 200, 200, Path.Direction.CCW); // Path.Direction.CW
    path.setLastPoint(-300, 300); // 重置最后一个点的位置
    canvas.drawPath(path2, mPaint);
  }

  /*
   * addPath
   *
   * public void addPath (Path src)
   * 将 src 合并到当前 path
   *
   * public void addPath (Path src, float dx, float dy)
   * 将 src 进行了位移之后合并到当前 path 中
   *
   * public void addPath (Path src, Matrix matrix)
   * 将 src 添加到当前 path 之前先使用 Matrix 进行变换
   */
  private void drawPathAddPath(Canvas canvas) {
    canvas.translate(0, mHeight / 2);
    Path path = new Path();
    Path src = new Path();
    path.addCircle(0, 0, 100, Path.Direction.CW);
    src.addCircle(0, 0, 100, Path.Direction.CW);
    for (int i = 0; i < 10; i++) {
      path.addPath(src, 200 * (i + 1), 0);
      canvas.drawPath(path, mPaint);
    }
  }

  /*
   * addArc arcTo 绘制圆弧
   *
   * public void addArc(RectF oval, float startAngle, float sweepAngle)
   *
   * public void arcTo(RectF oval, float startAngle, float sweepAngle)
   *
   * public void arcTo(RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo)
   *
   * oval       圆弧的外切矩形
   * startAngle 开始角度
   * sweepAngle	扫过角度(-360 <= sweepAngle <360)，不是终点角度！！
   * forceMoveTo true(默认) -> 不连接最后一个点与圆弧起点; false -> 连接最后一个点与圆弧起点
   */
  private void drawPathAddArc(Canvas canvas) {
    // 例子一
    canvas.translate(mWidth / 2, mHeight / 2); // 移动坐标系到屏幕中心
    canvas.scale(1, -1); // 翻转 y 轴(y 轴向上)
    mPaint.setStyle(Style.STROKE);

    Path path = new Path();
    // path.lineTo(100, 100);

    RectF oval = new RectF(0, 0, 300, 300);
    canvas.drawRect(oval, mPaint);
    path.addArc(oval, 0, 90);
    // path.arcTo(oval, 0, 90, true);
    // path.arcTo(oval, 0, 90, false);
    canvas.drawPath(path, mPaint);
  }

  /*
   * 范围裁剪
   *
   * clipRect() 裁剪一个矩形范围
   *
   * clipPath() 裁剪任意形状
   */
  private void clipPath(Canvas canvas) {
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallhaven_doe);

    // canvas.clipRect(0, 0, getWidth() / 2 / 2, getHeight() / 2 / 2);
    // canvas.drawBitmap(bitmap, 0, 0, mPaint);

    Path path = new Path();
    path.addCircle(getWidth() / 2, getHeight() / 2, 500, Path.Direction.CW);
    canvas.clipPath(path);
    canvas.drawBitmap(bitmap, 0, 0, mPaint);
  }

  /*
   * 布尔运算：用一些简单的图形通过一些规则合成一些相对比较复杂，或难以直接得到的图形
   *
   * DIFFERENCE：Path1 中减去 Path2 后剩下的部分
   *
   * INTERSECT：Path1 与 Path2 相交的部分
   *
   * UNION：包含全部 Path1 和 Path2 全部
   *
   * XOR：包含 Path1 与 Path2 但不包括两者相交的部分
   */
  private void booleanCompute(Canvas canvas) {
    canvas.translate(getWidth() / 2, getHeight() / 2);

    Path path1 = new Path();
    Path path2 = new Path();
    Path path3 = new Path();
    Path path4 = new Path();

    path1.addCircle(0, 0, 200, Path.Direction.CW);
    path2.addRect(0, -200, 200, 200, Path.Direction.CW);
    path3.addCircle(0, -100, 100, Path.Direction.CW);
    path4.addCircle(0, 100, 100, Path.Direction.CCW);

    path1.op(path2, Path.Op.DIFFERENCE);
    path1.op(path3, Path.Op.UNION);
    path1.op(path4, Path.Op.DIFFERENCE);

    canvas.drawPath(path1, mPaint);
  }

  // matrix 矩阵 ：使用 Matrix 来做常见变换
  // 创建 Matrix 对象 - 调用 Matrix 的 pre/postTranslate/Rotate/Scale/Skew() 方法来设置几何变换 - 使用
  // Canvas.setMatrix(matrix) 或 Canvas.concat(matrix) 来把几何变换应用到 Canvas
  private void matrixBase() {
    /*
     什么是矩阵？
      一个由 m 行 n 列元素排列成的矩形阵列。矩阵里的元素可以是数字、符号或数学式
    矩阵的加减乘操作？
      1. 大小相同（行数列数都相同）的矩阵之间可以相互加减的，具体是对每个位置上的元素做加减法
      2. 数乘是所有位置都乘以这个数
      3. 矩阵相乘是第一个矩阵第 m 行与第二个矩阵第 n 列，对应位置的每个值的乘积之和
    为什么相乘是这样的规则？
      矩阵的本质就是线性方程式，两者是一一对应关系，方程组的简化记法
    matrix 为什么是 3*3 的矩阵
      平移是矩阵相加，旋转和缩放则是矩阵相乘，引入齐次坐标，将平移的加法合并用乘法表示，统一计算方式，方便计算和降低运算量
    什么是齐次坐标
      n 维的向量用一个 n+1 维向量来表示
    matrix 矩阵中元素的作用
     MTRANS_X、MTRANS_Y 同时控制着 Translate
     MSCALE_X、MSCALE_Y 同时控制着 Scale
     MSCALE_X、MSKEW_X、MSCALE_Y、MSKEW_Y 同时控制着 Rotate
     MSKEW_X、MSKEW_Y 同时控制着 Skew
    set、pre、post 的区别
     分别代表设置、前乘、后乘变换；set 先清空前面的再设置；混合使用时 pre 先于 set 执行、post 后于 set 执行，建议是只使用一种乘法
    */
    Matrix matrix = new Matrix(); // 创建一个单位矩阵
    matrix.equals(new Matrix());
    matrix.hashCode();
    matrix.toString(); // 将 Matrix 转换为字符串
    matrix.toShortString(); // 将 Matrix 转换为短字符串
    matrix.set(new Matrix()); // 将参数 Matrix 的数值复制到当前 Matrix 中
    matrix.reset(); // 重置当前 Matrix(将当前 Matrix 重置为单位矩阵)
    matrix.setValues(new float[] {}); // 参数是浮点型的一维数组，长度需要大于 9，拷贝数组中的前 9 位数值赋值给当前 Matrix
    matrix.getValues(new float[] {}); // 将 Matrix 中的数值拷贝进参数的前 9 位中
    // map 系列用于数值计算，返回变换后的数值
    // 初始数据为 3 个点 (0, 0) (80, 100) (400, 300)
    float[] pts = new float[] {0, 0, 80, 100, 400, 300};
    matrix.setScale(0.5f, 1f);
    matrix.mapPoints(pts); // 数组作为参数传递原始数值，计算结果仍存放在 pts 数组中

    float[] dst = new float[6];
    matrix.mapPoints(dst, pts); // pts 作为参数传递原始数值，计算结果存放在 dst 中，pts 不变; 原始数据需要保留则一般使用这种方法
    // 最后一个 2 表示两个点，即四个数值,并非两个数值
    matrix.mapPoints(dst, 0, pts, 2, 2);

    float radius = 100;
    matrix.mapRadius(radius);
    matrix.setScale(0.5f, 1f);
    Log.i(TAG, "mapRadius: " + radius); // 测量半径,由于圆可能会因为画布变换变成椭圆，所以此处测量的是平均半径
  }

  /** setPolyToPoly 自定义变换 */
  private void customMatrixPoly(Canvas canvas) {
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallhaven_doe);

    Matrix polyMatrix = new Matrix();

    float[] src = {
      0,
      0, // 左上
      bitmap.getWidth(),
      0, // 右上
      bitmap.getWidth(),
      bitmap.getHeight(), // 右下
      0,
      bitmap.getHeight()
    }; // 左下

    float[] dst = {
      0,
      0, // 左上
      bitmap.getWidth(),
      400, // 右上
      bitmap.getWidth(),
      bitmap.getHeight() - 200, // 右下
      0,
      bitmap.getHeight()
    }; // 左下

    // 核心要点
    // src 原始数组，srcIndex 原始数组开始位置，dst 目标数组，dstIndex 目标数组开始位置，pointCount 测控点的数量(范围 0-4)
    // 随着 pointCount 数值增大 setPolyToPoly 的可以操作性也越来越强,大部分情况为 4; 0 - 3 有其它方法替代，只有为 4 时，可以任意形变，无其它方法替代
    // 如果这个 Matrix 赋值给了 Canvas，它的作用范围就是整个画布;如果赋值给了 Bitmap，它的作用范围就是整张图片
    // canvas.concat(new Matrix());
    // canvas.setMatrix(new Matrix());
    polyMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1); // src.length >> 1 为位移运算 相当于处以 2

    // 此处为了更好的显示对图片进行了等比缩放和平移(图片本身有点大)
    polyMatrix.postScale(0.26f, 0.26f);
    polyMatrix.postTranslate(0, 200);
    canvas.drawBitmap(bitmap, polyMatrix, mPaint);
  }

  /**
   * 使用 Camera 来做三维变换
   *
   * <p>Camera 的三维变换有三类：旋转、平移、移动相机(不常用)
   *
   * <p>3D 坐标系是左手坐标系，原点默认左上角，X 轴右，Y 轴上，Z 轴垂直屏幕内
   *
   * <p>摄像机默认是透视投影，近大远小，在屏幕左上角
   *
   * <p>3D 坐标系如何设置旋转中心？
   */
  private void camera(Canvas canvas) {
    canvas.translate(getWidth() / 2, getHeight() / 2);
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallhaven_doe);
    Matrix matrix = new Matrix();
    Camera camera = new Camera();
    camera.save();
    camera.rotateY(60);
    camera.getMatrix(matrix);
    camera.restore();
    matrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2);
    matrix.preScale(0.5f, 0.5f);
    canvas.drawBitmap(bitmap, matrix, mPaint);
  }

  public void doCamera() {
    invalidate();
  }

  /*
   * 硬件加速 : 指把绘制 View 的计算工作交给 GPU 来处理
   *
   * 未开启硬件加速时，View 的绘制是由 CPU 将绘制内容通过 CPU 转换成像素信息再显示到屏幕上
   *
   * 开启硬件加速后，CPU 将绘制内容交给 GPU 操作，最终由 GPU 完成渲染工作
   *
   * 硬件加速的好处：1. GPU 相较于 CPU 对于图形计算有更大的优势 2. 绘制机制的不同，导致界面重绘时刷新效率极大提高(GPU 重绘时只更新需要更新的
   * View，而关闭硬件加速后需要全部重绘)
   *
   * 硬件加速的弊端：canvas 的一些 API 在硬件加速下无法生效
   */
  private void hardwareSpeed() {
    // LAYER_TYPE_SOFTWARE 软件加速，开启时会自动关闭硬件加速
    // LAYER_TYPE_HARDWARE 硬件加速
    // 离屏缓冲，开启硬件加速后，单独启用一块地方绘制该 View，绘制内容会被缓存下来，在进行移动、伸缩、旋转、透明等无需 invalidate
    // 的属性动画时可以开启硬件加速来极大提高动画执行的效率, 不适用于基于自定义属性绘制的动画
    setLayerType(LAYER_TYPE_HARDWARE, null);
    ObjectAnimator animator = ObjectAnimator.ofFloat(this, "rotationY", 180);
    animator.addListener(
        new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {
            setLayerType(LAYER_TYPE_NONE, null);
          }
        });
    animator.start();
  }
}
