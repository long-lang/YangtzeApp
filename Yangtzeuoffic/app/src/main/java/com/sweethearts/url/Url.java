package com.sweethearts.url;

import com.blankj.utilcode.util.LogUtils;

import okhttp3.FormBody;
import okhttp3.Request;

public class Url {
    /**
     * 基本配置参数
     */

    //mob的Key secret
    public static final String key = "20588bd8fbea0";

    //长江大学主页
    public static final String Yangtzeu_Url = "http://www.yangtzeu.edu.cn/";
    //长江大学教务系统主页
    public static final String Yangtzeu_Base_Url = "http://jwc3.yangtzeu.edu.cn";
    //长江大学教务系统主页
    public static final String Yangtzeu_Base_Url_Ip = "http://221.233.24.23";

    //旧教务处主页
    public static final String Yangtzeu_JWC = "http://jwc.yangtzeu.edu.cn/";

    /**
     * 教务各类网址
     */
    //默认的学期期
    public static final String Default_Term = "89";
    //登录接口
    public static final String Yangtzeu_Login_Path = Yangtzeu_Base_Url + "/eams/login.action";
    //验证码地址
    public static final String Yangtzeu_Login_Code = Yangtzeu_Base_Url + "/eams/captcha/image.action";
    //注销地址
    public static final String Yangtzeu_Out = Yangtzeu_Base_Url + "/eams/logout.action";

    //学籍信息（本科）
    public static final String Yangtzeu_XueJI_Index1 = Yangtzeu_Base_Url + "/eams/stdDetail.action?projectId=1";
    public static final String Yangtzeu_XueJI1 = Yangtzeu_Base_Url + "/eams/stdDetail!innerIndex.action?projectId=1";
    //学籍信息（辅修）
    public static final String Yangtzeu_XueJI_Index2 = Yangtzeu_Base_Url + "/eams/stdDetail!index.action?projectId=2";
    public static final String Yangtzeu_XueJI2 = Yangtzeu_Base_Url + "/eams/stdDetail!innerIndex.action?projectId=2";


    //学生成绩（本科）
    public static final String Yangtzeu_Grade_Url_Index1 = Yangtzeu_Base_Url + "/eams/teach/grade/course/person!index.action?projectId=1";
    public static final String Yangtzeu_Grade_Url1 = Yangtzeu_Base_Url + "/eams/teach/grade/course/person!search.action?semesterId=";
    //学生成绩（辅修）
    public static final String Yangtzeu_Grade_Url_Index2 = Yangtzeu_Base_Url + "/eams/teach/grade/course/person!index.action?projectId=2";
    public static final String Yangtzeu_Grade_Url2 = Yangtzeu_Base_Url + "/eams/teach/grade/course/person!search.action?semesterId=";


    //学生所有成绩（本科）
    public static final String Yangtzeu_AllGrade_Url_Index1 = Yangtzeu_Base_Url + "/eams/teach/grade/course/person!index.action?projectId=1";
    public static final String Yangtzeu_AllGrade_Url1 = Yangtzeu_Base_Url + "/eams/teach/grade/course/person!historyCourseGrade.action?projectType=MAJOR";
    //学生所有成绩（辅修）
    public static final String Yangtzeu_AllGrade_Url_Index2 = Yangtzeu_Base_Url + "/eams/teach/grade/course/person!index.action?projectId=2";
    public static final String Yangtzeu_AllGrade_Url2 = Yangtzeu_Base_Url + "/eams/teach/grade/course/person!historyCourseGrade.action?projectType=MINOR";

    //修改密码
    public static final String Yangtzeu_Change_Password = Yangtzeu_Base_Url + "/eams/security/my!save.action";
    //控制面板
    public static final String Yangtzeu_Control = Yangtzeu_Base_Url + "/eams/myPlan.action";


