package com.owl.android_simple.tablayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.owl.android_simple.R;

/** A simple {@link Fragment} subclass. */
public class TabFragmentTwo extends Fragment {

  public TabFragmentTwo() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    return inflater.inflate(R.layout.tab_vp_two, container, false);
  }
}
