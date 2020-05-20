package com.sweethearts.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sweethearts.R;
import com.sweethearts.entity.GradeBean;
import com.sweethearts.http.OkHttp;
import com.sweethearts.http.OnResultStringListener;
import com.sweethearts.ui.activity.LoginActivity;
import com.sweethearts.ui.view.GradePartView1;
import com.sweethearts.url.Url;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class GradePart1Model {
    private String term_id;


    public void loadGradeData(Activity activity, GradePartView1 view) {
        // 得到term_id  没有就使用默认的id
        term_id = SPUtils.getInstance("user_info").getString("term_id", Url.Default_Term);
        final String[] term_trip = activity.getResources().getStringArray(R.array.term_trip);
        final String[] term_id = activity.getResources().getStringArray(R.array.term_id);
//        设置标题
        for (int i = 0; i < term_id.length; i++) {
            if (this.term_id.equals(term_id[i])) {
                view.getToolbar().setTitle(term_trip[i]);
            }
        }
        // 先清除数据 再加载数据
        view.getAdapter().clear();
        view.getGradeBeans().clear();
        requestGradeData(activity, view);
    }


    public void requestGradeData(final Activity activity, final GradePartView1 view) {
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

                }
            }

            @Override
            public void onFailure(String error) {
                view.getRefresh().finishRefresh();
                ToastUtils.showShort("重新刷新");
            }
        });

    }


    public void parseGrade(final Activity activity, final GradePartView1 view, String data) {
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

            }
        }

        view.getAdapter().setData(view.getGradeBeans());
        view.getAdapter().notifyItemRangeChanged(0, view.getAdapter().getItemCount());
    }
}
