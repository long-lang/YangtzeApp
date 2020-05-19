package com.sweethearts.presenter;

import android.app.Activity;

import com.sweethearts.model.TestModel;
import com.sweethearts.ui.view.TestView;

public class TestPresenter {
    private Activity activity;
    private TestView view;
    private TestModel mode;

    public TestPresenter(Activity activity, TestView view) {
        this.activity = activity;
        this.view = view;
        mode = new TestModel();
    }

    public void getTestInfo() {
        mode.getTestInfo(activity, view);

    }
}
