package com.example.kleog.fitnessapp.Views;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kleog.fitnessapp.Models.MealModel;
import com.example.kleog.fitnessapp.R;
import com.example.kleog.fitnessapp.Models.DailyUserInfoModel;
import com.example.kleog.fitnessapp.Models.FoodItemsModel;
import com.example.kleog.fitnessapp.Models.MealType;
import com.example.kleog.fitnessapp.Models.UserNutritionDB;
import com.example.kleog.fitnessapp.ViewModels.FoodItemsViewModel;
import com.example.kleog.fitnessapp.ViewModels.MealModelViewModel;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 23/05/2017.
 */

public class MealActivity extends LifecycleActivity {
    private UserNutritionDB db;

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

        db = UserNutritionDB.getDatabase(this.getApplicationContext());

        //initialise View Models
        mMealVM = ViewModelProviders.of(this).get(MealModelViewModel.class);
        mFoodVM = ViewModelProviders.of(this).get(FoodItemsViewModel.class);

        //initialise textViews to be updated
        calorieValue = (TextView) findViewById(R.id.calorieValue);
        carbsValue = (TextView) findViewById(R.id.carbsValue);
        proteinValue= (TextView) findViewById(R.id.proteinValue);
        fatValue = (TextView) findViewById(R.id.fatValue);


        //sets up the list underneath the add view button
        mFoodListView = (ListView) findViewById(R.id.foodList);
        mFoodItemsList = new ArrayList<>();
        mAdapter = new FoodItemListAdapter(this, mFoodItemsList);
        mFoodListView.setAdapter(mAdapter);




        //this is the type of value that will be returned (a liveData that contains the list)
        LiveData<MealModel> MealInfo = mMealVM.getMealInfo(mMealTypeEnum);

        MealInfo.observe(this, info -> {
            Log.d("LIVE_DATA_OBSERVER_MEAL", "onCreate: observed change in live data for Meal" + info.getMealType());
            calorieValue.setText(info.getTotalCalories().toString());
            carbsValue.setText(info.getTotalCarbs().toString());
            proteinValue.setText(info.getTotalProtein().toString());
            fatValue.setText(info.getTotalFat().toString());

        });

        //

        //temporary testing data
//        FoodItemsModel item1 = new FoodItemsModel(new Date(), MealType.LUNCH, "Chicken", 200.0, 50.0, 10.0, 5.0, 200.0, AmountEatenType.GRAMS);
//        FoodItemsModel item2 = new FoodItemsModel(new Date(), MealType.LUNCH, "Eggs", 100.0, 30.0, 20.0, 10.0, 3.0, AmountEatenType.UNITS);
//        FoodItemsModel item3 = new FoodItemsModel(new Date(), MealType.LUNCH, "rice", 250.0, 10.0, 30.0, 15.0, 150.0, AmountEatenType.GRAMS);
//        mAdapter.add(item1);
//        mAdapter.add(item2);
//        mAdapter.add(item3);


    }

    public void onClickNotFinished(View view) {
        //first thread for inserting data
        /*
        the commented out code below is used to insert random data into the db then retrieve the data and display a part of it
        leave it commented for now
         */
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... params) {
//                db.DailyUserInfoModel().insert(new DailyUserInfoModel(new Date(), 150, 100, 75, 50, 73));
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void param) {
//                Toast.makeText(getApplicationContext(), "user information was inserted", Toast.LENGTH_LONG).show();
//                String info = "user info not found";
//                try {
//                    //value returned by the async task is passed into info
//                    info = new AsyncTask<Void, Void, String>(){
//                        @Override
//                        protected String doInBackground(Void... params){
//
//                            //Log.d("MEAL_ACTIVITY", "doInBackground: retriving information: params[0]: " + params[0]);
//                            return db.DailyUserInfoModel().getDateLiveData(new Date()).getWeight().toString();
//                        }
//
//                    }.execute().get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//                Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();
//
//
//            }
//        }.execute();


        // temp useless adding of food
        //FoodItemsModel item1 = new FoodItemsModel(new Date(), MealType.LUNCH, "Chicken", 200.0, 50.0, 10.0, 5.0, 200.0, AmountEatenType.GRAMS);
        //mAdapter.add(item1);
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
        this.finish();
    }

    public void updateFoodEatenList() {

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

    public static class FoodItemListAdapter extends ArrayAdapter<FoodItemsModel> {
        private static LayoutInflater inflater = null;
        Context context;
        ArrayList<FoodItemsModel> foods;

        public FoodItemListAdapter(Context context, ArrayList<FoodItemsModel> foods) {
            super(context, R.layout.food_item_list_row, foods);
            this.context = context;
            this.foods = foods;
            inflater = (LayoutInflater) context
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
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
                vi = inflater.inflate(R.layout.food_item_list_row, null);
            TextView foodName = (TextView) vi.findViewById(R.id.foodItemName);

            //this is what happens when the remove button is pressed
            ImageButton deleteButton = (ImageButton) vi.findViewById(R.id.deleteImageButton);
            deleteButton.setTag(position);
            deleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int pos = (int) view.getTag();
                    //TODO also remove from database on this line
                    FoodItemsModel foodToRemove = foods.remove(pos);


                    notifyDataSetChanged();

                }
            });

            //this is where the values of the row are set
            //foodName.setText(foods.get(position).getFoodID());

            return vi;
        }

        @Override
        public void add(FoodItemsModel food) {
            foods.add(food);
            //super.add(food);
            notifyDataSetChanged();

        }

        @Override
        public void remove(FoodItemsModel food) {
            foods.remove(food);
            notifyDataSetChanged();
            //super.remove(employee);
        }
    }
}
