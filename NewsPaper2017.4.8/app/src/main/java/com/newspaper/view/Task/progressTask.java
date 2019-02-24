package com.newspaper.view.Task;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.newspaper.utils.MyView.TasksProgressView;
import com.newspaper.view.activity.GuideSlideActvity;
import com.newspaper.view.activity.LoadingActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by mephisto- on 2016/10/30.
 */

public class progressTask extends AsyncTask<String, Integer, HashMap<String, Bitmap>> implements Deliver {


    private HashMap<String, Bitmap> map;
    private String titles[];
    private TasksProgressView tasksView;

    private int CurrentProgress = 0;
    private int UrlsLength = 0;

    private ISend iSend;

    public progressTask(String[] titles, TasksProgressView tasksView, LoadingActivity loadingActivity) {
        this.titles = titles;
        this.tasksView = tasksView;
        iSend = loadingActivity;
        map = new HashMap<>();


    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        tasksView.setProgress(CurrentProgress);
    }

    @Override
    protected HashMap<String, Bitmap> doInBackground(String... Urls) {

//        UrlsLength = Urls.length;
        UrlsLength = 7;
        for (int positon = 0; positon < UrlsLength; positon++) {

            getBitmap(positon, Urls[positon], this);

        }
        return null;
    }

    @Override
    public void backToTask(int positon, int currentProgress, Bitmap bitmap) {
        if (currentProgress == 98) {
            currentProgress = 100;
        }
        map.put(titles[positon], bitmap);

        publishProgress(currentProgress);


    }

    @Override
    protected void onPostExecute(HashMap<String, Bitmap> stringBitmapHashMap) {
        super.onPostExecute(stringBitmapHashMap);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        tasksView.setProgress(values[0]);


        if (values[0] == (100)) {


            iSend.sendBitmap(map);


        }

    }


    private void getBitmap(int positon, String url, Deliver deliver) {
        OkHttpClient client = new OkHttpClient();
        Bitmap bm = null;
        try {
            Request request = new Request.Builder().url(url).build();
            okhttp3.Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                InputStream is = response.body().byteStream();
                bm = BitmapFactory.decodeStream(is);
                //返回获取的bitmap
                deliver.backToTask(positon, CurrentProgress += 14, bm);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public interface ISend {
        void sendBitmap(HashMap<String, Bitmap> map);
    }
}

interface Deliver {
    void backToTask(int positon, int currentProgress, Bitmap bitmap);
}
