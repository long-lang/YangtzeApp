package com.sweethearts.model.imodel;

import android.app.Activity;

import com.sweethearts.ui.view.NewsView1;

public interface INewsModel1 {
    void loadBanner(Activity activity, NewsView1 view);

    void fitGridView(Activity activity, NewsView1 view);

    void fitJwcGridView(Activity activity, NewsView1 view);
}
