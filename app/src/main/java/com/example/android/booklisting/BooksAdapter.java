package com.example.android.booklisting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private final static String DATE = "Date: ";
    private final static String PAGES = "Pages: ";

    private ArrayList<Books> mBooks = new ArrayList<>();
    private Context mContext;
    private MainActivity mActivity;

    BooksAdapter(Context context, ArrayList<Books> books) {

        this.mBooks = books;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listBooks = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);

        return new ViewHolder(listBooks);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final Books books = mBooks.get(position);

        Picasso.with(mContext).load(books.getmImage()).into(viewHolder.image);
        viewHolder.tittle.setText(books.getmTittle());
        viewHolder.author.setText(books.getmAuthor());
        viewHolder.pages.setText(PAGES + books.getmPages());
        viewHolder.publishedDate.setText(DATE + books.getMpublishedDate());
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tittle;
        TextView author;
        TextView pages;
        TextView publishedDate;


        ViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.imageId);
            tittle = (TextView) view.findViewById(R.id.tittleId);
            author = (TextView) view.findViewById(R.id.authorId);
            pages = (TextView) view.findViewById(R.id.pagesId);
            publishedDate = (TextView) view.findViewById(R.id.publishedDateId);
        }
    }
}