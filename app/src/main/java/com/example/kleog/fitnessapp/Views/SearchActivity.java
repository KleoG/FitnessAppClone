package com.example.kleog.fitnessapp.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.kleog.fitnessapp.R;
import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.services.android.Request;
import com.fatsecret.platform.services.android.ResponseListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private final String TAG = "SEARCH ACTIVITY";

    Toast mToast;

    ArrayList<CompactFood> arrayFood;

    //api
    private String key = "9363b5d78a9342818602505dad0b01cb";
    private String secret = "02d257d83e6249fd98d20782992c0de3";
    private String query;

    private APIFoodItemListAdapter adapter;
    private String mealType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE); //hide the loading icon

        //fat secret API stuff

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Listener listener = new Listener();

        Request req = new Request(key, secret, listener);

        final SearchView searchView = (SearchView) findViewById(R.id.foodSearchView);


        // gets what type of meal e.g. breakfast, lunch, dinner or snack
        mealType = getIntent().getStringExtra("MEAL_TYPE");

        setTitle(mealType);

        //handleIntent(getIntent());

        ListView lv = (ListView) findViewById(R.id.listViewFoodSearch);

        arrayFood = new ArrayList<>();


        adapter = new APIFoodItemListAdapter(SearchActivity.this, arrayFood);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(getApplicationContext(), QuantityActivity.class);

                CompactFood obj = (CompactFood) lv.getAdapter().getItem(position);  //Gets whole object in the position

                Log.d("SEARCH_ACTIVITY", "onItemClick: food description: " + obj.getDescription());

                intent.putExtra("FOOD_ID", obj.getId());
                intent.putExtra("FOOD_DESCRIPTION", obj.getDescription());
                intent.putExtra("MEAL_TYPE", mealType);

                startActivity(intent);
            }
        });


        //search button functionality

        searchView.setQueryHint("search for food");

        //allows user to click anywhere on the search view
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //TODO make it so onQeryTectChange will wait 0.5s before searching for food
                Log.d("SEARCH_VIEW", "onQueryTextChange: Search text has changed");
                arrayFood.clear();  //clears the the current food stored in list when text inputted

                query = newText;

                try {
                    req.getFoods(requestQueue, query, 0); // searches the current text in searchView
                } catch (Exception E) {
                    Log.d("FAT_SECRET", "onFoodListRespone: InvocationTargetException");
                    Toast.makeText(getApplicationContext(), "Error Searching API", Toast.LENGTH_SHORT).show();
                }

                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);    //show the loading icon upon entering text
                return false;

            }


        });
    }

    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy: search activity has been destroyed");
    }


    private class Listener implements ResponseListener {
        @Override
        public void onFoodListRespone(Response<CompactFood> response) {

            Log.d("FAT_SECRET", "onFoodListRespone: TOTAL FOOD ITEMS: " + response.getTotalResults());

            List<CompactFood> foods = response.getResults();
            //This list contains summary information about the food items

            //Log.d("FAT_SECRET", "onFoodListRespone: =========FOODS============");
            for (CompactFood food : foods) {
                arrayFood.add(food);

            }

            findViewById(R.id.loadingPanel).setVisibility(View.GONE);   //hide the loading icon for arrayFood list to appear
            adapter.notifyDataSetChanged(); // Update screen when search text inputted

            if (arrayFood.isEmpty()) {
                showAToast("0 Results Returned"); // if search finds no items, states "0 Results Returned"
                Log.d("SEARCH_VIEW", "onQueryTextChange: items = " + arrayFood.size());
            }


        }

        /**
         * shows a toat only when there is not one currently showing to avoid toast spam when typing
         *
         * @param message
         */
        public void showAToast(String message) {
            if (mToast == null) {
                //if toast is not showing then show one
                mToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                mToast.show();
            }

        }

    }


    private class APIFoodItemListAdapter extends ArrayAdapter<CompactFood> {
        Context context;
        ArrayList<CompactFood> foods;
        private LayoutInflater inflater = null;

        public APIFoodItemListAdapter(Context context, ArrayList<CompactFood> foods) {
            super(context, R.layout.api_food_item_list, foods);
            this.context = context;
            this.foods = foods;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return foods.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.api_food_item_list, null);
            TextView foodName = (TextView) vi.findViewById(R.id.apiFood);


            //this is where the values of the row are set
            foodName.setText(foods.get(position).getName());

            return vi;
        }


    }

}