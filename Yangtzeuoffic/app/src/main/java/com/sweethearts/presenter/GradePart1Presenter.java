package com.sweethearts.presenter;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sweethearts.http.OkHttp;
import com.sweethearts.http.OnResultStringListener;
import com.sweethearts.model.GradePart1Model;
import com.sweethearts.ui.fragment.GradePartFragment1;
import com.sweethearts.ui.view.GradePartView1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class GradePart1Presenter {
    private GradePart1Model model;
    private GradePartView1 view;
    private Activity activity;

    public GradePart1Presenter(Activity activity, GradePartView1 view) {
        this.view = view;
        this.activity = activity;
        model = new GradePart1Model();
    }

    public void loadGradeData() {
        LogUtils.i(2);
        OkHttp.do_Get(view.getIndexUrl(), new OnResultStringListener() {
            @Override
            public void onResponse(String response) {
                LogUtils.i(3);
                model.loadGradeData(activity, view);
            }

            @Override
            public void onFailure(String error) {
                LogUtils.i(4);
                view.getRefresh().finishRefresh();
            }
        });
    }
}
