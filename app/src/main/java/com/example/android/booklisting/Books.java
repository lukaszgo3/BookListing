package com.example.android.booklisting;

import java.util.ArrayList;

public class Books {

    private String mImage;
    private String mTittle;
    private ArrayList<String> mAuthor;
    private String mDescription;

    public Books(String image, String tittle, ArrayList<String> author, String description) {

        mImage = image;
        mTittle = tittle;
        mAuthor = author;
        mDescription = description;
    }

    public String getmImage() {
        return mImage;
    }

    public String getmTittle() {
        return mTittle;
    }

    public String getmAuthor() {
        String authors = Authors();
        return authors;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String Authors() {
        String authors = mAuthor.get(0);
        if (mAuthor.size() > 1) {
            for (int j=1; j < mAuthor.size(); j++){
                authors += mAuthor.get(j) + "\n";
            }
        }
        return authors;
    }
}

