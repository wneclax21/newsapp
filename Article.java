package com.example.android.newsapp;

/**
 * Created by colli on 8/24/2017.
 */

public class Article {

    private String mTitle;

    private String mSection;

    private String mAuthor;

    private String mDate;

    public Article (String Title, String Section, String Author, String Date){
        mTitle = Title;
        mSection = Section;
        mAuthor = Author;
        mDate = Date;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmDate() {
        return mDate;
    }
}
