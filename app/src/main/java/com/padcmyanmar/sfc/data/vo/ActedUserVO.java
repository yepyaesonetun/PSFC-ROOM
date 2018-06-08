package com.padcmyanmar.sfc.data.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 12/3/17.
 */

@Entity(tableName = "AcedUser")
public class ActedUserVO {

    @PrimaryKey
    @ColumnInfo(name = "actedUserId")
    @SerializedName("user-id")
    private @NonNull String userId;

    @ColumnInfo(name = "userName")
    @SerializedName("user-name")
    private String userName;

    @ColumnInfo(name = "profileImage")
    @SerializedName("profile-image")
    private String profileImage;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
