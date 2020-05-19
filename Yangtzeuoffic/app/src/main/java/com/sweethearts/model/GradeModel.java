package com.sweethearts.model;

import android.app.Activity;

import com.sweethearts.R;
import com.sweethearts.ui.fragment.GradePartFragment1;
import com.sweethearts.ui.fragment.GradePartFragment2;
import com.sweethearts.ui.fragment.GradePartFragment3;
import com.sweethearts.ui.view.GradeView;

public class GradeModel {
    public void setViewPager(Activity activity, GradeView view) {
        view.getAdapter().clear();
        // 添加3个fragment
        GradePartFragment1 fragment1 = new GradePartFragment1();
        GradePartFragment2 fragment2 = new GradePartFragment2();
        GradePartFragment3 fragment3 = new GradePartFragment3();

        // 与其适配
        view.getAdapter().addFragment(activity.getString(R.string.term_grade), fragment1);
        view.getAdapter().addFragment(activity.getString(R.string.all_grade), fragment2);
        view.getAdapter().addFragment(activity.getString(R.string.grade_point), fragment3);

        //设置 适配器
        view.getViewPager().setAdapter(view.getAdapter());
        // 设置页面限制
        view.getViewPager().setOffscreenPageLimit(2);
        // 将 TabLayout 与 ViewPager 绑定
        view.getTabLayout().setupWithViewPager(view.getViewPager());
    }
}
