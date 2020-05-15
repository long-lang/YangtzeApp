package com.sweethearts.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.sweethearts.R;

public class YangtzeuUtils {

    public static void showChooseTerm(Activity activity, final DialogInterface.OnClickListener listener) {
        final String[] term_trip = activity.getResources().getStringArray(R.array.term_trip);
        final String[] term_id = activity.getResources().getStringArray(R.array.term_id);
        @SuppressLint("InflateParams")
        View view = activity.getLayoutInflater().inflate(R.layout.view_choose_term, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        LinearLayout layout = view.findViewById(R.id.slow_container);
        for (int i = 0; i < term_trip.length; i++) {
            @SuppressLint("InflateParams")
            View item = activity.getLayoutInflater().inflate(R.layout.view_choose_term_item, null);
            TextView title = item.findViewById(R.id.title);
            TextView bt = item.findViewById(R.id.bt);
            title.setText(term_trip[i]);
            final int finalI = i;
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    ToastUtils.showLong("你选择了：" + term_trip[finalI]);
                    int which = Integer.parseInt(term_id[finalI]);
                    listener.onClick(null, which);
                }
            });
            layout.addView(item);
        }
    }

    //选择学期
    public static void showChoosePlan(Activity activity, final DialogInterface.OnClickListener listener) {
        final String[] term_trip = activity.getResources().getStringArray(R.array.plan_trip);
        final String[] term_id = activity.getResources().getStringArray(R.array.plan_id);
        @SuppressLint("InflateParams")
        View view = activity.getLayoutInflater().inflate(R.layout.view_choose_term, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        LinearLayout layout = view.findViewById(R.id.slow_container);
        for (int i = 0; i < term_trip.length; i++) {
            @SuppressLint("InflateParams")
            View item = activity.getLayoutInflater().inflate(R.layout.view_choose_term_item, null);
            TextView title = item.findViewById(R.id.title);
            TextView bt = item.findViewById(R.id.bt);
            title.setText(term_trip[i]);
            final int finalI = i;
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    ToastUtils.showLong("你选择了：" + term_trip[finalI]);
                    int which = Integer.parseInt(term_id[finalI]);
                    listener.onClick(null, which);
                }
            });
            layout.addView(item);
        }
    }
}
