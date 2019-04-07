package com.example.islam.googlebooks;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// LoaderCallbacks interface to can override three methods onCreatLoader, onLoadFinished, onLoaderReset
public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<List<Book>> {

    /** define global variables used in this class. **/

    // specify integer ID for loader, to distinguish between loaders
    private static final int Book_LOADER_ID = 1;

    // ProgressBar display when wait loading data
    ProgressBar loadingIndicator;

    // listView to handle list of books in UI
    ListView bookListView;
    // adapter to setting list of books
    private BookAdapter mAdapter;

    // TextView that is displayed when the list is empty
    private TextView mEmptyStateTextView;

    // EditText to take word of search
    EditText searchBox;

    // ImageView to put image and click on it to search
    ImageView searchButton;

    // execute of activity start from onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // reference to ProgressBar loading_indicator in layout
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        // reference to EditText search_box in layout
        searchBox = (EditText) findViewById(R.id.search_box);

        // reference to ImageView search_button in layout
        searchButton = (ImageView) findViewById(R.id.search_button);

        // reference to empty_view in the layout, to hold empty state when no books in the list
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        // reference to list ListView in the layout
        bookListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the ListView to display elements in UI
        bookListView.setAdapter(mAdapter);

        // LoaderManager to control in Loader
        final LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(Book_LOADER_ID, null, MainActivity.this);

        bookListView.setEmptyView(mEmptyStateTextView);


        // click on each book in listView to open this in new activity
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current book that was clicked on
                Book currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the book in this URI
                Intent bookIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(bookIntent);
            }
        });

        // Click on searchButton
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaderManager.restartLoader(Book_LOADER_ID, null, MainActivity.this);
            }
        });
    }

    // onCreateLoader to create and return a new loader
    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {

        String query = null;

        //url for book data from Google books API
        String url = null;

        // take word of search from EditText
        query = searchBox.getText().toString().trim();
        // must write url in this style, by constant part and variable part
        url = "https://www.googleapis.com/books/v1/volumes?q=search+"  + query + "&maxResults=15";
        return new BookLoader(MainActivity.this, url);
    }

    // onLoadFinished() called when loader is finished loading data to update UI by loader result
    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        // Hide loading indicator because the data has been loaded
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // if there is a valid list of books then add to adapter to update bookListView
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
            bookListView.setVisibility(View.VISIBLE);
        }else{
            mEmptyStateTextView.setText("Search by another word.");
            bookListView.setEmptyView(mEmptyStateTextView);
        }
    }

    // onLoaderReset method to reset(clear) loader when fetching error data
    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        //Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

}

