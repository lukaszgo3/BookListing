package com.example.android.booklisting;

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

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final Books books = mBooks.get(position);

        Picasso.with(mContext).load(books.getmImage()).into(viewHolder.image);
        viewHolder.tittle.setText(books.getmTittle());
        viewHolder.author.setText(books.getmAuthor());
        viewHolder.tittle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mActivity.showDetailsDialog(books.getmDescription());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tittle;
        TextView author;

        ViewHolder(View view) {
            super(view);

            this.image = (ImageView) view.findViewById(R.id.imageId);
            tittle = (TextView) view.findViewById(R.id.tittleId);
            author = (TextView) view.findViewById(R.id.authorId);
        }
    }
}