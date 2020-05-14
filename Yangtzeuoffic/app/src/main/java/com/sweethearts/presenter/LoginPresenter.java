package com.sweethearts.presenter;

import android.app.Activity;

import com.sweethearts.model.LoginModel;
import com.sweethearts.ui.view.LoginView;

public class LoginPresenter {
    private LoginModel model;
    private LoginView view;
    private Activity activity;

    public LoginPresenter(Activity activity, LoginView view) {
        this.view = view;
        this.activity = activity;
        model = new LoginModel();
    }
    public void loadLoginEvent() {
        model.loadLoginEvent(activity, view);
    }
}
