package com.owl.android_simple.animate;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.owl.android_simple.R;
/**
 * ObjectAnimator
 *
 * <p>如果是自定义控件，需要添加 setter / getter 方法
 *
 * <p>用 ObjectAnimator.ofXXX() 创建 ObjectAnimator 对象
 *
 * <p>用 start() 方法执行动画
 *
 * <p>setDuration(int duration) 设置动画时长
 *
 * <p>setInterpolator(Interpolator interpolator) 设置 Interpolator
 *
 * <p>用 addListener() 和 addUpdateListener() 来添加一个或多个监听器，移除监听器则是通过 remove[Update]Listener() 来指定移除对象
 *
 * <p>当动画被取消时，如果设置了 AnimatorListener，那么 onAnimationCancel() 和 onAnimationEnd()
 * 都会被调用，onAnimationCancel() 会先于 onAnimationEnd() 被调用
 *
 * <p>setRepeatMode() / setRepeatCount() 或 repeat() 方法设置重复执行
 */

/**
 * 使用 PropertyValuesHolder 来对多个属性同时做动画
 *
 * <p>使用 AnimatorSet 来同时管理调配多个动画
 *
 * <p>使用 PropertyValuesHolder.ofKeyframe() 来把一个属性拆分成多段，执行更加精细的属性动画
 */
public class AnimateTestAct extends AppCompatActivity {

  private TextView mText;
  private LinearLayout mLinear;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.type_evaluator_anim);
    mText = findViewById(R.id.text);
    mLinear = findViewById(R.id.line1);
  }

  private void Test() {
    ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLinear, "scaleX", 1f, 2f, 1f);
    ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLinear, "scaleY", 1f, 2f, 1f);
    AnimatorSet set = new AnimatorSet();
    set.setDuration(1000);
    set.playTogether(scaleX, scaleY);
    set.start();
  }

  public void click(View v) {
    Test();
  }

  private void ObjectAnimatorBase() {
    ObjectAnimator alpha = ObjectAnimator.ofFloat(mText, "alpha", 1f, 0f, 1f);
    ObjectAnimator rotation = ObjectAnimator.ofFloat(mText, "rotation", 0f, 360f, 0f);
    ObjectAnimator translationX = ObjectAnimator.ofFloat(mText, "translationX", 0f, -300f, 0f);
    ObjectAnimator scaleX = ObjectAnimator.ofFloat(mText, "scaleX", 1f, 2f, 1f);
  }

  private void PropertyValuesHolders() {
    PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", 1);
    PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", 1);
    PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha", 1);

    ObjectAnimator animator =
        ObjectAnimator.ofPropertyValuesHolder(mText, holder1, holder2, holder3);
    animator.start();
  }

  private void PropertyValuesHoldersOfKeyframe() {
    // 在 0% 处开始
    Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
    // 时间经过 50% 的时候，动画完成度 100%
    Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 100);
    // 时间见过 100% 的时候，动画完成度倒退到 80%，即反弹 20%
    Keyframe keyframe3 = Keyframe.ofFloat(1, 80);

    PropertyValuesHolder holder =
        PropertyValuesHolder.ofKeyframe("progress", keyframe1, keyframe2, keyframe3);

    ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mText, holder);

    animator.start();
  }

  /**
   * after(Animator anim) ：将现有动画插入到传入的动画之后执行
   *
   * <p>before(Animator anim)： 将现有动画插入到传入的动画之前执行
   *
   * <p>with(Animator anim) ：将现有动画和传入的动画同时执行
   */
  private void AnimSet() {
    ObjectAnimator oa1 = ObjectAnimator.ofFloat(mText, "rotationX", 0f, 360f);
    ObjectAnimator oa2 = ObjectAnimator.ofFloat(mText, "alpha", 1f, 0f);
    ObjectAnimator oa3 = ObjectAnimator.ofFloat(mText, "scaleX", 1f, 0f);
    AnimatorSet set = new AnimatorSet();
    set.setDuration(1000);
    set.playTogether(oa1, oa2, oa3); // 同时执行
    set.setStartDelay(300); // 延迟执行
    set.playSequentially(oa1, oa2, oa3); // 顺序执行
    set.start();
  }
}
