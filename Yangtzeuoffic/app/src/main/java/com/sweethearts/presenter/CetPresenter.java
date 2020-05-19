package com.sweethearts.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.sweethearts.R;
import com.sweethearts.http.OkHttp;
import com.sweethearts.http.OnResultStringListener;
import com.sweethearts.model.CetModel;
import com.sweethearts.ui.view.CetView;


public class CetPresenter {
    private Activity activity;
    private CetView view;
    private CetModel mode;

    public CetPresenter(Activity activity, CetView view) {
        this.activity = activity;
        this.view = view;
        mode = new CetModel();
    }


    public void getCetHistoryGrade() {
        OkHttp.do_Get(view.getIndexUrl(), new OnResultStringListener() {
            @Override
            public void onResponse(String response) {
                mode.getCetHistoryGrade(activity, view);
            }

            @Override
            public void onFailure(String error) {
                ToastUtils.showShort(R.string.try_again);
            }
        });
    }


}
