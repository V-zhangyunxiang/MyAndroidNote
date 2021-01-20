package com.owl.android_simple;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Description
 *
 * @author zhangyunxiang Date 2018/12/21 13:48
 */
public class TranslucentStatusBarActivity extends AppCompatActivity {
  LinearLayout ll;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // 动态添加输入框
    //        ll = findViewById(R.id.ooo);
    //        for (int i = 0; i < 1; i++) {
    //            View view = LayoutInflater.from(this).inflate(R.layout.shake_anim, null);
    //            Button btn = view.findViewById(R.id.button2);
    //            btn.setText("lll");
    //            ll.addView(view);
    //        }
    //
    //        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View v) {
    //                for (int i = 0; i < 1; i++) {
    //                    EditText ed = ll.getChildAt(i).findViewById(R.id.editText);
    //                    Log.i("zyx", ed.getText().toString());
    //                }
    //            }
    //        });

    // setFitsSystemWindows(this,true);
    // addStatusWithColorAll(this, getResources().getColor(R.color.colorAccent),true);
  }

  /**
   * 设置页面最外层布局 FitsSystemWindows 属性
   *
   * @param activity
   * @param value
   */
  public static void setFitsSystemWindows(Activity activity, boolean value) {
    ViewGroup contentFrameLayout = (ViewGroup) activity.findViewById(android.R.id.content);
    View parentView = contentFrameLayout.getChildAt(0);
    if (parentView != null) {
      parentView.setFitsSystemWindows(value);
    }
  }

  /**
   * 添加状态栏占位视图
   *
   * @param activity
   */
  private void addStatusViewWithColor(Activity activity, int color) {
    ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
    View statusBarView = new View(activity);
    ViewGroup.LayoutParams lp =
        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
    statusBarView.setBackgroundColor(color);
    contentView.addView(statusBarView, lp);
  }

  // 沉浸式终极方案
  public void addStatusWithColorAll(Activity activity, int color, boolean isChangeIconColor) {
    // 设置 paddingTop
    ViewGroup rootView =
        (ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content);
    rootView.setPadding(0, getStatusBarHeight(), 0, 0);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      // 5.0 以上直接设置状态栏颜色
      activity.getWindow().setStatusBarColor(color);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isChangeIconColor) {
        getWindow()
            .getDecorView()
            .setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
      }
    } else {
      // 根布局添加占位状态栏
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
