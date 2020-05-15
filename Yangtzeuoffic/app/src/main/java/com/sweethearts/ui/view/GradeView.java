package com.sweethearts.ui.view;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sweethearts.ui.adapter.FragmentAdapter;

public interface GradeView {
    FragmentAdapter getAdapter();
    ViewPager getViewPager();

    TabLayout getTabLayout();

    Toolbar getToolbar();

}
