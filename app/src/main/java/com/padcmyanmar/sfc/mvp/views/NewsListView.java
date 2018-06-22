package com.padcmyanmar.sfc.mvp.views;

import com.padcmyanmar.sfc.data.vo.NewsVO;

import java.util.List;

/**
 * Created by yepyaesonetun on 6/18/18.
 **/

public interface NewsListView extends BaseView{

    void displayNewsList(List<NewsVO> newsVOList);

    void displayCalculatedPrimeNumber(String value);

    void launchNewsDetailScreen(String id);
}
