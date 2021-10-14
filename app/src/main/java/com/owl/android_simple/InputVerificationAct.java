package com.owl.android_simple;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.owl.android_simple.view.VerificationCodeView;

public class InputVerificationAct extends AppCompatActivity {
  private TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.input_custom_code);
    VerificationCodeView verificationcodeview = findViewById(R.id.verificationCodeView);
    textView = findViewById(R.id.text);
    verificationcodeview.setOnCodeFinishListener(content -> textView.setText(content));
  }
}
