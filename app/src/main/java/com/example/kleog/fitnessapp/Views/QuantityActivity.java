package com.example.kleog.fitnessapp.Views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.kleog.fitnessapp.Models.FoodItemsModel;
import com.example.kleog.fitnessapp.Models.MealType;
import com.example.kleog.fitnessapp.R;
import com.example.kleog.fitnessapp.ViewModels.FoodItemsViewModel;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.model.Serving;
import com.fatsecret.platform.services.android.Request;
import com.fatsecret.platform.services.android.ResponseListener;

import java.util.Date;
import java.util.List;

public class QuantityActivity extends AppCompatActivity {

    private static final String TAG = "QUANTITY_ACTIVITY";

    private static final int MIN_QUANTITY = 0;

    //view model
    private FoodItemsViewModel mfoodItemsVM;

    //food details
    private String mMealType;
    private MealType mMealTypeEnum;
    private int mServingChosen;
    private List<Serving> mServingsList;
    private String mFoodName;
    private String mFoodDrescription;
    private long mFoodID;
    private Double mFoodTotalCalories, mFoodTotalCarbs, mFoodTotalProtein, mFoodTotalFat,
            mCaloriesPerServing, mCarbsPerServing, mProteinPerServing, mFatPerServing, mServingAmount;
    private boolean foodInDatabase; //set to true if the food item is already in the database


    //views
    private ProgressBar mLoadingIcon;
    private ImageView mFoodImage;
    private TextView mFoodTitle, mTotalCaloriesAmount;
    private EditText mFoodInformation, mFoodAmountText;
    private Spinner mFoodSpinner;
    private Button mSubmit;

    private FoodSpinnerAdapter adapter;

    //api
    private String key = "9363b5d78a9342818602505dad0b01cb";
    private String secret = "02d257d83e6249fd98d20782992c0de3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity);

        mfoodItemsVM = ViewModelProviders.of(this).get(FoodItemsViewModel.class);


        //TODO add button that shows in depth data of food


        //starts the loading icon
        mLoadingIcon = (ProgressBar) findViewById(R.id.foodProgressBar);
        mLoadingIcon.setVisibility(View.VISIBLE);

        mFoodImage = (ImageView) findViewById(R.id.foodPicture);

        mFoodTitle = (TextView) findViewById(R.id.foodTitle);
        mTotalCaloriesAmount = (TextView) findViewById(R.id.totalCaloriesAmount);

        mFoodInformation = (EditText) findViewById(R.id.foodInformation);
        mFoodInformation.setClickable(false);
        mFoodInformation.setFocusable(false);

        mFoodAmountText = (EditText) findViewById(R.id.foodAmountText);

        mFoodSpinner = (Spinner) findViewById(R.id.foodQuantityAmountSpinner);
        //TODO make spinner scrollable and add dividers

        mSubmit = (Button) findViewById(R.id.foodSubmitButton);

        // gets what type of meal e.g. breakfast, lunch, dinner or snack
        mMealType = getIntent().getStringExtra("MEAL_TYPE");
        mMealTypeEnum = converToMEALTYPE(mMealType);

        mFoodID = getIntent().getLongExtra("FOOD_ID", 0L);

        mFoodDrescription = getIntent().getStringExtra("FOOD_DESCRIPTION");
        //formats the description to separate lines with regex
        String regex = "- |\\| ";
        mFoodDrescription = mFoodDrescription.replaceAll(regex, "\n");


        //checking if food in already in DB
        FoodItemsModel foodInDB = null;
        try {
            foodInDB = mfoodItemsVM.getCurrentDayFoodWithID(mFoodID, mMealTypeEnum);

        } catch (Exception e) {
            Log.d("DATABASE", "onCreate: Error on finding food in the DB");
        }

        if (foodInDB != null) {
            Log.d(TAG, "onCreate: food was found in database");

            //sets the values that the user chose before
            mFoodSpinner.setSelection(foodInDB.getServingChosen());
            mFoodAmountText.setText(foodInDB.getServingUnits().toString());

            mTotalCaloriesAmount.setText(foodInDB.getCalories().toString());

            //set the food data
            mFoodTotalCalories = foodInDB.getCalories();
            mFoodTotalCarbs = foodInDB.getCarbs();
            mFoodTotalProtein = foodInDB.getProtein();
            mFoodTotalFat = foodInDB.getFat();
            mServingAmount = foodInDB.getServingUnits();

            //set serving chosen
            mServingChosen = foodInDB.getServingChosen();


            foodInDatabase = true;
        } else {
            Log.d(TAG, "onCreate: food was not found in database");
            mServingChosen = -1;
            foodInDatabase = false;
        }


        //fat secret API stuff

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Listener listener = new Listener();

        Request req = new Request(key, secret, listener);

        req.getFood(requestQueue, mFoodID);


