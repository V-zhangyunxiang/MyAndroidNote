package com.xiaozhu.testproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public String isMarried;

    public void setIsMarried(String isMarried) {
        this.isMarried = isMarried;
    }

    public String getIsMarried() {
        return isMarried;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.i("zyx", "taslkIdA=" + this.getTaskId());
//        String a = AAA.A;
    }

    public void click(View v) {
        //a++;
//        Intent intent = new Intent(this, CustomCodeBgActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        this.getApplicationContext().startActivity(intent);

        //不会激活组件，带有默认选项
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(webIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (activities.size() > 0) {
            //打开选择器
            startActivity(webIntent);
            //表示至少一个应用将响应该 Intent
            //return true;
        }
    }

    public void twoClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.xiaozhu.com"));
        //强制使用选择器
        Intent chooser = Intent.createChooser(intent, "选择浏览器");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

}
