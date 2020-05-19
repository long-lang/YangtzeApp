package com.sweethearts.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//   成绩的实体类
public class GradeBean implements Serializable {
    private List<GradeBean> gradeBeans= new ArrayList<>();

    //课程年份
    private String courseYear;
    //课程学期
    private String courseTerm;
    //课程号
    private String courseCode;

    //课程下标
    private String courseIndex;
    //课程名称
    private String courseName;
    //课程类型
    private String courseKind;
    //课程分数
    private String courseScore;
    //课程补考
    private String courseBuKao;
    private String courseZongPing;
    private String courseZuiZhong;
    //课程绩点
    private String coursePoint;

    public List<GradeBean> getGradeBeans() {
        return gradeBeans;
    }

    public void setGradeBeans(List<GradeBean> gradeBeans) {
        this.gradeBeans = gradeBeans;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getCourseTerm() {
        return courseTerm;
    }

    public void setCourseTerm(String courseTerm) {
        this.courseTerm = courseTerm;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseIndex() {
        return courseIndex;
    }

    public void setCourseIndex(String courseIndex) {
        this.courseIndex = courseIndex;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseKind() {
        return courseKind;
    }

    public void setCourseKind(String courseKind) {
        this.courseKind = courseKind;
    }

    public String getCourseScore() {
        return courseScore;
    }

    public void setCourseScore(String courseScore) {
        this.courseScore = courseScore;
    }

    public String getCourseBuKao() {
        return courseBuKao;
    }

    public void setCourseBuKao(String courseBuKao) {
        this.courseBuKao = courseBuKao;
    }

    public String getCourseZongPing() {
        return courseZongPing;
    }

    public void setCourseZongPing(String courseZongPing) {
        this.courseZongPing = courseZongPing;
    }

    public String getCourseZuiZhong() {
        return courseZuiZhong;
    }

    public void setCourseZuiZhong(String courseZuiZhong) {
        this.courseZuiZhong = courseZuiZhong;
    }

    public String getCoursePoint() {
        return coursePoint;
    }

    public void setCoursePoint(String coursePoint) {
        this.coursePoint = coursePoint;
    }



}
