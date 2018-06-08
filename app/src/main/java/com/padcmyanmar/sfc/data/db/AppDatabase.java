package com.padcmyanmar.sfc.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.padcmyanmar.sfc.data.vo.ActedUserVO;
import com.padcmyanmar.sfc.data.vo.CommentActionVO;
import com.padcmyanmar.sfc.data.vo.FavoriteActionVO;
import com.padcmyanmar.sfc.data.vo.NewsInImageVO;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.PublicationVO;
import com.padcmyanmar.sfc.data.vo.SentToVO;

/**
 * Created by yepyaesonetun on 6/4/18.
 **/

@Database(entities = {NewsVO.class,
        ActedUserVO.class,
        CommentActionVO.class,
        NewsInImageVO.class,
        FavoriteActionVO.class,
        PublicationVO.class,
        SentToVO.class},
        version = 7)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "PADC-SFC-NEWS.DB";

    private static AppDatabase INSTANCE;

    // TODO: define DAOs
    public abstract NewsDao newsDao();

    public abstract NewsInImageDao newsInImageDao();

    public abstract PublicationDao publicationDao();

    public abstract ActedUserDao actedUserDao();

    public abstract CommentActionDao commentActionDao();

    public abstract FavoriteActionDao favoriteActionDao();

    public abstract SendToDao sendToDao();

    public static AppDatabase getNewsDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()   //Remove this after testing. Access to DB should always be from background thread.
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destoryInstance() {
        INSTANCE = null;
    }
}
