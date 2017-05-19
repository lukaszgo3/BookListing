package com.example.android.booklisting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class BooksAdapter extends RecyclerView.Adapter {

    private ArrayList<Books> mBooks = new ArrayList<>();

    private class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tittle;
        TextView author;

        ViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.imageId);
            tittle = (TextView) view.findViewById(R.id.tittleId);
            author = (TextView) view.findViewById(R.id.authorId);
        }
    }

    public BooksAdapter(ArrayList<Books> books) {
        this.mBooks = books;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listBooks = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);


        return new ViewHolder(listBooks);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Books books = mBooks.get(position);
        ((ViewHolder)holder).image.setImageResource(books.getmImage());
        ((ViewHolder)holder).tittle.setText(books.getmTittle());
        ((ViewHolder)holder).author.setText(books.getmAuthor());


    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }
}