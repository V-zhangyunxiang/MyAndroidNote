package com.owl.android_simple.animate;

import android.animation.Animator;
import android.animation.Animator.AnimatorPauseListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import com.owl.android_simple.R;
import com.owl.android_simple.view.UpDownView;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Description
 *
 * @author zhangyunxiang Date 2020-02-18 22:27
 */
public class UpDownAnimAct extends AppCompatActivity {
  UpDownView mView;
  ImageView mHand;
  ImageView mCircle;
  ObjectAnimator animator3;
  Vibrator vibrator;
  AnimatorSet animatorSet = new AnimatorSet();
  boolean isRepeat = true;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.up_and_down_anim);
    mView = findViewById(R.id.round_up_down);
    mHand = findViewById(R.id.iv_hand);
    mCircle = findViewById(R.id.iv_circle);
    vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);

    findViewById(R.id.button).setOnClickListener(v -> setDonghua());

    findViewById(R.id.button2)
        .setOnClickListener(
            v -> {
              vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
              vibrator.vibrate(500);
            });

    findViewById(R.id.button3).setOnClickListener(v -> setDonghua());
  }

  private void setDonghua() {
    mHand.setVisibility(View.VISIBLE);
    mCircle.setVisibility(View.VISIBLE);
    mView.setVisibility(View.VISIBLE);

    ObjectAnimator animator1 = ObjectAnimator.ofFloat(mHand, "translationY", 0, dip2px(-110));
    ObjectAnimator animator2 = ObjectAnimator.ofFloat(mCircle, "translationY", 0, dip2px(-110));
    animator3 = ObjectAnimator.ofFloat(mView, "topProgress", 110, 0);
    animator3.addListener(
        new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {
            Log.i("zyx", "animator3");
            super.onAnimationEnd(animation);
            // TODO: 2020-02-21 直播间上移一段距离
          }
        });
    ObjectAnimator animator4 = ObjectAnimator.ofFloat(mView, "bottomProgress", 110, 0);
    animatorSet.setDuration(1000);
    animatorSet.addListener(
        new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {
            Log.i("zyx", "animatorSet");
            super.onAnimationEnd(animation);
            mHand.setVisibility(View.INVISIBLE);
            mCircle.setVisibility(View.INVISIBLE);
            mView.setVisibility(View.INVISIBLE);
            mHand.setTranslationY(0);
            mCircle.setTranslationY(0);
            mView.setOriginValue(110, 110);
            animatorSet.removeAllListeners();
            if (isRepeat) {
              isRepeat = false;
              repeat();
            }
          }
        });
    animatorSet.addPauseListener(
        new AnimatorPauseListener() {
          @Override
          public void onAnimationPause(Animator animation) {}

          @Override
          public void onAnimationResume(Animator animation) {}
        });
    animatorSet.setInterpolator(new FastOutLinearInInterpolator());
    animatorSet.play(animator1).with(animator2).with(animator3).before(animator4);
    animatorSet.start();
  }

  private void repeat() {
    ScheduledExecutorService singleThreadScheduledPool =
        Executors.newSingleThreadScheduledExecutor();
    singleThreadScheduledPool.scheduleAtFixedRate(
        new Runnable() {
          @Override
          public void run() {
            String threadName = Thread.currentThread().getName();
            Log.i("zxy", "线程：" + threadName + ",正在执行");
            runOnUiThread(
                new Runnable() {
                  public void run() {
                    if (!animatorSet.isRunning()) {
                      setDonghua();
                    }
                  }
                });
          }
        },
        0,
        2,
        TimeUnit.SECONDS);
  }

  public int dip2px(float dpValue) {
    final float scale = this.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }
}
