package com.padcmyanmar.sfc.mvp.presenters;

import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.mvp.views.NewsDetailView;

/**
 * Created by yepyaesonetun on 6/18/18.
 **/

public class NewsDetailPresenter extends BasePresenter<NewsDetailView>{

    public NewsDetailPresenter(NewsDetailView mView) {
        super(mView);
    }

    public void onFinishUIComponentsSetUp(String newsId){
        NewsVO newsVO = NewsModel.getInstance().getNewsById(newsId);
        mView.displayDetail(newsVO);
    }

}
