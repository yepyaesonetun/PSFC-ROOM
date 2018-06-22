package com.padcmyanmar.sfc.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padcmyanmar.sfc.R;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.delegates.NewsItemDelegate;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by aung on 11/4/17.
 */

public class NewsNextDesignViewHolder extends BaseViewHolder<NewsVO> {

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


    private NewsItemDelegate mDelegate;


    public NewsNextDesignViewHolder(View itemView, NewsItemDelegate newsItemDelegate) {
        super(itemView);
        mDelegate = newsItemDelegate;
    }

    @Override
    public void setData(NewsVO data) {
        mData = data;

        if(!data.getImages().isEmpty()){
            Glide.with(ivNewsHeroImage.getContext())
                    .load(data.getImages().get(0))
                    .into(ivNewsHeroImage);
        }else {
            ivNewsHeroImage.setVisibility(View.GONE);
        }

        Glide.with(ivPublicationLogo.getContext())
                .load(data.getPublication().getLogo())
                .into(ivPublicationLogo);

        tvPublishedDate.setText(data.getPostedDate());
        tvBriefNews.setText(data.getBrief());
    }

    @Override
    public void onClick(View v) {
        mDelegate.onTapNews(mData);

        //EventBus.getDefault().post(new TapNewsEvent("news-id"));
        //EventBus.getDefault().post(new RestApiEvents.EmptyResponseEvent());
    }

//    @OnClick(R.id.btn_comment_news)
//    public void onTapComments(View view) {
//        mDelegate.onTapComment();
//    }
}
