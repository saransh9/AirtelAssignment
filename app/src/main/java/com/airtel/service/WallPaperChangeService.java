package com.airtel.service;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

/**
 * Created by saransh on 23/03/18.
 */

public class WallPaperChangeService extends Service {

    Timer timer;
    private SharedPreferences pref;
    private static int VALUE=1;


    public WallPaperChangeService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        timer = new Timer();
        timer.schedule(new TimerTaskHelper(), 2000, 10000);
        Log.d("time", String.valueOf(System.currentTimeMillis()));


        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public class TimerTaskHelper extends TimerTask {

        @Override
        public void run() {
            pref = getApplicationContext().getSharedPreferences("Wallpapers", Context.MODE_PRIVATE);

            String json = pref.getString("api", "");
            Log.v("data service", json);
            String[] data = json.split(",");

            final Bitmap[] m = new Bitmap[1];
            File ma = null;
            try {
                 ma =Glide.with(getApplicationContext())
                        .downloadOnly()
                        .load(data[VALUE])
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get() ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (ma != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(ma.getPath());
                WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());

                           try {
                               myWallpaperManager.setBitmap(bitmap);
                               VALUE++;
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer!=null)
        {
            timer.cancel();
        }
        Log.d("time", "stopped");
    }


}
