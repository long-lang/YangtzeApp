package com.sweethearts.ui.activity;

import android.os.Bundle;

import com.sweethearts.R;
import com.sweethearts.presenter.LaucherPresenter;
import com.sweethearts.ui.activity.base.BaseActivity;
import com.sweethearts.ui.view.LaucherView;

public class LaucherActivity extends BaseActivity implements LaucherView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laucher);
        init();

    }

    @Override
    public void findViews() {

    }

    @Override
    public void setEvents() {
        LaucherPresenter laucherPresenter=new LaucherPresenter(this,this);
        laucherPresenter.LoadLoginView();
    }
}
