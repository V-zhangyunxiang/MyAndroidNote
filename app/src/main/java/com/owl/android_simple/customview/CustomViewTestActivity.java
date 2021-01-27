package com.owl.android_simple.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/1/26 11:43
 */

/*
* 自定义 ViewGroup 一般是利用现有的组件根据特定的布局方式来组成新的组件，大多继承自 ViewGroup 或各种 Layout，包含有子 View
* 在没有现成的 View，需要自己实现的时候，就使用自定义 View，一般继承自 View，SurfaceView 或其他的 View，不包含子 View
* 自定义 View 在大多数情况下都有替代方案，利用图片或者组合动画来实现，但是使用后者可能会面临内存耗费过大，制作麻烦等诸多问题
* 如果对 View 的宽高进行修改了，不要调用 super.onMeasure( widthMeasureSpec, heightMeasureSpec),要调用 setMeasuredDimension(widthSize, heightSize) 这个函数。
* Q: 在测量完 View 并使用 setMeasuredDimension 函数之后 View 的大小基本上已经确定了，那么为什么还要再次确定 View 的大小呢？
  A: 这是因为 View 的大小不仅由 View 本身控制，而且受父控件的影响，所以我们在确定 View 大小的时候最好使用系统提供的 onSizeChanged 回调函数
  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
  }
  UNSPECIFIED 默认值，父控件没有给子 View 任何限制，子 View 可以设置为任意大小。
  EXACTLY	  	表示父控件已经确切的指定了子 View 的大小。
  AT_MOST	    表示子 View 具体大小没有尺寸限制，但是存在上限，上限一般为父 View 大小
*
*
* */
public class CustomViewTestActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
}