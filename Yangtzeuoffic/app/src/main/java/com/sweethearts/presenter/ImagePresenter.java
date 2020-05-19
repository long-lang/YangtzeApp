package com.sweethearts.presenter;

import android.app.Activity;

import com.sweethearts.model.ImageModel;
import com.sweethearts.ui.view.MyImageView;

public class ImagePresenter {
    private Activity activity;
    private MyImageView view;
    private ImageModel mode;

    public ImagePresenter(Activity activity, MyImageView view) {
        this.activity = activity;
        this.view = view;
        mode = new ImageModel();
    }

    public void showDialog(String title, Object object) {
        mode.showDialog(activity, view, title, object);
    }
}
