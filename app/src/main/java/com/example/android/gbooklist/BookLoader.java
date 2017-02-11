package com.example.android.gbooklist;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by loganrun on 2/6/17.
 */
public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String mUrl;
    private static final String LOG_TAG = BookLoader.class.getSimpleName();


    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
        Log.i(LOG_TAG, "This is the url " + mUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Book> books = SearchUtils.fetchBookData(mUrl);
        return books;
    }
}