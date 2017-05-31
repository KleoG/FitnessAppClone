package com.example.kleog.fitnessapp;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.widget.ListAdapter;

public class SearchActivity extends Activity {

    private String mealType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // gets what type of meal e.g. breakfast, lunch, dinner or snack
        mealType = getIntent().getStringExtra("MEAL_TYPE");

        setTitle(mealType);

        handleIntent(getIntent());

    }

    @Override
    protected  void onNewIntent(Intent intent){
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        // Get the intent, verify the action and get the query
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }



    /**
     * this completes an action based on the query the user inputs in the search bar
     * @param query this is the query the user types in, in the search bar
     */
    public void doSearch(String query){
        // because we are using SQLite, performing a full-text search (using FTS3, rather than a
        // LIKE query) can provide a more robust search across text data and can produce results
        // significantly faster.

    }

    // extend ListAdapater
//    @Override
//    public void setListAdapter(ListAdapter adapter){
//
//    }

    // this will notify when the search dialog is activited, shows when the activity
    // has lost input focus to search dialog
    @Override
    public boolean onSearchRequested() {
        //pauseSomeStuff();
        return super.onSearchRequested();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
