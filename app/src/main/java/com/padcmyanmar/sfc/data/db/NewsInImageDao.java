package com.padcmyanmar.sfc.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.padcmyanmar.sfc.data.vo.NewsInImageVO;
import com.padcmyanmar.sfc.data.vo.NewsVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yepyaesonetun on 6/8/18.
 **/

@Dao
public abstract class NewsInImageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insertNewsInImage(NewsInImageVO newsInImage);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertNewsInImages(List<NewsInImageVO> newsInImage);

    @Query("SELECT * FROM newsinimage")
    public abstract LiveData<List<NewsInImageVO>> getAllNewsInImages();

    @Query("SELECT * FROM newsinimage WHERE newsId =:newsId")
    public abstract List<NewsInImageVO> getImagesByNewsId(String newsId);

    @Query("DELETE FROM newsinimage")
    public abstract void deleteAll();

    public void insertImageWithNews(NewsVO newsVO) {
        List<NewsInImageVO> newsInImageVOs = new ArrayList<>();

        List<String> images = newsVO.getImages();
        for (int i = 0; i < images.size(); i++) {
            NewsInImageVO newsInImage = new NewsInImageVO();
            newsInImage.setNewsId(newsVO.getNewsId());
            newsInImage.setImageUrl(images.get(i));

            newsInImageVOs.add(newsInImage);
        }

        insertNewsInImages(newsInImageVOs);
    }

    public void bindImages(NewsVO newsVO) {
        List<String> images = new ArrayList<>();

        List<NewsInImageVO> newsInImageVOs = getImagesByNewsId(newsVO.getNewsId());
        for (NewsInImageVO newsInImage : newsInImageVOs) {
            images.add(newsInImage.getImageUrl());
        }

        newsVO.setImages(images);
    }
}
