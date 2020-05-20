package com.sweethearts.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sweethearts.R;
import com.sweethearts.Utils.YangtzeuUtils;
import com.sweethearts.entity.GradeBean;
import com.sweethearts.listener.OnResultListener;
import com.sweethearts.presenter.GradePart1Presenter;
import com.sweethearts.ui.activity.ChartActivity;
import com.sweethearts.ui.activity.base.BaseFragment;
import com.sweethearts.ui.adapter.GradeAdapter;
import com.sweethearts.ui.view.GradePartView1;
import com.sweethearts.url.Url;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GradePartFragment1 extends BaseFragment implements GradePartView1 {
    private boolean isPrepared = false;

    private View rootView;
    private GradePart1Presenter presenter;

    private RecyclerView recyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    private GradeAdapter gradeAdapter;

    private Button chooseTerm;
    private Button sort_low;
    private Button sort_high;
    private Button to_chart;
    private Button change;
    private List<GradeBean> gradeBeans;

    private String index_url;
    private String url;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grade_part1, container, false);
        findViews();
        setEvents();
        return rootView;
    }

    @Override
    public void findViews() {
        recyclerView = rootView.findViewById(R.id.mRecyclerView);
        chooseTerm = rootView.findViewById(R.id.chooseTerm);
        sort_low = rootView.findViewById(R.id.sort_low);
        sort_high = rootView.findViewById(R.id.sort_high);
        smartRefreshLayout = rootView.findViewById(R.id.refresh);
        to_chart = rootView.findViewById(R.id.to_chart);
        change = rootView.findViewById(R.id.change);
    }

    public void setEvents() {
        index_url = Url.Yangtzeu_Grade_Url_Index1;
        url = Url.Yangtzeu_Grade_Url1;

        gradeBeans = new ArrayList<>();
        //成绩适配
        gradeAdapter = new GradeAdapter(getActivity());

        // recyclerView类似于listView
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(gradeAdapter);

        presenter = new GradePart1Presenter(getActivity(), this);
        //智能刷新控件 是否启用列表惯性滑动到底部时自动加载更多
        smartRefreshLayout.setEnableLoadMore(false);
        //设置下拉刷新监听事件
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                presenter.loadGradeData();
            }
        });
        //自动刷新
        smartRefreshLayout.autoRefresh();

        //给选择学期设置监听事件
        chooseTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showChooseTerm初始化切换的弹窗
                YangtzeuUtils.showChooseTerm(Objects.requireNonNull(getActivity()), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        将获取到的学期id存到term_id中
                        SPUtils.getInstance("user_info").put("term_id", String.valueOf(which));
                        presenter.loadGradeData();
                    }
                });
            }
        });

        // 由高向低
        sort_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gradeAdapter.sort(false);

            }
        });

        // 表格监听事件
        to_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ObjectUtils.isNotEmpty(gradeBeans)) {
                    GradeBean list = new GradeBean();
                    list.setGradeBeans(gradeBeans);
                    Intent intent = new Intent(getActivity(), ChartActivity.class);
                    intent.putExtra("data", list);
                    startActivity(intent);
                } else {
                    ToastUtils.showShort("没有数据");
                }
            }
        });
        // 本辅科切换点击事件
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YangtzeuUtils.showChooseModel(new OnResultListener<Integer>() {
                    @Override
                    public void onResult(Integer projectId) {
                        switch (projectId) {
                            case 1:
                                index_url = Url.Yangtzeu_Grade_Url_Index1;
                                url = Url.Yangtzeu_Grade_Url1;
                                smartRefreshLayout.autoRefresh();
                                break;
                            case 2:
                                index_url = Url.Yangtzeu_Grade_Url_Index2;
                                url = Url.Yangtzeu_Grade_Url2;
                                smartRefreshLayout.autoRefresh();
                                break;
                        }
                    }
                });
            }
        });
        // 由低向高排序
        sort_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gradeAdapter.sort(true);
            }
        });
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public SmartRefreshLayout getRefresh() {
        return smartRefreshLayout;
    }

    @Override
    public GradeAdapter getAdapter() {
        return gradeAdapter;
    }


    @Override
    public List<GradeBean> getGradeBeans() {
        return gradeBeans;
    }

    @Override
    public Toolbar getToolbar() {
        return  GradeFragment.toolbar;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getIndexUrl() {
        return index_url;
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
}
