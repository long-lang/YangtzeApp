package com.sweethearts.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sweethearts.R;
import com.sweethearts.presenter.LaucherPresenter;
import com.sweethearts.ui.view.LaucherView;

// 启动类 先加载启动页面 再在2s后加载登陆页面
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
