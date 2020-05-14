package com.sweethearts.presenter;

import android.app.Activity;

import com.sweethearts.model.LaucherModel;
import com.sweethearts.ui.activity.LaucherActivity;
import com.sweethearts.ui.view.LaucherView;

public class LaucherPresenter {
    private Activity activity;
    private LaucherView laucherView;
    private LaucherModel laucherModel;
    public LaucherPresenter(Activity activity,LaucherView laucherView) {
        this.activity = activity;
        this.laucherView=laucherView;
        laucherModel=new LaucherModel();

    }

    public void  LoadLoginView(){
        laucherModel.loadLoginActivity(activity,laucherView);
    }
}
