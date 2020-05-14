package com.sweethearts.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import com.sweethearts.ui.activity.LaucherActivity;
import com.sweethearts.ui.activity.LoginActivity;
import com.sweethearts.ui.view.LaucherView;

public class LaucherModel {
    public void loadLoginActivity(final Activity activity,LaucherView laucherView){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(activity,LoginActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        },2000);

    }


}
