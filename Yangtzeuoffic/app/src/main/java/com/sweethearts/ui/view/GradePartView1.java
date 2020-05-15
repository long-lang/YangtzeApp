package com.sweethearts.ui.view;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sweethearts.entity.GradeBean;
import com.sweethearts.ui.adapter.GradeAdapter;
import java.util.List;


public interface GradePartView1 {

    RecyclerView getRecyclerView();

    SmartRefreshLayout getRefresh();

    GradeAdapter getAdapter();

    //成绩容器
    List<GradeBean> getGradeBeans();

    Toolbar getToolbar();

    String getUrl();

    String getIndexUrl();

}
