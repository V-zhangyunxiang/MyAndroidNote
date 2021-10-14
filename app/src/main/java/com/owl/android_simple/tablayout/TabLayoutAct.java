package com.owl.android_simple.tablayout;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.owl.android_simple.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author zhangyunxiang Date 2018/12/21 15:58
 */
public class TabLayoutAct extends AppCompatActivity {
  private TabLayout mTabLayout;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tablayout_main);
    mTabLayout = findViewById(R.id.tab_demo);
    mTabLayout.addTab(mTabLayout.newTab());
    mTabLayout.addTab(mTabLayout.newTab());
    mTabLayout.addTab(mTabLayout.newTab());
    mTabLayout.addTab(mTabLayout.newTab());
    ViewPager viewPager = findViewById(R.id.view_pager);
    mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
    mTabLayout.setTabTextColors(
        getResources().getColor(R.color.colorPrimary),
        getResources().getColor(R.color.colorOrange));
    final List<Fragment> list = new ArrayList<>();
    list.add(new TabFragmentOne());
    list.add(new TabFragmentTwo());
    list.add(new TabFragmentThree());
    list.add(new TabFragmentFour());
    final List<String> title = new ArrayList<>();
    title.add("国内");
    title.add("国际");
    title.add("要闻");
    title.add("科技");
    FragmentPagerAdapter fragmentPagerAdapter =
        new FragmentPagerAdapter(getSupportFragmentManager()) {
          @Override
          public Fragment getItem(int i) {
            return list.get(i);
          }

          @Override
          public int getCount() {
            return list.size();
          }

          @Nullable
          @Override
          public CharSequence getPageTitle(int position) {
            return title.get(position);
          }
        };
    viewPager.addOnPageChangeListener(
        new ViewPager.OnPageChangeListener() {
          @Override
          public void onPageScrolled(int i, float v, int i1) {}

          @Override
          public void onPageSelected(int i) {}

          @Override
          public void onPageScrollStateChanged(int i) {}
        });
    mTabLayout.addOnTabSelectedListener(
        new TabLayout.BaseOnTabSelectedListener() {
          @Override
          public void onTabSelected(TabLayout.Tab tab) {
            if (tab.getTag() != null) {
              Toast.makeText(TabLayoutAct.this, tab.getText(), Toast.LENGTH_SHORT).show();
            }
          }

          @Override
          public void onTabUnselected(TabLayout.Tab tab) {}

          @Override
          public void onTabReselected(TabLayout.Tab tab) {}
        });
    viewPager.setAdapter(fragmentPagerAdapter);
    mTabLayout.setupWithViewPager(viewPager);
  }
}
