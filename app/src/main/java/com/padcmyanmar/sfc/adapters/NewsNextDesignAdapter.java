package com.padcmyanmar.sfc.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.padcmyanmar.sfc.R;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.delegates.NewsItemDelegate;
import com.padcmyanmar.sfc.viewholders.NewsNextDesignViewHolder;
import com.padcmyanmar.sfc.viewholders.NewsViewHolder;

/**
 * Created by yepyaesonetun on 6/22/18.
 **/

public class NewsNextDesignAdapter extends BaseRecyclerAdapter<NewsNextDesignViewHolder, NewsVO> {

    private NewsItemDelegate mNewsItemDelegate;

    public NewsNextDesignAdapter(Context context, NewsItemDelegate newsItemDelegate) {
        super(context);
        mNewsItemDelegate = newsItemDelegate;
    }

    @NonNull
    @Override
    public NewsNextDesignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newsItemView = mLayoutInflator.inflate(R.layout.view_item_news_next_design, parent, false);
        return new NewsNextDesignViewHolder(newsItemView, mNewsItemDelegate);
    }
}
