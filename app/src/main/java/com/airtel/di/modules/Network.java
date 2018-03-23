package com.airtel.di.modules;

import android.util.Log;


import com.airtel.di.scope.ApplicationScope;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by saransh on 15/03/18.
 */

@Module
public class Network {

    @Provides
    @ApplicationScope
    HttpLoggingInterceptor loggingInterceptor()
    {
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("dagger2", "Log: " + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;

    }

    @Provides
    @ApplicationScope
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggerInterceptor)
    {
        return new OkHttpClient.Builder()
                .addInterceptor(loggerInterceptor)
                .readTimeout(200000, TimeUnit.MILLISECONDS)
                .connectTimeout(200000, TimeUnit.MILLISECONDS)
                .writeTimeout(200000,TimeUnit.MILLISECONDS)
                .build();

    }
}
