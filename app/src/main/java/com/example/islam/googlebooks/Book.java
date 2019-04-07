/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.islam.googlebooks;

// class Book contain information related to single book
public class Book {


    // Title of the book
    private String mTitle;

    // publish date of the book
    private String mPublisheDate;

    // authors of the book
    private String mAuthors;

    // number pages of the book
    private String mPageCount;

    // image of the book
    private String mBookImageLink;

    // Website URL of the book
    private String mBookUrl;

    /**
     * Constructs a new Book object.
     *
     * @param title is the title of the Book
     * @param authors is the authors created the Book
     * @param publisherDate is the date of published Book
     * @param pageCount is number of pages of the Book
     * @param bookImageLink is link image of book
     * @param bookUrl is the website URL to read the Book
     */
    public Book(String title, String authors, String publisherDate, String pageCount, String bookImageLink, String bookUrl) {
        mTitle = title;
        mAuthors = authors;
        mPublisheDate = publisherDate;
        mPageCount = pageCount;
        mBookImageLink = bookImageLink;
        mBookUrl = bookUrl;
    }

    // Returns title of the Book.
    public String getTitle() {
        return mTitle;
    }

    // Returns author of the Book.
    public String getAuthor(){return mAuthors;}

    // Returns publisher date of the Book.
    public String getPublisheDate() {return mPublisheDate;}

    // Returns number pages of the book
    public String getPageCount(){ return mPageCount; }

    // Returns url of the Book.
    public String getUrl(){return mBookUrl; }

    // Returns image of the Book.
    public String getImageLinks(){return mBookImageLink;}

}
