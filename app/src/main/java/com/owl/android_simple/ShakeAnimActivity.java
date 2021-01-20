package com.owl.android_simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

/**
 * Description
 *
 * @author zhangyunxiang Date 2018/12/6 14:06
 */
public class ShakeAnimActivity extends MainActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.shake_anim);
    // 左右抖动
    final EditText editText = findViewById(R.id.editText);
    Button btn = findViewById(R.id.button2);
    btn.setOnClickListener(
        v -> {
          if (TextUtils.isEmpty(editText.getText().toString())) {
            Animation animation =
                AnimationUtils.loadAnimation(ShakeAnimActivity.this, R.anim.shake);
            editText.startAnimation(animation);
          }
        });
  }
}
