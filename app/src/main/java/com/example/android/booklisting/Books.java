package com.example.android.booklisting;

public class Books {

    private int mImage;
    private String mTittle;
    private String mAuthor;

    public Books(int image, String tittle, String author){

        mImage = image;
        mTittle = tittle;
        mAuthor = author;
    }

    public int getmImage() {
        return mImage;
    }

    public String getmTittle() {
        return mTittle;
    }

    public String getmAuthor() {
        return mAuthor;
    }
}

