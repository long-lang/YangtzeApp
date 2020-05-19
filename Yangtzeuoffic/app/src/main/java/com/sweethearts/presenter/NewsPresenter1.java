package com.sweethearts.presenter;

import android.app.Activity;

import com.sweethearts.model.NewsModel1;
import com.sweethearts.ui.view.NewsView1;

public class NewsPresenter1 {
    private NewsModel1 model;
    private NewsView1 view;
    private Activity activity;

    public NewsPresenter1(Activity activity, NewsView1 view) {
        this.view = view;
        this.activity = activity;
        model = new NewsModel1();
    }

    public void loadBanner() {
        model.loadBanner(activity, view);
    }

    public void fitGridView() {
        model.fitGridView(activity, view);
    }

    public void fitJwcGridView() {
        model.fitJwcGridView(activity, view);
    }
}
