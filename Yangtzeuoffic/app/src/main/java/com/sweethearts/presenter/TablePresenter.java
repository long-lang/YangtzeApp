package com.sweethearts.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.sweethearts.R;
import com.sweethearts.http.OkHttp;
import com.sweethearts.http.OnResultStringListener;
import com.sweethearts.model.TableModel;
import com.sweethearts.ui.view.TableView;

public class TablePresenter {
    private TableModel model;
    private TableView view;
    private Activity activity;

    public TablePresenter(Activity activity, TableView view) {
        this.view = view;
        this.activity = activity;
        model = new TableModel();
    }

    public void setWithTabLayout() {
        model.setWithTabLayout(activity, view);
    }

    public void loadTableData() {
        OkHttp.do_Get(view.getIndexUrl(), new OnResultStringListener() {
            @Override
            public void onResponse(String response) {
                model.loadTableDataStep1(activity, view);
            }
            @Override
            public void onFailure(String error) {
                ToastUtils.showShort(activity.getString(R.string.get_table_error));
                view.getRefreshLayout().finishRefresh();
            }
        });


    }

    public void setTableBackground() {
        model.setTableBackground(activity, view);
    }
}
