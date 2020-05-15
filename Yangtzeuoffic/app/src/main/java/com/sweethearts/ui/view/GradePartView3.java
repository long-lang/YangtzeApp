package com.sweethearts.ui.view;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sweethearts.entity.PointBean;
import com.sweethearts.ui.adapter.PointAdapter;


import java.util.List;

public interface GradePartView3 {

    String getUrl();

    String getIndexUrl();

    SmartRefreshLayout getRefresh();

    List<PointBean> getGradeBeans();

    TextView getAllNumberView();

    TextView getAllScoreView();

    TextView getAllPointView();

    RecyclerView getRecyclerView();

    LinearLayout getHeaderContainer();

    PointAdapter getPointAdapter();
}
