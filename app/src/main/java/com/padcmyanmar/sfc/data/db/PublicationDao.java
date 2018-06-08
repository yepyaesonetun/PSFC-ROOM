package com.padcmyanmar.sfc.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.padcmyanmar.sfc.data.vo.PublicationVO;

import java.util.List;

/**
 * Created by yepyaesonetun on 6/8/18.
 **/

@Dao
public interface PublicationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertPublication(PublicationVO publication);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertPublications(PublicationVO... publications);

    @Query("SELECT * FROM publication")
    LiveData<List<PublicationVO>> getAllPublications();

    @Query("SELECT * FROM publication WHERE publicationId =:id")
    public abstract PublicationVO getPublicationById(String id);

    @Query("DELETE FROM publication")
    void deleteAll();

}