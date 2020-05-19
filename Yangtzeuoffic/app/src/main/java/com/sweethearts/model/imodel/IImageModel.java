package com.sweethearts.model.imodel;

import android.app.Activity;

import com.sweethearts.ui.view.MyImageView;


public interface IImageModel {
    void showDialog(Activity activity, MyImageView view, String title, Object object);
}
