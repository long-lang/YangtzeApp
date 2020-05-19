package com.sweethearts.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sweethearts.R;
import com.sweethearts.entity.NewsBean;
import com.sweethearts.presenter.NewsPresenter2;
import com.sweethearts.ui.activity.base.BaseFragment;
import com.sweethearts.ui.adapter.NewsAdapter;
import com.sweethearts.ui.view.NewsView2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2018/2/4.
 *
 */

public class NewsFragmentAll extends BaseFragment implements NewsView2 {
    // 标志位，标志已经初始化完成。
    private boolean isPrepared=false;
    private boolean isLoadFinish = false;
    private SmartRefreshLayout refresh;
    private RecyclerView recyclerView;
    private String title;
    private NewsAdapter newsAdapter;
    private View rootView;
    private NewsPresenter2 presenter2;
    private String guding_url;
    private String from_url;
    private String urlHeader;
    private List<NewsBean> beans = new ArrayList<>();
    private boolean isRefresh = true;
    private int startIndex = 0;
    private int allPage = 0;


    public NewsFragmentAll() {

    }

    public static NewsFragmentAll newInstance(String url, String title) {
        NewsFragmentAll fragment = new NewsFragmentAll();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            guding_url = bundle.getString("url");
            from_url = bundle.getString("url");
            assert from_url != null;
            urlHeader = from_url.substring(0, from_url.lastIndexOf(".")) + "/";
            title = bundle.getString("title");
        }

        rootView =  inflater.inflate(R.layout.fragment_news_layout_all, container, false);
        findViews();
        isPrepared = true;
        lazyLoad();
        return rootView;
    }

    @Override
    public void findViews() {
        refresh = rootView.findViewById(R.id.refresh);
        recyclerView = rootView.findViewById(R.id.recyclerView);
    }

    @Override
    public void setEvents() {
        newsAdapter = new NewsAdapter(getActivity());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(newsAdapter);
        presenter2 = new NewsPresenter2(getActivity(), this);

        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
                allPage = 0;
                isRefresh = true;
                newsAdapter.clearData();
                from_url = guding_url;
                presenter2.loadNewsData();
            }
        });

        refresh.setRefreshFooter(new ClassicsFooter(Objects.requireNonNull(getActivity())));
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
                allPage = allPage - 1;
                LogUtils.e(allPage);
                if (allPage <= 0) {
                    ToastUtils.showShort(R.string.no_more);
                    refreshLayout.finishLoadMore();
                    return;
                }
                from_url = urlHeader + allPage + ".htm";

                isRefresh = false;
                presenter2.loadNewsData();
            }
        });
        refresh.autoRefresh();
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        if (!isLoadFinish) {
            setEvents();
            isLoadFinish = true;
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public List<NewsBean> getData() {
        return beans;
    }





    @Override
    public NewsAdapter getNewsAdapter() {
        return newsAdapter;
    }

    @Override
    public String getKind() {
        return title;
    }

    @Override
    public SmartRefreshLayout getSmartRefreshLayout() {
        return refresh;
    }

    @Override
    public String getUrl() {
        return from_url;
    }

    @Override
    public boolean isRefresh() {
        return isRefresh;
    }

    @Override
    public String getUrlHeader() {
        return urlHeader;
    }

    @Override
    public int getStartIndex() {
        return startIndex;
    }

    @Override
    public int getAllPage() {
        return allPage;
    }

    @Override
    public void setStartIndex(int start) {
        startIndex = start;
    }

    @Override
    public void setAllPage(int page) {
        allPage = page;
    }

}