package com.padcmyanmar.sfc.data.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.padcmyanmar.sfc.data.db.AttractionImagesTypeConverter;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by aung on 12/2/17.
 */

@Entity(tableName = "News",
        foreignKeys ={
        @ForeignKey(entity = PublicationVO.class,
                parentColumns = "publicationId",
                childColumns = "publicationId",onDelete = CASCADE)})
@TypeConverters(AttractionImagesTypeConverter.class)
public class NewsVO {

    @PrimaryKey
    @ColumnInfo(name = "newsId")
    @SerializedName("news-id")
    private @NonNull
    String newsId;

    @SerializedName("brief")
    private String brief;

    @SerializedName("details")
    private String details;

    @Ignore
    @SerializedName("images")
    private List<String> images;

    @ColumnInfo(name = "postedDate")
    @SerializedName("posted-date")
    private String postedDate;

    @Ignore
    @SerializedName("publication")
    private PublicationVO publication;

    @ColumnInfo(name = "publicationId")
    private String publicationId;

    @Ignore
    @SerializedName("favorites")
    private List<FavoriteActionVO> favoriteActions;

    @Ignore
    @SerializedName("comments")
    private List<CommentActionVO> commentActions;

    @Ignore
    @SerializedName("sent-tos")
    private List<SentToVO> sentToActions;

    public NewsVO(@NonNull String newsId, String brief, String details, String publicationId) {
        this.newsId = newsId;
        this.brief = brief;
        this.details = details;
        this.publicationId = publicationId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<String> getImages() {
        if(images == null){
            return new ArrayList<>();
        }
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public PublicationVO getPublication() {
        return publication;
    }

    public void setPublication(PublicationVO publication) {
        this.publication = publication;
    }

    public String getPublicationId() {
        if(publication != null){
            return publication.getPublicationId();
        }
        return null;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public List<FavoriteActionVO> getFavoriteActions() {
        return favoriteActions;
    }

    public void setFavoriteActions(List<FavoriteActionVO> favoriteActions) {
        this.favoriteActions = favoriteActions;
    }

    public List<CommentActionVO> getCommentActions() {
        return commentActions;
    }

    public void setCommentActions(List<CommentActionVO> commentActions) {
        this.commentActions = commentActions;
    }

    public List<SentToVO> getSentToActions() {
        return sentToActions;
    }

    public void setSentToActions(List<SentToVO> sentToActions) {
        this.sentToActions = sentToActions;
    }
}
