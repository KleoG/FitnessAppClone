package com.example.kleog.fitnessapp.Views;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kleog.fitnessapp.Models.FoodItemsModel;
import com.example.kleog.fitnessapp.Models.MealModel;
import com.example.kleog.fitnessapp.Models.MealType;
import com.example.kleog.fitnessapp.R;
import com.example.kleog.fitnessapp.ViewModels.FoodItemsViewModel;
import com.example.kleog.fitnessapp.ViewModels.MealModelViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kevin on 23/05/2017.
 */

public class MealActivity extends LifecycleActivity {

    private final String TAG = "MEAL ACTIVITY";

    private String mMealType;
    private MealType mMealTypeEnum;


    //food list variables
    private ListView mFoodListView;

    private ArrayList<FoodItemsModel> mFoodItemsList;

    private FoodItemListAdapter mAdapter;

    private TextView calorieValue, carbsValue, proteinValue, fatValue;


    //view models
    private MealModelViewModel mMealVM;
    private FoodItemsViewModel mFoodVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the title of the page to the button pressed
        setContentView(R.layout.activity_meal_page);
        mMealType = getIntent().getStringExtra("MEAL_TYPE");
        mMealTypeEnum = converToMEALTYPE(mMealType);

        setTitle(mMealType);

        Log.d(TAG, "onCreate: meal activity of type " + mMealType);

        //initialise View Models
        mMealVM = ViewModelProviders.of(this).get(MealModelViewModel.class);
        mFoodVM = ViewModelProviders.of(this).get(FoodItemsViewModel.class);

        //initialise textViews to be updated
        calorieValue = (TextView) findViewById(R.id.calorieValue);
        carbsValue = (TextView) findViewById(R.id.carbsValue);
        proteinValue = (TextView) findViewById(R.id.proteinValue);
        fatValue = (TextView) findViewById(R.id.fatValue);


        //sets up the list underneath the add view button
        mFoodListView = (ListView) findViewById(R.id.foodList);
        mFoodItemsList = new ArrayList<>();
        mAdapter = new FoodItemListAdapter(this, mFoodItemsList, mFoodVM);
        mFoodListView.setAdapter(mAdapter);


        //this is the type of value that will be returned (a liveData that contains the list)
        LiveData<MealModel> MealInfo = mMealVM.getMealInfo(mMealTypeEnum);

        /*
          the values at the top of the page will be updated here
         */
        MealInfo.observe(this, info -> {

            assert info != null;
            Log.d("LIVE_DATA_OBSERVER_MEAL", "onCreate: observed change in live data for Meal: " + info.getMealType());

            Log.d("LIVE_DATA_OBSERVER_MEAL", "onCreate: " + info.getMealType() + " total calories: " + info.getTotalCalories());
            Log.d("LIVE_DATA_OBSERVER_MEAL", "onCreate: " + info.getMealType() + " total carbs: " + info.getTotalCarbs());
            Log.d("LIVE_DATA_OBSERVER_MEAL", "onCreate: " + info.getMealType() + " total protein: " + info.getTotalProtein());
            Log.d("LIVE_DATA_OBSERVER_MEAL", "onCreate: " + info.getMealType() + " total fat: " + info.getTotalFat());

            //String.format("%.1f" will convert the double into into a string with only 1 decimal place
            calorieValue.setText(String.format(Locale.UK, "%.1f", info.getTotalCalories()));
            carbsValue.setText(String.format(Locale.UK, "%.1f", info.getTotalCarbs()));
            proteinValue.setText(String.format(Locale.UK, "%.1f", info.getTotalProtein()));
            fatValue.setText(String.format(Locale.UK, "%.1f", info.getTotalFat()));

        });

        LiveData<List<FoodItemsModel>> foodListInfo = mFoodVM.getCurrentDayFoodsOfType(mMealTypeEnum);

        /*
          list view for food will be updated here
         */
        foodListInfo.observe(this, foodInfo -> {
            assert foodInfo != null;

            mAdapter.clear();

            mAdapter.addAll(foodInfo);

            mAdapter.notifyDataSetChanged();
        });
    }


    /**
     * is called when the graphs button is clicked
     *
     * @param view object being clicked on - in this case the "graphs" button
     */

    public void goToSearchPage(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("MEAL_TYPE", mMealType);

        startActivity(intent);  // changes page to the intent (graph page)
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        this.finish();
    }

    public void onDestroy() {

        super.onDestroy();
        Log.d(TAG, "onDestroy: meal activity: " + mMealType + " has been destroyed");
    }

    private MealType converToMEALTYPE(String meal_type) {
        switch (meal_type) {
            case "Breakfast":
                return MealType.BREAKFAST;
            case "Lunch":
                return MealType.LUNCH;
            case "Dinner":
                return MealType.DINNER;
            case "Snacks":
                return MealType.SNACKS;
            default:
                return null;
        }
    }

    /**
     * custom mAdapter to display current foods
     */

    private static class FoodItemListAdapter extends ArrayAdapter<FoodItemsModel> {
        private static LayoutInflater inflater = null;
        Context context;
        ArrayList<FoodItemsModel> foods;

        FoodItemsViewModel foodVM;

        FoodItemListAdapter(Context context, ArrayList<FoodItemsModel> foods, FoodItemsViewModel foodVM) {
            super(context, R.layout.food_item_list_row, foods);
            this.context = context;
            this.foods = foods;
            inflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);

            this.foodVM = foodVM;
        }

        @Override
        public int getCount() {
            return foods.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.food_item_list_row, null);
            TextView foodName = (TextView) vi.findViewById(R.id.foodItemName);

            /*
              this is what happens when the remove button is pressed
             */
            ImageButton deleteButton = (ImageButton) vi.findViewById(R.id.deleteImageButton);
            deleteButton.setTag(position);
            deleteButton.setOnClickListener(view -> {
                int pos = (int) view.getTag();

                FoodItemsModel foodToRemove = foods.remove(pos);

                foodVM.removeFood(foodToRemove);
                Log.d("FOOD_LIST_VIEW", "onClick delete: food with name: " + foodName + " + ID: " + foodToRemove.getFoodID());


                notifyDataSetChanged();

            });

            ImageButton modifyButton = (ImageButton) vi.findViewById(R.id.modifyImageButton);
            modifyButton.setTag(position);
            modifyButton.setOnClickListener(view -> {
                int pos = (int) view.getTag();

                FoodItemsModel foodToModify = foods.get(pos);


                Intent i = new Intent(getContext(), QuantityActivity.class);
                i.putExtra("FOOD_ID", foodToModify.getFoodID());
                i.putExtra("FOOD_DESCRIPTION", foodToModify.getFoodDescription());
                String mealType = convertMealTypeToString(foodToModify.getEatenDuringMeal());
                i.putExtra("MEAL_TYPE", mealType);
                context.startActivity(i);

            });


            //this is where the values of the row are set
            foodName.setText(foods.get(position).getFoodName());

            return vi;
        }

        String convertMealTypeToString(MealType type) {
            switch (type) {
                case BREAKFAST:
                    return "Breakfast";
                case LUNCH:
                    return "Lunch";
                case DINNER:
                    return "Dinner";
                case SNACKS:
                    return "Snacks";
                default:
                    return null;
            }
        }
    }
}
