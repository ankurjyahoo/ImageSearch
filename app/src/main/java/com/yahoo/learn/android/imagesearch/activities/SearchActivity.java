package com.yahoo.learn.android.imagesearch.activities;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.learn.android.imagesearch.adapters.EndlessScrollListener;
import com.yahoo.learn.android.imagesearch.adapters.ImageResultsAdapter;
import com.yahoo.learn.android.imagesearch.models.ImageResult;
import com.yahoo.learn.android.imagesearch.models.SearchSettings;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {
    static final String             EXTRA_IMAGE_URL = "url";
    static final String             EXTRA_SETTINGS = "settings";
    private static final int        SETTINGS_REQUEST_CODE = 23;
    private static final int        MAX_PAGES = 8;
    private static final int        MAX_RESULTS = 64;
    private static final int        RESULTS_PER_PAGE = 8;

    private SearchSettings          mSettings = new SearchSettings();
    private SearchResponseHandler   mSearchResponseHandler = new SearchResponseHandler();
    private String                  mQueryTerm = null;

//    private EditText                etSearch;
    private AsyncHttpClient         mHttpClient;
    private ArrayList<ImageResult>  mImageResults = new ArrayList<ImageResult>();
    private ImageResultsAdapter     aResultsAdapter;


    private static final String QUERY_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        etSearch = (EditText) findViewById(R.id.etSearch);
        GridView gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new ImageClickListener());

        aResultsAdapter = new ImageResultsAdapter(this, mImageResults);
        gvResults.setAdapter(aResultsAdapter);
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemCount) {
                performSearchQuery();
            }
        });
        mHttpClient = new AsyncHttpClient();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(new SearchQueryHandler());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

//    public void onImageSearch(View view) {
//        performSearchQuery(etSearch.getText().toString());
//    }

    private void performNewSearchQuery() {
        // Initial search, clear the results
        mImageResults.clear();
        aResultsAdapter.notifyDataSetChanged();

        // run the async search query
        performSearchQuery();
    }

    // Initial and subsequent searches
    private void performSearchQuery() {
        int numResults = mImageResults.size();

        // Check if we have maxed
        if (numResults >= MAX_RESULTS)
            return;

        if (numResults % RESULTS_PER_PAGE != 0) {
            Log.e("PAGE_BOUNDS_ERROR", "Current result set size " + numResults + " does not align with page bounds");
            // Clear the results and reissue query
            aResultsAdapter.clear();
            numResults = 0;
        }

        String queryURL = QUERY_URL + mQueryTerm + mSettings.getParamsString() +
                ((numResults != 0) ? ("&start=" + numResults) : "");

        Log.i("INFO", queryURL);
        mHttpClient.get(queryURL, mSearchResponseHandler);
    }

    public void launchSettingsActivity(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(EXTRA_SETTINGS, mSettings);
        startActivityForResult(intent, SETTINGS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SETTINGS_REQUEST_CODE) {
//            mSettings = (SearchSettings) data.getSerializableExtra(EXTRA_SETTINGS);
            mSettings = (SearchSettings) data.getParcelableExtra(EXTRA_SETTINGS);
            if (mQueryTerm != null)
                performNewSearchQuery();
        }
    }

    private class SearchResponseHandler extends JsonHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//            Log.i("INFO", response.toString());
            try {
                JSONArray results = response.getJSONObject("responseData").getJSONArray("results");
                if (results.length() == 0 && mImageResults.size() == 0) {
                    // Empty result set
                    // TODO: Use alerts instead, will do so when I learn about those
                    Toast.makeText(SearchActivity.this, R.string.empty_result_message, Toast.LENGTH_LONG).show();
                    return;
                }
                aResultsAdapter.addAll(ImageResult.fromJSONArray(results));
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Log.i("INFO", SearchActivity.this.mImageResults.toString());
        }
    }

    private class ImageClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(SearchActivity.this, DisplayActivity.class);
            intent.putExtra(EXTRA_IMAGE_URL, mImageResults.get(position).url);
            startActivity(intent);
        }
    }

    private class SearchQueryHandler implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            mQueryTerm = query;

            performNewSearchQuery();

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }
}
