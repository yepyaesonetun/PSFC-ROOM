package com.padcmyanmar.sfc.data.models;

import android.util.Log;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.padcmyanmar.sfc.data.db.AppDatabase;
import com.padcmyanmar.sfc.data.vo.CommentActionVO;
import com.padcmyanmar.sfc.data.vo.FavoriteActionVO;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.PublicationVO;
import com.padcmyanmar.sfc.data.vo.SentToVO;
import com.padcmyanmar.sfc.events.RestApiEvents;
import com.padcmyanmar.sfc.network.MMNewsAPI;
import com.padcmyanmar.sfc.network.reponses.GetNewsResponse;
import com.padcmyanmar.sfc.utils.AppConstants;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aung on 12/3/17.
 */

public class NewsModel {

    private static NewsModel sObjInstance;

    private int mmNewsPageIndex = 1;
    private Map<String, NewsVO> mNewsMap;

    //    private AppDatabase mAppDatabase;
    private MMNewsAPI theAPI;

//    private NewsModel(Context context) {
//        mAppDatabase = AppDatabase.getNewsDatabase(context);
//
//        EventBus.getDefault().register(this);
//        mNews = new ArrayList<>();
//        startLoadingMMNews();
//    }

    private NewsModel() {
        initMMNewsAPI();
        mNewsMap = new HashMap<>();
    }

    public MMNewsAPI getMMNewsAPI() {
        return theAPI;
    }


    private void initMMNewsAPI() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.NEWS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        theAPI = retrofit.create(MMNewsAPI.class);
    }

    public static NewsModel getInstance() {
        if (sObjInstance == null) {
            sObjInstance = new NewsModel();
        }
        return sObjInstance;
    }

//    public static void initDatabase(Context context) {
//        sObjInstance = new NewsModel(context);
//    }

//    public LiveData<List<NewsVO>> getNews() {
//        return mAppDatabase.newsDao().getAllNews();
//    }

//    public NewsVO getNew(String id) {
//        return mAppDatabase.newsDao().getNew(id);
//    }
//
//    public PublicationVO getPublication(String id) {
//        return mAppDatabase.publicationDao().getPublicationById(id);
//    }

    public NewsVO getNewsById(String id) {
        return mNewsMap.get(id);
    }

    // parsing Subject as parameter is good format
    public void startLoadingMMNews(final PublishSubject<List<NewsVO>> newsListSubject) {
//        MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmNewsPageIndex);

        Single<GetNewsResponse> getNewsResponseObservable = getMMNews();
        getNewsResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<GetNewsResponse, List<NewsVO>>() {
                    @Override
                    public List<NewsVO> apply(GetNewsResponse getNewsResponse) throws Exception {
                        return getNewsResponse.getNewsList();
                    }
                })
                .subscribeWith(new DisposableSingleObserver<List<NewsVO>>() {
                    @Override
                    public void onSuccess(List<NewsVO> newsVOS) {
                        newsListSubject.onNext(newsVOS);
                        for (NewsVO vo : newsVOS) {
                            mNewsMap.put(vo.getNewsId(), vo);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("NewsModel", "onError: " + e.getMessage());
                    }
                });

    }

    public Single<GetNewsResponse> getMMNews() {
        return getMMNewsAPI().loadMMNews(mmNewsPageIndex, AppConstants.ACCESS_TOKEN);
    }


//    @Subscribe(threadMode = ThreadMode.BACKGROUND)
//    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
//
//        // firstly delete data
////        mAppDatabase.newsDao().deleteAllNews();
//
//        //mNews.addAll(event.getLoadNews());
//
//        // insert new
//        for (NewsVO newsVO : event.getLoadNews()) {
//
//            long insertedPublicationId = mAppDatabase.publicationDao().insertPublication(newsVO.getPublication());
//            mAppDatabase.newsInImageDao().insertImageWithNews(newsVO);
//
//            if (newsVO.getCommentActions() != null) {
//                for (CommentActionVO commentActionVO : newsVO.getCommentActions()) {
//                    mAppDatabase.actedUserDao().insertActedUser(commentActionVO.getActedUser());
//                    mAppDatabase.commentActionDao().insertCommentById(newsVO.getNewsId(),
//                            commentActionVO.getActedUser().getUserId(),
//                            commentActionVO);
//                }
//            }
//
//            if (newsVO.getFavoriteActions() != null) {
//                for (FavoriteActionVO favoriteActionVO : newsVO.getFavoriteActions()) {
//                    mAppDatabase.actedUserDao().insertActedUser(favoriteActionVO.getActedUser());
//                    mAppDatabase.favoriteActionDao().insertFavoriteById(newsVO.getNewsId(),
//                            favoriteActionVO.getActedUser().getUserId(),
//                            favoriteActionVO);
//                }
//            }
//
//            if (newsVO.getSentToActions() != null) {
//                for (SentToVO sentToVO : newsVO.getSentToActions()) {
//                    mAppDatabase.actedUserDao().insertActedUser(sentToVO.getSender());
//                    mAppDatabase.actedUserDao().insertActedUser(sentToVO.getReceiver());
//                    mAppDatabase.sendToDao().insertSendToById(newsVO.getNewsId(),
//                            sentToVO.getSender().getUserId(),
//                            sentToVO.getReceiver().getUserId(),
//                            sentToVO);
//                }
//            }
//
//            mAppDatabase.newsDao().insertNewsWithPublicationId(newsVO.getPublication().getPublicationId(), newsVO);
//            mAppDatabase.newsDao().insertNew(newsVO);
//        }
//
//        mmNewsPageIndex = event.getLoadedPageIndex() + 1;
//    }
}
