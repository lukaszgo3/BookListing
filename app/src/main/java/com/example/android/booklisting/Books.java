package com.example.android.booklisting;

import java.util.ArrayList;

class Books {

    private String mImage;
    private String mTittle;
    private String mpublishedDate;
    private ArrayList<String> mAuthor;

    Books(String image, String tittle, String publishedDate, ArrayList<String> author) {

        mImage = image;
        mTittle = tittle;
        mpublishedDate = publishedDate;
        mAuthor = author;
    }

    String getmImage() {
        return mImage;
    }

    String getmTittle() {
        return mTittle;
    }

    String getMpublishedDate() {
        return mpublishedDate;
    }

    String getmAuthor() {
        return Authors();
    }

    private String Authors() {
        String authors = mAuthor.get(0);
        if (mAuthor.size() > 1) {
            for (int j = 1; j < mAuthor.size(); j++) {
                authors += mAuthor.get(j) + "\n";
            }
        }
        return authors;
    }
}

