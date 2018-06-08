package com.padcmyanmar.sfc.data.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 12/3/17.
 */

@Entity(tableName = "FavoriteAction",
        foreignKeys = {
                @ForeignKey(entity = NewsVO.class,
                        parentColumns = "newsId",
                        childColumns = "newsId"),
                @ForeignKey(entity = ActedUserVO.class,
                        parentColumns = "actedUserId",
                        childColumns = "actedUserId")
        })
public class FavoriteActionVO {

    @PrimaryKey
    @ColumnInfo(name = "favoriteActionId")
    @SerializedName("favorite-id")
    private @NonNull String favoriteId;

    @ColumnInfo(name = "favoriteDate")
    @SerializedName("favorite-date")
    private String favoriteDate;

    @Ignore
    @SerializedName("acted-user")
    private ActedUserVO actedUser;

    @ColumnInfo(name = "newsId")
    private String newsId;

    @ColumnInfo(name = "actedUserId")
    private String userId;

    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getFavoriteDate() {
        return favoriteDate;
    }

    public void setFavoriteDate(String favoriteDate) {
        this.favoriteDate = favoriteDate;
    }

    public ActedUserVO getActedUser() {
        return actedUser;
    }

    public void setActedUser(ActedUserVO actedUser) {
        this.actedUser = actedUser;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