    //Cet教务系统查询（本科）
    public static final String Yangtzeu_Cet_Index1 = Yangtzeu_Base_Url + "/eams/stdOtherExamSignUp!index.action?projectId=1";
    public static final String Yangtzeu_Cet1 = Yangtzeu_Base_Url + "/eams/stdOtherExamSignUp!innerIndex.action?projectId=1";
    //Cet教务系统查询（辅修）
    public static final String Yangtzeu_Cet_Index2 = Yangtzeu_Base_Url + "/eams/stdOtherExamSignUp!index.action?projectId=2";
    public static final String Yangtzeu_Cet2 = Yangtzeu_Base_Url + "/eams/stdOtherExamSignUp!innerIndex.action?projectId=2";




    //Cet官方日期表单查询
    public static final String Yangtzeu_Cet_Date = "http://cet.neea.edu.cn/cet/js/data.js";
    //Cet官方报名
    public static final String Yangtzeu_Guan_Cet = "https://passport.etest.net.cn/CETLogin";


    //Cet报名
    public static final String Yangtzeu_Cet_Add = Yangtzeu_Base_Url + "/eams/stdOtherExamSignUp!configList.action";
    //Cet准考证查询验证码
    public static final String Yangtzeu_Cet_Card_Yzm = "http://cet-bm.neea.edu.cn/Home/VerifyCodeImg";

    //课表查询ids（本科）
    public static final String Yangtzeu_Table_Index1 = Yangtzeu_Base_Url + "/eams/courseTableForStd!index.action?projectId=1";
    public static final String Yangtzeu_Table_Ids1 = Yangtzeu_Base_Url + "/eams/courseTableForStd!innerIndex.action?projectId=1";
    //课表查询ids（辅修）
    public static final String Yangtzeu_Table_Index2 = Yangtzeu_Base_Url + "/eams/courseTableForStd!index.action?projectId=2";
    public static final String Yangtzeu_Table_Ids2 = Yangtzeu_Base_Url + "/eams/courseTableForStd!innerIndex.action?projectId=2";
    //课表查询
    public static final String Yangtzeu_Table = Yangtzeu_Base_Url + "/eams/courseTableForStd!courseTable.action";

    //我的考试（本科）
    public static final String Yangtzeu_My_Test1 = Yangtzeu_Base_Url + "/eams/stdExamTable!innerIndex.action?projectId=1";
    //我的考试（辅修）
    public static final String Yangtzeu_My_Test2 = Yangtzeu_Base_Url + "/eams/stdExamTable!innerIndex.action?projectId=2";
    //我的考试详情
    public static final String Yangtzeu_My_Details_Test = Yangtzeu_Base_Url + "/eams/stdExamTable!examTable.action?examBatch.id=";

    //评教（本科）
    public static final String Yangtzeu_Teacher1 = Yangtzeu_Base_Url + "/eams/quality/stdEvaluate!innerIndex.action?projectId=1";
    //评教（辅修）
    public static final String Yangtzeu_Teacher2 = Yangtzeu_Base_Url + "/eams/quality/stdEvaluate!innerIndex.action?projectId=2";


    //选课查询 （本科）
    public static final String Yangtzeu_ChooseClass_Index1 = Yangtzeu_Base_Url + "/eams/stdElectCourse!index.action?projectId=1";
    public static final String Yangtzeu_ChooseClass1 = Yangtzeu_Base_Url + "/eams/stdElectCourse!innerIndex.action?projectId=1";
    //选课查询 （辅修）
    public static final String Yangtzeu_ChooseClass_Index2 = Yangtzeu_Base_Url + "/eams/stdElectCourse!index.action?projectId=2";
    public static final String Yangtzeu_ChooseClass2 = Yangtzeu_Base_Url + "/eams/stdElectCourse!innerIndex.action?projectId=2";



    //培养计划
    public static final String Yangtzeu_Personal_Plan = Yangtzeu_Base_Url + "/eams/myPlan.action";


    //专业培养计划 （本科）
    public static final String Yangtzeu_Major_Mode_Index1 = Yangtzeu_Base_Url + "/eams/stdMajorPlan!index.action?projectId=1";
    public static final String Yangtzeu_Major_Mode1 = Yangtzeu_Base_Url + "/eams/stdMajorPlan!search.action";
    //专业培养计划 （辅修）
    public static final String Yangtzeu_Major_Mode_Index2 = Yangtzeu_Base_Url + "/eams/stdMajorPlan!index.action?projectId=2";
    public static final String Yangtzeu_Major_Mode2 = Yangtzeu_Base_Url + "/eams/stdMajorPlan!search.action";


