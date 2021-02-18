package com.owl.android_simple.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Picture;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/1/28 14:56
 */
public class CanvasView extends View {
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
    mPaint.setColor(Color.BLACK);
    // 设置笔渲染模式
    // STROKE                //描边
    // FILL                  //填充
    // FILL_AND_STROKE       //描边加填充，表示会加上描边的宽度
    mPaint.setStyle(Style.STROKE);
    // 描边宽度
    mPaint.setStrokeWidth(10);
    // 抗锯齿
    mPaint.setAntiAlias(true);
    // 设置文本字体大小
    mPaint.setTextSize(50);
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
   * @param widthMeasureSpec
   * @param heightMeasureSpec
   */
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);

    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

    // resultW 代表最终设置的宽，resultH 代表最终设置的高
    int resultW = widthSize;
    int resultH = heightSize;

    if (widthMode == MeasureSpec.AT_MOST) {
      // resultW = contentW < widthSize ? contentW : widthSize;
    }

    if (heightMode == MeasureSpec.AT_MOST) {
      // resultH = contentH < widthSize ? contentH : heightSize;
    }
    // setMeasuredDimension(resultW, resultH);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
  }

  // 录制内容方法
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

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    /** 绘制颜色 */
    // canvas.drawColor(Color.BLUE);
    /** 绘制点 */
    // canvas.drawPoint(200, 200, mPaint);
    /** 绘制一组点 */
    // canvas.drawPoints(new float[] {500, 500, 500, 600, 500, 700}, mPaint);
    /** 绘制一条直线 */
    // canvas.drawLine(300, 300, 500, 600, mPaint);
    /** 绘制一组线 每四数字(两个点的坐标)确定一条线 */
    //    canvas.drawLines(
    //        new float[] {
    //          100, 200, 200, 200,
    //          100, 300, 200, 300
    //        },
    //        mPaint);
    /** 绘制矩形 */
    // canvas.drawRect(200, 500, 600, 700, mPaint);
    //     Rect rect = new Rect(200, 500, 600, 700);
    //     rect.set(200, 500, 600, 700);
    //     canvas.drawRect(rect, mPaint);

    // RectF rectF = new RectF(200, 500, 600, 700);
    // rectF.set(200, 500, 600, 700);
    // canvas.drawRect(rectF, mPaint);
    /** Rect 和 RectF 的区别：最大的区别是精度不同，rect 是 int 的，rectF 是 float 的 */

    /** 绘制圆角矩形,30，30 表示圆弧的两个半径，30，30 这个点到 X,Y 轴包围的范围就是圆弧的角度，当 X，Y 分别 >= 矩形的宽高一半时，圆角矩形会变成一个椭圆 */
    // 第一种
    // RectF rectF = new RectF(100, 100, 800, 400);
    // canvas.drawRoundRect(rectF, 30, 30, mPaint);
    // 第二种
    // canvas.drawRoundRect(100, 100, 800, 400, 360, 160, mPaint);

    /** 绘制椭圆,实际上就是绘制一个矩形的内切图形,传递的 左上右下 实际是矩形的坐标; 如果长宽相等，就是一个圆 */
    // 第一种
    // RectF rectF = new RectF(100, 100, 700, 400);
    // canvas.drawOval(rectF, mPaint);
    // 第二种
    // canvas.drawOval(100, 100, 800, 400, mPaint);

    // 绘制圆, cx、cy 表示圆心坐标，radius 表示半径
    // canvas.drawCircle(300, 400, 200, mPaint);

    /**
     * 绘制圆弧, 顺时针旋转为正角度方向(没有负角度，sweepAngle取值范围是 [-360, 360) 度)
     *
     * <p>userCenter 表示是否使用中心点，如果使用，起点和终点会连向中心点，否则就只是起点和终点连线围起来的弧形
     */
    // startAngle  // 开始角度
    // sweepAngle  // 扫过角度
    // useCenter   // 是否使用中心
    // 第一种
    // 绘制背景矩形
    //    RectF rectF = new RectF(100, 100, 800, 400);
    //    mPaint.setColor(Color.GRAY);
    //    canvas.drawRect(rectF, mPaint);
    //    mPaint.setColor(Color.BLUE);
    // canvas.drawArc(rectF, 0, 90, false, mPaint);
    //
    //    RectF rectF2 = new RectF(100, 600, 800, 900);
    //    // 绘制背景矩形
    //    mPaint.setColor(Color.GRAY);
    //    canvas.drawRect(rectF2, mPaint);
    //    // 绘制圆弧
    //    mPaint.setColor(Color.BLUE);
    //    canvas.drawArc(rectF2, 0, 90, true, mPaint);
    // ------------------------------------ 下面是画布 --------------------------------------------
    /** 画布操作. 所有的画布操作都只影响后续的绘制，对之前已经绘制过的内容没有影响 */

    /** 位移，将画布圆心移动到指定位置。位移是基于当前位置移动，而不是每次基于屏幕左上角的 (0,0) 点移动 */
    //    mPaint.setColor(Color.BLACK);
    //    canvas.translate(200, 200);
    //    canvas.drawCircle(0, 0, 100, mPaint);
    //
    //    mPaint.setColor(Color.BLUE);
    //    canvas.translate(200, 200);
    //    canvas.drawCircle(0, 0, 100, mPaint);

    /** 缩放，缩放的中心默认为坐标原点,而缩放中心轴就是坐标轴; 当缩放比例为负数的时候会根据缩放中心轴进行翻转(可以看做是对折) */
    // 将坐标系原点移动到画布正中心，可以看做是先根据缩放中心(坐标原点)缩放到原来的 0.5 倍，然后分别按照 x 轴和 y 轴进行翻转
    //    canvas.translate(getWidth() / 2, getHeight() / 2);
    //    RectF rect = new RectF(0, -400, 400, 0); // 矩形区域
    //    mPaint.setColor(Color.BLACK); // 绘制黑色矩形
    //    canvas.drawRect(rect, mPaint);
    //
    //    canvas.scale(-0.5f, -0.5f, 200, 0); // 画布缩放  <-- 缩放中心向右偏移了200个单位,中心轴也向右移动了
    //
    //    mPaint.setColor(Color.BLUE); // 绘制蓝色矩形
    //    canvas.drawRect(rect, mPaint);

    // 缩放也是可以叠加的，缩放比例相乘
    //    mPaint.setStyle(Style.STROKE);
    //    canvas.translate(getWidth() / 2, getHeight() / 2);
    //    RectF rect = new RectF(-400, -400, 400, 400); // 矩形区域
    //    for (int i = 0; i <= 20; i++) {
    //      canvas.scale(0.9f, 0.9f);
    //      canvas.drawRect(rect, mPaint);
    //    }
    /** 旋转;默认的旋转中心依旧是坐标原点;旋转角度也是可以叠加的(相加) */
    // 将坐标系原点移动到画布正中心
    //    canvas.translate(getWidth() / 2, getHeight() / 2);
    //    RectF rect = new RectF(0, -400, 400, 0); // 矩形区域
    //    mPaint.setColor(Color.BLACK); // 绘制黑色矩形
    //    canvas.drawRect(rect, mPaint);
    //    canvas.rotate(180); // 旋转180度 <-- 默认旋转中心为原点
    //    mPaint.setColor(Color.BLUE); // 绘制蓝色矩形
    //    canvas.drawRect(rect, mPaint);

    // 将坐标系原点移动到画布正中心
    //    canvas.translate(getWidth() / 2, getHeight() / 2);
    //    RectF rect = new RectF(0, -400, 400, 0); // 矩形区域
    //    mPaint.setColor(Color.BLACK); // 绘制黑色矩形
    //    canvas.drawRect(rect, mPaint);
    //    canvas.rotate(180, 200, 0); // 旋转180度 <-- 旋转中心向右偏移200个单位
    //    mPaint.setColor(Color.BLUE); // 绘制蓝色矩形
    //    canvas.drawRect(rect, mPaint);

    //    mPaint.setStyle(Style.STROKE);
    //    canvas.translate(getWidth() / 2, getHeight() / 2);
    //    canvas.drawCircle(0, 0, 400, mPaint); // 绘制两个圆形
    //    canvas.drawCircle(0, 0, 380, mPaint);
    //    for (int i = 0; i <= 360; i += 10) { // 绘制圆形之间的连接线
    //      canvas.drawLine(0, 380, 0, 400, mPaint);
    //      canvas.rotate(10);
    //    }
    /** 错切(skew)，特殊类型的线性变换 */
    // 将坐标系原点移动到画布正中心
    //    canvas.translate(getWidth() / 2, getHeight() / 2);
    //    RectF rect = new RectF(0, 0, 200, 200); // 矩形区域
    //    mPaint.setColor(Color.BLACK); // 绘制黑色矩形
    //    canvas.drawRect(rect, mPaint);
    /** float sx, float sy ; sx x 方向上倾斜角度的 tan 值，sy 是 y 轴上的 tan 值 */
    //    canvas.skew(1, 0); // 水平错切 <- 45度
    //    mPaint.setStyle(Style.STROKE);
    //    mPaint.setColor(Color.BLUE); // 绘制蓝色矩形
    //    canvas.drawRect(rect, mPaint);

    // --------------------------------- 以下是 canvas 的相关 api -------------------------------------
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

    /** Picture 操作 */
    //  让存储在 picture 中的 canvas 绘制出来，有 3 种方法
    //  1. 调用 mPicture.draw(canvas); 会影响Canvas的状态
    //  2. canvas.drawPicture 使用 Canvas 提供的 drawPicture 方法绘制
    //  3. 将 Picture 包装成为 PictureDrawable，使用 PictureDrawable 的 draw 方法绘制

    // mPicture.draw(canvas);

    // canvas.drawPicture(mPicture);
    // canvas.drawPicture(mPicture, new RectF(0, 0, 50, 200)); // 该处 rect 暂时不清楚作用

    // 包装成为 Drawable
    // PictureDrawable drawable = new PictureDrawable(mPicture);
    // 设置绘制区域,图形超出部分会被忽略
    // drawable.setBounds(0, 0, 250, 500);
    // 绘制
    // drawable.draw(canvas);

    /** BitMap 操作 */

    /** 三种 bitmap 获取方式 */
    // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallhaven_doe);
    // BitmapFactory.decodeFile("/sdcard/bitmap.png");
    // BitmapFactory.decodeStream();

    /** 三种 canvas.drawBitmap 方式 */
    // 第一种
    // public void drawBitmap (Bitmap bitmap, Matrix matrix, Paint paint);
    // canvas.drawBitmap(bitmap,new Matrix(),new Paint());

    // 第二种,指定了图片左上角的坐标(与坐标原点的距离)
    // public void drawBitmap (Bitmap bitmap, float left, float top, Paint paint);
    // canvas.drawBitmap(bitmap, 20, 30, new Paint());
    /**
     * Rect src 指定绘制图片的区域; Rect dst 或 RectF dst 指定图片在屏幕上显示(绘制)的区域
     *
     * <p>用一张图片包含了大量的素材，在绘制的时候每次只截取一部分进行绘制，这样可以大大的减少素材数量
     */
    // 第三种
    // public void drawBitmap (Bitmap bitmap, Rect src, Rect dst, Paint paint);
    // public void drawBitmap (Bitmap bitmap, Rect src, RectF dst, Paint paint);
    // 指图片显示部分(左上角的四分之一)
    // Rect src = new Rect(0, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
    // 显示图片的全部
    // Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    // 指定图片在屏幕上显示的区域
    // Rect dst = new Rect(0, mHeight / 2, mWidth, mHeight);
    // 绘制图片
    // canvas.drawBitmap(bitmap, src, dst, null);
    /** BitMap 操作 */

    /** 绘制文字 */
    //    String str = "ABCDEFG";
    //    // float x, float y 简单理解为字体的左下角坐标
    //    canvas.drawText(str, 200, 500, mPaint);
    //    // int start, int end，截取 [x,y) 区间的字符串
    //    canvas.drawText(str, 1, 3, mWidth / 2, mHeight / 2, mPaint);

    /** path 路径 使用 Path 不仅能够绘制简单图形，也可以绘制比较复杂的图形; 根据路径绘制文本和剪裁画布都会用到 Path */

    /** moveTo setLastPoint lineTo close */
    // lineTo 上次操作结束的点到目标点的连线，如果没有进行过操作则默认点为坐标原点
    // moveTo 改变下次操作的起点
    // setLastPoint 重置上一次操作的最后一个点，与 moveTo 不同的是，会影响上一次点的坐标
    // close 连接当前最后一个点和最初的点，如果连接了无法形成封闭图形，则 close 什么也不做
    //    canvas.translate(mWidth / 2, mHeight / 2);
    //    Path path = new Path();
    //    // path.lineTo(100, 200);
    //    path.lineTo(200, 200);
    //    // path.moveTo(200, 100);
    //    // path.setLastPoint(200, 100);
    //    path.lineTo(200, 0);
    //    path.close();
    //    canvas.drawPath(path, mPaint);
    /** addXxx Direction.CW 顺时针 Direction.CCW 逆时针 */
    //    canvas.translate(mWidth / 2, mHeight / 2); // 移动坐标系到屏幕中心
    //    Path path = new Path();
    //    path.addRect(-200, -200, 300, 200, Direction.CCW);
    //    canvas.drawPath(path, mPaint);
    /** 单纯使用 Direction.CW/CCW 看不出区别，需要配合 setLastPoint 才能看出明显区别 */
    //    canvas.translate(mWidth / 2, mHeight / 2); // 移动坐标系到屏幕中心
    //    Path path = new Path();
    //    path.addRect(-200, -200, 200, 200, Path.Direction.CCW);
    //    path.setLastPoint(-300, 300); // <-- 重置最后一个点的位置
    //    canvas.drawPath(path, mPaint);
    /**
     * public void addPath (Path src);
     *
     * <p>public void addPath (Path src, float dx, float dy); 将 src 进行了位移之后再添加进当前 path 中
     *
     * <p>public void addPath (Path src, Matrix matrix); 将 src 添加到当前 path 之前先使用 Matrix 进行变换
     */
    canvas.translate(0, mHeight / 2);
    Path path2 = new Path();
    Path src = new Path();
    path2.addCircle(0, 0, 100, Path.Direction.CW);
    src.addCircle(0, 0, 100, Path.Direction.CW);
    for (int i = 0; i < 10; i++) {
      path2.addPath(src, 200 * (i + 1), 0);
      canvas.drawPath(path2, mPaint);
    }
    /**
     * addArc arcTo 绘制圆弧
     *
     * <p>public void addArc (RectF oval, float startAngle, float sweepAngle)
     *
     * <p>public void arcTo(RectF oval, float startAngle, float sweepAngle)
     *
     * <p>public void arcTo(RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo)
     * forceMoveTo true(默认) -> 将最后一个点移动到圆弧起点，即不连接最后一个点与圆弧起点; false -> 不移动，而是连接最后一个点与圆弧起点
     */

    /** 其它 api */
    //    path.isEmpty(); // path 是否为空
    //    path.isRect(new RectF()); // 当前 path 是否是一个矩形
    //    path.set(new Path()); // 将新的 path 赋值给当前 path
    //    path.offset(300, 0); // 平移 path

    // offset (float dx, float dy, Path dst) 将当前 path 平移后的状态存入 dst 中，不会影响当前 path
    //    canvas.translate(mWidth / 2, mHeight / 2); // 移动坐标系到屏幕中心
    //    Path path = new Path(); // path 中添加一个圆形(圆心在坐标原点)
    //    path.addCircle(0, 0, 100, Path.Direction.CW);
    //    Path dst = new Path(); // dst 中添加一个矩形
    //    dst.addRect(-200, -200, 200, 200, Path.Direction.CW);
    //    path.offset(300, 0, dst); // 平移
    //    canvas.drawPath(path, mPaint); // 绘制 path
    //    mPaint.setColor(Color.BLUE); // 更改画笔颜色
    //    canvas.drawPath(dst, mPaint); // 绘制dst
    // 会发现 dst 绘制出来不是矩形，而是 offset 位移后的圆，说明 dst 有内容时，会清空 dst 并存入当前 path
  }
}
