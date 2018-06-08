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

import com.padcmyanmar.sfc.R;
import com.padcmyanmar.sfc.adapters.NewsImagesPagerAdapter;
import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.PublicationVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aung on 11/11/17.
 */

public class NewsDetailsActivity extends BaseActivity {

    private static final String IE_NEWS_ID = "IE_NEWS_ID";
    private String mNewsId;

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

    private NewsModel newsModel;

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

        mNewsId = getIntent().getStringExtra(IE_NEWS_ID);

        newsModel  = ViewModelProviders.of(this).get(NewsModel.class);
        newsModel.initDatabase(this);
        NewsVO mNew = newsModel.getNew(mNewsId);

        // set publisher name by ID
//        String pId = mNew.getPublication().getPublicationId();
//        PublicationVO publicationVO = newsModel.getPublication(pId);
//        tvPublicationName.setText(publicationVO.getTitle());

        tvNewsDetails.setText(mNew.getDetails());
        tvPublishedDate.setText(mNew.getPostedDate());


        NewsImagesPagerAdapter newsImagesPagerAdapter = new NewsImagesPagerAdapter(getApplicationContext());
        vpNewsDetailsImages.setAdapter(newsImagesPagerAdapter);


    }
}
