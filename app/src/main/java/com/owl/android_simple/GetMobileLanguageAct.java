package com.owl.android_simple;

import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

/**
 * Description
 *
 * @author zhangyunxiang Date 2019-11-11 14:06
 */
public class GetMobileLanguageAct extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Locale locale;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      locale = LocaleList.getDefault().get(0);
    } else {
      locale = Locale.getDefault();
    }
    String language = locale.getLanguage();
    System.out.println("语言为: " + language);
  }
}
