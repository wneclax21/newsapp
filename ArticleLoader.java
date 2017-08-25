package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by colli on 8/24/2017.
 */

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    private String mUrl;

    public ArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading(){forceLoad();}

    @Override
    public List<Article> loadInBackground() {
        if(mUrl == null) {
            return null;
        }

        List<Article> articles = QueryUtils.fetchArticleData(mUrl);
        return articles;
    }
}
