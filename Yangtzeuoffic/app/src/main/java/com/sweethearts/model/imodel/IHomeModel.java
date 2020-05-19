package com.sweethearts.model.imodel;

import android.app.Activity;

import com.sweethearts.ui.view.HomeView;

public interface IHomeModel {
    void setToolbarWithDrawer(Activity activity, HomeView view);

    void fitViewPagerAndTabLayout(Activity activity, HomeView view);

    void getHoliday(Activity activity, HomeView view);

    void getWeather(Activity activity, HomeView view);

    void setStudyTime(Activity activity, HomeView view);
}
