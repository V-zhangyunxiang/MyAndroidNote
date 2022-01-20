package com.owl.android_simple;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.owl.android_simple.view.VoiceEndFireProgressView;
import java.util.List;

public class ExternalWebAct extends AppCompatActivity {
  private VoiceEndFireProgressView progressView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.i("zyx", "taskId=" + this.getTaskId());
    progressView = findViewById(R.id.progress_bar);

    progressView.setViewData(50);
  }

  public void click(View v) {
    // 不会激活组件，带有默认选项，可以设置默认打开方式
    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
    List<ResolveInfo> activities =
        getPackageManager().queryIntentActivities(webIntent, PackageManager.MATCH_DEFAULT_ONLY);
    if (activities.size() > 0) {
      // 打开选择器
      startActivity(webIntent);
    }
  }

  public void twoClick(View view) {
    // 没有默认选项
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.xiaozhu.com"));
    // 强制使用选择器
    Intent chooser = Intent.createChooser(intent, "选择浏览器");
    if (intent.resolveActivity(getPackageManager()) != null) {
      startActivity(chooser);
    }
  }
}