        /**
         * submit button functionality
         */
        mSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //if user hasnt entered the amount then do nothing
                if (mServingAmount == null || mServingAmount == 0.0) {
                    Log.d(TAG, "onClick: submit button pressed but no serving amount entered or serving amount is 0.0");

                    Toast.makeText(getApplicationContext(), "must choose valid serving amount (cannot be empty or 0)", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onClick: submit button pressed with serving amount :" + mServingAmount);
                    FoodItemsModel foodToAdd = new FoodItemsModel(new Date(), mMealTypeEnum, mFoodID, mFoodName, mFoodTotalCalories, mFoodTotalProtein, mFoodTotalCarbs, mFoodTotalFat, mServingChosen, mServingAmount, mFoodDrescription);

                    //if food is already in database then update it
                    if (foodInDatabase) {
                        mfoodItemsVM.updateFood(foodToAdd);

                    } else { //otherwise insert it into db
                        mfoodItemsVM.insertFood(foodToAdd);
                    }
                    Intent i = new Intent(getApplicationContext(), MealActivity.class);
                    i.putExtra("MEAL_TYPE", mMealType);
                    startActivity(i);
                }
            }
        });

        /**
         * spinner on select functionality
         */
        mFoodSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        Log.d(TAG, "onItemSelected: serving at pos: " + pos + " chosen");

                        Serving item = (Serving) parent.getItemAtPosition(pos);
                        mCaloriesPerServing = item.getCalories().doubleValue(); // gets selected item and converts it to double
                        mCarbsPerServing = item.getCarbohydrate().doubleValue();
                        mProteinPerServing = item.getProtein().doubleValue();
                        mFatPerServing = item.getFat().doubleValue();

                        mServingChosen = pos;

                        //if selected new item without changing amount then update values and UI
                        if (mServingAmount != null) {
                            mFoodTotalCalories = mServingAmount * mCaloriesPerServing;
                            mFoodTotalCarbs = mServingAmount * mCarbsPerServing;
                            mFoodTotalProtein = mServingAmount * mProteinPerServing;
                            mFoodTotalFat = mServingAmount * mFatPerServing;

                            mTotalCaloriesAmount.setText(mFoodTotalCalories.toString());
                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        /**
         * foodAmount Text functionality
         */
        mFoodAmountText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //if empty or value is not greater than 0 then do nothing
                //TODO fix crash where user enters decimal points first
                if (s.length() == 0 || !(Double.valueOf(s.toString()) > 0)) {
                    mFoodTotalCalories = null;
                    mFoodTotalCarbs = null;
                    mFoodTotalProtein = null;
                    mFoodTotalFat = null;
                    mServingAmount = null;
                } else {
                    mServingAmount = Double.parseDouble(mFoodAmountText.getText().toString()); //convert string to double

                    mFoodTotalCalories = mServingAmount * mCaloriesPerServing;
                    mFoodTotalCarbs = mServingAmount * mCarbsPerServing;
                    mFoodTotalProtein = mServingAmount * mProteinPerServing;
                    mFoodTotalFat = mServingAmount * mFatPerServing;

                    mTotalCaloriesAmount.setText(mFoodTotalCalories.toString());


                }
            }
        });


        mLoadingIcon.setVisibility(View.INVISIBLE);
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


    private class Listener implements ResponseListener {
        @Override
        public void onFoodResponse(Food food) {
            Log.d("QUANTITY_ACTIVITY", "onFoodResponse: " + food.getDescription());
            mFoodName = food.getName();
            mServingsList = food.getServings();

            mFoodTotalCalories = null;
            mFoodTotalCarbs = null;
            mFoodTotalProtein = null;
            mFoodTotalFat = null;


            //creates and attaches adapter to spinner
            adapter = new FoodSpinnerAdapter(getApplicationContext(), R.layout.food_serving_type_spinner_list, mServingsList);
            mFoodSpinner.setAdapter(adapter);

            //if it is a net item that is not in the db
            if (mServingChosen == -1) {
                //must be done after adapter is set
                mServingChosen = mFoodSpinner.getSelectedItemPosition();

            } else {
                mFoodSpinner.setSelection(mServingChosen);
            }
            Serving selectedServing = (Serving) mFoodSpinner.getItemAtPosition(mServingChosen);
            mCaloriesPerServing = selectedServing.getCalories().doubleValue(); // gets selected item and converts it to double
            mCarbsPerServing = selectedServing.getCarbohydrate().doubleValue();
            mProteinPerServing = selectedServing.getProtein().doubleValue();
            mFatPerServing = selectedServing.getFat().doubleValue();

            mFoodTitle.setText(mFoodName);
            mFoodInformation.setText(mFoodDrescription);

        }


    }

    private class FoodSpinnerAdapter extends ArrayAdapter<Serving> {

        private final LayoutInflater mInflater;
        private final Context mContext;
        private final List<Serving> servings;
        private final int mResource;

        public FoodSpinnerAdapter(Context context, int resource,
                                  List objects) {
            super(context, resource, 0, objects);


            mContext = context;
            mInflater = LayoutInflater.from(context);
            mResource = resource;
            servings = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return createItemView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return createItemView(position, convertView, parent);
        }

        private View createItemView(int position, View convertView, ViewGroup parent) {
            final View view = mInflater.inflate(mResource, parent, false);

            TextView name = (TextView) view.findViewById(R.id.foodSpinnerTypeName);


            Serving offerData = servings.get(position);

            name.setText(offerData.getServingDescription());

            return view;
        }

        @Override
        public Serving getItem(int position) {

            return servings.get(position);
        }
    }

}
