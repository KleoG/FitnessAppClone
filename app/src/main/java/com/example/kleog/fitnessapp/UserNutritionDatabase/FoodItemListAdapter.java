package com.example.kleog.fitnessapp.UserNutritionDatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kleog.fitnessapp.R;

import java.util.ArrayList;

/**
 * Created by Kevin on 30/05/2017.
 */

public class FoodItemListAdapter extends BaseAdapter {
    Context context;
    ArrayList<FoodItemsModel> foods;
    private static LayoutInflater inflater = null;

    public FoodItemListAdapter(Context context, ArrayList<FoodItemsModel> foods) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.foods = foods;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return foods.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return foods.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.food_item_list_row, null);

        return vi;
    }
}
