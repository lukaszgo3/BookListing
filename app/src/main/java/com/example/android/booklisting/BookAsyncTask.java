package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

class BookAsyncTask extends AsyncTaskLoader<List<Books>> {

    private final String mUrl;

    BookAsyncTask(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Books> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return Utils.booksLog(mUrl);
    }
}