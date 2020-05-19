package com.sweethearts.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;

import androidx.annotation.NonNull;

import com.sweethearts.R;
import com.sweethearts.presenter.NewsPresenter1;
import com.sweethearts.ui.activity.base.BaseFragment;
import com.sweethearts.ui.view.NewsView1;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Administrator on 2018/2/4.
 */

public class NewsFragment1 extends BaseFragment implements NewsView1 {
    // 标志位，标志已经初始化完成。
    private boolean isPrepared = false;
    private boolean isLoadFinish = false;

    private View InflateView;
    private String[] title;
    private GridView grid_view;
    private EditText search;
    private BGABanner banner;
    private GridView jwc_gridView;

    public NewsFragment1() {

    }

    public static NewsFragment1 newInstance(String[] title) {
        NewsFragment1 fragment = new NewsFragment1();
        Bundle bundle = new Bundle();
        bundle.putStringArray("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        title = getArguments() != null ? getArguments().getStringArray("title") : new String[0];

        InflateView = inflater.inflate(R.layout.fragment_news_layout1, container, false);
        findViews();
        isPrepared = true;
        lazyLoad();
        return InflateView;
    }


    @Override
    public void findViews() {
        jwc_gridView = InflateView.findViewById(R.id.jwc_gridView);
        grid_view = InflateView.findViewById(R.id.grid_view);
        search = InflateView.findViewById(R.id.search);
        banner = InflateView.findViewById(R.id.banner);

    }

    @Override
    public void setEvents() {
        NewsPresenter1 presenter1 = new NewsPresenter1(getActivity(), this);
        presenter1.loadBanner();
        presenter1.fitJwcGridView();
        presenter1.fitGridView();

    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (!isLoadFinish) {
            setEvents();
            isLoadFinish = true;
        }
    }

    @Override
    public BGABanner getBGABanner() {
        return banner;
    }

    @Override
    public EditText getSearchView() {
        return search;
    }

    @Override
    public GridView getGridView() {
        return grid_view;
    }

    @Override
    public GridView getJwcGridView() {
        return jwc_gridView;
    }


    @Override
    public String[] getTitleArray() {
        return title;
    }

}
