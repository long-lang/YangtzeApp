package com.sweethearts.ui.view;


import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sweethearts.entity.NewsBean;
import com.sweethearts.ui.adapter.NewsAdapter;

import java.util.List;

public interface NewsView2 {
    RecyclerView getRecyclerView();

    List<NewsBean> getData();


    NewsAdapter getNewsAdapter();

    String getKind();
    SmartRefreshLayout getSmartRefreshLayout();

    String getUrl();

    boolean isRefresh();

    String getUrlHeader();

    int getStartIndex();
    int getAllPage();

    void setStartIndex(int start);
    void setAllPage(int page);
}
