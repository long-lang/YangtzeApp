package com.sweethearts.presenter;

import android.app.Activity;

import com.sweethearts.model.ChartModel;
import com.sweethearts.ui.activity.ChartActivity;
import com.sweethearts.ui.view.ChartView;

public class ChartPresenter {
    private Activity activity;
    private ChartView view;
    private ChartModel mode;

    public ChartPresenter(Activity activity, ChartView view) {
        this.activity = activity;
        this.view = view;
        mode = new ChartModel();
    }

    public void setChart() {
        mode.setChart(activity, view);
    }
}
