package com.sweethearts.model;

import android.app.Activity;

import com.sweethearts.ui.view.MainView;

public interface IMainModel {
    void setBottomViewWithFragment(Activity activity, MainView view);

    void initEvents(Activity activity, MainView view);


}
