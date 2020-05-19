package com.sweethearts.presenter;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.sweethearts.R;
import com.sweethearts.http.OkHttp;
import com.sweethearts.http.OnResultStringListener;
import com.sweethearts.model.GradePart3Model;
import com.sweethearts.ui.view.GradePartView3;

public class GradePart3Presenter {
    private GradePart3Model model;
    private GradePartView3 view;
    private Activity activity;

    public GradePart3Presenter(Activity activity, GradePartView3 view) {
        this.view = view;
        this.activity = activity;
        model = new GradePart3Model();
    }

    public void loadPointData() {
        OkHttp.do_Get(view.getIndexUrl(), new OnResultStringListener() {
            @Override
            public void onResponse(String response) {
                model.loadPointData(activity, view);
            }

            @Override
            public void onFailure(String error) {
                ToastUtils.showShort(R.string.try_again);
                view.getRefresh().finishRefresh();
            }
        });
    }
}
