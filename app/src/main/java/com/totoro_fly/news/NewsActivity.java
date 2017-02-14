package com.totoro_fly.news;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<New.ResponseBean.ResultsBean>> {

    @Bind(R.id.news_list)
    ListView newsList;
    @Bind(R.id.empty_textview)
    TextView emptyTextview;
    @Bind(R.id.activity_news)
    LinearLayout activityNews;
    private static final String NEWS_URL = "http://content.guardianapis.com/search?q=debates&api-key=test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
    }

    @Override
    public Loader<ArrayList<New.ResponseBean.ResultsBean>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, NEWS_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<New.ResponseBean.ResultsBean>> loader, ArrayList<New.ResponseBean.ResultsBean> data) {
        if (data == null)
            return;
        
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<New.ResponseBean.ResultsBean>> loader) {

    }
}
