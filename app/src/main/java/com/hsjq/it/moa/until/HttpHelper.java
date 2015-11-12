package com.hsjq.it.moa.until;

import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.system.Os;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by JQLMT on 2015/11/3.
 */
public class HttpHelper {
    /**
     * 加载网络图片
     *
     * @param url 图片的URL
     * @return Bitmap类型的图片
     */
    public static Bitmap GetBitMapByUrl(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            // bitmap = BitmapFactory.decodeStream(is);
            bitmap = BitmapFactory.decodeStream(is).copy(Bitmap.Config.ARGB_8888, true);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 检查用户是否联网
     *
     * @param context
     * @return 联网True和不联网Flase
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private JSONObject mJsonObject;

    /**
     * 发送网络请求调用Ajax接口
     *
     * @param params 消息点的内容
     * @param strUrl 消息的Url接收地址
     * @return Json格式的返回值
     */
    public JSONObject RequestAjax(Map<String, Object> params, String strUrl, Context context) {

        new AQuery(context).ajax(strUrl, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                //   System.out.println(json.toString());
                try {
                    Log.w("1111111111111", json.get("errorCode").toString());
                    mJsonObject = json;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return mJsonObject;
    }
}
