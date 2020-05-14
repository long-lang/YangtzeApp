package com.sweethearts.model;

import android.app.Activity;
import android.content.Intent;

import com.sweethearts.ui.activity.LaucherActivity;
import com.sweethearts.ui.activity.LoginActivity;
import com.sweethearts.ui.view.LaucherView;

public class LaucherModel {
    public void loadLoginActivity(final Activity Activity,LaucherView laucherView){
        Intent intent =new Intent(Activity,LoginActivity.class);
        Activity.startActivity(intent);
    }


}
