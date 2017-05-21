package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Books>> {

    private static final String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final int BOOK_ID = 1;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private boolean isClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isClick = false;

        recyclerView = (RecyclerView) findViewById(R.id.list_books);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BooksAdapter(MainActivity.this, new ArrayList<Books>()));

        final TextView state;
        state = (TextView) findViewById(R.id.state_text_view);
        state.setText(getString(R.string.Test1));
        state.setVisibility(View.VISIBLE);

        final EditText searchView = (EditText) findViewById(R.id.editId);
        if (!TextUtils.isEmpty(searchView.getText().toString().trim())) {
            infoUp();
        }

        ProgressBar loadingSpinner = (ProgressBar) findViewById(R.id.progress_bar);
        loadingSpinner.setVisibility(View.GONE);

        final ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        ImageView search = (ImageView) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isClick = true;

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                final boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();


                if (isConnected) {

                    state.setVisibility(View.GONE);
                    state.setText(getString(R.string.Test1));
                    recyclerAdapter = new BooksAdapter(MainActivity.this, new ArrayList<Books>());

                    recyclerView.setAdapter(recyclerAdapter);

                    if (!TextUtils.isEmpty(searchView.getText().toString().trim())) {
                        infoUp();
                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.Test1), Toast.LENGTH_SHORT).show();
                        state.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    ProgressBar loadingSpinner = (ProgressBar) findViewById(R.id.progress_bar);
                    loadingSpinner.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);

                    state.setText(getString(R.string.Test1));
                    state.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void infoUp() {
        EditText bookName = (EditText) findViewById(R.id.editId);
        String title = bookName.getText().toString();
        title = title.replace(" ", "+");
        String uriString = GOOGLE_BOOKS_URL + title;
        Bundle args = new Bundle();
        args.putString("Uri", uriString);
        android.app.LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(BOOK_ID, args, MainActivity.this);
        if (loaderManager.getLoader(BOOK_ID).isStarted()) {
            //restart it if there's one
            getLoaderManager().restartLoader(BOOK_ID, args, MainActivity.this);
        }
    }

    @Override
    public android.content.Loader<List<Books>> onCreateLoader(int id, Bundle args) {
        recyclerView.setVisibility(View.GONE);

        ProgressBar loadingSpinner = (ProgressBar) findViewById(R.id.progress_bar);
        loadingSpinner.setVisibility(View.VISIBLE);
        return new BookAsyncTask(this, args.getString("Uri"));
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<Books>> loader, List<Books> books) {

        ProgressBar loadingSpinner = (ProgressBar) findViewById(R.id.progress_bar);
        loadingSpinner.setVisibility(View.GONE);

        recyclerView.setVisibility(View.VISIBLE);
        recyclerAdapter = new BooksAdapter(MainActivity.this, new ArrayList<Books>());
        if (books != null && !books.isEmpty()) {
            recyclerAdapter = new BooksAdapter(MainActivity.this, (ArrayList<Books>) books);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            if (isClick) {
                Toast.makeText(MainActivity.this, "Not Found!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<Books>> loader) {
        recyclerAdapter = new BooksAdapter(MainActivity.this, new ArrayList<Books>());
    }

}

