package com.sweethearts.ui.view;

import android.app.ProgressDialog;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;
import com.sweethearts.ui.view.base.BaseView;


import java.util.List;

public interface TestView extends BaseView {
     Toolbar getToolbar();
     TabLayout  getTabLayout();
     FrameLayout getContainer();
     ProgressDialog  getProgressDialog();
     FragmentManager getManager();

      List<Fragment> getFragments();
}
