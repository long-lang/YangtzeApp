package com.sweethearts.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.sweethearts.MyWebView;
import com.sweethearts.R;
import com.sweethearts.Utils.MyUtils;
import com.sweethearts.url.Url;


public class SchoolPlanActivity extends AppCompatActivity {
    private FrameLayout contianer;
    private  String from_url  ;
    private  MyWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_plan);
        init();

    }

    private void init(){
        contianer  = findViewById(R.id.web_container);
        webView = new MyWebView(this);
        from_url = getIntent().getStringExtra("from_url");
        contianer.addView(webView, 0, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        MyUtils.setCookies(from_url);
        webView.loadUrl(from_url);
    }
}
