package com.owl.android_simple.tablayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.owl.android_simple.R;

/** A simple {@link Fragment} subclass. */
public class TabFragmentOne extends Fragment {

  public TabFragmentOne() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    return inflater.inflate(R.layout.tab_vp_one, container, false);
  }
}
