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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Books>> {

    private static final String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final int BOOK_ID = 1;

    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView recyclerView;
    private boolean clicked;
    ProgressBar spinner;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clicked = false;
        recyclerAdapter = new BooksAdapter(MainActivity.this, new ArrayList<Books>());
        recyclerView = (RecyclerView) findViewById(R.id.list_books);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(recyclerAdapter);

        editText = (EditText) findViewById(R.id.editId);
        spinner = (ProgressBar) findViewById(R.id.spiner);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(BOOK_ID, null, this);

        //Search button/lens
        ImageView search = (ImageView) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                clicked = true;

                if (activeNetwork != null && activeNetwork.isConnected()) {

                    if (editText.getText().toString().equals("")) {
                        Toast.makeText(MainActivity.this, getString(R.string.Test1), Toast.LENGTH_SHORT).show();
                    }

                    getLoaderManager().restartLoader(BOOK_ID, null, MainActivity.this);
                    if (v != null) {
                        InputMethodManager iMn = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        iMn.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    spinner.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, R.string.noInternet, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public Loader<ArrayList<Books>> onCreateLoader(int id, Bundle args) {

        recyclerView.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);

        String editBooks = editText.getText().toString();
        try {
            editBooks = URLEncoder.encode(editBooks, "UTF-8");
        } catch (IOException e) {
            editBooks = "";
        }
        String url = GOOGLE_BOOKS_URL + editBooks + "&maxResults=15";

        return new BookLoad(MainActivity.this, url);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Books>> loader, ArrayList<Books> books) {

        spinner.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        if (books != null && !books.isEmpty()) {
            recyclerAdapter = new BooksAdapter(MainActivity.this, books);
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            if (clicked) {
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