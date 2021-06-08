package com.owl.android_simple.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
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
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.PictureDrawable;
import android.support.annotation.Nullable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.owl.android_simple.R;
import java.util.Locale;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/1/28 14:56
 */
public class CanvasView extends View {

  private static final String TAG = "zyx";
  // 创建 Paint 对象的时候，构造方法的参数里加一个 ANTI_ALIAS_FLAG 的 flag，就可以在初始化的时候就开启抗锯齿
  Paint mPaint = new Paint();
  Picture mPicture = new Picture();
  int mWidth;
  int mHeight;

  public CanvasView(Context context) {
    super(context);
    // initPaint();
  }

  public CanvasView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initPaint();
    // initRecording();
  }

  private void initPaint() {
    Log.i("zyx", "initPaint");
    // 设置笔颜色
    // 基础颜色-ColorFilter-Xfermode
    // 直接设置颜色的 API 用来给图形和文字设置颜色
    // setColorFilter() 用来基于颜色进行过滤处理
    // setXfermode() 用来处理源图像和 View 已有内容的关系
    mPaint.setColor(Color.BLACK);
    // mPaint.setColor(Color.parseColor("#009688"));
    // 设置笔渲染模式
    // STROKE                //描边
    // FILL                  //填充
    // FILL_AND_STROKE       //描边加填充，表示会加上描边的宽度
    mPaint.setStyle(Style.STROKE);
    // 描边宽度
    mPaint.setStrokeWidth(0);
    // 抗锯齿
    mPaint.setAntiAlias(true);
    // 设置文本字体大小
    mPaint.setTextSize(50);
    // 设置字体
    mPaint.setTypeface(Typeface.SERIF);
    //    mPaint.setTypeface(
    //        Typeface.createFromAsset(getContext().getAssets(), "futura_bold_italic_font.ttf"));
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
    //    fontMetrics.top;
    //    fontMetrics.ascent;
    //    fontMetrics.descent;
    //    fontMetrics.bottom;
    // 抖动
    mPaint.setDither(true);
    // 双线性过滤
    mPaint.setFilterBitmap(true);
    // 重置 paint 的所有属性
    // mPaint.reset();
    // 将目标 paint 属性复制到当前 paint 中
    // mPaint.set(new Paint());
    // 批量设置 flags;相当于依次调用它们的 set 方法
    // mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
  }

  public void setStrokeCap(Canvas canvas) {
    mPaint.setStrokeWidth(60);
    // 设置线条形状
    // BUTT 平头、ROUND 圆头、SQUARE 方头。默认为 BUTT
    mPaint.setStrokeCap(Cap.BUTT);
    canvas.drawLine(500, 700, 1000, 700, mPaint);
    mPaint.setStrokeCap(Cap.SQUARE);
    canvas.drawLine(500, 900, 1000, 900, mPaint);
    mPaint.setStrokeCap(Cap.ROUND);
    canvas.drawLine(500, 1100, 1000, 1100, mPaint);
  }

  public void setStrokeJoin(Canvas canvas) {
    // 设置拐角形状
    mPaint.setStrokeWidth(60);
    // 设置 MITER 型拐角的延长线的最大值
    mPaint.setStrokeMiter(4);
    // MITER 尖角  BEVEL 平角  ROUND 圆角 默认为 MITER
    mPaint.setStrokeJoin(Join.ROUND);
    Path path = new Path();
    path.moveTo(mWidth / 2, mHeight / 2);
    path.lineTo((mWidth / 2) + 500, mHeight / 2);
    path.lineTo((mWidth / 2) + 500, (mHeight / 2) + 600);
    path.lineTo((mWidth / 2), (mHeight / 2) + 600);
    canvas.drawPath(path, mPaint);
  }

  /**
   * PathEffect 给图形的轮廓设置效果,分为两类
   *
   * <p>单一效果的 CornerPathEffect(拐角变成圆角)
   *
   * <p>DiscretePathEffect(定长的线段拼接，并随机偏离)
   *
   * <p>DashPathEffect(虚线)
   *
   * <p>PathDashPathEffect(Path shape, float advance, float phase, Style style)(使用一个 Path 来绘制虚线)
   * shape 参与绘制的 path，advance两个相邻 shape 的距离(两个 shape 起点的距离) phase 虚线的偏移
   *
   * <p>style，是用来指定拐弯改变的时候 shape 的转换方式
   *
   * <p>TRANSLATE：位移 ROTATE：旋转 MORPH：变体
   *
   * <p>组合效果的 SumPathEffect ComposePathEffect
   */
  public void setCornerPathEffect(Canvas canvas) {
    // 把所有拐角变成圆角,20 指圆角的半径
    PathEffect cornerPathEffect = new CornerPathEffect(20);
    // segmentLength 是用来拼接的每个线段的长度, deviation 是偏离量
    PathEffect discretePathEffect = new DiscretePathEffect(10, 30);
    // 数组指定了虚线的格式：数组中元素必须为偶数（最少 2 个)按照「画线长度、空白长度、画线长度、空白长度」……的顺序排列; phase 是虚线的偏移量
    PathEffect dashPathEffect = new DashPathEffect(new float[] {10, 10, 20, 10}, 0);

    mPaint.setPathEffect(dashPathEffect);
    Path path = new Path();
    path.moveTo(mWidth / 2, mHeight / 2);
    path.lineTo((mWidth / 2) + 500, mHeight / 2);
    path.lineTo((mWidth / 2) + 500, (mHeight / 2) + 600);
    path.lineTo((mWidth / 2), (mHeight / 2) + 600);
    canvas.drawPath(path, mPaint);
  }

  /** 绘制内容下面加一层阴影 */
  public void setShadowLayer(Canvas canvas) {
    // 10 是阴影的模糊范围 0,0 是阴影的偏移量 red 是阴影的颜色
    // 清除阴影层，使用 mPaint.clearShadowLayer()
    mPaint.setShadowLayer(10, 0, 0, Color.RED);
    String text = "Hello World";
    canvas.drawText(text, 80, 300, mPaint);
  }

  /**
   * 在绘制层上方的附加效果
   *
   * <p>MaskFilter 有两种： BlurMaskFilter 和 EmbossMaskFilter
   *
   * <p>BlurMaskFilter 模糊效果
   *
   * <p>EmbossMaskFilter 浮雕效果
   */
  public void setMaskFilter(Canvas canvas) {
    // radius 参数是模糊的范围， style 是模糊的类型
    // BlurMaskFilter.Blur
    // NORMAL: 内外都模糊绘制
    // SOLID: 内部正常绘制，外部模糊
    // INNER: 内部模糊，外部不绘制
    // OUTER: 内部不绘制，外部模糊(一个白色的背景盖在图片上)
    mPaint.setMaskFilter(new BlurMaskFilter(50, Blur.OUTER));
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallhaven_doe);
    canvas.drawBitmap(bitmap, 100, 100, mPaint);
  }

  /**
   * shader
   *
   * @param canvas
   */
  public void drawBitmapShader(Canvas canvas) {
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallhaven_doe);
    Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    mPaint.setStyle(Style.FILL);
    mPaint.setShader(shader);
    canvas.drawCircle(mWidth / 2, mHeight / 2, 300, mPaint);
  }

  /**
   * 测量文字的宽度并返回
   *
   * <p>measureText 测量出来的宽度总是比 getTextBounds 大一点
   *
   * <p>measureText 测量的是文字占用的宽度
   *
   * <p>getTextBounds 测量的是文字显示的宽度，而文字本身是带有空隙的
   */
  public void measureText(Canvas canvas) {
    String str = "Hello World";
    canvas.drawText(str, mWidth / 2, mHeight / 2, mPaint);
    float width = mPaint.measureText(str);
    canvas.drawLine(mWidth / 2, mHeight / 2, (mWidth / 2) + width, mHeight / 2, mPaint);
  }

  /**
   * 获取文字的显示范围
   *
   * <p>text 要显示的文字，start 和 end 分别是文字的起始和结束位置，bounds 是存储文字显示范围的对象，方法在测算完成之后会把结果写进 bounds
   *
   * <p>getTextBounds(String text, int start, int end, Rect bounds)
   */
  public void getTextBounds(Canvas canvas) {
    String str = "Hello World";
    canvas.drawText(str, mWidth / 2, mHeight / 2, mPaint);
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

  /**
   * MeasureSpec.UNSPECIFIED:子元素告诉父容器它希望它的宽高想要多大就要多大
   *
   * <p>MeasureSpec 将它的高 2 位用来代表测量模式 Mode，低 30 位用来代表数值大小 Size
   *
   * <p>MeasureSpec.EXACTLY :子元素是一个精确的数值
   *
   * <p>MeasureSpec.AT_MOST：子 View 希望它的宽或者高由自己决定，但不能超过父类提供的建议宽高
   *
   * <p>MeasureSpec.makeMeasureSpec():将 Mode 和 Size 组合成一个 measureSpec 数值
   *
   * <p>MeasureSpec.getMode()
   *
   * <p>MeasureSpec.getSize()
   *
   * <p>resolveSize()
   *
   * @param widthMeasureSpec
   * @param heightMeasureSpec
   */
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    // 父 View 推荐的宽高
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    // 我们希望的宽高
    int hopeW;
    int hopeH;
    // resultW 代表最终设置的宽，resultH 代表最终设置的高
    int resultW;
    int resultH;

    // View 模板代码，省略了 exactly 模式，高度同理
    //    hopeW = (int) mPaint.measureText(mText);
    //    hopeW += getPaddingLeft() + getPaddingRight();
    //    hopeH = mTextSize + getPaddingTop() + getPaddingBottom();
    //
    //    // ViewGroup 模板代码
    //    hopeW = getPaddingLeft() + getPaddingRight();
    //    hopeH = getPaddingTop() + getPaddingBottom();
    //    measureChildren(widthMeasureSpec, heightMeasureSpec);
    //    MarginLayoutParams layoutParams;
    //    for (int i = 0; i < getChildCount(); i++) {
    //      View child = getChildAt(i);
    //      layoutParams = (MarginLayoutParams) child.getLayoutParams();
    //      // 子元素不可见时，不参与布局，因此不需要将其尺寸计算在内
    //      if (child.getVisibility() == View.GONE) {
    //        continue;
    //      }
    //      hopeW += child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
    //      hopeH += child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    //    }

    // 下面代码 View 和 ViewGroup 两者相同
    //    if (widthMode == MeasureSpec.AT_MOST) {
    //      if (!TextUtils.isEmpty(mText)) {
    //        resultW = Math.min(hopeW, widthSize);
    //      }
    //    } else if (widthMode == MeasureSpec.EXACTLY) {
    //      resultW = widthSize;
    //    } else if (widthMode == MeasureSpec.UNSPECIFIED) {
    //      resultW = hopeW;
    //    }
    // setMeasuredDimension(resultW, resultH);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    // ViewGroup 模板代码
    //    int topStart = getPaddingTop();
    //    int leftStart = getPaddingLeft();
    //    int childW;
    //    int childH;
    //    MarginLayoutParams layoutParams = null;
    //    for ( int i = 0;i < getChildCount();i++ ) {
    //      View child = getChildAt(i);
    //      layoutParams = (MarginLayoutParams) child.getLayoutParams();
    //
    //      //子元素不可见时，不参与布局，因此不需要将其尺寸计算在内
    //      if ( child.getVisibility() == View.GONE ) {
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
    //      child.layout(leftStart,topStart, leftStart + childW, topStart + childH);
    //
    //      leftStart += childW + layoutParams.rightMargin;
    //      topStart += childH + layoutParams.bottomMargin;
    //    }
  }

  /**
   * 使用 Picture 前最好关闭硬件加速，以免引起不必要的问题；可以把 Picture 看作是一个录制 Canvas 操作的录像机
   *
   * <p>beginRecording 和 endRecording 是成对使用的,一个开始录制,一个是结束录制,两者之间的操作将会存储在 Picture 中, 并不会显示在屏幕中，在
   * endRecording 后不应再操作 canvas
   *
   * <p>通常当有些内容我们需要绘制多次时，可以先用它录制下来，之后直接把录制的 Picture 绘制出来就可以了,直接绘制 Picture 会比在绘制一遍各种内容快一些
   */
  private void initRecording() {
    Log.i("zyx", "initRecording");
    // 开始录制 (接收返回值 Canvas)
    // 此处设置的 width、height 对实际显示出来的图像并没有影响，mPicture.getWidth() 和 mPicture.getHeight() 拿到的就是
    // beginRecording 设置的宽高值，如果你并不使用，此处可以设置成任意值
    Canvas canvas = mPicture.beginRecording(0, 0);
    // 创建一个画笔
    Paint paint = new Paint();
    paint.setColor(Color.BLUE);
    paint.setStyle(Paint.Style.FILL);
    // 在 Canvas 中具体操作
    // 位移
    canvas.translate(250, 250);
    // 绘制一个圆
    canvas.drawCircle(0, 0, 100, paint);
    mPicture.endRecording();
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    Log.i("zyx", "onSizeChanged");
    super.onSizeChanged(w, h, oldw, oldh);
    mWidth = w;
    mHeight = h;
  }

  /**
   * Android 绘制都是按顺序的，先绘制的内容会被后绘制的内容覆盖
   *
   * <p>在继承已有控件的基础上添加绘制代码时，需要考虑代码的绘制顺序
   *
   * <p>当存在子 View 时，每一个 ViewGroup 会先调用自己的 onDraw() 来绘制完自己的主体之后再去绘制它的子 View
   *
   * <p>完整的绘制过程
   *
   * <p>1. 背景 ： 发生在 drawBackground 方法中，但此方法是 private 的，一般在 XML 或者 Java 代码中设置
   *
   * <p>2. 主体(onDraw)
   *
   * <p>3. 子 View(dispatchDraw)
   *
   * <p>4. 滑动边缘渐变和滑动条 XML 或者 setScrollBar 系列
   *
   * <p>5 前景(6.0 以上) XML 或者 setForeground 设置
   *
   * <p>也可以重写 draw 方法，将绘制代码设置在 super.draw 前或者后
   */
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    camera(canvas);
  }

  // 绘制子 View
  @Override
  protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
  }

  // 绘制滑动边缘渐变和滑动条、前景色
  @Override
  public void onDrawForeground(Canvas canvas) {
    super.onDrawForeground(canvas);
  }

  /**
   * 硬件加速 : 指把绘制 View 的计算工作交给 GPU 来处理
   *
   * <p>未开启硬件加速时，View 的绘制是由 CPU 将绘制内容通过 CPU 转换成像素信息再显示到屏幕上
   *
   * <p>开启硬件加速后，CPU 将绘制内容交给 GPU 操作，最终由 GPU 完成渲染工作
   *
   * <p>硬件加速的好处：1. GPU 相较于 CPU 对于图形计算有更大的优势 2. 绘制机制的不同，导致界面重绘时刷新效率极大提高(GPU 重绘时只更新需要更新的
   * View，而关闭硬件加速后需要全部重绘)
   *
   * <p>硬件加速的弊端：canvas 的一些 API 在硬件加速下无法生效
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

  /** 绘制颜色 */
  private void drawColor(Canvas canvas) {
    canvas.drawColor(Color.BLUE);
  }

  /** 绘制点 */
  private void drawPoint(Canvas canvas) {
    canvas.drawPoint(200, 200, mPaint);
    canvas.drawPoints(new float[] {500, 500, 500, 600, 500, 700}, mPaint);
  }

  /** 绘制一条直线 */
  private void drawLine(Canvas canvas) {
    canvas.drawLine(300, 300, 500, 600, mPaint);
    // 绘制一组线 每四数字(两个点的坐标)确定一条线
    canvas.drawLines(new float[] {100, 200, 200, 200, 100, 300, 200, 300}, mPaint);
  }

  /** 绘制矩形 */
  /** Rect 和 RectF 的区别：最大的区别是精度不同，rect 是 int 的，rectF 是 float 的 */
  private void drawRect(Canvas canvas) {
    // canvas.drawRect(200, 500, 600, 700, mPaint);

    Rect rect = new Rect();
    rect.set(200, 500, 600, 700);
    canvas.drawRect(rect, mPaint);
  }

  /** 绘制圆角矩形,30,30 表示圆弧的两个半径，30,30 这个点到 X,Y 轴包围的范围就是圆弧的角度,当 X,Y 分别 >= 矩形的宽高一半时，圆角矩形会变成一个椭圆 */
  private void drawRoundRect(Canvas canvas) {
    // 第一种
    RectF rectF = new RectF(100, 100, 800, 400);
    canvas.drawRoundRect(rectF, 30, 30, mPaint);
    // 第二种
    canvas.drawRoundRect(100, 100, 800, 400, 360, 160, mPaint);
  }

  /** 绘制椭圆,实际上就是绘制一个矩形的内切图形,传递的 左上右下 实际是矩形的坐标; 如果长宽相等，就是一个圆 */
  private void drawOval(Canvas canvas) {
    // 第一种
    RectF rectF = new RectF(100, 100, 700, 400);
    canvas.drawOval(rectF, mPaint);
    // 第二种
    canvas.drawOval(100, 100, 800, 400, mPaint);
  }

  /** 绘制圆, cx、cy 表示圆心坐标,radius 表示半径 */
  private void drawCircle(Canvas canvas) {
    canvas.drawCircle(300, 400, 200, mPaint);
  }

  /**
   * 绘制圆弧, 顺时针旋转为正角度方向(sweepAngle取值范围是 [-360, 360) 度)
   *
   * <p>在默认的屏幕坐标系中角度增大方向为顺时针
   *
   * <p>userCenter 表示是否使用中心点，如果使用，起点和终点会连向中心点，否则就只是起点和终点连线围起来的弧形
   *
   * <p>startAngle 开始角度 sweepAngle 扫过角度 useCenter 是否使用中心
   */
  private void drawArc(Canvas canvas) {
    // 绘制背景矩形
    RectF rectF = new RectF(100, 100, 800, 400);
    mPaint.setColor(Color.GRAY);
    canvas.drawRect(rectF, mPaint);
    // 绘制圆弧
    mPaint.setColor(Color.BLUE);
    canvas.drawArc(rectF, 0, 90, false, mPaint);

    RectF rectF2 = new RectF(100, 600, 800, 900);
    // 绘制背景矩形
    mPaint.setColor(Color.GRAY);
    canvas.drawRect(rectF2, mPaint);
    // 绘制圆弧
    mPaint.setColor(Color.BLUE);
    canvas.drawArc(rectF2, 0, 90, true, mPaint);
  }

  /** 画布操作. 所有的画布操作都只影响后续的绘制，对之前已经绘制过的内容没有影响 */

  /** 位移，将画布圆心移动到指定位置。位移是基于当前位置移动,而不是每次基于屏幕左上角的 (0,0) 点移动 */
  private void canvasTranslate(Canvas canvas) {
    mPaint.setColor(Color.BLACK);
    canvas.translate(200, 200);
    canvas.drawCircle(0, 0, 100, mPaint);

    mPaint.setColor(Color.BLUE);
    canvas.translate(200, 200);
    canvas.drawCircle(0, 0, 100, mPaint);
  }

  /** 缩放，缩放的中心默认为坐标原点,而缩放中心轴就是坐标轴; 当缩放比例为负数的时候会根据缩放中心轴进行翻转(可以看做是对折) */
  private void canvasScale(Canvas canvas) {
    canvas.translate(getWidth() / 2, getHeight() / 2);
    RectF rect = new RectF(0, -400, 400, 0); // 矩形区域
    mPaint.setColor(Color.BLACK); // 绘制黑色矩形
    canvas.drawRect(rect, mPaint);

    // 先根据缩放中心(坐标原点)缩放到原来的 0.5 倍，然后分别按照 x 轴和 y 轴进行翻转
    canvas.scale(-0.5f, -0.5f, 200, 0); // 画布缩放  <-- 缩放中心向右偏移了200个单位,中心轴也向右移动了
    mPaint.setColor(Color.BLUE); // 绘制蓝色矩形
    canvas.drawRect(rect, mPaint);

    // 缩放也是可以叠加的，缩放比例相乘
    mPaint.setStyle(Style.STROKE);
    canvas.translate(getWidth() / 2, getHeight() / 2);
    RectF rect2 = new RectF(-400, -400, 400, 400); // 矩形区域
    for (int i = 0; i <= 20; i++) {
      canvas.scale(0.9f, 0.9f);
      canvas.drawRect(rect2, mPaint);
    }
  }

  /** 旋转:默认的旋转中心依旧是坐标原点; 旋转角度也是可以叠加的(相加) */
  private void canvasRotate(Canvas canvas) {
    // 示例一
    canvas.translate(getWidth() / 2, getHeight() / 2);
    RectF rect = new RectF(0, -400, 400, 0); // 矩形区域
    mPaint.setColor(Color.BLACK); // 绘制黑色矩形
    canvas.drawRect(rect, mPaint);

    canvas.rotate(180); // 旋转180度 <-- 默认旋转中心为原点
    mPaint.setColor(Color.BLUE); // 绘制蓝色矩形
    canvas.drawRect(rect, mPaint);

    // 示例二
    canvas.translate(getWidth() / 2, getHeight() / 2);
    RectF rect2 = new RectF(0, -400, 400, 0); // 矩形区域
    mPaint.setColor(Color.BLACK); // 绘制黑色矩形
    canvas.drawRect(rect, mPaint);

    canvas.rotate(180, 200, 0); // 旋转180度 <-- 旋转中心向右偏移200个单位
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

  /** 错切(skew)，特殊类型的线性变换 */
  private void canvasSkew(Canvas canvas) {
    canvas.translate(getWidth() / 2, getHeight() / 2);
    RectF rect = new RectF(0, 0, 200, 200); // 矩形区域
    mPaint.setColor(Color.BLACK); // 绘制黑色矩形
    canvas.drawRect(rect, mPaint);
    /** float sx, float sy ; sx x 方向上倾斜角度的 tan 值，sy 是 y 轴上的 tan 值 */
    canvas.skew(1, 0); // 水平错切 <- 45度
    mPaint.setStyle(Style.STROKE);
    mPaint.setColor(Color.BLUE); // 绘制蓝色矩形
    canvas.drawRect(rect, mPaint);
  }

  // 上面针对画布的操作，一般都需要配合下面的组合使用,避免对后面的代码产生影响
  private void saveRestore() {
    // restore
    // 状态回滚，就是从栈顶取出一个状态然后根据内容进行恢复，调用一次 restore 方法则将状态栈中第5次取出，根据里面保存的状态进行状态恢复
    // restoreToCount
    // 弹出指定位置以及以上所有状态，并根据指定位置状态进行恢复
    // 以上面状态栈图片为例，如果调用restoreToCount(2) 则会弹出 2 3 4 5 的状态，并根据第 2 次保存的状态进行恢复
    // getSaveCount
    // 获取保存的次数，即状态栈中保存状态的数量，以上面状态栈图片为例，使用该函数的返回值为 5
    // 不过需要注意，该函数的最小返回值为 1，即使弹出了所有的状态，返回值依旧为 1，代表默认状态

    //    save();      //保存状态
    //    ...          //具体操作
    //    restore();   //回滚到之前的状态
  }

  private void drawPicture(Canvas canvas) {
    // 让存储在 picture 中的 canvas 绘制出来，有 3 种方法

    // 1.调用 mPicture.draw(canvas); 会影响Canvas的状态
    mPicture.draw(canvas);

    // 2.canvas.drawPicture 使用 Canvas 提供的 drawPicture 方法绘制
    canvas.drawPicture(mPicture);
    canvas.drawPicture(mPicture, new RectF(0, 0, 50, 200)); // 该处 rect 暂时不清楚作用

    // 3.将 Picture 包装成为 PictureDrawable，使用 PictureDrawable 的 draw 方法绘制
    PictureDrawable drawable = new PictureDrawable(mPicture);
    // 设置绘制区域,图形超出部分会被忽略
    drawable.setBounds(0, 0, 250, 500);
    // 绘制
    drawable.draw(canvas);
  }

  /**
   * 三种 bitmap 获取方式
   *
   * <p>BitmapFactory.decodeResource(getResources(), R.drawable.wallhaven_doe);
   *
   * <p>BitmapFactory.decodeFile("/sdcard/bitmap.png");
   *
   * <p>BitmapFactory.decodeStream();
   */
  private void drawBitmap(Canvas canvas) {
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallhaven_doe);
    // 第一种
    // public void drawBitmap (Bitmap bitmap, Matrix matrix, Paint paint);
    canvas.drawBitmap(bitmap, new Matrix(), new Paint());

    // 第二种,指定了图片左上角的坐标(与坐标原点的距离)
    // public void drawBitmap (Bitmap bitmap, float left, float top, Paint paint);
    canvas.drawBitmap(bitmap, 20, 30, new Paint());

    /**
     * Rect src 指定绘制图片的区域; Rect dst 或 RectF dst 指定图片在屏幕上显示(绘制)的区域
     *
     * <p>用一张图片包含了大量的素材，在绘制的时候每次只截取一部分进行绘制，这样可以大大的减少素材数量
     */
    // 第三种
    // public void drawBitmap (Bitmap bitmap, Rect src, Rect dst, Paint paint);
    // public void drawBitmap (Bitmap bitmap, Rect src, RectF dst, Paint paint);
    // 指图片显示部分(左上角的四分之一)
    Rect src1 = new Rect(0, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
    // 显示图片的全部
    Rect src2 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    // 指定图片在屏幕上显示的区域
    Rect dst = new Rect(0, mHeight / 2, mWidth, mHeight);
    // 绘制图片
    canvas.drawBitmap(bitmap, src1, dst, null);
  }

  /**
   * 绘制文字
   *
   * <p>public void drawText (String text, float x, float y, Paint paint)
   *
   * <p>public void drawText(String text, int start, int end, float x, float y, Paint paint)
   *
   * <p>public void drawPosText (String text, float[] pos, Paint paint)
   *
   * <p>drawTextOnPath(String text, Path path, float hOffset, float vOffset, Paint paint)
   *
   * <p>drawPosText float[] 指定每个字符的 x、y 坐标;必须指定所有字符位置,否则直接 crash 掉,且性能差,该 api 已弃用
   *
   * <p>drawText() 方法参数中的 y 值,就是指定的基线的位置,用来让所有文字互相对齐的基准线; x 并不是文字真实的位置,会往左一点点
   * 因为文字本身是有空隙的，宽度略大于实际显示的宽度
   */
  private void drawText(Canvas canvas) {
    String str = "中华人民共和国";
    // float x, float y 简单理解为字体的左下角坐标
    canvas.drawText(str, 200, 500, mPaint);
    // int start, int end，截取 [x,y) 区间的字符串
    // canvas.drawText(str, 1, 3, mWidth / 2, mHeight / 2, mPaint);
    // 沿着一条 Path 来绘制文字, hOffset 和 vOffset 文字相对于 Path 的水平偏移量和竖直偏移量
    // 设置 hOffset 为 5, vOffset 为 10, 文字就会右移 5 像素和下移 10 像素
    // canvas.drawTextOnPath(str, new Path(), 0, 0, mPaint);
  }

  /**
   * StaticLayout 换行绘制文字
   *
   * <p>与 canvas drawText 相比，StaticLayout 能在 view 的边缘自动折行，也能识别 \n 换行符
   *
   * <p>width 是文字区域的宽度，文字到达这个宽度后就会自动换行
   *
   * <p>align 是文字的对齐方向
   *
   * <p>spacingmult 是行间距的倍数，通常情况下填 1 就好
   *
   * <p>spacingadd 是行间距的额外增加值，通常情况下填 0 就好
   *
   * <p>includeadd 是指是否在文字上下添加额外的空间，来避免某些过高的字符的绘制出现越界
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

  /** path 路径 使用 Path 不仅能够绘制简单图形，也可以绘制比较复杂的图形; 根据路径绘制文本和剪裁画布都会用到 Path */
  private void drawPathBase(Canvas canvas) {
    // lineTo 上次操作结束的点到目标点的连线，如果没有进行过操作则默认点为坐标原点
    // moveTo 改变下次操作的起点
    // setLastPoint 重置上一次操作的最后一个点，与 moveTo 不同的是，会影响上一次点的坐标
    // close 连接当前最后一个点和最初的点，如果连接了无法形成封闭图形，则 close 什么也不做; mPaint setStyle 为填充模式时可能也会起到这个效果
    // rLineTo、rMoveTo 指相对位置,相对于当前位置
    // path.setFillType(fillType) 是用来设置图形自相交时的填充算法，与 path 绘制的方向有关系;  WINDING 是「全填充」(默认)，而 EVEN_ODD
    // 是填充非相交的部分
    // path 重置有 reset 和 rewind 两种模式，区别是 reset 保留 fillType 模式，但不保存原有数据；rewind 相反，一般选择 reset
    canvas.translate(mWidth / 2, mHeight / 2);
    Path path = new Path();
    // path.lineTo(100, 200);
    path.lineTo(200, 200);
    // path.moveTo(200, 100);
    // path.setLastPoint(200, 100);
    path.lineTo(200, 0);
    path.close();
    canvas.drawPath(path, mPaint);
  }

  /**
   * addXxx 系列(除了圆弧)
   *
   * <p>圆形 public void addCircle (float x, float y, float radius, Path.Direction dir)
   *
   * <p>椭圆 public void addOval (RectF oval, Path.Direction dir)
   *
   * <p>矩形 public void addRect(float left,float top, float right, float bottom, Path.Direction dir)
   *
   * <p>public void addRect(RectF rect,Path.Direction dir)
   *
   * <p>Direction.CW 顺时针 Direction.CCW 逆时针
   */
  private void drawPathAddXXX(Canvas canvas) {
    canvas.translate(mWidth / 2, mHeight / 2); // 移动坐标系到屏幕中心
    Path path = new Path();
    path.addRect(-200, -200, 300, 200, Direction.CCW);
    canvas.drawPath(path, mPaint);

    /** 单纯使用 Direction.CW/CCW 看不出区别，需要配合 setLastPoint 才能看出明显区别 */
    canvas.translate(mWidth / 2, mHeight / 2); // 移动坐标系到屏幕中心
    Path path2 = new Path();
    path.addRect(-200, -200, 200, 200, Path.Direction.CCW);
    path.setLastPoint(-300, 300); // <-- 重置最后一个点的位置
    canvas.drawPath(path2, mPaint);
  }

  /**
   * addPath
   *
   * <p>public void addPath (Path src);
   *
   * <p>public void addPath (Path src, float dx, float dy); 将 src 进行了位移之后再添加进当前 path 中
   *
   * <p>public void addPath (Path src, Matrix matrix); 将 src 添加到当前 path 之前先使用 Matrix 进行变换
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

  /**
   * addArc arcTo 绘制圆弧
   *
   * <p>public void addArc (RectF oval, float startAngle, float sweepAngle)
   *
   * <p>public void arcTo(RectF oval, float startAngle, float sweepAngle)
   *
   * <p>public void arcTo(RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo)
   *
   * <p>是否使用 moveTo 将变量移动到圆弧的起点位移
   *
   * <p>forceMoveTo true(默认) -> 将最后一个点移动到圆弧起点，即不连接最后一个点与圆弧起点; false -> 不移动，而是连接最后一个点与圆弧起点
   */
  private void drawPathAddArc(Canvas canvas) {
    // 例子一
    canvas.translate(mWidth / 2, mHeight / 2); // 移动坐标系到屏幕中心
    // canvas.scale(1, -1); // <-- 注意 翻转y坐标轴

    Path path = new Path();
    path.lineTo(100, 100);

    RectF oval = new RectF(0, 0, 300, 300);
    canvas.drawRect(oval, mPaint);
    // path.addArc(oval, 0, 90); //默认是 true 的 addArc
    path.arcTo(oval, 0, 90, true); // <-- 和上面一句作用等价
    canvas.drawPath(path, mPaint);

    // 例子二
    canvas.translate(mWidth / 2, mHeight / 2); // 移动坐标系到屏幕中心
    canvas.scale(1, -1); // <-- 注意 翻转y坐标轴

    Path path2 = new Path();
    path.lineTo(100, 100);

    RectF oval2 = new RectF(0, 0, 300, 300);
    path.arcTo(oval2, 0, 270);
    // path.arcTo(oval,0,270,false);
    canvas.drawPath(path2, mPaint);
  }

  /** 其它 api */
  private void drawPathOtherApi() {
    Path path = new Path();
    path.isEmpty(); // path 是否为空

    path.lineTo(0, 400);
    path.lineTo(400, 400);
    path.lineTo(400, 0);
    path.lineTo(0, 0);
    RectF rect = new RectF();
    path.isRect(rect); // 当前 path 是否是一个矩形，如果 path 是一个矩形的话，会将矩形的信息存放进参数 rect 中

    path.set(new Path()); // 将新的 path 赋值给当前 path
    path.offset(300, 0); // 平移 path
    // path.offset (float dx, float dy, Path dst) 将当前 path 平移后的状态存入 dst 中，不会影响当前 path
  }

  /** offset 测试 */
  private void drawPathOffset(Canvas canvas) {
    canvas.translate(mWidth / 2, mHeight / 2);
    Path path = new Path(); // path 中添加一个圆形(圆心在坐标原点)
    path.addCircle(0, 0, 100, Path.Direction.CW);

    Path dst = new Path(); // dst 中添加一个矩形
    dst.addRect(-200, -200, 200, 200, Path.Direction.CW);

    path.offset(300, 0, dst); // 平移
    canvas.drawPath(path, mPaint); // 绘制 path

    mPaint.setColor(Color.BLUE); // 更改画笔颜色
    canvas.drawPath(dst, mPaint); // 绘制 dst
    // 会发现 dst 绘制出来不是矩形,而是 offset 位移后的圆; 说明 dst 有内容时，会清空 dst 原有内容并存入当前 path 的内容
  }

  /**
   * 范围裁剪
   *
   * <p>clipRect() 裁剪一个矩形范围
   *
   * <p>clipPath() 裁剪任意形状
   */
  private void clipPath(Canvas canvas) {
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallhaven_doe);
    //    canvas.clipRect(0, 0, getWidth() / 2 / 2, getHeight() / 2 / 2);
    //    canvas.drawBitmap(bitmap, 0, 0, mPaint);

    Path path = new Path();
    path.addCircle(getWidth() / 2, getHeight() / 2, 500, Path.Direction.CW);
    canvas.clipPath(path);
    canvas.drawBitmap(bitmap, 0, 0, mPaint);
  }

  /**
   * 布尔运算：用一些简单的图形通过一些规则合成一些相对比较复杂，或难以直接得到的图形
   *
   * <p>DIFFERENCE：Path1 中减去 Path2 后剩下的部分
   *
   * <p>INTERSECT：Path1 与 Path2 相交的部分
   *
   * <p>UNION：包含全部 Path1 和 Path2 全部
   *
   * <p>XOR：包含 Path1 与 Path2 但不包括两者相交的部分
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
}
