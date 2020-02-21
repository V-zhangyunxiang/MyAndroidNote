package com.xiaozhu.testproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CustomCodeBgActivity extends AppCompatActivity {
   private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_code_bg);
        VerificationCodeView verificationcodeview = findViewById(R.id.verificationCodeView);
        textView = findViewById(R.id.text);
        verificationcodeview.setOnCodeFinishListener(
                content -> textView.setText(content));

    }



}
