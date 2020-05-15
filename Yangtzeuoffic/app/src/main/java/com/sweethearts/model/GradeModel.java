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

        GradePartFragment1 fragment1 = new GradePartFragment1();
        GradePartFragment2 fragment2 = new GradePartFragment2();
        GradePartFragment3 fragment3 = new GradePartFragment3();

        view.getAdapter().addFragment(activity.getString(R.string.term_grade), fragment1);
        view.getAdapter().addFragment(activity.getString(R.string.all_grade), fragment2);
        view.getAdapter().addFragment(activity.getString(R.string.grade_point), fragment3);

        view.getViewPager().setAdapter(view.getAdapter());
        view.getViewPager().setOffscreenPageLimit(2);

        view.getTabLayout().setupWithViewPager(view.getViewPager());
    }
}
