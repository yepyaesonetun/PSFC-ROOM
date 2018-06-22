package com.padcmyanmar.sfc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padcmyanmar.sfc.R;
import com.padcmyanmar.sfc.SFCNewsApp;
import com.padcmyanmar.sfc.adapters.NewsAdapter;
import com.padcmyanmar.sfc.adapters.NewsNextDesignAdapter;
import com.padcmyanmar.sfc.components.CustomNestedScrollView;
import com.padcmyanmar.sfc.components.EmptyViewPod;
import com.padcmyanmar.sfc.components.SmartRecyclerView;
import com.padcmyanmar.sfc.components.SmartScrollListener;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.mvp.presenters.NewsListPresenter;
import com.padcmyanmar.sfc.mvp.views.NewsListView;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yepyaesonetun on 6/21/18.
 **/

public class NewsListNextDesignActivity extends BaseActivity
        implements NewsListView {

    @BindView(R.id.nestedScrollView)
    CustomNestedScrollView nestedScrollView;

    @BindView(R.id.rv_news)
    SmartRecyclerView rvNews;

    @BindView(R.id.vp_empty_news)
    EmptyViewPod vpEmptyNews;

    @BindView(R.id.tv_head_line_news_title)
    TextView tvHeadLineNewsTitle;

    @BindView(R.id.tv_news_count)
    TextView tvNewsCount;

    @BindView(R.id.cv_head_line)
    CardView cvNewsHeadLine;

    @BindView(R.id.iv_head_line_publication_logo)
    ImageView ivHeadLinePublicationLogo;

    @BindView(R.id.tv_head_line_publication_name)
    TextView tvHeadLinePublicationName;

    @BindView(R.id.iv_news_head_line)
    ImageView ivNewsHeroImage;

    @BindView(R.id.view_headline)
    View viewHeadLine;

    private SmartScrollListener mSmartScrollListener;

    private NewsNextDesignAdapter mNewsAdapter;

    private NewsListPresenter mPresenter;

    private NewsVO headLineVO = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list_next_desin);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new NewsListPresenter(this);
        mPresenter.onCreate();

        rvNews.setNestedScrollingEnabled(false);

        rvNews.setEmptyView(vpEmptyNews);
        rvNews.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mNewsAdapter = new NewsNextDesignAdapter(getApplicationContext(), mPresenter);
        rvNews.setAdapter(mNewsAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReach() {
                //Snackbar.make(rvNews, "This is all the data for NOW.", Snackbar.LENGTH_LONG).show();
                //TODO load more data.
            }
        });

        rvNews.addOnScrollListener(mSmartScrollListener);

        viewHeadLine.setVisibility(headLineVO == null ? View.GONE : View.VISIBLE);

        cvNewsHeadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    launchNewsDetailScreen(headLineVO.getNewsId());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestory();
    }

    @Override
    public void displayErrorMsg(String errorMsg) {

    }

    @Override
    public void displayNewsList(List<NewsVO> newsVOList) {
        headLineVO = newsVOList.get(2);
        viewHeadLine.setVisibility(View.VISIBLE);
        tvNewsCount.setText(getString(R.string.head_line_news_count, newsVOList.size()));

        bindHeadLineNews(headLineVO);
        mNewsAdapter.appendNewData(newsVOList);
    }

    private void bindHeadLineNews(NewsVO newsVO) {
        tvHeadLineNewsTitle.setText(newsVO.getBrief());
        tvHeadLinePublicationName.setText(newsVO.getPublication().getTitle());
        if(!newsVO.getImages().isEmpty()){
            Glide.with(ivNewsHeroImage.getContext())
                    .load(newsVO.getImages().get(0))
                    .into(ivNewsHeroImage);
        }else {
            ivNewsHeroImage.setVisibility(View.GONE);
        }

        Glide.with(ivHeadLinePublicationLogo.getContext())
                .load(newsVO.getPublication().getLogo())
                .into(ivHeadLinePublicationLogo);
    }

    @Override
    public void displayCalculatedPrimeNumber(String value) {
        // do nothing at this moment
    }

    @Override
    public void launchNewsDetailScreen(String id) {
        Intent intent = NewsDetailsActivity.newIntent(this, id);
        startActivity(intent);
    }
}
