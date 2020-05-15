package com.sweethearts.presenter;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

import com.sweethearts.model.GradeModel;
import com.sweethearts.ui.fragment.GradeFragment;
import com.sweethearts.ui.view.GradeView;

public class GradePresenter {
    private GradeModel model;
    private GradeView view;
    private Activity activity;

    public GradePresenter(Activity activity, GradeView view) {
        this.view = view;
        this.activity = activity;
        model = new GradeModel();
    }

    public void setViewPager() {
        model.setViewPager(activity, view);
    }

}
