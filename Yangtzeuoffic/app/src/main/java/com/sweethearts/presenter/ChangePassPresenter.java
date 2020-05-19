package com.sweethearts.presenter;

import android.app.Activity;

import com.sweethearts.model.ChangePassModel;
import com.sweethearts.ui.view.ChangePassView;


public class ChangePassPresenter {
    private ChangePassModel model;
    private ChangePassView view;
    private Activity activity;

    public ChangePassPresenter(Activity activity, ChangePassView view) {
        this.view = view;
        this.activity = activity;
        model = new ChangePassModel();
    }

    public void changePassEvent() {
        model.changePassEvent(activity, view);
    }
}
