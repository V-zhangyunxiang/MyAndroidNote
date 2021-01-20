package com.owl.android_simple.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.owl.android_simple.R;
import java.lang.reflect.Field;

/**
 * Description
 *
 * @author zhangyunxiang Date 2018/11/29 17:26
 */
public class VerificationCodeView extends LinearLayout
    implements TextWatcher, View.OnKeyListener, View.OnFocusChangeListener {
  private Context mContext;
  private onCodeFinishListener onCodeFinishListener;
  private long endTime = 0;
  /** 输入框数量（圆形输入框） */
  private int mEtNumber;
  /** 输入框类型 （数字，隐藏数字） */
  private VCInputType mEtInputType;
  /** 文字颜色 */
  private int mEtTextColor;
  /** 文字大小 */
  private float mEtTextSize;

  /** 输入框背景 */
  private int mEtTextBg;
  /** 光标样式 */
  private int mCursorDrawable;

  public enum VCInputType {
    NUMBER,
    NUMBERPASSWORD
  }

  /**
   * 用作显示隐藏密码
   *
   * @return 类型枚举
   */
  public VCInputType getmEtInputType() {
    return mEtInputType;
  }

  public void setmEtInputType(VCInputType mEtInputType) {
    this.mEtInputType = mEtInputType;
  }

  public interface onCodeFinishListener {
    void onComplete(String content);
  }

  public void setOnCodeFinishListener(onCodeFinishListener onCodeFinishListener) {
    this.onCodeFinishListener = onCodeFinishListener;
  }

  public VerificationCodeView(Context context) {
    super(context);
  }

  public VerificationCodeView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    this.mContext = context;
    @SuppressLint({"Recycle", "CustomViewStyleable"})
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.verificationCodeView);
    mEtNumber = typedArray.getInteger(R.styleable.verificationCodeView_vcv_et_number, 4);
    int inputType =
        typedArray.getInt(
            R.styleable.verificationCodeView_vcv_et_inputType, VCInputType.NUMBER.ordinal());
    mEtInputType = VCInputType.values()[inputType];
    mEtTextColor =
        typedArray.getColor(R.styleable.verificationCodeView_vcv_et_text_color, Color.BLACK);
    mEtTextSize =
        typedArray.getDimensionPixelSize(R.styleable.verificationCodeView_vcv_et_text_size, 16);
    mEtTextBg =
        typedArray.getResourceId(
            R.styleable.verificationCodeView_vcv_et_bg, R.drawable.ed_shape_oval);
    mCursorDrawable =
        typedArray.getResourceId(
            R.styleable.verificationCodeView_vcv_et_cursor, R.drawable.ed_cursor_green);
    typedArray.recycle();
    initView();
  }

  private void initView() {
    for (int i = 0; i < mEtNumber; i++) {
      EditText editText = new EditText(mContext);
      createEditText(editText);
      if (i == 0) {
        editText.setFocusable(true);
      }
    }
  }

  private void createEditText(EditText editText) {
    LinearLayout.LayoutParams layoutParams =
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    layoutParams.gravity = Gravity.CENTER;
    editText.setGravity(Gravity.CENTER);
    editText.setCursorVisible(true);
    editText.setMaxEms(1);
    editText.setTextColor(mEtTextColor);
    editText.setTextSize(mEtTextSize);
    editText.setMaxLines(1);
    editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
    switch (mEtInputType) {
      case NUMBER:
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        break;
      case NUMBERPASSWORD:
        editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        break;
    }
    editText.setBackgroundResource(mEtTextBg);
    editText.addTextChangedListener(this);
    editText.setOnKeyListener(this);
    editText.setOnFocusChangeListener(this);
    // 修改光标的颜色（反射）
    try {
      Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
      f.setAccessible(true);
      f.set(editText, mCursorDrawable);
    } catch (Exception ignored) {

    }
    addView(editText, layoutParams);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {}

  @Override
  public void afterTextChanged(Editable s) {
    if (s.length() != 0) {
      focus();
    }
  }

  @Override
  public void onFocusChange(View v, boolean hasFocus) {
    if (hasFocus) {
      focus();
    }
  }

  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_DEL) {
      backFocus();
    }
    return false;
  }

  private void focus() {
    int count = getChildCount();
    EditText editText;
    for (int i = 0; i < count; i++) {
      editText = (EditText) getChildAt(i);
      if (editText.getText().length() < 1) {
        editText.setCursorVisible(true);
        editText.requestFocus();
        return;
      } else {
        editText.setCursorVisible(false);
      }
    }
    EditText lastEditText = (EditText) getChildAt(mEtNumber - 1);
    if (lastEditText.getText().length() > 0) {
      getResult();
    }
  }

  private void getResult() {
    StringBuilder stringBuffer = new StringBuilder();
    EditText editText;
    for (int i = 0; i < mEtNumber; i++) {
      editText = (EditText) getChildAt(i);
      stringBuffer.append(editText.getText());
    }
    if (onCodeFinishListener != null) {
      onCodeFinishListener.onComplete(stringBuffer.toString());
    }
  }

  private void backFocus() {
    long startTime = System.currentTimeMillis();
    EditText editText;
    for (int i = mEtNumber - 1; i >= 0; i--) {
      editText = (EditText) getChildAt(i);
      if (editText.getText().length() >= 1 && startTime - endTime > 100) {
        editText.setText("");
        editText.setCursorVisible(true);
        editText.requestFocus();
        endTime = startTime;
        return;
      }
    }
  }
}
