package com.sweethearts.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sweethearts.R;
import com.sweethearts.entity.PointBean;
import com.sweethearts.presenter.GradePart3Presenter;
import com.sweethearts.ui.activity.base.BaseFragment;
import com.sweethearts.ui.adapter.PointAdapter;
import com.sweethearts.ui.view.GradePartView3;
import com.sweethearts.url.Url;

import java.util.ArrayList;
import java.util.List;

public class GradePartFragment3 extends BaseFragment implements GradePartView3 {
    private boolean isPrepared = false;
    private boolean isLoadFinish = false;

    private View rootView;
    //绩点容器
    private List<PointBean> gradeBeans = new ArrayList<>();
    private GradePart3Presenter presenter;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView mRecyclerView;
    private PointAdapter pointAdapter;
    private TextView all_number;
    private TextView all_score;
    private TextView all_point;
    private Button change;
    private String url;
    private String index_url;
    private LinearLayout header;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grade_part3, container, false);
        findViews();
        isPrepared = true;
        lazyLoad();
        return rootView;
    }


    public void findViews() {
        smartRefreshLayout = rootView.findViewById(R.id.smartRefreshLayout);
        mRecyclerView = rootView.findViewById(R.id.mRecyclerView);
        all_number = rootView.findViewById(R.id.all_number);
        all_score = rootView.findViewById(R.id.all_score);
        all_point = rootView.findViewById(R.id.all_point);
        header = rootView.findViewById(R.id.header);

    }


    public void setEvents() {
        index_url = Url.Yangtzeu_AllGrade_Url_Index1;
        url = Url.Yangtzeu_AllGrade_Url1;

        pointAdapter = new PointAdapter(getActivity());
        presenter = new GradePart3Presenter(getActivity(), this);


        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(pointAdapter);

        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pointAdapter.clear();
                header.setVisibility(View.GONE);
                presenter.loadPointData();
            }
        });
        smartRefreshLayout.autoRefresh();



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
    public SmartRefreshLayout getRefresh() {
        return smartRefreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public LinearLayout getHeaderContainer() {
        return header;
    }

    @Override
    public PointAdapter getPointAdapter() {
        return pointAdapter;
    }

    @Override
    public TextView getAllNumberView() {
        return all_number;
    }

    @Override
    public TextView getAllScoreView() {
        return all_score;
    }

    @Override
    public TextView getAllPointView() {
        return all_point;
    }

    @Override
    public List<PointBean> getGradeBeans() {
        return gradeBeans;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getIndexUrl() {
        return index_url;
    }
}