    //我的培养方案详情
    public static final String Yangtzeu_Me_Mode_Details = Yangtzeu_Base_Url + "/eams/myDraftPersonalPlan!info.action";

    //站内消息
    public static final String Yangtzeu_ZhanNei = Yangtzeu_Base_Url + "/eams/systemMessageForStd!search.action";
    //成绩导出
    public static final String Yangtzeu_Grade_Export = Yangtzeu_Base_Url + "/eams/postgraduate/midterm/stdExamine!export.action";

    //长大新闻
    public static final String Yangtzeu_News = "http://news.yangtzeu.edu.cn/";
    //长大新闻搜索
    public static final String Yangtzeu_News_Search = "http://news.yangtzeu.edu.cn/search_list.jsp?wbtreeid=1001&newskeycode2=6ICD56CU";
    //计算机二级成绩
    public static final String Yangtzeu_Computer_Grade = "http://cjcx.neea.edu.cn/html1/folder/1508/206-1.htm?sid=300";
    //一卡通
    public static final String Yangtzeu_Card_Home = "http://10.10.220.77";
    //一卡通登录
    public static final String Yangtzeu_CenterCard_Login = Yangtzeu_Card_Home + "/Login.aspx";
    //教务通知
    public static final String Yangtzeu_JWTZ = "http://jwc.yangtzeu.edu.cn/jwxw/jwtz.htm";
    //本周事务
    public static final String Yangtzeu_BZSW = "http://jwc.yangtzeu.edu.cn/jwxw/bzsw.htm";
    //教学动态
    public static final String Yangtzeu_JXDT = "http://jwc.yangtzeu.edu.cn/jwxw/jxdt.htm";
    //教学简报
    public static final String Yangtzeu_JXJB = "http://jwc.yangtzeu.edu.cn/jwxw/jxjb.htm";

    //长江大学学院网站
    public static final String Yangtzeu_XueYuanWeb = "http://www.yangtzeu.edu.cn/xndh/jxdw.htm";
    //缴纳网费
    public static final String Yangtzeu_Fee = "http://58.50.120.1:89/Self/dashboard";


    /**
     * 物理实验中心
     */
    //主页
    public static String Yangtzeu_Physical_Home = "http://phylab.yangtzeu.edu.cn/jpkc/";
    //登录
    public static String Yangtzeu_Physical_Login = "http://10.10.16.16/index.php/Home/Index/login";
    //列表
    public static String Yangtzeu_Physical_List = "http://10.10.16.16/index.php/Home/Student/listexp";
    //删除预约
    public static String Yangtzeu_Physical_Delete = "http://10.10.16.16/index.php/Home/Student/delmyexp";
    //我的预约
    public static String Yangtzeu_Physical_Grade = "http://10.10.16.16/index.php/Home/Student/listmyexp";
    //我的预约
    public static String Yangtzeu_Physical_Add = "http://10.10.16.16/index.php/Home/Student/prelistexp";
    //验证码
    public static String Yangtzeu_Physical_Verify = "http://10.10.16.16/index.php/home/index/verify";



    //课表默认背景
    public static final String Yangtzeu_Table_Background_White = "http://whysroom.oss-cn-beijing.aliyuncs.com/yangtzeu/normal/white.jpg";
    //课表默认背景
    public static final String Yangtzeu_Table_Background = "http://whysroom.oss-cn-beijing.aliyuncs.com/yangtzeu/normal/table_bg.jpg";
    //爱心
    public static final String Yangtzeu_App_Love = "http://whysroom.oss-cn-beijing.aliyuncs.com/yangtzeu/normal/love.png";

    /**
     * Voa听力
     */
    public static final String Yangtzeu_Voa_Home = "http://www.51voa.com/";


}
