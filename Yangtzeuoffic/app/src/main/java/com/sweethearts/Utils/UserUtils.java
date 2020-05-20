package com.sweethearts.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.sweethearts.http.OkHttp;
import com.sweethearts.http.OnResultStringListener;
import com.sweethearts.ui.activity.LoginActivity;
import com.sweethearts.url.Url;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class UserUtils {

    public static void do_Logout(final Activity activity) {
        // 清除所有cookie
        final ProgressDialog progressDialog = MyUtils.getProgressDialog(activity, "注销中...");
        progressDialog.show();
        //设置密码空
        SPUtils.getInstance("user_info").remove("password");
        //设置cookie空
        SPUtils.getInstance("user_info").remove("cookie");
        //设置名字空
        SPUtils.getInstance("user_info").remove("name");
        //设置学号空
        SPUtils.getInstance("user_info").remove("number");
        //设置班级空
        SPUtils.getInstance("user_info").remove("class");
        //设置Cookie失效
        SPUtils.getInstance("user_info").put("online", false);

        // 发送退出登陆请求
        OkHttp.do_Get(Url.Yangtzeu_Out, new OnResultStringListener() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                ActivityUtils.finishAllActivities();
                MyUtils.startActivity(LoginActivity.class);
                OkHttp.cookieJar().clear();
                OkHttp.cookieJar().clearSession();
            }

            @Override
            public void onFailure(String error) {
                progressDialog.dismiss();
                ActivityUtils.finishAllActivities();
                MyUtils.startActivity(LoginActivity.class);
            }
        });
    }

    public interface OnLogResultListener {
        void onSuccess(String response);
        void onFailure(String error);
    }


}
