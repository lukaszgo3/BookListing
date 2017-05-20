package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

class BookAsyncTask extends AsyncTaskLoader<List<Books>> {

    /**
     * Query URL
     */
    private final String mUrl;

    /**
     * Constructs a new {@link BookAsyncTask}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    BookAsyncTask(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Books> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return Utils.booksLog(mUrl);
    }
}