package com.hsjq.it.moa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.hsjq.it.moa.until.CircularImage;
import com.hsjq.it.moa.until.HttpHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    /*
    *私有的成员变量
     */
    EditText editTextUserName, editTextUserPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //成员变量初始化
        CircularImage cover_user_photo = (CircularImage) findViewById(R.id.cover_user_photo);
        cover_user_photo.setImageResource(R.drawable.img_01);
        //私有成员变量的绑定
        editTextUserName = (EditText) findViewById(R.id.edittext_user_name);
        editTextUserPassword = (EditText) findViewById(R.id.edittext_user_password);
        buttonLogin = (Button) findViewById(R.id.button_login);
        /*
        *设置登录按钮的绑定事件
         */
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUserName = editTextUserName.getText().toString();
                String strUserPassword = editTextUserPassword.getText().toString();
                if (strUserName == null || strUserName.trim().equals("")) {
                    editTextUserName.setError(getResources().getString(R.string.login_user_name_error));
                    editTextUserName.requestFocus();
                    return;
                } else {
                    if (strUserPassword == null || strUserPassword.trim().equals("")) {
                        editTextUserPassword.setError(getResources().getString(R.string.login_user_password_error));
                        editTextUserPassword.requestFocus();
                        return;
                    } else {
                        buttonLogin.setText(R.string.login_loginingg);
                        buttonLogin.setEnabled(false);
                        //调用登录的方法实现登陆的功能
                        //开始发送登陆的请求，进行登陆操作
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //                            params = getParams();
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("username", editTextUserName.getText());
                                map.put("password", editTextUserPassword.getText());
                                new AQuery(getBaseContext()).ajax("http://test.hsjq.com:8080/Login/LoginHandler.ashx", map, JSONObject.class, new AjaxCallback<JSONObject>() {
                                    @Override
                                    public void callback(String url, JSONObject json, AjaxStatus status) {
                                        //   System.out.println(json.toString());
                                        AfterLogin(json);
                                    }
                                });
                            }
                        }).start();

                    }
                }
                buttonLogin.setEnabled(true);
            }
        });
    }

    /**
     * 在完成了登录请求之后的处理
     *
     * @param jsonObject
     */
    private void AfterLogin(JSONObject jsonObject) {
        if (jsonObject == null) {
            Toast.makeText(getBaseContext(), R.string.login_login_error, Toast.LENGTH_SHORT).show();
            buttonLogin.setText(R.string.login_login);
            editTextUserName.requestFocus();
        } else {
            try {
                if (jsonObject.getString("errorCode").equals("0001")) {
                    //登录成功了啦，跳转到新的页面
                    Toast.makeText(getBaseContext(), R.string.login_login_success, Toast.LENGTH_SHORT).show();
                    Log.w("-----------------", jsonObject.getString("errorCode"));
                    //JSONArray array = jsonObject.getJSONArray("messageContent");
                    JSONObject userInfo = jsonObject.getJSONArray("messageContent").getJSONObject(0);
                    if (userInfo != null) {
                        //存储用户的登录信息
                        SharedPreferences.Editor editor = getSharedPreferences("logininfo", Activity.MODE_PRIVATE).edit();
                        editor.putString("users", userInfo.getString("USER_CODE"));
                        editor.putString("userName", userInfo.getString("USER_NAME"));
                        editor.putString("userGender", userInfo.getString("USER_SEX"));
                        editor.putString("userEmail", userInfo.getString("USER_EMAIL"));
                        editor.putString("userPhoneNum", userInfo.getString("USER_MOBILE"));
                        editor.putString("userAddress", userInfo.getString("USER_ADES"));
                        editor.putString("userCid", userInfo.getString("CID"));
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), R.string.login_login_failed, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), R.string.login_login_failed, Toast.LENGTH_SHORT).show();
                    buttonLogin.setText(R.string.login_login);
                    editTextUserName.requestFocus();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /*
    Double Press back to Exist
    双击退出应用程序
     */
    private long exitTime = 0;

    /*
    *双击退出应用程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), R.string.tips_exist, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
