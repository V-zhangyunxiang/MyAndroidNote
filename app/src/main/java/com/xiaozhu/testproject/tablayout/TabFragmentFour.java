package com.xiaozhu.testproject.tablayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaozhu.testproject.R;

/** A simple {@link Fragment} subclass. */
public class TabFragmentFour extends Fragment {

  public TabFragmentFour() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    return inflater.inflate(R.layout.tab_vp_four, container, false);
  }
}
