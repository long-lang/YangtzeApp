package com.sweethearts.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.lib.touch.TouchImageView;
import com.sweethearts.R;
import com.sweethearts.entity.ImageBean;
import com.sweethearts.presenter.ImagePresenter;
import com.sweethearts.ui.activity.base.BaseActivity;
import com.sweethearts.ui.view.MyImageView;
import com.sweethearts.Utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

public class ImageActivity extends BaseActivity implements MyImageView {
    public static String Referer = "";
    private List<ImageBean.ImageItem> imageItems = new ArrayList<>();
    private Toolbar toolbar;
    private BGABanner bgaBanner;
    private ImagePresenter president;
    private FrameLayout app_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imageItems = ((ImageBean) getIntent().getSerializableExtra("image_list")).getImageItems();
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoStateBar);
        setContentView(R.layout.activity_image);
        init();
        MyUtils.setToolbarBackToHome(this, toolbar);
    }

    @Override
    public void findViews() {
        toolbar = findViewById(R.id.toolbar);
        bgaBanner = findViewById(R.id.banner);
        app_bar= findViewById(R.id.app_bar);
    }

    @Override
    public void setEvents() {
        president = new ImagePresenter(this, this);

        List<View> views = new ArrayList<>();
        for (int i = 0; i < imageItems.size(); i++) {
            @SuppressLint("InflateParams")
            View view =  getLayoutInflater().inflate(R.layout.activity_image_item, null);
            views.add(view);
        }
        bgaBanner.setData(views);
        bgaBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, Object model, int position) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
                itemView.setLayoutParams(params);
                TouchImageView imageView = itemView.findViewById(R.id.mTouchImageView);
                final Object object = imageItems.get(position).getObject();
                final String title = imageItems.get(position).getTitle();
                toolbar.setTitle(title);
                toolbar.setSubtitle(TimeUtils.getNowString());
                LogUtils.i(title,object);

                MyUtils.loadImageNoCache(ImageActivity.this, imageView, object);

                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        president.showDialog(title, object);
                        return true;
                    }
                });
            }
        });

    }

    private int i = 0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.change_bg).setIcon(R.drawable.ic_change).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                i = i + 1;
                int which = i % 3;
                switch (which) {
                    case 0:
                        app_bar.setBackgroundColor(getResources().getColor(R.color.translate));
                        bgaBanner.setBackgroundColor(Color.BLACK);
                        break;
                    case 1:
                        app_bar.setBackgroundColor(Color.parseColor("#50000000"));
                        bgaBanner.setBackgroundColor(Color.WHITE);
                        break;
                    case 2:
                        app_bar.setBackgroundColor(Color.parseColor("#50000000"));
                        bgaBanner.setBackgroundColor(Color.GRAY);
                        break;
                }
                return false;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
