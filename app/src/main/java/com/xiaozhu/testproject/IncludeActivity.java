package com.xiaozhu.testproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Description
 *
 * @author zhangyunxiang
 * Date 2019/1/17 14:46
 */
public class IncludeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_test_activity);
        View view = findViewById(R.id.include_item_one);
        TextView tv = view.findViewById(R.id.text_one);
        tv.setText("new aaaa");

//        LinearLayout ll = view.findViewById(R.id.item_ll);
//        ll.setBackgroundColor(Color.BLUE);

        View view1 = findViewById(R.id.include_item_two);
        TextView tv_two = view1.findViewById(R.id.text_one);
        tv_two.setText("new bbbb");
    }
}
