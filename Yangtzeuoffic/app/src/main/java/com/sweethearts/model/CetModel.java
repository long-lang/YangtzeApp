package com.sweethearts.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;

import com.sweethearts.R;
import com.sweethearts.Utils.MyUtils;
import com.sweethearts.entity.CetCardBean;
import com.sweethearts.entity.CetDateBean;
import com.sweethearts.http.OkHttp;
import com.sweethearts.http.OnResultStringListener;
import com.sweethearts.http.callback.ByteCallBack;
import com.sweethearts.ui.view.CetView;
import com.sweethearts.url.Url;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;



public class CetModel  {


    public void getCetHistoryGrade(final Activity activity, final CetView view) {
        OkHttp.do_Get(view.getUrl(), new OnResultStringListener() {
            @Override
            public void onResponse(String result) {
                Document document = Jsoup.parse(result);
                Elements tbody_List = document.select("table.gridtable>tbody");
                if (!tbody_List.isEmpty() && tbody_List.size() > 1) {
                    Elements trList = tbody_List.get(1).select("tbody tr");

                    view.getCetHistoryContainer().removeAllViews();

                    for (int i = 0; i < trList.size(); i++) {
                        String text = trList.get(i).text();
                        if (StringUtils.isEmpty(text)) {
                            ToastUtils.showShort("CET历史成绩：" + activity.getString(R.string.no_data));
                            continue;
                        }

                        LinearLayout ItemView2 = (LinearLayout) View.inflate(activity, R.layout.activity_cet_item, null);
                        LinearLayout cardView = ItemView2.findViewById(R.id.item);
                        view.getCetHistoryContainer().addView(ItemView2);

                        String item[] = text.split(" ");
                        try {
                            TextView name = ItemView2.findViewById(R.id.name);
                            name.setText(item[0]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            TextView grade = ItemView2.findViewById(R.id.grade);
                            if (Double.valueOf(item[1]) < 425) {
                                cardView.setBackgroundColor(activity.getResources().getColor(R.color.white_d));
                            } else {
                                cardView.setBackgroundColor(activity.getResources().getColor(R.color.white));
                            }
                            grade.setText(item[1]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            TextView is_pass = ItemView2.findViewById(R.id.is_pass);
                            is_pass.setText(item[2]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            TextView term = ItemView2.findViewById(R.id.term);
                            term.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                            term.setSingleLine(true);
                            term.setSelected(true);
                            term.setFocusable(true);
                            term.setFocusableInTouchMode(true);
                            term.setText(item[3]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    ToastUtils.showShort("CET历史成绩：" + activity.getString(R.string.no_data));
                }
            }

            @Override
            public void onFailure(String error) {
                ToastUtils.showShort(R.string.load_error);
            }
        });
    }







    private String getItem(String string) {
        String item;
        if (string.contains("'")) {
            item = string.substring(string.indexOf("'") + 1, string.lastIndexOf("'"));
        } else {
            item = string.substring(string.indexOf(":") + 1);
        }
        return item;
    }

}
