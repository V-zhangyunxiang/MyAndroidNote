package com.owl.android_simple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Description
 *
 * @author zhangyunxiang Date 2019/1/7 14:12
 */
public class WebViewActivity extends AppCompatActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.webview_layout);
    WebView webView = findViewById(R.id.webview_test);
    WebSettings webSettings = webView.getSettings();
    webView.setWebViewClient(
        new WebViewClient() {
          @Override
          public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
          }
        });
    webSettings.setJavaScriptEnabled(true); // 开启JavaScript支持
    webSettings.setPluginState(WebSettings.PluginState.ON);
    // 设置自适应屏幕，两者合用
    webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
    webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
    webView.loadUrl(
        "https://test-m-00.xiaozhu"
            + ".com/h5youku2.html?vid=XMzgxOTMyMTQzNg==&target=1&client_id=16edde5f79e61324"
            + "&lodgeUnitId=32326640603&time=142000");
  }
}
