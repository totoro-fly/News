package com.totoro_fly.news;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by totoro-fly on 2017/2/11.
 */

public class QueryUtils {
    private static final String TAG = "QueryUtils ";

    public static ArrayList<New.ResponseBean.ResultsBean> fetchNew(String stringUrl) {
        URL url = createUrl(stringUrl);
        String jsonResponse = makeHTTPRequest(url);
        ArrayList<New.ResponseBean.ResultsBean> list = extractJson(jsonResponse);
        return list;
    }

    private static URL createUrl(String url) {
        if (url == null)
            return null;
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            Log.d(TAG, "createUrl", e);
            e.printStackTrace();
        }
        return u;
    }

    private static String makeHTTPRequest(URL url) {
        if (url == null)
            return null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        String jsonResponse = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(2 * 1000);
            httpURLConnection.setConnectTimeout(4 * 1000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            } else {
                Log.d(TAG, "HTTP responseCode !=200");
            }
        } catch (IOException e) {
            Log.d(TAG, "makeHTTPRequest ", e);
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.d(TAG, "inputSteam close fail", e);
                    e.printStackTrace();
                }
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
        return jsonResponse;
    }

    private static String readFromInputStream(InputStream inputStream) {
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream == null)
            return null;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line=bufferedReader.readLine();
            }
        } catch (IOException e) {
            Log.d(TAG, "readFromInputstream", e);
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static ArrayList<New.ResponseBean.ResultsBean> extractJson(String stringJson) {
        if (stringJson.isEmpty())
            return null;
        Gson gson = new Gson();
        New n = gson.fromJson(stringJson, New.class);
        ArrayList<New.ResponseBean.ResultsBean> list = (ArrayList<New.ResponseBean.ResultsBean>) n.getResponse().getResults();
        return list;
    }
}
