package com.example.android.gbooklist;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final int BOOK_LOADER_ID = 1;

    private static final String BOOK_SEARCH_REQ = "https://www.googleapis.com/books/v1/volumes?q= ";
    private BookAdapter mAdapter;
    private EditText mBookSearch;
    private String mBookQuery;
    private String query;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        ListView bookListView = (ListView)findViewById(R.id.result_list);
        mEmptyStateTextView = (TextView)findViewById(R.id.empty_text);
        bookListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);
        View loadingIndicator =(View)findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.GONE);



        Button submitQuery = (Button)findViewById(R.id.submit);

        submitQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBookSearch = (EditText)findViewById(R.id.book_query);
                mBookQuery = mBookSearch.getText().toString();
                query = BOOK_SEARCH_REQ + mBookQuery + "&maxResults=15";

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {

                    LoaderManager loaderManager = getLoaderManager();
                    loaderManager.initLoader(BOOK_LOADER_ID, null, BookActivity.this);
                    View loadingIndicator = findViewById(R.id.progress_bar);
                    loadingIndicator.setVisibility(View.VISIBLE);

                } else {

                    View loadingIndicator = findViewById(R.id.progress_bar);
                    loadingIndicator.setVisibility(View.GONE);

                    mEmptyStateTextView = (TextView)findViewById(R.id.empty_text);
                    mEmptyStateTextView.setText(R.string.no_internet_connection);

                    return;
                }

            }
        });

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this,query);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        if (books != null && !books.isEmpty()){
            mAdapter.clear();
            mAdapter.addAll(books);
        }else {
            View loadingIndicator = findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_data_found);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();

    }
}
