package com.padcmyanmar.sfc;

import android.app.Application;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.network.MMNewsAPI;
import com.padcmyanmar.sfc.utils.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aung on 11/4/17.
 */

public class SFCNewsApp extends Application {

    public static final String LOG_TAG = "SFCNewsApp";

    private MMNewsAPI theAPI;

    @Override
    public void onCreate() {
        super.onCreate();
//        NewsModel.initDatabase(getApplicationContext());
//        NewsModel.getInstance().startLoadingMMNews();
    }

}
