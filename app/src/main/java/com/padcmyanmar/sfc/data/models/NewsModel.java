package com.padcmyanmar.sfc.data.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.padcmyanmar.sfc.data.db.AppDatabase;
import com.padcmyanmar.sfc.data.vo.CommentActionVO;
import com.padcmyanmar.sfc.data.vo.FavoriteActionVO;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.PublicationVO;
import com.padcmyanmar.sfc.data.vo.SentToVO;
import com.padcmyanmar.sfc.events.RestApiEvents;
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

public class NewsModel {

    private static NewsModel objInstance;

    private List<NewsVO> mNews;
    private int mmNewsPageIndex = 1;

    private AppDatabase mAppDatabase;

    private NewsModel(Context context) {
        mAppDatabase = AppDatabase.getNewsDatabase(context);

        EventBus.getDefault().register(this);
        mNews = new ArrayList<>();
        startLoadingMMNews();
    }

    public static NewsModel getInstance() {
        if (objInstance != null) {
            return objInstance;
        }
        throw new RuntimeException("In this phase, NewsModel object should already has the objInstance initialized.");
    }

    public static void initDatabase(Context context) {
        objInstance = new NewsModel(context);
    }

    public LiveData<List<NewsVO>> getNews() {
        return mAppDatabase.newsDao().getAllNews();
    }

    public NewsVO getNew(String id){
        return mAppDatabase.newsDao().getNew(id);
    }

    public PublicationVO getPublication(String id){
        return mAppDatabase.publicationDao().getPublicationById(id);
    }

    public void startLoadingMMNews() {
        MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmNewsPageIndex);
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {

        // firstly delete data
//        mAppDatabase.newsDao().deleteAllNews();

        mNews.addAll(event.getLoadNews());

        // insert new
        for (NewsVO newsVO : event.getLoadNews()) {

            long insertedPublicationId = mAppDatabase.publicationDao().insertPublication(newsVO.getPublication());
            mAppDatabase.newsInImageDao().insertImageWithNews(newsVO);

            if (newsVO.getCommentActions() != null) {
                for (CommentActionVO commentActionVO : newsVO.getCommentActions()) {
                    mAppDatabase.actedUserDao().insertActedUser(commentActionVO.getActedUser());
                    mAppDatabase.commentActionDao().insertCommentById(newsVO.getNewsId(),
                            commentActionVO.getActedUser().getUserId(),
                            commentActionVO);
                }
            }

            if (newsVO.getFavoriteActions() != null) {
                for (FavoriteActionVO favoriteActionVO : newsVO.getFavoriteActions()) {
                    mAppDatabase.actedUserDao().insertActedUser(favoriteActionVO.getActedUser());
                    mAppDatabase.favoriteActionDao().insertFavoriteById(newsVO.getNewsId(),
                            favoriteActionVO.getActedUser().getUserId(),
                            favoriteActionVO);
                }
            }

            if (newsVO.getSentToActions() != null) {
                for (SentToVO sentToVO : newsVO.getSentToActions()) {
                    mAppDatabase.actedUserDao().insertActedUser(sentToVO.getSender());
                    mAppDatabase.actedUserDao().insertActedUser(sentToVO.getReceiver());
                    mAppDatabase.sendToDao().insertSendToById(newsVO.getNewsId(),
                            sentToVO.getSender().getUserId(),
                            sentToVO.getReceiver().getUserId(),
                            sentToVO);
                }
            }

            mAppDatabase.newsDao().insertNewsWithPublicationId(newsVO.getPublication().getPublicationId(), newsVO);
            mAppDatabase.newsDao().insertNew(newsVO);
        }

        mmNewsPageIndex = event.getLoadedPageIndex() + 1;
    }
}
