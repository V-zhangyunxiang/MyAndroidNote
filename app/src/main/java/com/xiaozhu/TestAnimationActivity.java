package com.xiaozhu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.xiaozhu.testproject.R;

/**
 * Description
 *
 * @author zhangyunxiang
 * Date 2020-02-18 22:27
 */
public class TestAnimationActivity extends AppCompatActivity {

    UpDownView mView;
    ImageView mHand;
    ImageView mCircle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_down);
        mView = findViewById(R.id.round_up_down);
        mHand = findViewById(R.id.iv_hand);
        mCircle = findViewById(R.id.iv_circle);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AnimatorSet animatorSet = new AnimatorSet();

                ObjectAnimator animator1 = ObjectAnimator.ofFloat(mHand, "translationY", 0, dip2px(-110));
                animator1.setRepeatCount(-1);
                animator1.setRepeatMode(ValueAnimator.RESTART);

                ObjectAnimator animator2 = ObjectAnimator.ofFloat(mCircle, "translationY", 0, dip2px(-110));
                animator2.setRepeatCount(-1);
                animator2.setRepeatMode(ValueAnimator.RESTART);

                ObjectAnimator animator3 = ObjectAnimator.ofFloat(mView, "topProgress", 110, 0);
                animator3.setRepeatCount(-1);
                animator3.setRepeatMode(ValueAnimator.RESTART);

                ObjectAnimator animator4 = ObjectAnimator.ofFloat(mView, "bottomProgress", 110, 0);
                animator4.setRepeatCount(-1);
                animator4.setRepeatMode(ValueAnimator.RESTART);

                animatorSet.setDuration(1400);
                animatorSet.setInterpolator(new FastOutSlowInInterpolator());
                animatorSet.play(animator1).with(animator2).with(animator3);
                animatorSet.start();
            }
        });
    }

    public int dip2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
