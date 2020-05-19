package com.sweethearts.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.sweethearts.R;
import com.sweethearts.Utils.MyUtils;
import com.sweethearts.Utils.YangtzeuUtils;
import com.sweethearts.listener.OnResultListener;
import com.sweethearts.presenter.PingJiaoPresenter;
import com.sweethearts.ui.view.PingJiaoView;
import com.sweethearts.url.Url;

public class PingJiaoActivity extends BaseActivity implements PingJiaoView {
    private LinearLayout container;
    private Toolbar toolbar;
    private PingJiaoPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingjiao);
        init();
        MyUtils.setToolbarBackToHome(this, toolbar);
    }

    @Override
    public void findViews() {
        toolbar = findViewById(R.id.toolbar);
        container = findViewById(R.id.slow_container);
    }

    @Override
    public void setEvents() {
        url = Url.Yangtzeu_Teacher1;
        presenter = new PingJiaoPresenter(PingJiaoActivity.this, PingJiaoActivity.this);
    }

    @Override
    protected void onResume() {
        presenter.loadData();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("切换").setIcon(R.drawable.ic_change)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        YangtzeuUtils.showChooseModel(new OnResultListener<Integer>() {
                            @Override
                            public void onResult(Integer projectId) {
                                switch (projectId) {
                                    case 1:
                                        url = Url.Yangtzeu_Teacher1;
                                        presenter.loadData();
                                        break;
                                    case 2:
                                        url = Url.Yangtzeu_Teacher2;
                                        presenter.loadData();
                                        break;
                                }
                            }
                        });
                        return false;
                    }
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add("原文查看").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MyUtils.openUrl(ActivityUtils.getTopActivity(), url, true);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public LinearLayout getContainer() {
        return container;
    }

    @Override
    public String getIndexUrl() {
        return index_url;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
