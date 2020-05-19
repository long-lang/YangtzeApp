package com.sweethearts.ui.view;

import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;

import com.lib.x5web.WebViewProgressBar;
import com.lib.x5web.X5WebView;

public interface HomePartView3 {
    X5WebView getWebView();

    WebViewProgressBar getProgressBar();

    Toolbar getToolbar();
    FrameLayout getContainer();
}
