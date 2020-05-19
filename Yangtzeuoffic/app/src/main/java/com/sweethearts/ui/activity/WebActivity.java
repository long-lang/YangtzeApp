package com.sweethearts.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lib.touch.DragView;

import com.lib.x5web.WebViewProgressBar;
import com.lib.x5web.X5LoadFinishListener;
import com.lib.x5web.X5WebView;
import com.sweethearts.MyWebView;
import com.sweethearts.R;
import com.sweethearts.Utils.MyUtils;
import com.sweethearts.entity.CollectionBean;
import com.sweethearts.ui.activity.base.BaseActivity;
import com.sweethearts.url.Url;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.WebView;


import java.util.Objects;


public class WebActivity extends BaseActivity {
    private X5WebView mWebView;
    private Toolbar toolbar;
    private WebViewProgressBar progress;

    private String from_url;
    private String cookie;

    private boolean isLoadingFinish = false;
    //是否查询空教室
    private boolean isKJS = false;
    //是否是评教页面
    private boolean isPjFinish = false;
    private FrameLayout web_container;
    private boolean isNoTitle;
    private DragView ic_close;
    private String base_html;
    private String base_url;
    private String base_title;
    private boolean isAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow().setFlags(
                        android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception ignored) {
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        base_title = getIntent().getStringExtra("base_title");
        base_url = getIntent().getStringExtra("base_url");
        base_html = getIntent().getStringExtra("base_html");
        from_url = getIntent().getStringExtra("from_url");
        cookie = getIntent().getStringExtra("cookie");
        isKJS = getIntent().getBooleanExtra("isKJS", false);
        isAnswer = getIntent().getBooleanExtra("isAnswer", false);
        isNoTitle = getIntent().getBooleanExtra("isNoTitle", false);

        if (cookie == null) {
            //登录成功保存Cookie
            cookie = SPUtils.getInstance("user_info").getString("cookie");
        }

        LogUtils.i("网页加载的Cookie:" + cookie + "\n");

        super.onCreate(savedInstanceState);
        if (isNoTitle) {
            setTheme(R.style.AppTheme_NoStateBar);
            ScreenUtils.setFullScreen(this);
        }

        if (StringUtils.equals(from_url, Url.Yangtzeu_Fee)) {
            setTheme(R.style.AppTheme_ShuiYaQing);
        }
        setContentView(R.layout.activity_web);
        init();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LogUtils.e("test", "----------横屏------------");
            toolbar.setVisibility(View.GONE);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            LogUtils.e("test", "----------竖屏------------");
            toolbar.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        mWebView.getSettings().setJavaScriptEnabled(false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.getSettings().setJavaScriptEnabled(true);
    }


    @Override
    public void findViews() {
        mWebView = new X5WebView(this);
        toolbar = findViewById(R.id.web_toolbar);
        progress = findViewById(R.id.web_progress);
        web_container = findViewById(R.id.web_container);
        ic_close = findViewById(R.id.ic_close);

    }

    @SuppressLint({"SetJavaScriptEnabled", "RestrictedApi"})
    @Override
    public void setEvents() {
        if (isNoTitle) {
            toolbar.setVisibility(View.GONE);
            ic_close.setVisibility(View.VISIBLE);
        }

        ic_close.setPadding(ConvertUtils.dp2px(15));
        ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        web_container.addView(mWebView, 0, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        progress.setProgressColor(Color.GREEN);
        mWebView.setTitleAndProgressBar(toolbar, progress);

        String[] cookie_list = cookie.split(";");
        CookieManager cookieManager = CookieManager.getInstance();



        //设置cookie
        MyUtils.setCookies(from_url);

        mWebView.setX5LoadFinishListener(new X5LoadFinishListener() {
            @Override
            public void onLoadFinish(WebView webView, WebViewProgressBar progressBar, String s) {
                isLoadingFinish = true;

                CookieManager cookieManager = CookieManager.getInstance();
                String CookieStr = cookieManager.getCookie(s);
                LogUtils.e("X5网页Cookie：", "Cookies ========== " + CookieStr);

                if (isKJS) {
                    mWebView.showFreeRoom();
                    isKJS = false;
                }

                //评教完成3s后，重新跳转评教页面
                if (webView.getUrl().contains("stdEvaluate!innerIndex.action") && !isPjFinish) {
                    final AlertDialog alert = new AlertDialog.Builder(WebActivity.this)
                            .setMessage("评教完成，2s后跳转")
                            .create();
                    alert.show();
                    webView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            alert.dismiss();
                            Intent intent = new Intent(getApplicationContext(), PingJiaoActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                            MyUtils.startActivity(intent);
                        }
                    }, 2000);
                    isPjFinish = true;
                }
            }
        });

        if (base_html == null) {

            mWebView.loadUrl(from_url);
        } else {
            if (base_title != null)
                toolbar.setTitle(base_title);
            if (base_html != null)
                mWebView.loadDataWithBaseURL(base_url, base_html, "text/html", "utf-8", null);
            LogUtils.e("加载基础代码", base_html);
        }


    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            Exit();
        }
    }


    private boolean isExit = false;

    @SuppressLint("HandlerLeak")
    private void Exit() {
        if (!isExit) {
            isExit = true;
//            ToastUtils.showShort(R.string.double_close_web);
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    isExit = false;
                }
            }.sendEmptyMessageDelayed(0, 2000); // 利用handler延迟发送更改状态信息
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }


}
