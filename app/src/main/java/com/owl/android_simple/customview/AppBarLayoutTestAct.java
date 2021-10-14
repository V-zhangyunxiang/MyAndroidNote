package com.owl.android_simple.customview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.owl.android_simple.R;

/**
 * Description
 *
 * @author zhangyunxiang Date 2018/12/5 11:57
 */
public class AppBarLayoutTestAct extends AppCompatActivity
    implements AppBarLayout.OnOffsetChangedListener {
  /*
  1. CoordinatorLayout 是这个库的组织容器，一切基于 support design 扩展出来的特性都应该发生在 CoordinatorLayout 及它的子 View 体系中

  2. CoordinatorLayout 是一个普通的 ViewGroup，它的布局特性类似于 FrameLayout

  3. AppbarLayout 应该作为一个 CoordinatorLayout 的直接子 View，否则它与普通的 LinearLayout 无异。

  4. AppbarLayout 的子 View 不仅仅是 Toolbar,它们可以是任何的 View，但通常和 Toolbar 配合使用

    CoordinatorLayout
            |
    AppbarLayout(CollapsingToolbarLayout)
            |
          Toolbar
  */
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTransStatus();
    setContentView(R.layout.appbar_main_test);
  }

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

  @Override
  public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
    int offset = Math.abs(verticalOffset);
    int scrollRange = appBarLayout.getTotalScrollRange();
    if (offset <= scrollRange / 2) {
    } else {
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
