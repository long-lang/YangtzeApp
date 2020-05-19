package com.sweethearts.ui.view;

import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;

import com.lib.loading.LVBlock;
import com.lib.x5web.X5WebView;

public interface NewsDetailsView {
    String getFromUrl();
    LVBlock getLoadingView();

    RelativeLayout getContainer();
    X5WebView getWebView();

    Toolbar getToolbar();
}
