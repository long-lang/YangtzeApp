package com.sweethearts.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sweethearts.R;
import com.sweethearts.entity.GradeBean;
import com.sweethearts.http.OkHttp;
import com.sweethearts.http.OnResultStringListener;
import com.sweethearts.ui.view.GradePartView2;
import com.sweethearts.url.Url;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class GradePart2Model {
    private String term_id;


    public void loadGradeData(Activity activity, GradePartView2 view) {
        term_id = SPUtils.getInstance("user_info").getString("term_id", Url.Default_Term);
        final String[] term_trip = activity.getResources().getStringArray(R.array.term_trip);
        final String[] term_id = activity.getResources().getStringArray(R.array.term_id);
        for (int i = 0; i < term_id.length; i++) {
            if (this.term_id.equals(term_id[i])) {
                view.getToolbar().setTitle(term_trip[i]);
            }
        }

        view.public_choose_scores().clear();
        view.major_choose_scores().clear();
        view.major_scores().clear();
        view.practice_scores().clear();
        view.getAdapter().clear();
        view.getGradeBeans().clear();

        requestGradeData(activity, view);
    }


    public void requestGradeData(final Activity activity, final GradePartView2 view) {
        //取得姓名
        final String url = view.getUrl() + term_id;
        OkHttp.do_Get(url, new OnResultStringListener() {
            @Override
            public void onResponse(String response) {
                view.getRefresh().finishRefresh();
                if (response.contains("请不要过快点击") || response.contains("重复登录")) {
                    requestGradeData(activity, view);
                } else if (response.contains("课程名称")) {
                    if (response.contains("总评成绩")) {
                        parseGrade(activity, view, response);
                    } else {
                        final int now_term = Integer.parseInt(term_id);
                        int before = Integer.parseInt(term_id) - 1;

                        if (before == 47) before = 46;
                        if (before == 68) before = 49;
                        if (before == 88) before = 69;
                        if (before == 108) before = 89;
                        final int finalBefore = before;

                        SnackbarUtils.with(view.getRecyclerView()).setMessage("当前学期（ID：" + now_term + "）没有成绩数据")
                                .setDuration(SnackbarUtils.LENGTH_LONG)
                                .setAction("上学期成绩", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        term_id = String.valueOf(finalBefore);
                                        requestGradeData(activity, view);
                                    }
                                }).show();
                    }

                } else {
                    //设置Cookie不可用
                    SPUtils.getInstance("user_info").put("online", false);
                }
            }

            @Override
            public void onFailure(String error) {
                view.getRefresh().finishRefresh();
                ToastUtils.showShort(R.string.refresh_again);
            }
        });


    }


    public void parseGrade(final Activity activity, final GradePartView2 view, String data) {
        List<Double> public_choose_scores = view.public_choose_scores();
        List<Double> major_choose_scores = view.major_choose_scores();
        List<Double> major_scores = view.major_scores();
        List<Double> practice_scores = view.practice_scores();

        Document document = Jsoup.parse(data);
        Elements elements = document.select("div.grid table.gridtable tbody tr");
        int Column = document.select("div.grid table.gridtable thead tr th").size();

        if (Column == 10) {
            //含补考
            for (Element element : elements) {
                Elements tds = element.select("tr td");
                GradeBean gradeBean = new GradeBean();

                String[] YearTerm = tds.get(0).text().split(" ");
                String courseYear = YearTerm[0];
                String courseTerm = YearTerm[1];

                String courseCode = tds.get(1).text();
                String courseIndex = tds.get(2).text();
                String courseName = tds.get(3).text();
                String courseKind = tds.get(4).text();
                String courseScore = tds.get(5).text();
                String courseBuKao = tds.get(6).text();
                String courseZongPing = tds.get(7).text();
                String courseZuiZhong = tds.get(8).text();
                String coursePoint = tds.get(9).text();

                gradeBean.setCourseYear(courseYear);
                gradeBean.setCourseTerm(courseTerm);
                gradeBean.setCourseCode(courseCode);
                gradeBean.setCourseIndex(courseIndex);
                gradeBean.setCourseName(courseName);
                gradeBean.setCourseKind(courseKind);
                gradeBean.setCourseScore(courseScore);
                gradeBean.setCourseBuKao(courseBuKao);
                gradeBean.setCourseZongPing(courseZongPing);
                gradeBean.setCourseZuiZhong(courseZuiZhong);
                gradeBean.setCoursePoint(coursePoint);

                view.getGradeBeans().add(gradeBean);

                if (courseKind.contains("公选")) {
                    if (Double.valueOf(courseZuiZhong) >= 60) {
                        public_choose_scores.add(Double.valueOf(courseScore));
                    }
                }
                if (courseKind.contains("选修")) {
                    if (Double.valueOf(courseZuiZhong) >= 60) {
                        major_choose_scores.add(Double.valueOf(courseScore));
                    }
                }
                if (courseKind.contains("必修")) {
                    if (Double.valueOf(courseZuiZhong) >= 60) {
                        major_scores.add(Double.valueOf(courseScore));
                    }
                }
                if (courseKind.contains("实践")) {
                    if (Double.valueOf(courseZuiZhong) >= 60) {
                        practice_scores.add(Double.valueOf(courseScore));
                    }
                }

            }
        }

        if (Column == 9) {
            //不含补考
            for (Element element : elements) {
                Elements tds = element.select("tr td");
                GradeBean gradeBean = new GradeBean();

                String[] YearTerm = tds.get(0).text().split(" ");
                String courseYear = YearTerm[0];
                String courseTerm = YearTerm[1];

                String courseCode = tds.get(1).text();
                String courseIndex = tds.get(2).text();
                String courseName = tds.get(3).text();
                String courseKind = tds.get(4).text();
                String courseScore = tds.get(5).text();
                String courseZongPing = tds.get(6).text();
                String courseZuiZhong = tds.get(7).text();
                String coursePoint = tds.get(8).text();

                gradeBean.setCourseYear(courseYear);
                gradeBean.setCourseTerm(courseTerm);
                gradeBean.setCourseCode(courseCode);
                gradeBean.setCourseIndex(courseIndex);
                gradeBean.setCourseName(courseName);
                gradeBean.setCourseKind(courseKind);
                gradeBean.setCourseScore(courseScore);
                gradeBean.setCourseBuKao(null);
                gradeBean.setCourseZongPing(courseZongPing);
                gradeBean.setCourseZuiZhong(courseZuiZhong);
                gradeBean.setCoursePoint(coursePoint);


                view.getGradeBeans().add(gradeBean);

                if (courseKind.contains("公选")) {
                    if (Double.valueOf(courseZuiZhong) >= 60) {
                        public_choose_scores.add(Double.valueOf(courseScore));
                    }
                }
                if (courseKind.contains("选修")) {
                    if (Double.valueOf(courseZuiZhong) >= 60) {
                        major_choose_scores.add(Double.valueOf(courseScore));
                    }
                }
                if (courseKind.contains("必修")) {
                    if (Double.valueOf(courseZuiZhong) >= 60) {
                        major_scores.add(Double.valueOf(courseScore));
                    }
                }
                if (courseKind.contains("实践")) {
                    if (Double.valueOf(courseZuiZhong) >= 60) {
                        practice_scores.add(Double.valueOf(courseScore));
                    }
                }

            }
        }
        // 初始化头部信息
        double public_choose_sum = 0;
        for (int i = 0; i < public_choose_scores.size(); i++) {
            public_choose_sum += public_choose_scores.get(i);
        }
        if (public_choose_sum >= 8.0) {
            view.public_choose_score_tv().setTextColor(activity.getResources().getColor(R.color.colorAccent));
        }
        view.public_choose_score_tv().setText(String.valueOf("已修公选课学分：" + public_choose_sum));


        double major_choose_sum = 0;
        for (int i = 0; i < major_choose_scores.size(); i++) {
            major_choose_sum += major_choose_scores.get(i);
        }

        if (major_choose_sum >= 24.0) {
            view.major_choose_score_tv().setTextColor(activity.getResources().getColor(R.color.colorAccent));
        }
        view.major_choose_score_tv().setText(String.valueOf("已修专选课学分：" + major_choose_sum));


        double major_sum = 0;
        for (int i = 0; i < major_scores.size(); i++) {
            major_sum += major_scores.get(i);
        }
        view.major_score_tv().setTextColor(activity.getResources().getColor(R.color.colorAccent));
        view.major_score_tv().setText(String.valueOf("已修必修课学分：" + major_sum));


        double practice_sum = 0;
        for (int i = 0; i < practice_scores.size(); i++) {
            practice_sum += practice_scores.get(i);
        }
        view.practice_score_tv().setTextColor(activity.getResources().getColor(R.color.colorAccent));
        view.practice_score_tv().setText(String.valueOf("已修实践课学分：" + practice_sum));

        view.all_score_container().setVisibility(View.VISIBLE);

        view.getAdapter().setData(view.getGradeBeans());
        view.getAdapter().notifyItemRangeChanged(0, view.getAdapter().getItemCount());


    }
}
