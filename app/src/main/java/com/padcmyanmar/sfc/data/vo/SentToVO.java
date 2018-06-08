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

@Entity(tableName = "SentToAction",
        foreignKeys ={
                @ForeignKey(entity = NewsVO.class,
                        parentColumns = "newsId",
                        childColumns = "newsId"),
                @ForeignKey(entity = ActedUserVO.class,
                        parentColumns = "actedUserId",
                        childColumns = "senderUserId"),
                @ForeignKey(entity = ActedUserVO.class,
                        parentColumns = "actedUserId",
                        childColumns = "receiverUserId")})

public class SentToVO {

    @PrimaryKey
    @ColumnInfo(name = "sentToActionId")
    @SerializedName("send-to-id")
    private @NonNull String sendToId;

    @ColumnInfo(name = "sentDate")
    @SerializedName("sent-date")
    private String sentDate;

    @ColumnInfo(name = "newsId")
    private String newsId;

    @ColumnInfo(name = "senderUserId")
    private String senderUserId;

    @ColumnInfo(name = "receiverUserId")
    private String receiverUserId;

    @Ignore
    @SerializedName("acted-user")
    private ActedUserVO sender;

    @Ignore
    @SerializedName("received-user")
    private ActedUserVO receiver;

    public String getSendToId() {
        return sendToId;
    }

    public void setSendToId(String sendToId) {
        this.sendToId = sendToId;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }

    public String getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(String receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public ActedUserVO getSender() {
        return sender;
    }

    public void setSender(ActedUserVO sender) {
        this.sender = sender;
    }

    public ActedUserVO getReceiver() {
        return receiver;
    }

    public void setReceiver(ActedUserVO receiver) {
        this.receiver = receiver;
    }
}
