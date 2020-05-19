package com.sweethearts.presenter;

import android.app.Activity;

import com.sweethearts.model.HomePart1Model;
import com.sweethearts.ui.view.HomePartView1;

public class HomePart1Presenter {
    private HomePart1Model model;
    private HomePartView1 view;
    private Activity activity;

    public HomePart1Presenter(Activity activity, HomePartView1 view) {
        this.view = view;
        this.activity = activity;
        model = new HomePart1Model();
    }


    public void loadData() {
        model.loadData(activity,view);
    }

    public void loadNotice() {
        model.loadNotice(activity,view);
    }
}
