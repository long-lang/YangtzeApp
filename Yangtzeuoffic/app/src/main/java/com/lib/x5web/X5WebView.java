package com.lib.x5web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.Toolbar;


import com.sweethearts.R;
import com.sweethearts.Utils.MyUtils;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;




import java.util.Map;


/**
 * Created by Administrator on 2018/4/9.
 *
 * @author 王怀玉
 * @explain X5WebView
 */

public class X5WebView extends WebView {
    private Context context;
    private Toolbar toolbar;
    private WebViewProgressBar progressBar;
    private X5LoadFinishListener x5LoadFinishListener;

    public static ValueCallback<Uri> uploadFile;
    public static ValueCallback<Uri[]> uploadFiles;
    private AlertDialog dialog;


    public X5WebView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.context = context;
        init();
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
        this.context = context;
        init();
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
        this.context = context;
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void init() {
        this.getView().setClickable(true);
        SetUp();
        initWebViewSettings();
    }


    public void setTitleAndProgressBar(Toolbar toolbar, WebViewProgressBar progressBar) {
        this.toolbar = toolbar;
        this.progressBar = progressBar;
    }

    public void setX5LoadFinishListener(X5LoadFinishListener x5LoadFinishListener) {
        this.x5LoadFinishListener = x5LoadFinishListener;
    }

    private void SetUp() {
        this.setWebViewClient(mWebViewClient);
        this.setWebChromeClient(mWebChromeClient);


    }

    //客户端设置
    WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String Url) {
            //return false 历史记录不会保存重定向的网页

            view.loadUrl(Url);

            //微信H5支付核心代码

            return super.shouldOverrideUrlLoading(view, Url);
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (progressBar != null)
                progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView webView, String url) {
            if (x5LoadFinishListener != null)
                x5LoadFinishListener.onLoadFinish(webView, progressBar, url);
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
        }
    };

    //辅助类
    WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView webView, String title) {
            if (title.isEmpty()) {
                title = context.getString(R.string.web_details);
            }
            setToolBarTitle(title);
        }

        @Override
        public void onReceivedIcon(WebView webView, Bitmap bitmap) {
            super.onReceivedIcon(webView, bitmap);
        }

        @Override
        public void onProgressChanged(WebView webView, int progress) {
            setProgress(progress);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
            uploadFiles = valueCallback;
            chooseFiles();
            return true;
        }
    };

    public final static int CHOOSE_FILE = 120;

    private void chooseFiles() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        ((Activity) context).startActivityForResult(Intent.createChooser(i, "File Chooser"), CHOOSE_FILE);
    }

    private void setProgress(int progress) {
        if (progressBar != null) {
            progressBar.setProgress(progress);
        }
    }

    private void setToolBarTitle(String title) {
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }


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



        //设置混合协议
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(2);
        }

    }

    public void showFreeRoom() {
        X5WebView.this.loadUrl("javascript:__doPostBack('btFreeRoom','')");
    }


}
