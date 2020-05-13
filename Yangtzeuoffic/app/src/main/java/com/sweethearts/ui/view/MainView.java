package com.sweethearts.ui.view;

import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sweethearts.ui.fragment.GradeFragment;
import com.sweethearts.ui.fragment.HomeFragment;
import com.sweethearts.ui.fragment.MineFragment;
import com.sweethearts.ui.fragment.TableFragment;

public interface MainView {
    FrameLayout getFragmentContainer();
    HomeFragment getHomeFragment();
    TableFragment getTableFragment();

    MineFragment getMineFragment();
    BottomNavigationView getBottomNavigationView();

    GradeFragment getGradeFragment();

}
