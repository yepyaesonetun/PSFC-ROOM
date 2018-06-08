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

@Entity(tableName = "CommentAction",
        foreignKeys = {
                @ForeignKey(entity = NewsVO.class,
                        parentColumns = "newsId",
                        childColumns = "newsId"),
                @ForeignKey(entity = ActedUserVO.class,
                        parentColumns = "actedUserId",
                        childColumns = "actedUserId")
        })
public class CommentActionVO {

    @PrimaryKey
    @ColumnInfo(name = "commentActionId")
    @SerializedName("comment-id")
    private @NonNull  String commentId;

    @ColumnInfo(name = "comment")
    @SerializedName("comment")
    private String comment;

    @ColumnInfo(name = "commentDate")
    @SerializedName("comment-date")
    private String commentDate;

    @Ignore
    @SerializedName("acted-user")
    private ActedUserVO actedUser;

    @ColumnInfo(name = "actedUserId")
    private String userId;

    @ColumnInfo(name = "newsId")
    private String newsId;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public ActedUserVO getActedUser() {
        return actedUser;
    }

    public void setActedUser(ActedUserVO actedUser) {
        this.actedUser = actedUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
}
