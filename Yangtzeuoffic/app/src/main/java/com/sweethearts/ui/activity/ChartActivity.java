package com.sweethearts.ui.activity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ScreenUtils;
import com.sweethearts.R;
import com.sweethearts.Utils.MyUtils;
import com.sweethearts.entity.GradeBean;
import com.sweethearts.presenter.ChartPresenter;
import com.sweethearts.ui.activity.base.BaseActivity;
import com.sweethearts.ui.view.ChartView;


import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;


public class ChartActivity extends BaseActivity implements ChartView {

    private List<GradeBean> datas;
    private Toolbar toolbar;
    private LineChartView mLineChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        datas = ((GradeBean) getIntent().getSerializableExtra("data")).getGradeBeans();
        super.onCreate(savedInstanceState);
        ScreenUtils.setLandscape(this);
        setContentView(R.layout.activity_chart);
        init();
        MyUtils.setToolbarBackToHome(this, toolbar);
    }

    @Override
    public void findViews() {
        toolbar = findViewById(R.id.toolbar);
        mLineChartView = findViewById(R.id.mLineChartView);

    }

    @Override
    public void setEvents() {
        ChartPresenter president = new ChartPresenter(this, this);
        president.setChart();

    }

    @Override
    public LineChartView getLineChartView() {
        return mLineChartView;
    }


    @Override
    public List<GradeBean> getData() {
        return datas;
    }
}
