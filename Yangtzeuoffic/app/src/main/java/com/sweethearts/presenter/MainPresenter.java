package com.sweethearts.presenter;

import android.app.Activity;


import com.sweethearts.model.MainModel;
import com.sweethearts.ui.view.MainView;

public class MainPresenter {
    private MainModel model;
    private MainView view;
    private Activity activity;
    public MainPresenter(Activity activity, MainView view) {
        this.view = view;
        this.activity = activity;
        model = new MainModel();
    }

    public void setBottomViewWithFragment() {
        model.setBottomViewWithFragment(activity, view);
    }
}
