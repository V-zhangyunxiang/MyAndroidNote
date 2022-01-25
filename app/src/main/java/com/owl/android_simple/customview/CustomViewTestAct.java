package com.owl.android_simple.customview;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.owl.android_simple.R;
import com.owl.android_simple.data.PieData;
import com.owl.android_simple.view.CanvasView;
import com.owl.android_simple.view.PieView;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author zhangyunxiang Date 2021/1/26 11:43
 */

/*
 自定义 View 分两类
  1.自定义 ViewGroup 一般是利用现有的组件根据特定的布局方式来组成新的组件，大多继承自 ViewGroup 或各种 Layout，包含有子 View
  2.在没有现成的 View，需要自己实现的时候，就使用自定义 View，一般继承自 View，SurfaceView 或其他的 View，不包含子 View
 自定义 View 大致步骤
   1.构造函数	      初始化(初始化画笔 Paint)
   2.onMeasure	    测量 View 的大小
   3.onSizeChanged  确定 View 大小(记录当前 View 的宽高)
   4.onLayout	      确定子 View 布局(无子 View，不关心)
   5.onDraw	        实际绘制内容
   6.提供接口	      提供接口(提供设置数据的接口)
*/
public class CustomViewTestAct extends AppCompatActivity {
  PieView pieView;
  int[] mColors = {
    Color.parseColor("#ff00ff"), Color.parseColor("#00ff00"), Color.parseColor("#ffff00")
  };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.custom_main_test);
    CanvasView canvasView = findViewById(R.id.canvas_view);
  }

  // 绘制饼状图
  private void setPieView() {
    // pieView = findViewById(R.id.pieView);
    List<PieData> data = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      PieData data1 = new PieData();
      data1.color = mColors[i];
      if (i == 0) {
        data1.percentage = 0.3f;
      } else if (i == 1) {
        data1.percentage = 0.5f;
      } else {
        data1.percentage = 0.2f;
      }
      data.add(data1);
    }
    pieView.setData(data);
  }
}
