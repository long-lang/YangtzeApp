package com.sweethearts.model;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sweethearts.R;
import com.sweethearts.model.imodel.IHomePart2Model;
import com.sweethearts.ui.adapter.FragmentAdapter;
import com.sweethearts.ui.fragment.HomeFragment;
import com.sweethearts.ui.fragment.NewsFragment1;
import com.sweethearts.ui.fragment.NewsFragmentAll;
import com.sweethearts.ui.view.HomePartView2;

public class HomePart2Model implements IHomePart2Model {

    private String[] title = {"长大要闻", "通知通告", "综合新闻", "缤纷校园", "校园热点",
            "专题学习", "学术信息", "文明创建", "媒体长大", "音画长大"};
    private String[] url = {"http://news.yangtzeu.edu.cn/zdyw.htm"
            , "http://news.yangtzeu.edu.cn/tzgg.htm"
            , "http://news.yangtzeu.edu.cn/zhxw.htm"
            , "http://news.yangtzeu.edu.cn/bfxy.htm"
            , "http://news.yangtzeu.edu.cn/xyrd.htm"
            , "http://news.yangtzeu.edu.cn/ztxx.htm"
            , "http://news.yangtzeu.edu.cn/xsxx.htm"
            , "http://news.yangtzeu.edu.cn/wmcj.htm"
            , "http://news.yangtzeu.edu.cn/mtzd.htm"
            , "http://news.yangtzeu.edu.cn/yhzd.htm"};

    @Override
    public void fitView(Activity activity, HomePartView2 view) {
        FragmentAdapter adapter = new FragmentAdapter(((FragmentActivity) activity).getSupportFragmentManager());

        NewsFragment1 fragment1 = NewsFragment1.newInstance(title);
        adapter.addFragment(activity.getString(R.string.news_home), fragment1);

        for (int i = 0; i < title.length; i++) {
            NewsFragmentAll fragmentAll = NewsFragmentAll.newInstance(url[i], title[i]);
            adapter.addFragment(title[i], fragmentAll);
        }

        view.getViewPager().setOffscreenPageLimit(title.length);
        view.getViewPager().setAdapter(adapter);
        view.getTabLayout().setupWithViewPager(view.getViewPager());
        view.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (HomeFragment.app_bar != null) {
                    HomeFragment.app_bar.setExpanded(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        view.getTabLayout().addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (HomeFragment.app_bar != null) {
                    HomeFragment.app_bar.setExpanded(false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (HomeFragment.app_bar != null) {
                    HomeFragment.app_bar.setExpanded(false);
                }
            }
        });
    }
}
