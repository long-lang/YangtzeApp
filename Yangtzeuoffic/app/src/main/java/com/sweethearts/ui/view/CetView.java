package com.sweethearts.ui.view;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.sweethearts.ui.view.base.BaseView;


public interface CetView extends BaseView {
    String getCetInfo();
    LinearLayout getCetHistoryContainer();
    void setCetInfo(String cet);


}
