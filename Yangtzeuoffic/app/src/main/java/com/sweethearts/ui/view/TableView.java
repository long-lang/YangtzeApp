package com.sweethearts.ui.view;

import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sweethearts.entity.Course;
import com.sweethearts.ui.adapter.TableFragmentAdapter;

import java.util.List;

public interface TableView {
    Toolbar getToolbar();

    LinearLayout getWeekLayout();

    RecyclerView getRecyclerView();

    List<Course> getCourse();

    SmartRefreshLayout getRefreshLayout();

    TabLayout getTabLayout();

    TableFragmentAdapter getTableFragmentAdapter();

    ImageView getTableBackgroundView();


    String getIdsUrl();

    String getIndexUrl();
}
