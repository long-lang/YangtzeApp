package com.sweethearts.presenter;

import android.app.Activity;

import com.sweethearts.model.HomePart3Model;
import com.sweethearts.ui.view.HomePartView3;

public class HomePart3Presenter {
    private HomePart3Model model;
    private HomePartView3 view;
    private Activity activity;

    public HomePart3Presenter(Activity activity, HomePartView3 view) {
        this.view = view;
        this.activity = activity;
        model = new HomePart3Model();
    }

    public void loadView() {
        model.loadView(activity, view);
    }
}
