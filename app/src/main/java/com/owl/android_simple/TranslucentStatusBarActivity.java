package com.owl.android_simple;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Description
 *
 * @author zhangyunxiang Date 2018/12/21 13:48
 */
public class TranslucentStatusBarActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // setFitsSystemWindows(this, true);
    // addStatusWithColor(this, getResources().getColor(R.color.colorAccent), true);
  }

  /** 设置页面最外层布局 FitsSystemWindows 属性，需配合透明状态栏使用（代码和 XML 都可设置透明状态栏） */
  public void setFitsSystemWindows(Activity activity, boolean value) {
    ViewGroup contentFrameLayout = activity.findViewById(android.R.id.content);
    View parentView = contentFrameLayout.getChildAt(0);
    if (parentView != null) {
      parentView.setFitsSystemWindows(value);
    }
  }

  /** 代码设置状态栏透明 配合 fitsSystemWindows 使用 */
  public void setTransStatus() {
    if (Build.VERSION.SDK_INT >= 21) {
      Window window = getWindow();
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window
          .getDecorView()
          .setSystemUiVisibility(
              View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.TRANSPARENT);
    } else if (Build.VERSION.SDK_INT >= 19) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
  }

  // 沉浸式终极方案
  public void addStatusWithColor(Activity activity, int color, boolean isChangeIconColor) {
    // 设置 paddingTop
    ViewGroup rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
    rootView.setPadding(0, getStatusBarHeight(), 0, 0);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      // 5.0 以上直接设置状态栏字色和图标浅黑色
      activity.getWindow().setStatusBarColor(color);
      // 设置图标颜色为黑色
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isChangeIconColor) {
        getWindow()
            .getDecorView()
            .setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
      }
    } else {
      // 加一个和 status bar 一样大小的 View 站位，从而让让标题栏不会与 status bar 重叠。而图片延伸到状态栏只需要设置
      // FLAG_TRANSLUCENT_STATUS 就 OK
      activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
      View statusBarView = new View(activity);
      ViewGroup.LayoutParams lp =
          new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
      statusBarView.setBackgroundColor(color);
      decorView.addView(statusBarView, lp);
    }
  }

  /**
   * 利用反射获取状态栏高度
   *
   * @return
   */
  public int getStatusBarHeight() {
    int result = 0;
    // 获取状态栏高度的资源id
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }
}
