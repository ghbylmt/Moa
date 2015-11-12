package com.hsjq.it.moa;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.os.Handler;
import android.widget.Toast;


public class Welcome extends Activity {

    //    欢迎页面的启动时间
    private static final int LOAD_DISPLAY_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (getSharedPreferences("showTutorail", Activity.MODE_PRIVATE).getBoolean("flag", false)) {
                    //Toast.makeText(getApplicationContext(), "不需要展示教程引导页面", Toast.LENGTH_SHORT).show();
                    //判断当前的用户是否已经登录
                    if (getSharedPreferences("logininfo", Activity.MODE_PRIVATE).getString("users", null) != null) {
                        Intent mainactivity = new Intent(Welcome.this, MainActivity.class);
                        startActivity(mainactivity);
                        Welcome.this.finish();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.welcome_not_login_yet, Toast.LENGTH_SHORT).show();
                        Intent loginactivity = new Intent(Welcome.this, LoginActivity.class);
                        startActivity(loginactivity);
                        Welcome.this.finish();
                    }
                } else {
                    // Toast.makeText(getApplicationContext(), "需要展示引导页面", Toast.LENGTH_SHORT).show();
                    Intent intentTutorail = new Intent(Welcome.this, Tutorail.class);
                    startActivity(intentTutorail);
                    Welcome.this.finish();
                }
            }
        }, LOAD_DISPLAY_TIME);
    }
}
