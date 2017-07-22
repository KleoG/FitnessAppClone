package com.example.kleog.fitnessapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kleog.fitnessapp.UserNutritionDatabase.FoodItemsModel;

import java.util.ArrayList;

/**
 * Created by Kevin on 30/05/2017.
 */

public class FoodItemListAdapter extends ArrayAdapter<FoodItemsModel> {
    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<FoodItemsModel> foods;

    public FoodItemListAdapter(Context context, ArrayList<FoodItemsModel> foods) {
        super(context, R.layout.food_item_list_row, foods);
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

                foods.remove(pos);
                notifyDataSetChanged();

            }
        });

        //this is where the values of the row are set
        foodName.setText(foods.get(position).getFoodID());

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
