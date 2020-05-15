package com.sweethearts.Utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;


import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.sweethearts.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 2016 on 2017/11/29.
 */

public class MyUtils {

    public static void mVibrator(Context context, int time) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, time}; //0ms—500ms
        Objects.requireNonNull(vibrator).vibrate(pattern, -1);
    }



    //正则表达式匹配两个指定字符串中间的内容
    public static List<String> getSubUtil(String soap, String regex) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
        }
        return list;
    }



    public static void startActivity(String cls) {
        try {
            Intent intent = new Intent();
            //前名一个参数是应用程序的包名,后一个是这个应用程序的主Activity名
            intent.setComponent(new ComponentName(AppUtils.getAppPackageName(), cls));
            startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showShort(R.string.open_error);
        }
    }

    public static void startActivity(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Context context = ActivityUtils.getTopActivity();
        if (context == null) {
            context = Utils.getApp().getApplicationContext();
        }
        if (context == null) {
            ActivityUtils.startActivity(intent);
            return;
        }
        context.startActivity(intent);
        enterAnimation(context);
    }

    public static void enterAnimation(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    public static void setToolbarBackToHome(final AppCompatActivity activity, Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    public static String createSDCardDir(String DirName) {
        String path = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // 创建一个文件夹对象，赋值为外部存储器的目录
            File sdcardDir = Environment.getExternalStorageDirectory();
            //得到一个路径，内容是sdcard的文件夹路径和名字
            path = sdcardDir.getPath() + "/" + DirName;
            File path1 = new File(path);
            if (!path1.exists()) {
                //若不存在，创建目录，可以在应用启动的时候创建
                if (path1.mkdirs()) {
                    LogUtils.i("文件夹路径创建成功：" + path);
                }
            } else {
                Log.i("MyUtils", "createSDCardDir()：文件夹路径已存在：" + path);
            }
        } else {
            LogUtils.e("文件夹创建失败：" + DirName, "可能未取得读写权限");
        }
        return path;
    }


}
