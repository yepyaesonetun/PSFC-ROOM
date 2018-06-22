package com.padcmyanmar.sfc.mvp.presenters;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.delegates.NewsItemDelegate;
import com.padcmyanmar.sfc.events.RestApiEvents;
import com.padcmyanmar.sfc.mvp.views.BaseView;
import com.padcmyanmar.sfc.mvp.views.NewsListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by yepyaesonetun on 6/18/18.
 **/

public class NewsListPresenter extends BasePresenter<NewsListView>
        implements NewsItemDelegate {

    private PublishSubject<List<NewsVO>> mNewsSubject;

    public NewsListPresenter(NewsListView view) {
        super(view);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onCreate() {
        // create subject and data load
        super.onCreate();
        mNewsSubject = PublishSubject.create();
        mNewsSubject.subscribeActual(new Observer<List<NewsVO>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<NewsVO> newsVOList) {
                mView.displayNewsList(newsVOList);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        });

        NewsModel.getInstance().startLoadingMMNews(mNewsSubject);

        decidePrimeNumberProcess();
    }

    /**
     * calculate Prime Number as assignment phase 3
     **/
    private void decidePrimeNumberProcess() {
        Single<String> primeSingleObservable = Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                int[] dummyData = new int[]{2, 3, 5, 7, 8, 9, 10, 12, 15, 16, 20, 35};
                return calculatePrime(dummyData);
            }
        });
        primeSingleObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        mView.displayCalculatedPrimeNumber(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.displayErrorMsg(e.getMessage());
                    }
                });

    }

    @SuppressLint("DefaultLocale")
    private String calculatePrime(int... dummyData) {
        String primeNumbers = "";
        for (int number : dummyData) {
            if (number == 2 || isPrime(number)) {
                primeNumbers = String.format("%s%d, ", primeNumbers, number);
            }
        }

        if (!TextUtils.isEmpty(primeNumbers) && primeNumbers.contains(",")) {
            primeNumbers = primeNumbers.substring(0, primeNumbers.lastIndexOf(", "));
        }

        return primeNumbers;
    }

    private boolean isPrime(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    /**
     * calculate Prime Number as assignment phase 3
     **/


    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
        if (event.getLoadNews() == null) {
            mView.displayErrorMsg("Empty Data");
        } else {
            mView.displayNewsList(event.getLoadNews());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokingAPI(RestApiEvents.ErrorInvokingAPIEvent event) {
        mView.displayErrorMsg(event.getErrorMsg());
    }

    @Override
    public void onTapComment() {

    }

    @Override
    public void onTapSendTo() {

    }

    @Override
    public void onTapFavorite() {

    }

    @Override
    public void onTapStatistics() {

    }

    @Override
    public void onTapNews(NewsVO vo) {
        mView.launchNewsDetailScreen(vo.getNewsId());
    }
}
