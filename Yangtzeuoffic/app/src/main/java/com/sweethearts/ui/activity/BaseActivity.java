package com.sweethearts.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sweethearts.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void init() {
        findViews();
        setEvents();
    }

   public abstract void findViews();
   public abstract void setEvents();
}
