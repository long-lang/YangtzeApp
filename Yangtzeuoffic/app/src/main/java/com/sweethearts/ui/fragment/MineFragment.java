package com.sweethearts.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.sweethearts.R;
import com.sweethearts.Utils.MyUtils;
import com.sweethearts.Utils.UserUtils;
import com.sweethearts.ui.activity.CetActivity;
import com.sweethearts.ui.activity.ChangePassActivity;
import com.sweethearts.ui.activity.ChooseClassActivity;
import com.sweethearts.ui.activity.PingJiaoActivity;
import com.sweethearts.ui.activity.SchoolPlanActivity;
import com.sweethearts.ui.activity.TestActivity;
import com.sweethearts.url.Url;

import java.util.Objects;

public class MineFragment extends BaseFragment {
    public View rootView;
    private boolean isPrepared = false;
    private LinearLayout cetLayout;
    private LinearLayout logoutLayout;
    private LinearLayout changeLayout;
    private LinearLayout chooseClass;
    private LinearLayout testLayout;
    private LinearLayout pingJiaoLayout;
    private LinearLayout planLayout;
    private TextView exit_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        findViews();
        setEvents();
        return rootView;
    }

    private void findViews() {
        cetLayout = rootView.findViewById(R.id.cetLayout);
        logoutLayout = rootView.findViewById(R.id.logoutLayout);
        changeLayout = rootView.findViewById(R.id.changeLayout);
        exit_text = rootView.findViewById(R.id.exit_text);
        chooseClass = rootView.findViewById(R.id.chooseClass);
        testLayout = rootView.findViewById(R.id.testLayout);
        pingJiaoLayout = rootView.findViewById(R.id.pingJiaoLayout);

    }

    private void setEvents(){
        cetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.startActivity(CetActivity.class);
            }
        });

        exit_text.setText("退出登录");
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.trip)
                        .setMessage(R.string.is_logout)
                        .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserUtils.do_Logout(getActivity());
                            }
                        })
                        .setNegativeButton(R.string.clear, null)
                        .create();
                dialog.show();
            }
        });

        changeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.startActivity(ChangePassActivity.class);
            }
        });

        chooseClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.startActivity(ChooseClassActivity.class);
            }
        });

        testLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.startActivity(TestActivity.class);
            }
        });

        pingJiaoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.startActivity(PingJiaoActivity.class);
            }
        });
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (!isLoadFinish) {
         //   setEvents();
            isLoadFinish = true;
        }
    }
}
