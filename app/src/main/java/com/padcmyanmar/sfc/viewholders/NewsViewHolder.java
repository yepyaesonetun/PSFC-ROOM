package com.padcmyanmar.sfc.viewholders;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padcmyanmar.sfc.R;
import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.PublicationVO;
import com.padcmyanmar.sfc.delegates.NewsItemDelegate;
import com.padcmyanmar.sfc.events.RestApiEvents;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by aung on 11/4/17.
 */

public class NewsViewHolder extends BaseViewHolder<NewsVO> {

    @BindView(R.id.iv_publication_logo)
    ImageView ivPublicationLogo;

    @BindView(R.id.tv_publication_name)
    TextView tvPublicationName;

    @BindView(R.id.tv_published_date)
    TextView tvPublishedDate;

    @BindView(R.id.tv_brief_news)
    TextView tvBriefNews;

    @BindView(R.id.iv_news_hero_image)
    ImageView ivNewsHeroImage;

    @BindView(R.id.tv_news_statistical_data)
    TextView tvNewsStatisticalData;

    private NewsItemDelegate mDelegate;


    public NewsViewHolder(View itemView, NewsItemDelegate newsItemDelegate) {
        super(itemView);
        mDelegate = newsItemDelegate;
    }

    @Override
    public void setData(NewsVO data) {
        mData = data;

//        PublicationVO publicationVO = newsModel.getPublication(data.getNewsId());
//
//        tvPublicationName.setText(publicationVO.getTitle());

//        PublicationVO publicationVO = NewsModel.getInstance().getPublication(data.getPublicationId());
//        tvPublicationName.setText(publicationVO.getTitle());

        tvPublishedDate.setText(data.getPostedDate());
        tvBriefNews.setText(data.getBrief());
//        if (!data.getImages().isEmpty()) {
//            ivNewsHeroImage.setVisibility(View.VISIBLE);
//            Glide.with(itemView.getContext())
//                    .load(data.getImages().get(0))
//                    .into(ivNewsHeroImage);
//        } else {
//            ivNewsHeroImage.setVisibility(View.GONE);
//        }

//        String statisticalData = itemView.getContext().getString(R.string.format_news_statistical_data,
//                data.getFavoriteActions().size(),
//                data.getCommentActions().size(),
//                data.getSentToActions().size());
//        tvNewsStatisticalData.setText(statisticalData);
    }

    @Override
    public void onClick(View v) {
        mDelegate.onTapNews(mData);

        //EventBus.getDefault().post(new TapNewsEvent("news-id"));
        //EventBus.getDefault().post(new RestApiEvents.EmptyResponseEvent());
    }

    @OnClick(R.id.btn_comment_news)
    public void onTapComments(View view) {
        mDelegate.onTapComment();
    }
}
