package com.sweethearts.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sweethearts.R;
import com.sweethearts.Utils.MyUtils;
import com.sweethearts.presenter.GradePresenter;
import com.sweethearts.ui.adapter.FragmentAdapter;
import com.sweethearts.ui.view.GradeView;
import com.sweethearts.url.Url;

import java.util.Objects;

public class GradeFragment extends Fragment implements GradeView {
    public View rootView;
    private ViewPager pager;
    private TabLayout tab;
    private FragmentAdapter fragmentAdapter;
    @SuppressLint("StaticFieldLeak")
    public static Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_grade, container, false);
        findViews();
        setEvents();
        return rootView;
    }

    public void findViews() {
        toolbar = rootView.findViewById(R.id.toolbar);
        pager = rootView.findViewById(R.id.pager);
        tab = rootView.findViewById(R.id.tab);
    }


    public void setEvents() {

        //设置Fragment适配器
        fragmentAdapter = new FragmentAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager());

        final GradePresenter presenter = new GradePresenter(getActivity(), this);
        presenter.setViewPager();


        toolbar.inflateMenu(R.menu.grade_menu);

    }
    @Override
    public FragmentAdapter getAdapter() {
        return fragmentAdapter;
    }

    @Override
    public ViewPager getViewPager() {
        return pager;
    }

    @Override
    public TabLayout getTabLayout() {
        return tab;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }
}
