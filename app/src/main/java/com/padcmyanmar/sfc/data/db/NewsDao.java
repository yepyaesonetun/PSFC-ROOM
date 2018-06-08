package com.padcmyanmar.sfc.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;

import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.PublicationVO;

import java.util.List;

/**
 * Created by yepyaesonetun on 6/4/18.
 **/
@Dao
public abstract class NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insertNew(NewsVO newsVO);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertNews(NewsVO... newsVOSs);

    @Query("SELECT * FROM news")
    public abstract LiveData<List<NewsVO>> getAllNews();

    @Query("DELETE FROM news")
    public abstract void deleteAllNews();

    @Query("SELECT * FROM News WHERE newsId = :newsId")
    public abstract NewsVO getNew(String newsId);

    public void insertNewsWithPubId(String publicationId, NewsVO newsVO) {
        newsVO.setPublicationId(publicationId);
        insertNews(newsVO);
    }

    @Query("SELECT * FROM News n JOIN Publication p ON n.publicationId = :publicationId")
    public abstract PublicationVO getPublication(String publicationId);
}
