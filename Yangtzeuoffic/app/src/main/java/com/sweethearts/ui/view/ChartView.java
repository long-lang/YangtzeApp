package com.sweethearts.ui.view;

import com.sweethearts.entity.GradeBean;

import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;

public interface ChartView {
    LineChartView getLineChartView();

    List<GradeBean> getData();
}
