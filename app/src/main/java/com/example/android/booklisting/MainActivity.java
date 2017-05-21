package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Books>> {

    private static final String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final int BOOK_ID = 1;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private boolean isClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isClicked = false;

        recyclerView = (RecyclerView) findViewById(R.id.list_books);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BooksAdapter(MainActivity.this, new ArrayList<Books>()));

        final EditText searchView = (EditText) findViewById(R.id.editId);
        if (!TextUtils.isEmpty(searchView.getText().toString().trim())) {
            infoUp();
        }

        ProgressBar spinner = (ProgressBar) findViewById(R.id.spiner);
        spinner.setVisibility(View.GONE);

        final ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Search button/lens
        ImageView search = (ImageView) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isClicked = true;

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                final boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    recyclerAdapter = new BooksAdapter(MainActivity.this, new ArrayList<Books>());
                    recyclerView.setAdapter(recyclerAdapter);

                    if (!TextUtils.isEmpty(searchView.getText().toString().trim())) {
                        infoUp();
                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.Test1), Toast.LENGTH_SHORT).show();
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    ProgressBar spinner = (ProgressBar) findViewById(R.id.spiner);
                    spinner.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, R.string.noInternet, Toast.LENGTH_LONG).show();
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
    public Loader<ArrayList<Books>> onCreateLoader(int id, Bundle args) {
        recyclerView.setVisibility(View.GONE);

        ProgressBar loadingSpinner = (ProgressBar) findViewById(R.id.spiner);
        loadingSpinner.setVisibility(View.VISIBLE);
        return new BookAsyncTask(getApplicationContext(), args.getString("Uri"));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Books>> loader, ArrayList<Books> books) {

        ProgressBar spiner= (ProgressBar) findViewById(R.id.spiner);
        spiner.setVisibility(View.GONE);

        recyclerView.setVisibility(View.VISIBLE);
        recyclerAdapter = new BooksAdapter(MainActivity.this, new ArrayList<Books>());
        if (books != null && !books.isEmpty()) {
            recyclerAdapter = new BooksAdapter(MainActivity.this, books);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            if (isClicked) {
                Toast.makeText(MainActivity.this, "Book " +
                        "Not Found!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Books>> loader) {
        recyclerAdapter = new BooksAdapter(MainActivity.this, new ArrayList<Books>());
    }
}