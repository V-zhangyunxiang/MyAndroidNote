package com.owl.android_simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Description
 *
 * @author zhangyunxiang Date 2019/1/17 14:46
 */
public class IncludeLayoutTestActivity extends AppCompatActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.include_layout_test);

    TextView tv = findViewById(R.id.text_one);

    View view = findViewById(R.id.include_item_one);
    TextView tv2 = view.findViewById(R.id.text_one);
    tv.setText("new aaaa");

    View view1 = findViewById(R.id.include_item_two);
    TextView tv_two = view1.findViewById(R.id.text_one);
    tv_two.setText("new bbbb");
  }
}
