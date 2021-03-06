package com.sweethearts.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sweethearts.R;
import com.sweethearts.Utils.MyUtils;
import com.sweethearts.http.OkHttp;
import com.sweethearts.ui.activity.MainActivity;
import com.sweethearts.ui.view.LoginView;
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

public class LoginModel {
    private final static String language = "zh_CN";
    Handler handler;


    public void loadLoginEvent(final Activity activity, final LoginView view) {
//      设置登陆事件
        view.getLogin_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取学号跟密码
                String user_number = Objects.requireNonNull(view.getUserName_ET().getText()).toString().trim();
                String user_password = Objects.requireNonNull(view.getPassword_ET().getText()).toString().trim();
                if (user_number.isEmpty()) {
                    ToastUtils.showShort(R.string.input_number);
                    return;
                }
                if (user_password.isEmpty()) {
                    ToastUtils.showShort(R.string.input_pass);
                    return;
                }
                ToastUtils.showLong(R.string.login_ing);
                // 执行登陆事件
                SPUtils.getInstance("user_info").put("number", user_number);
                SPUtils.getInstance("user_info").put("password", user_password);
                doLogin(activity,user_number,user_password);
            }
        });

    }

    public void doLogin(final Activity activity,final String userName,final String password){
        handler = new Handler(activity.getMainLooper());
        // 发送请求
        Request request = new Request.Builder()
                .url(Url.Yangtzeu_Login_Path)
                .get()
                .build();
        OkHttp.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 发送失败 回到登陆页面
                Intent intent = new Intent(activity, activity.getClass());
                activity.startActivity(intent);
                activity.finish();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 通过响应体的内容  对输入的密码进行加密
                String responseBody = response.body().string();
                if (responseBody.contains("我的账户")) {
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
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
                        login(activity,userName,login_encode_pass);
                    } else {
                        LogUtils.i("lel", "数据初始化失败");
                    }

                }
            }
        });
    }

    public void login(final Activity activity,final String userName,final String login_encode_pass){
       // 进行模拟登陆 提交表单
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
                // 发送失败 回到登陆页面
                Intent intent = new Intent(activity, activity.getClass());
                activity.startActivity(intent);
                activity.finish();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String   myreponseBody = response.body().string();
                if (myreponseBody.contains("我的账户")) {
                    ToastUtils.showShort("登陆成功");
                    loginSuccess();
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    //清除 登陆的activity 使点击返回时 回到手机主屏幕 而不是登陆页面
                    activity.finish();
                    return;
                }
                //登录失败的错误原因
                if (myreponseBody.contains("请不要过快点击")) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            login(activity,userName, login_encode_pass);
                        }
                    }, 1000);
                    return;
                }

                //密码错误进行  重新进行此页面  账号密码回显
                if ( myreponseBody.contains("密码错误")&& myreponseBody.contains("用户")&&myreponseBody.contains("密码")) {
                    ToastUtils.showShort("账号 或 密码错误");
                    return;
                }
            }
        });
    }

    // 登陆成功 获取相应的jsessionid 保存到user_info这个实体中
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
        String name = SPUtils.getInstance("user_info").getString("number");


    }


}
