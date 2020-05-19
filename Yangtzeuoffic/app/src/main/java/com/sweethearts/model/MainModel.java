package com.sweethearts.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ServiceUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.tabs.TabLayout;
import com.sweethearts.R;
import com.sweethearts.Utils.YangtzeuUtils;
import com.sweethearts.service.BackgroundService;
import com.sweethearts.ui.fragment.HomeFragment;
import com.sweethearts.ui.view.MainView;



public class MainModel implements IMainModel {

    public void setBottomViewWithFragment(final Activity activity,final MainView view) {
        final FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
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

    @Override
    public void initEvents(Activity activity, MainView view) {
        YangtzeuUtils.getTripInfo(activity, false);
        //检查APP更新
        /*YangtzeuUtils.checkAppVersion(activity);*/

        Intent intent = new Intent(activity, BackgroundService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(intent);
        } else {
            ServiceUtils.startService(BackgroundService.class);
        }

    }

}

