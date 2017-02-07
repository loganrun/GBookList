package com.example.android.gbooklist;

/**
 * Created by loganrun on 2/6/17.
 */
public class Book {
    private String mTitle;
    private String mAuthor;
    private String mDate;


    public Book(String title, String author, String date){

        mTitle = title;
        mAuthor = author;
        mDate = date;
    }

    public String getTitle(){ return mTitle;}

    public String getAuthor() {return mAuthor;}

    public  String getDate() {return mDate;}
}
