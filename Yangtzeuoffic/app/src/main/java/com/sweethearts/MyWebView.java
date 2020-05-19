package com.sweethearts;

import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.Bitmap;

import android.net.http.SslError;

import android.util.AttributeSet;

import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;


import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.blankj.utilcode.util.LogUtils;
import com.sweethearts.Utils.MyUtils;


public class MyWebView extends WebView {
    private Context context;
    public MyWebView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    public MyWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.context = context;
        init();
    }

    public MyWebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
        this.context = context;
        init();
    }



    @SuppressLint("SetJavaScriptEnabled")
    public void init() {
        initWebViewSettings();
        SetUp();
    }

    private void SetUp() {
        this.setWebViewClient(mWebViewClient);
    }

    WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String Url) {
            //return false 历史记录不会保存重定向的网页
            //微信H5支付核心代码
            view.loadUrl(Url);
            LogUtils.i("asdsadas");
            return true;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView webView, String url) {

        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
        }
    };

    public final static int CHOOSE_FILE = 120;

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);

        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);


        // 设置自适应屏幕，两者合用
        webSetting.setUseWideViewPort(true); //将图片调整到适合WebView的大小
        webSetting.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        // 缩放操作
        webSetting.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSetting.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSetting.setDisplayZoomControls(false); //隐藏原生的缩放控件


        // 其他细节操作
        webSetting.setAllowFileAccess(true); //设置可以访问文件
        webSetting.setAllowFileAccessFromFileURLs(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSetting.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSetting.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSetting.setGeolocationEnabled(true);
        webSetting.setBlockNetworkImage(false);
        webSetting.setSavePassword(true);
        webSetting.setSaveFormData(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setAppCachePath(MyUtils.rootPath() + "A_Tool/WebCache/"); //设置  Application Caches 缓存目录
        webSetting.setDatabaseEnabled(true);
        webSetting.setDatabasePath(MyUtils.rootPath() + "A_Tool/WebCache/");
        webSetting.setDomStorageEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);

        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

    }


}
