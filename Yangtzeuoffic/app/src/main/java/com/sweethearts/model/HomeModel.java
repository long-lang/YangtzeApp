package com.sweethearts.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.google.android.material.tabs.TabLayout;
import com.lib.subutil.GsonUtils;
import com.sweethearts.model.imodel.IHomeModel;
import com.sweethearts.R;
import com.sweethearts.entity.HolidayNextBean;
import com.sweethearts.entity.WeatherBean;
import com.sweethearts.http.OkHttp;
import com.sweethearts.http.OnResultStringListener;
import com.sweethearts.ui.view.HomeView;
import com.sweethearts.url.Url;
import com.sweethearts.Utils.MyUtils;
import com.sweethearts.Utils.YangtzeuUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeModel implements IHomeModel {

    @Override
    public void setToolbarWithDrawer(Activity activity, HomeView view) {
        view.getToolbar().inflateMenu(R.menu.main);
        ((AppCompatActivity) activity).setSupportActionBar(view.getToolbar());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, view.getDrawerLayout(), view.getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        view.getDrawerLayout().addDrawerListener(toggle);
        toggle.syncState();


    }

    @Override
    public void fitViewPagerAndTabLayout(Activity activity, final HomeView view) {
        view.getAppBarLayout().setExpanded(true);
        view.getTabLayout().setupWithViewPager(view.getViewPager());
        view.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i != 0) {
                    view.getTabLayout().setVisibility(View.GONE);
                    view.getAppBarLayout().setExpanded(false);
                } else {
                    view.getTabLayout().setVisibility(View.VISIBLE);
                    view.getAppBarLayout().setExpanded(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        view.getTabLayout().addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() != 0) {
                    view.getTabLayout().setVisibility(View.GONE);
                    view.getAppBarLayout().setExpanded(false);
                } else {
                    view.getTabLayout().setVisibility(View.VISIBLE);
                    view.getAppBarLayout().setExpanded(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() != 0) {
                    view.getTabLayout().setVisibility(View.GONE);
                    view.getAppBarLayout().setExpanded(false);
                } else {
                    view.getTabLayout().setVisibility(View.VISIBLE);
                    view.getAppBarLayout().setExpanded(true);
                }
            }
        });

    }

    @Override
    public void getHoliday(final Activity activity, final HomeView view) {
        OkHttp.do_Get(Url.Yangtzeu_Next_Holiday, new OnResultStringListener() {
            private final TextView holidayView = view.getHoliday();

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                HolidayNextBean bean = GsonUtils.fromJson(response, HolidayNextBean.class);
                if (bean != null) {
                    HolidayNextBean.HolidayBean near = null;
                    List<HolidayNextBean.HolidayBean> holiday = bean.getHoliday();
                    for (HolidayNextBean.HolidayBean holidayBean : holiday) {
                        long festival = holidayBean.getFestival();
                        if (festival > TimeUtils.getNowMills()) {
                            near = holidayBean;
                            break;
                        }
                    }

                    double countdown = MyUtils.getScale(bean.getCountdown(), 1);
                    if (near != null) {
                        String span = ConvertUtils.millis2FitTimeSpan(near.getFestival() - TimeUtils.getNowMills(), 1);
                        holidayView.setText("离" + near.getName()+"（" +near.getDays()+ "天假期）还有：" + span + "，考研倒计时：" + countdown+"天");
                    } else {
                        holidayView.setText("离2019年考研还有：" + countdown+"天");
                    }
                } else {
                    onFailure(response);
                }
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(String error) {
                long millis = TimeUtils.string2Millis("2019-12-22 10:00:00");
                String span = ConvertUtils.millis2FitTimeSpan(millis - TimeUtils.getNowMills(), 2);
                holidayView.setText("离2019年考研还有：" + span);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getWeather(Activity activity, final HomeView view) {
        long startMills = TimeUtils.string2Millis("2019-02-25 00:00:00");
        long nowMills = TimeUtils.getNowMills();

        //已经开学
        if (nowMills > startMills) {
            double pass_day = MyUtils.getScale((nowMills - startMills) / (24 * 60 * 60 * 1000.0), 2);

            //在学期内则显示本学期已经过去了天数，否则显示假期过去天数
            if (pass_day <= 140) {
                view.getPass().setText("本学期已经过去了：" + pass_day + "天/140天");
            } else {
                view.getPass().setText("暑假已经过去了：" + (pass_day - 140) + "天/35天");
            }

        }
        //假期，未开学
        else {
            String s_pass_day = ConvertUtils.millis2FitTimeSpan(startMills - nowMills, 3);
            view.getPass().setText("离寒假开学还有：" + s_pass_day);
        }


        //获取周几
        String weekday = TimeUtils.getChineseWeek(TimeUtils.getNowMills());
        //获取当前是本学期第几周
        int study_week = YangtzeuUtils.getStudyWeek();
        if (study_week != 0) {
            view.getWeek().setText("第" + study_week + "周 " + weekday);
        } else {
            view.getWeek().setText("假期时间 " + weekday);
        }

        //获取城市
        String city = SPUtils.getInstance("user_info").getString("city", "荆州");
        String url = Url.Yangtzeu_Weather + city;
        OkHttp.do_Get(url, new OnResultStringListener() {
            @Override
            public void onResponse(String response) {
                WeatherBean bean = GsonUtils.fromJson(response, WeatherBean.class);
                List<WeatherBean.ResultBean> resultBeans = bean.getResult();
                WeatherBean.ResultBean resultBean = resultBeans.get(0);
                if (ObjectUtils.isNotEmpty(resultBean)) {
                    String city = resultBean.getCity();
                    String weather = resultBean.getWeather();
                    String temp = resultBean.getTemperature();

                    view.getWeather().setText(weather);
                    view.getTemp().setText(temp);
                    view.getCity().setText(city);
                }
            }

            @Override
            public void onFailure(String error) {
                LogUtils.e(error);
            }
        });
    }


    /**
     * @param activity Activity
     * @param view     HomeView
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void setStudyTime(Activity activity, final HomeView view) {
        final Handler handler = new Handler(activity.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                view.getStudyTime().setText(msg.obj.toString());
            }
        };

        new Timer("计时器").scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = YangtzeuUtils.getStudyTimeSpan();
                handler.sendMessage(msg);
            }

        }, 0, 1000L);

    }


}
