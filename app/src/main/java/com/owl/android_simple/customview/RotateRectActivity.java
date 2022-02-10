package com.owl.android_simple.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.owl.android_simple.R;
import com.owl.android_simple.view.RotateRectView;

/**
 * Description
 *
 * @author zhangyunxiang Date 2022/2/8 16:55
 */
public class RotateRectActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.rorate_rect_360);
    RotateRectView rotateRectView = findViewById(R.id.round_up_down);
    findViewById(R.id.startDown)
        .setOnClickListener(
            new OnClickListener() {
              @Override
              public void onClick(View v) {
                ObjectAnimator degrees = ObjectAnimator.ofFloat(rotateRectView, "degrees", 0, 360);
                degrees.setInterpolator(new LinearInterpolator());
                degrees.setDuration(2000);
                degrees.addListener(
                    new AnimatorListenerAdapter() {
                      @Override
                      public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rotateRectView.setVisibility(View.GONE);
                      }

                      @Override
                      public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        rotateRectView.setVisibility(View.VISIBLE);
                      }
                    });
                degrees.start();
              }
            });
  }
}
