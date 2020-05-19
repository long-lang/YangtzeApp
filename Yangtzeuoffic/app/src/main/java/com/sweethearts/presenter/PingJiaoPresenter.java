package com.sweethearts.presenter;

import android.app.Activity;

import com.sweethearts.model.PingJiaoModel;
import com.sweethearts.ui.view.PingJiaoView;


public class PingJiaoPresenter {
    private PingJiaoModel model;
    private PingJiaoView view;
    private Activity activity;

    public PingJiaoPresenter(Activity activity, PingJiaoView view) {
        this.view = view;
        this.activity = activity;
        model = new PingJiaoModel();
    }

    public void loadData() {
        model.loadData(activity, view);
    }
}
