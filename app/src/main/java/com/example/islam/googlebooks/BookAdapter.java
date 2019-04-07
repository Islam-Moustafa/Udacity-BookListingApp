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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

/**
 * An {@link BookAdapter} knows how to create a list item layout for each book
 * in the data source (a list of {@link Book} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    // private static final String LOG_TAG = EarthquakeActivity.class.getName();


    /**
     * Constructs a new {@link BookAdapter}.
     *
     * @param context of the app
     * @param earthquakes is the list of earthquakes, which is the data source of the adapter
     */
    public BookAdapter(Context context, List<Book> earthquakes) {

        super(context, 0, earthquakes);
    }


    // getView method return a view that displays data at specific position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        // getItem(position) to return data item at a specific position in the list
        Book currentEarthquake = getItem(position);


        // Find the TextView with view ID title
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentEarthquake.getTitle());

        // Find the TextView with view ID author
        TextView author = (TextView) listItemView.findViewById(R.id.author);
        author.setText("Author: " + currentEarthquake.getAuthor());

        // Find the TextView with view ID publisheDate
        TextView publisherDate = (TextView) listItemView.findViewById(R.id.publisheDate);
        publisherDate.setText("Publish Date: " + currentEarthquake.getPublisheDate());

        // Find the TextView with view ID page
        TextView pageCount = (TextView) listItemView.findViewById(R.id.page);
        pageCount.setText("Pages: " + currentEarthquake.getPageCount());

        // Find the ImageView with view ID bookImage
        ImageView imageLinks = (ImageView) listItemView.findViewById(R.id.bookImage);
        new DownloadImageAsyncTask(imageLinks)
                .execute(currentEarthquake.getImageLinks());

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    // use AsyncTask inside adapter to load image of each book without freeze UI
    private class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        // this ImageView to show image of book
        ImageView mBookImage;

        // must do default constructor
        public DownloadImageAsyncTask(ImageView bookImage) {
            this.mBookImage = bookImage;
        }

        // doInBackground method must take list of inputs
        protected Bitmap doInBackground(String... urls) {
             String urldisplay = urls[0];

            // use Bitmap to compress image and download fast
            Bitmap bitmap = null;
            // Note must use try/catch with java.net.URL
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error in image book", e.getMessage());
            }
            return bitmap;
        }

        // show result in imageView
        protected void onPostExecute(Bitmap result) {
            mBookImage.setImageBitmap(result);
            mBookImage.setVisibility(View.VISIBLE);
        }
    }
}
