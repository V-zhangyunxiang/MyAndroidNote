package com.xiaozhu.testproject;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


/**
 * Description
 *
 * @author zhangyunxiang
 * Date 2018/12/5 11:57
 */
public class UserCommentActivity extends AppCompatActivity implements AppBarLayout
        .OnOffsetChangedListener {
    private ImageView iv;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private View toolbarOpen, toolbarClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_comment);

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        appBarLayout = findViewById(R.id.app_bar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        //        collapsingToolbarLayout.setTitle("xiaozhu");
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);
        collapsingToolbarLayout.setExpandedTitleColor(Color.BLUE);

        toolbarOpen = findViewById(R.id.include_toolbar_open);
        toolbarClose = findViewById(R.id.include_toolbar_close);

        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        int offset = Math.abs(i);
        int scrollRange = appBarLayout.getTotalScrollRange();
        if (offset <= scrollRange / 2) {
            toolbarOpen.setVisibility(View.VISIBLE);
            toolbarClose.setVisibility(View.GONE);
        } else {
            toolbarClose.setVisibility(View.VISIBLE);
            toolbarOpen.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appBarLayout.removeOnOffsetChangedListener(this);
    }
}
