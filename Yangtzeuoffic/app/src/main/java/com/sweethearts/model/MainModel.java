package com.sweethearts.model;

import android.app.Activity;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.tabs.TabLayout;
import com.sweethearts.R;
import com.sweethearts.ui.fragment.HomeFragment;
import com.sweethearts.ui.view.MainView;



public class MainModel {

    public void setBottomViewWithFragment(final Activity activity,final MainView view) {
        //  开启 fragment事务管理
        final FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
        //  将fragment 跟 帧布局 由fragmentUtils绑定
        FragmentUtils.add(manager, view.getHomeFragment(), view.getFragmentContainer().getId(), false);
        FragmentUtils.add(manager, view.getTableFragment(), view.getFragmentContainer().getId(), true);
        FragmentUtils.add(manager, view.getMineFragment(), view.getFragmentContainer().getId(), true);
        FragmentUtils.add(manager, view.getGradeFragment(), view.getFragmentContainer().getId(), true);

        view.getBottomNavigationView().setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        view.getBottomNavigationView().setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        view.getHomeFragment().setUserVisibleHint(true);
                        // showhide 方法 将第一个参数的fragment显示，之后的fragment隐藏
                        FragmentUtils.showHide(view.getHomeFragment(),
                                 view.getGradeFragment(), view.getTableFragment(), view.getMineFragment());

//                        TabLayout tabLayout = HomeFragment.tabLayout;
//                        if (tabLayout != null) {
//                            Objects.requireNonNull(tabLayout.getTabAt(0)).select();
//                        }
                        return true;
                    case R.id.grade:
                        view.getGradeFragment().setUserVisibleHint(true);
                        FragmentUtils.showHide(view.getGradeFragment(),
                                 view.getTableFragment(), view.getHomeFragment(), view.getMineFragment());

                        return true;
                    case R.id.classTable:
                        view.getTableFragment().setUserVisibleHint(true);
                        FragmentUtils.showHide(view.getTableFragment(),
                                 view.getGradeFragment(), view.getHomeFragment(), view.getMineFragment());
                        return true;
                    case R.id.mine:
                        view.getMineFragment().setUserVisibleHint(true);
                        FragmentUtils.showHide(view.getMineFragment(),
                                view.getGradeFragment(), view.getTableFragment(), view.getHomeFragment());
                        return true;
                }
                return false;
            }
        });
    }
}

