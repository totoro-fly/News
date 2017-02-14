package com.totoro_fly.news;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by totoro-fly on 2017/2/11.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<New.ResponseBean.ResultsBean>> {
    private static final String TAG = NewsLoader.class.getSimpleName();
    private String stringUrl;

    public NewsLoader(Context context, String stringUrl) {
        super(context);
        this.stringUrl = stringUrl;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        onForceLoad();
    }

    @Override
    public ArrayList<New.ResponseBean.ResultsBean> loadInBackground() {
        if (stringUrl == null)
            return null;
        ArrayList list = QueryUtils.fetchNew(stringUrl);
        return list;
    }
}
