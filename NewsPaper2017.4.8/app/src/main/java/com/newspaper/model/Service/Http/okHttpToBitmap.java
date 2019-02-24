package com.newspaper.model.Service.Http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by root on 16-10-7.
 */

public class okHttpToBitmap {
    public static Bitmap getBitmap(String url) {
        OkHttpClient client = new OkHttpClient();
        Bitmap bm = null;
        try {
            Request request = new Request.Builder().url(url).build();
            okhttp3.Response response = client.newCall(request).execute();
            InputStream is = response.body().byteStream();
            bm = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }
}
