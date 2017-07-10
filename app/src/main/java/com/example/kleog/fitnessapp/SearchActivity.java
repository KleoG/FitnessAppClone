package com.example.kleog.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.kleog.fitnessapp.UserNutritionDatabase.FoodItemsModel;
import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.CompactRecipe;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.model.Recipe;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.services.android.Request;
import com.fatsecret.platform.services.android.ResponseListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ArrayList<CompactFood> arrayFood;

    private APIFoodItemListAdapter adapter;

    private String mealType;


    //api
    String key = "9363b5d78a9342818602505dad0b01cb";
    String secret = "02d257d83e6249fd98d20782992c0de3";
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


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
                Intent intent = new Intent(SearchActivity.this, QuantityActivity.class);
                CompactFood obj = (CompactFood) lv.getAdapter().getItem(position);  //Gets whole object in the position
                //req.getFood(requestQueue, obj.getId());
                intent.putExtra("FOOD_ITEM", obj.getName());
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
                Log.d("SEARCH_VIEW", "onQueryTextChange: Search text has changed");
                arrayFood.clear();  //clears the the current food stored in list when text inputted

                query = newText;
                req.getFoods(requestQueue, query, 0); // searches the current text in searchView

                return false;
            }
        });


    }


    class Listener implements ResponseListener {
        @Override
        public void onFoodListRespone(Response<CompactFood> response) {
            try {
                Log.d("FAT_SECRET", "onFoodListRespone: TOTAL FOOD ITEMS: " + response.getTotalResults());

                List<CompactFood> foods = response.getResults();
                //This list contains summary information about the food items

                //Log.d("FAT_SECRET", "onFoodListRespone: =========FOODS============");
                for (CompactFood food : foods) {
                    arrayFood.add(food);

                }

                adapter.notifyDataSetChanged(); // Update screen when search text inputted
            } catch (Exception E) {
                Log.d("FAT_SECRET", "onFoodListRespone: InvocationTargetException");
                Toast.makeText(getApplicationContext(), "Error Searching API", Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void onRecipeListRespone(Response<CompactRecipe> response) {
            System.out.println("TOTAL RECIPES: " + response.getTotalResults());

            List<CompactRecipe> recipes = response.getResults();
            System.out.println("=========RECIPES==========");
            for (CompactRecipe recipe : recipes) {
                System.out.println(recipe.getName());
            }
        }

        @Override
        public void onFoodResponse(Food food) {
            Log.d("FAT_SECRET", "onFoodResponse: FOOD NAME: " + food.getName());
        }

        @Override
        public void onRecipeResponse(Recipe recipe) {
            System.out.println("RECIPE NAME: " + recipe.getName());
        }
    }


}

class APIFoodItemListAdapter extends ArrayAdapter<CompactFood> {
    Context context;
    ArrayList<CompactFood> foods;
    private static LayoutInflater inflater = null;

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

