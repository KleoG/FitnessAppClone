package com.example.kleog.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
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

    private ArrayAdapter<String> adapter;

    private String mealType;

    //api
    String key;
    String secret;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // gets what type of meal e.g. breakfast, lunch, dinner or snack
        mealType = getIntent().getStringExtra("MEAL_TYPE");

        setTitle(mealType);

        //handleIntent(getIntent());

        ListView lv = (ListView) findViewById(R.id.listViewFoodSearch);

        ArrayList<String> arrayFood = new ArrayList<>();
        arrayFood.addAll(Arrays.asList(getResources().getStringArray(R.array.food_array)));

        adapter = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_list_item_1, arrayFood);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(SearchActivity.this, QuantityActivity.class);
                String entry = parent.getItemAtPosition(position).toString();
                intent.putExtra("FOOD_ITEM", entry);
                startActivity(intent);
            }
        });




        //search button functionality
        final SearchView searchView = (SearchView) findViewById(R.id.foodSearchView);

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
                adapter.getFilter().filter(newText);
                return false;
            }
        });



        //fat secret API stuff

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Listener listener = new Listener();

        Request req = new Request(key, secret, listener);


        //This response contains the list of food items at zeroth page for your query
        req.getFoods(requestQueue, query, 0);

        //This response contains the list of food items at page number 3 for your query
        //If total results are less, then this response will have empty list of the food items
        req.getFoods(requestQueue, query, 3);

        //This food object contains detailed information about the food item
        req.getFood(requestQueue, 29304L);

        //This response contains the list of recipe items at zeroth page for your query
        req.getRecipes(requestQueue, query, 0);

        //This response contains the list of recipe items at page number 2 for your query
        //If total results are less, then this response will have empty list of the recipe items
        req.getRecipes(requestQueue, query, 2);

        //This recipe object contains detailed information about the recipe item
        req.getRecipe(requestQueue, 315L);
    }



}
class Listener implements ResponseListener {
    @Override
    public void onFoodListRespone(Response<CompactFood> response) {
        Log.d("FAT_SECRET", "onFoodListRespone: TOTAL FOOD ITEMS: " + response.getTotalResults());

        List<CompactFood> foods = response.getResults();
        //This list contains summary information about the food items

        Log.d("FAT_SECRET", "onFoodListRespone: =========FOODS============");
        for (CompactFood food: foods) {
            System.out.println(food.getName());
        }
    }

    @Override
    public void onRecipeListRespone(Response<CompactRecipe> response) {
        System.out.println("TOTAL RECIPES: " + response.getTotalResults());

        List<CompactRecipe> recipes = response.getResults();
        System.out.println("=========RECIPES==========");
        for (CompactRecipe recipe: recipes) {
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

