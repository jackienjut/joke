package com.example.hailu.joke.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.hailu.joke.key.BaiduApiKey;
import com.example.hailu.joke.util.BaiduHttpUtil;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hailu on 2016/6/2.
 */
public class JokeService {

    static String http_text = "http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text";
    static String http_pic = "http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_pic";
    static BaiduHttpUtil baiduHttpUtil = new BaiduHttpUtil();

    static String httpUrl_Txt = "http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text";
    static String httpUrl_Pic="http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_pic";
    static String httpArg_temp = "page=";

    public static String getJokeText(int pagenum) {
        String str = null;
        String httpArg = httpArg_temp+pagenum;
        int count = 10;
        while (count > 0) {
            try {
                count--;
                str = request(httpUrl_Txt, httpArg);
                if (str != null)
                    break;
                else
                    continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static String getJokePic(int pagenum) {
        String str = null;
        String httpArg = httpArg_temp+pagenum;
        int count = 10;
        while (count > 0) {
            try {
                count--;
                str = request(httpUrl_Pic, httpArg);
                if (str != null)
                    break;
                else
                    continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }



    public static String request(String httpUrl, String httpArg) throws IOException {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        URL url = new URL(httpUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        // 填入apikey到HTTP header
        connection.setRequestProperty("apikey", BaiduApiKey.BAIDU_API_KEY);
        connection.connect();
        InputStream is = connection.getInputStream();
        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String strRead = null;
        while ((strRead = reader.readLine()) != null) {
            sbf.append(strRead);
            sbf.append("\r\n");
        }
        reader.close();
        result = sbf.toString();

        return result;
    }


    public static Bitmap getImageFromHttp(String url_path) {
        Bitmap bitmap = null;
        if (!checkUrl(url_path))
            return null;
        try {
            InputStream inputStream = getImageViewInputStream(url_path);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static InputStream getImageViewInputStream(String URL_PATH) throws IOException {
        InputStream inputStream = null;
        URL url = new URL(URL_PATH);
        if (url != null) {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
        }
        return inputStream;
    }


    private static boolean checkUrl(String url) {

        if (url == null)
            return false;
        else if (url.trim().equals(""))
            return false;
        else if (!url.startsWith("http"))
            return false;
        return true;
    }
}
