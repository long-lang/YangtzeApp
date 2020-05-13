package com.sweethearts.ui.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;


import com.blankj.utilcode.util.SPUtils;
import com.sweethearts.R;
import com.sweethearts.Utils.MyUtils;
import com.sweethearts.http.OkHttp;
import com.sweethearts.url.Url;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.Cookie;
import okhttp3.FormBody;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final static String language = "zh_CN";
    EditText studentNumberTv;
    EditText passwordTv ;
    String studentNumber;
    String password;
    Handler handler;
    String msg ;
    String table_ids;
    final  int week = 1;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentNumberTv = findViewById(R.id.username);
        passwordTv = findViewById(R.id.password);
       // text = findViewById(R.id.response_text);
        // getInfo("201703191","120010");
    }

    public void btn_Login(View view) {
         studentNumber = studentNumberTv.getText().toString().trim();
        password = passwordTv.getText().toString().trim();
        getInfo(studentNumber,password);
        //getInfo("201703191","120010");
    }
    public void getInfo(final String userName,final String password){
        handler = new Handler(this.getMainLooper());
        Request request = new Request.Builder()
                .url(Url.Yangtzeu_Login_Path)
                .get()
                .build();
        OkHttp.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.i("lel","第一次请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                if (responseBody.contains("我的账户")) {
                    LogUtils.i("lel",responseBody);
                    LogUtils.i("lel", "登陆成功1");
                } else if (responseBody.contains("用户名") &&responseBody.contains("密码")) {
                    Document document = Jsoup.parse(responseBody);
                    Elements scripts = document.select("script");
                    String scripts_text = scripts.toString();
                    String regex0 = "CryptoJS.SHA1[(]\'(.*?)\'";
                    List<String> key_list = MyUtils.getSubUtil(scripts_text, regex0);
                    if (!ObjectUtils.isEmpty(key_list)) {
                        String login_key = key_list.get(0);
                        String login_encode_pass = EncryptUtils.encryptSHA1ToString(login_key + password).toLowerCase();
                        LogUtils.i("密码加密前缀：" + login_key, "密码加密完成：" + login_encode_pass);
                        login(userName,login_encode_pass);
                    } else {
                        LogUtils.i("lel", "数据初始化失败");
                    }

                }
            }
        });
    }

    public void login(final String userName,final String login_encode_pass){
        FormBody formBody = new FormBody.Builder()
                .add("username", userName)
                .add("password", login_encode_pass)
                .add("encodedPassword", "")
                .add("session_locale", language)
                .build();

        Request request = new Request.Builder()
                .post(formBody)
                .url(Url.Yangtzeu_Login_Path)
                .build();
        OkHttp.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.i("lel","第二次请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String   myreponseBody = response.body().string();
                LogUtils.i("lel",String.valueOf(myreponseBody.contains("我的账户")));
                //LogUtils.i("lel",String.valueOf(myreponseBody.contains("用户名")));
                msg = myreponseBody;
                if (myreponseBody.contains("我的账户")) {
                    LogUtils.i("得到我的账户");
                    LogUtils.i(myreponseBody);
                    loginSuccess();
                    return;
                }
                //登录失败的错误原因
                if (myreponseBody.contains("请不要过快点击")) {
                    LogUtils.i("点击过快");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            login(userName, login_encode_pass);
                        }
                    }, 1000);
                    return;
                }
            }
        });
    }
    private static void loginSuccess() {
        HashMap<String, String> cookieStr = new HashMap<>();
        List<Cookie> CookieList = OkHttp.cookieJar().loadForRequest(Objects.requireNonNull(HttpUrl.get(URI.create(Url.Yangtzeu_Login_Path))));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < CookieList.size(); i++) {
            builder.append(CookieList.get(i));
            builder.append(";");
        }

        //截取有用的Cookie
        String[] list = builder.toString().split(";");
        for (String string : list) {
            if (string.contains("JSESSIONID")) {
                cookieStr.put("JSESSIONID", string + ";");
            } else if (string.contains("GSESSIONID")) {
                cookieStr.put("GSESSIONID", string + ";");
            } else if (string.contains("adc-ck-jwxt_pools")) {
                cookieStr.put("pools", string + ";");
            }
        }

        String term_id = SPUtils.getInstance("user_info").getString("term_id", "89");
        cookieStr.put("semester", "semester.id=" + term_id + ";");

        String mCookie = cookieStr.get("JSESSIONID") +
                cookieStr.get("GSESSIONID") +
                cookieStr.get("semester") +
                cookieStr.get("pools");
        LogUtils.i("获取到的cookie",mCookie);
        SPUtils.getInstance("user_info").put("online", true);
        SPUtils.getInstance("user_info").put("cookie", mCookie);
        LogUtils.i("从spUtils获取到的cookie值",SPUtils.getInstance("user_info").getString("cookie"));
        String name = SPUtils.getInstance("user_info").getString("number");


    }

    //该url可以变
    public void btn_getgrade(View view) {
        Request request = new Request.Builder()
                .addHeader("cookie",SPUtils.getInstance("user_info").getString("cookie"))
                .url("http://jwc3.yangtzeu.edu.cn/eams/teach/grade/course/person!search.action?semesterId=89&projectType=")
                .build();
        OkHttp.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.i(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Document document = Jsoup.parse(response.body().string());
                LogUtils.i(document);

            }
        });
    }

    public void btn_getTable(View view) {
        loadTableDataStep1();
    }

    //获取用户的ids
    public void loadTableDataStep1() {
        Request request = new Request.Builder()
                .addHeader("cookie",SPUtils.getInstance("user_info").getString("cookie"))
                .url(Url.Yangtzeu_Table_Ids1)
                .build();
        OkHttp.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.i("ids获取失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Document document = Jsoup.parse(response.body().string());
                    Elements scripts = document.select("script");
                    String script = scripts.get(scripts.size() - 1).toString();

                    String regex0 = "addInput(.*?);";
                    String week = MyUtils.getSubUtil(script, regex0).get(0);
                    String[] ids = week.split(",");
                    String temp = ids[2];
                    temp = temp.substring(temp.indexOf("\"") + 1, temp.lastIndexOf("\""));
                    table_ids = temp;
                    LogUtils.i("用户IDS", table_ids);
                    loadTableDataStep2();
                } catch (Exception e) {
                    table_ids = "";
                    LogUtils.i("用户IDS", table_ids);
                }
            }
        });


    }


    public void loadTableDataStep2() {
        String term_id = SPUtils.getInstance("user_info").getString("term_id", Url.Default_Term);
        FormBody formBody = new FormBody.Builder()
                .add("ignoreHead", "1")
                .add("setting.kind", "std")
                .add("startWeek", String.valueOf(week))
                .add("project.id", "1")
                .add("semester.id", term_id)
                .add("ids", table_ids)
                .build();

        Request request = new Request.Builder()
                .addHeader("cookie", SPUtils.getInstance("user_info").getString("cookie"))
                .url(Url.Yangtzeu_Table)
                .post(formBody)
                .build();

        OkHttp.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.i("获取课表失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.i("获取课表成功");
                LogUtils.i( response.body().string());
            }
        });
    }
}

