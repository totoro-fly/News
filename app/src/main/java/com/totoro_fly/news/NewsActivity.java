package com.totoro_fly.news;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
    NewsAdapter newsAdapter;
    @Bind(R.id.main_progress)
    ProgressBar mainProgress;
    private static final String NEWS_URL = "http://content.guardianapis.com/search?q=debates&api-key=test";
    private static final int NEWS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        newsList.setEmptyView(emptyTextview);
        newsAdapter = new NewsAdapter(this, new ArrayList<New.ResponseBean.ResultsBean>());
        newsList.setAdapter(newsAdapter);
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                New.ResponseBean.ResultsBean n = (New.ResponseBean.ResultsBean) newsAdapter.getItem(i);
                Uri uri = Uri.parse(n.getWebUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        } else {
            mainProgress.setVisibility(View.GONE);
            emptyTextview.setText("无网络");
        }

    }

    @Override
    public Loader<ArrayList<New.ResponseBean.ResultsBean>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, NEWS_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<New.ResponseBean.ResultsBean>> loader, ArrayList<New.ResponseBean.ResultsBean> data) {
        mainProgress.setVisibility(View.GONE);
        emptyTextview.setText("无数据");
        newsAdapter.clear();
        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<New.ResponseBean.ResultsBean>> loader) {
        newsAdapter.clear();
    }
}
