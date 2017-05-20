package com.example.android.booklisting;

import java.util.ArrayList;

class Books {

    private String mImage;
    private String mTittle;
    private ArrayList<String> mAuthor;
    private String mDescription;

    Books(String image, String tittle, ArrayList<String> author, String description) {

        mImage = image;
        mTittle = tittle;
        mAuthor = author;
        mDescription = description;
    }

    String getmImage() {
        return mImage;
    }

    String getmTittle() {
        return mTittle;
    }

    String getmAuthor() {
        return Authors();
    }

    String getmDescription() {
        return mDescription;
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

