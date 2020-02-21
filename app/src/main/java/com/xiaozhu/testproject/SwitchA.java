package com.xiaozhu.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Description
 *
 * @author zhangyunxiang
 * Date 2018/12/7 14:07
 */
public class SwitchA extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_a);
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SwitchA.this, SwitchB.class);
                startActivity(intent);
                overridePendingTransition(R.anim.b_in, R.anim.a_out);
            }
        });
    }

}
