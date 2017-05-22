package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

class BookLoad extends AsyncTaskLoader<ArrayList<Books>> {

    private final String mUrl;

    BookLoad(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Books> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return Utils.booksLog(getContext(), mUrl);
    }
}