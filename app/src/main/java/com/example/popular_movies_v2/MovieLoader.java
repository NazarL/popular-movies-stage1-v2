package com.example.popular_movies_v2;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private final String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return QueryUtils.fetchMoviesData(mUrl);
    }

}
