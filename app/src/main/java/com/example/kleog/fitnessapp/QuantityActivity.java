package com.example.kleog.fitnessapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.model.Serving;
import com.fatsecret.platform.services.android.Request;
import com.fatsecret.platform.services.android.ResponseListener;

import java.util.List;

public class QuantityActivity extends AppCompatActivity {


    public static final int MIN_QUANTITY = 0;

    private String mMealType;
    private int mQuantity;
    private List<Serving> mServingsList;
    private String mFoodName;
    private String mFoodDrescription;
    private long mFoodID;



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

        //TODO check if item is already in database and if so load from there instead

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

        mSubmit = (Button) findViewById(R.id.foodSubmitButton);


        // gets what type of meal e.g. breakfast, lunch, dinner or snack
        mMealType = getIntent().getStringExtra("MEAL_TYPE");
        mFoodDrescription = getIntent().getStringExtra("FOOD_DESCRIPTION");
        mFoodID = getIntent().getLongExtra("FOOD_ID", 0L);



        //fat secret API stuff

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Listener listener = new Listener();

        Request req = new Request(key, secret, listener);

        req.getFood(requestQueue, mFoodID);




        mLoadingIcon.setVisibility(View.INVISIBLE);
    }

    // called when submit button is pressed - has not been linked to button yet
    public void submitQuantity(View view) {

    }

    public int getQuantity() {
        return mQuantity;
    }

    @VisibleForTesting
    public void setQuantity(int qty) {
        mQuantity = qty;
    }





    private class Listener implements ResponseListener {
        @Override
        public void onFoodResponse(Food food) {
            Log.d("QUANTITY_ACTIVITY", "onFoodResponse: " + food.getDescription());
            mFoodName = food.getName();
            mServingsList = food.getServings();

            //creates and attaches adapter to spinner
            adapter = new FoodSpinnerAdapter(getApplicationContext(), R.layout.food_serving_type_spinner_list, mServingsList);
            mFoodSpinner.setAdapter(adapter);

            mFoodTitle.setText(mFoodName);
            mFoodInformation.setText(mFoodDrescription);




        }


    }

    public class FoodSpinnerAdapter extends ArrayAdapter<Serving> {

        private final LayoutInflater mInflater;
        private final Context mContext;
        private final List<Serving> servings;
        private final int mResource;

        public FoodSpinnerAdapter( Context context, int resource,
                                   List objects) {
            super(context, resource, 0, objects);


            mContext = context;
            mInflater = LayoutInflater.from(context);
            mResource = resource;
            servings = objects;
        }
        @Override
        public View getDropDownView(int position,  View convertView,
                                    ViewGroup parent) {
            return createItemView(position, convertView, parent);
        }

        @Override
        public  View getView(int position,  View convertView, ViewGroup parent) {
            return createItemView(position, convertView, parent);
        }

        private View createItemView(int position, View convertView, ViewGroup parent){
            final View view = mInflater.inflate(mResource, parent, false);

            TextView name = (TextView) view.findViewById(R.id.foodSpinnerTypeName);


            Serving offerData = servings.get(position);

            name.setText(offerData.getServingDescription());

            return view;
        }
    }

}
