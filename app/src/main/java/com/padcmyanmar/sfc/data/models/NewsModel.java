package com.padcmyanmar.sfc.data.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.padcmyanmar.sfc.data.db.AppDatabase;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.PublicationVO;
import com.padcmyanmar.sfc.events.RestApiEvents;
import com.padcmyanmar.sfc.network.MMNewsDataAgent;
import com.padcmyanmar.sfc.network.MMNewsDataAgentImpl;
import com.padcmyanmar.sfc.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 12/3/17.
 */

public class NewsModel extends ViewModel {

    private static NewsModel objInstance;

    private List<NewsVO> mNews;
    private int mmNewsPageIndex = 1;

    private AppDatabase mAppDatabase;

    public NewsModel() {
        EventBus.getDefault().register(this);
        mNews = new ArrayList<>();
        startLoadingMMNews();
    }

    public static NewsModel getInstance() {
        if (objInstance == null) {
            objInstance = new NewsModel();
        }
        return objInstance;
    }

    public void initDatabase(Context context) {
        mAppDatabase = AppDatabase.getNewsDatabase(context);
    }

    public LiveData<List<NewsVO>> getNews() {
        return mAppDatabase.newsDao().getAllNews();
    }

    public NewsVO getNew(String id){
        return mAppDatabase.newsDao().getNew(id);
    }

    public PublicationVO getPublication(String id){
        return mAppDatabase.newsDao().getPublication(id);
    }

    public void startLoadingMMNews() {
        MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmNewsPageIndex);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        AppDatabase.destoryInstance();
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {

        // firstly delete data
//        mAppDatabase.newsDao().deleteAllNews();

        mNews.addAll(event.getLoadNews());

        // insert new
        for (NewsVO newsVO : event.getLoadNews()) {
            NewsVO vo = newsVO;
            mAppDatabase.newsDao().insertNew(newsVO);
        }

        mmNewsPageIndex = event.getLoadedPageIndex() + 1;
    }
}
