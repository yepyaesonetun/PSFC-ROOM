package com.padcmyanmar.sfc.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.padcmyanmar.sfc.R;
import com.padcmyanmar.sfc.adapters.NewsImagesPagerAdapter;
import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.PublicationVO;
import com.padcmyanmar.sfc.mvp.presenters.NewsDetailPresenter;
import com.padcmyanmar.sfc.mvp.views.NewsDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aung on 11/11/17.
 */

public class NewsDetailsActivity extends BaseActivity implements NewsDetailView {

    private static final String IE_NEWS_ID = "IE_NEWS_ID";

    @BindView(R.id.vp_news_details_images)
    ViewPager vpNewsDetailsImages;

    @BindView(R.id.iv_publication_logo)
    ImageView ivPublicationLogo;

    @BindView(R.id.tv_publication_name)
    TextView tvPublicationName;

    @BindView(R.id.tv_published_date)
    TextView tvPublishedDate;

    @BindView(R.id.tv_news_details)
    TextView tvNewsDetails;

    private NewsDetailPresenter mPresenter;

    /**
     * Create intent object to start NewsDetailsActivity.
     *
     * @param context
     * @param newsId  : id for news object.
     * @return
     */
    public static Intent newIntent(Context context, String newsId) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(IE_NEWS_ID, newsId);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this, this);

        mPresenter = new NewsDetailPresenter(this);
        mPresenter.onCreate();

        // set publisher name by ID
//        String pId = mNew.getPublication().getPublicationId();
//        PublicationVO publicationVO = newsModel.getPublication(pId);
//        tvPublicationName.setText(publicationVO.getTitle());
//
//        tvNewsDetails.setText(mNew.getDetails());
//        tvPublishedDate.setText(mNew.getPostedDate());

        NewsImagesPagerAdapter newsImagesPagerAdapter = new NewsImagesPagerAdapter(getApplicationContext());
        vpNewsDetailsImages.setAdapter(newsImagesPagerAdapter);

        String mNewsId = getIntent().getStringExtra(IE_NEWS_ID);
        mPresenter.onFinishUIComponentsSetUp(mNewsId);
    }

    @Override
    public void displayDetail(NewsVO news) {
        Glide.with(ivPublicationLogo.getContext())
                .load(news.getPublication().getLogo())
                .into(ivPublicationLogo);
        tvPublicationName.setText(news.getPublication().getTitle());
        tvPublishedDate.setText(news.getPostedDate());
        tvNewsDetails.setText(news.getDetails());
    }

    @Override
    public void displayErrorMsg(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }
}
