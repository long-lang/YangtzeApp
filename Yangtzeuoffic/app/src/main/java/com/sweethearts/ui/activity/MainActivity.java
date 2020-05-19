package com.sweethearts.ui.activity;



import android.annotation.SuppressLint;
import android.os.Bundle;

import android.widget.FrameLayout;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sweethearts.R;

import com.sweethearts.presenter.MainPresenter;
import com.sweethearts.ui.fragment.GradeFragment;
import com.sweethearts.ui.fragment.HomeFragment;
import com.sweethearts.ui.fragment.MineFragment;
import com.sweethearts.ui.fragment.TableFragment;
import com.sweethearts.ui.view.MainView;

//主页面 加载类
public class MainActivity extends BaseActivity implements MainView {

    // 主页片段
    private HomeFragment homeFragment;
    // 课表片段
    private TableFragment tableFragment;
    // 我的片段
    private MineFragment mineFragment;
    // 成绩片段
    private GradeFragment gradeFragment;
    // 底部导航栏
    private BottomNavigationView bottomNavigationView;
    // 帧布局
    private FrameLayout container;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void findViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        homeFragment = new HomeFragment();
        homeFragment.setUserVisibleHint(true);
        gradeFragment = new GradeFragment();
        tableFragment = new TableFragment();
        mineFragment = new MineFragment();

        container = findViewById(R.id.slow_container);
    }

    @Override
    public void setEvents() {
        // 对底部导航栏 进行初始化 加载（R.menu.main_bottom_menu_hide）
        bottomNavigationView.inflateMenu(R.menu.main_bottom_menu_hide);
        MainPresenter presenter = new MainPresenter(this, this);
        presenter.setBottomViewWithFragment();
    }
    @Override
    public HomeFragment getHomeFragment() {
        return homeFragment;
    }

    @Override
    public TableFragment getTableFragment() {
        return tableFragment;
    }

    @Override
    public MineFragment getMineFragment() {
        return mineFragment;
    }

    @Override
    public GradeFragment getGradeFragment() {
        return gradeFragment;
    }

    @Override
    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }
    public FrameLayout getFragmentContainer() {
        return container;
    }

}

