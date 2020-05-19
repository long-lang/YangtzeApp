package com.sweethearts.ui.activity;



import android.annotation.SuppressLint;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.blankj.utilcode.util.SPUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sweethearts.R;

import com.sweethearts.Utils.YangtzeuUtils;
import com.sweethearts.entity.Course;
import com.sweethearts.listener.OnClassListener;
import com.sweethearts.presenter.MainPresenter;
import com.sweethearts.ui.activity.base.BaseActivity;
import com.sweethearts.ui.fragment.GradeFragment;
import com.sweethearts.ui.fragment.HomeFragment;
import com.sweethearts.ui.fragment.MineFragment;
import com.sweethearts.ui.fragment.TableFragment;
import com.sweethearts.ui.view.MainView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity implements MainView,
        NavigationView.OnNavigationItemSelectedListener {

    private HomeFragment homeFragment;
    private TableFragment tableFragment;
    private MineFragment mineFragment;
    private GradeFragment gradeFragment;

    private DrawerLayout drawer;
    private NavigationView leftNavigationView;
    private Timer timer1 = new Timer();
    private TextView class_info;
    private LinearLayout have_class;
    private LinearLayout not_have_class;

    private BottomNavigationView bottomNavigationView;
    private FrameLayout container;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void findViews() {
        //左侧导航栏
        leftNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        container = findViewById(R.id.slow_container);
        drawer = findViewById(R.id.drawer_layout);
        View headerView = leftNavigationView.getHeaderView(0);
        class_info = headerView.findViewById(R.id.class_info);

        have_class = headerView.findViewById(R.id.have_class);
        not_have_class = headerView.findViewById(R.id.not_have_class);

    }

    @Override
    public void setEvents() {
        timer1 = new Timer();
        //左侧导航栏
        leftNavigationView.setNavigationItemSelectedListener(this);

        homeFragment = new HomeFragment();
        homeFragment.setUserVisibleHint(true);
        gradeFragment = new GradeFragment();
        tableFragment = new TableFragment();
        mineFragment = new MineFragment();
        //bottomNavigationView.inflateMenu(R.menu.main_bottom_menu_hide);
        MainPresenter presenter = new MainPresenter(this, this);
        presenter.setBottomViewWithFragment();
        presenter.initEvents();

        //更新一天课程完成信息
        final Handler classPlanHandler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                setClassPlan();
            }

        };

        timer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                classPlanHandler.sendMessage(msg);
            }
        }, 0, 5000L);


        boolean is_hide_many_page = SPUtils.getInstance("app_info").getBoolean("is_hide_many_page", false);
        if (is_hide_many_page) {
            bottomNavigationView.inflateMenu(R.menu.main_bottom_menu_hide);
        } else {
            bottomNavigationView.inflateMenu(R.menu.main_bottom_menu);
        }
    }

    private void setClassPlan() {
        YangtzeuUtils.checkHaveClassNow(this, new OnClassListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClass(List<Course> course) {
                int course_size = course.size();
                if (course_size != 0) {
                    have_class.removeAllViews();
                    not_have_class.removeAllViews();
                } else {
                    class_info.setText("今日课程数：" + course_size);
                }
                for (int i = 0; i < course.size(); i++) {
                    Course course_item = course.get(i);
                    String course_title = course_item.getName();
                    course_title = course_title.replace("\"", "");
                    String course_room = course_item.getRoom();
                    course_room = course_room.replace("\"", "");
                    int course_sec = Integer.parseInt(course_item.getSection()) + 1;

                    @SuppressLint("InflateParams")
                    View view = getLayoutInflater().inflate(R.layout.activity_main_header_item, null);
                    TextView title = view.findViewById(R.id.title);
                    TextView time = view.findViewById(R.id.time);
                    TextView room = view.findViewById(R.id.room);

                    title.setText(course_title);
                    time.setText("第" + course_sec + "大节");
                    room.setText(course_room);

                    double time_sec = YangtzeuUtils.getSectionTimeSpan();
                    if (time_sec > course_sec) {
                        have_class.addView(view);
                    } else if (time_sec < course_sec) {
                        not_have_class.addView(view);
                    } else {
                        class_info.setText("正在上课：" + course_title);
                    }
                }
            }
        });

    }

    @Override
    public HomeFragment getHomeFragment() {
        return homeFragment;
    }

    @Override
    public TableFragment getTableFragment() {
        return tableFragment;
    }

    @Override
    public MineFragment getMineFragment() {
        return mineFragment;
    }

    @Override
    public GradeFragment getGradeFragment() {
        return gradeFragment;
    }
    @Override
    public DrawerLayout getDrawerLayout() {
        return drawer;
    }

    @Override
    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }
    public FrameLayout getFragmentContainer() {
        return container;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}

