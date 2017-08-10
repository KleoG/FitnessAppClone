package com.example.kleog.fitnessapp.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kleog.fitnessapp.Models.DailyUserInfoModel;
import com.example.kleog.fitnessapp.Models.FoodItemsModel;
import com.example.kleog.fitnessapp.Models.MealModel;
import com.example.kleog.fitnessapp.Models.MealType;
import com.example.kleog.fitnessapp.Models.UserNutritionDB;

import java.util.Date;
import java.util.List;

/**
 * Created by Kevin on 08/08/2017.
 */

public class FoodItemsViewModel extends AndroidViewModel {
    //for use with log.d
    private static final String TAG = "FoodItemsViewModel";
    private final UserNutritionDB appDatabase;

    private LiveData<List<FoodItemsModel>> foods;
    //indicates what meal type the foods in the live data are stores e.g. if foods (above) contains lunch foods then the value of the variable below will be MealType.LUNCH
    private MealType foodTypeCurrentlyInLiveData;

    public FoodItemsViewModel(Application application) {
        super(application);
        appDatabase = UserNutritionDB.getDatabase(this.getApplication());
        foodTypeCurrentlyInLiveData = null;
    }

    public LiveData<List<FoodItemsModel>> getCurrentDayFoodsOfType(MealType type) {

        if (foodTypeCurrentlyInLiveData != type) {

            foods = appDatabase.FoodItemsModel().getFoodEatenOnDateAndMealType(new Date(), type);
            //updates what the meal type of the foods currently stored
            foodTypeCurrentlyInLiveData = type;
        }

        return foods;

    }

    /**
     * retrieves a particular food on the current day
     *
     * @param ID   food id
     * @param type what mealType the food was eaten at
     * @return the food if it is found
     * @throws Exception
     */
    public FoodItemsModel getCurrentDayFoodWithID(long ID, MealType type) throws Exception {
        try {
            return new RetrieveAsyncTask(appDatabase).execute(ID, type).get();
        } catch (Exception e) {
            return null;
            //throw new Exception("error trying to retrieve food item with id:" + ID + " meal type: " + type);
        }

    }

    /**
     * inserts a new food into the database only use if not there already
     *
     * @param food food object to be inserted
     */
    public void insertFood(FoodItemsModel food) {
        Log.d(TAG, "insertFood: inserting foodID: " + food.getFoodID());
        new InsertAsyncTask(appDatabase).execute(food);
    }

    public void updateFood(FoodItemsModel food) {
        Log.d(TAG, "updateFood: updating foodID: " + food.getFoodID());
        new UpdateAsyncTask(appDatabase).execute(food);
    }

    public void removeFood(FoodItemsModel food) {
        new RemoveAsyncTask(appDatabase).execute(food);
    }

    private static class RetrieveAsyncTask extends AsyncTask<Object, Void, FoodItemsModel> {

        private UserNutritionDB db;

        RetrieveAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;
        }

        @Override
        /**
         * params[0] = food id
         * params[1] = meal type
         */
        protected FoodItemsModel doInBackground(Object... params) {

            Date date = new Date();


            return db.FoodItemsModel().getSingleFoodItem(date, (MealType) params[1], (long) params[0]);
        }

    }

    private static class InsertAsyncTask extends AsyncTask<FoodItemsModel, Void, Void> {

        private UserNutritionDB db;

        InsertAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(FoodItemsModel... foods) {
            FoodItemsModel food = foods[0];

            db.FoodItemsModel().insert(food);

            //how much to add to meal and daily user info
            Double caloriesToAdd = food.getCalories();
            Double carbsToAdd = food.getCarbs();
            Double proteinToAdd = food.getProtein();
            Double fatToAdd = food.getFat();


            //get meal that food was eaten during
            MealModel oldMeal = db.MealModel().getMeal(food.getEatenDuringMeal(), food.getDate());

            //update oldMeal
            oldMeal.setTotalCalories(oldMeal.getTotalCalories() + caloriesToAdd);
            oldMeal.setTotalCarbs(oldMeal.getTotalCarbs() + carbsToAdd);
            oldMeal.setTotalProtein(oldMeal.getTotalProtein() + proteinToAdd);
            oldMeal.setTotalFat(oldMeal.getTotalFat() + fatToAdd);

            //update oldMeal
            db.MealModel().update(oldMeal);

            //get daily user info
            DailyUserInfoModel oldInfo = db.DailyUserInfoModel().getDate(food.getDate());

            //update old info
            oldInfo.setTotalCalories(oldInfo.getTotalCalories() + caloriesToAdd);
            oldInfo.setTotalCarbs(oldInfo.getTotalCarbs() + carbsToAdd);
            oldInfo.setTotalProtein(oldInfo.getTotalProtein() + proteinToAdd);
            oldInfo.setTotalFat(oldInfo.getTotalFat() + fatToAdd);

            //update user info
            db.DailyUserInfoModel().update(oldInfo);

            Log.d(TAG, "doInBackground: finished inserting foodID: " + food.getFoodID());

            return null;

        }

    }

    private static class UpdateAsyncTask extends AsyncTask<FoodItemsModel, Void, Void> {

        private UserNutritionDB db;

        UpdateAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(FoodItemsModel... foods) {

            Date date = foods[0].getDate();

            FoodItemsModel updatedFood = foods[0];

            FoodItemsModel oldFood = db.FoodItemsModel().getSingleFoodItem(date, updatedFood.getEatenDuringMeal(), updatedFood.getFoodID());

            //if food is not already in db then cannot update and throws exception
            if (oldFood == null) {
                throw new NullPointerException("food item with ID: " + updatedFood.getFoodID() + " was not found there cannot update");
            }

            //otherwise continue as normal
            db.FoodItemsModel().update(updatedFood);


            //how much to add to meal and daily user info (updated food - old food)
            Double caloriesToAdd = updatedFood.getCalories() - oldFood.getCalories();
            Double carbsToAdd = updatedFood.getCarbs() - oldFood.getCarbs();
            Double proteinToAdd = updatedFood.getProtein() - oldFood.getProtein();
            Double fatToAdd = updatedFood.getFat() - oldFood.getFat();

            //get meal that food was eaten during
            MealModel oldMeal = db.MealModel().getMeal(updatedFood.getEatenDuringMeal(), date);

            //update oldMeal
            oldMeal.setTotalCalories(oldMeal.getTotalCalories() + caloriesToAdd);
            oldMeal.setTotalCarbs(oldMeal.getTotalCarbs() + carbsToAdd);
            oldMeal.setTotalProtein(oldMeal.getTotalProtein() + proteinToAdd);
            oldMeal.setTotalFat(oldMeal.getTotalFat() + fatToAdd);

            //update oldMeal
            db.MealModel().update(oldMeal);

            //get daily user info
            DailyUserInfoModel oldInfo = db.DailyUserInfoModel().getDate(date);

            //update old info
            oldInfo.setTotalCalories(oldInfo.getTotalCalories() + caloriesToAdd);
            oldInfo.setTotalCarbs(oldInfo.getTotalCarbs() + carbsToAdd);
            oldInfo.setTotalProtein(oldInfo.getTotalProtein() + proteinToAdd);
            oldInfo.setTotalFat(oldInfo.getTotalFat() + fatToAdd);

            //update user info
            db.DailyUserInfoModel().update(oldInfo);


            Log.d(TAG, "doInBackground: finished updating foodID: " + updatedFood.getFoodID());

            return null;

        }

    }

    private static class RemoveAsyncTask extends AsyncTask<FoodItemsModel, Void, Void> {
        private UserNutritionDB db;

        RemoveAsyncTask(UserNutritionDB appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(FoodItemsModel... foods) {
            FoodItemsModel food = foods[0];

            db.FoodItemsModel().delete(food);

            //how much to remove from meal and daily user info
            Double caloriesToRemove = food.getCalories();
            Double carbsToRemove = food.getCarbs();
            Double proteinToRemove = food.getProtein();
            Double fatToRemove = food.getFat();


            //get meal that food was eaten during
            MealModel oldMeal = db.MealModel().getMeal(food.getEatenDuringMeal(), food.getDate());

            //update oldMeal
            oldMeal.setTotalCalories(oldMeal.getTotalCalories() - caloriesToRemove);
            oldMeal.setTotalCarbs(oldMeal.getTotalCarbs() - carbsToRemove);
            oldMeal.setTotalProtein(oldMeal.getTotalProtein() - proteinToRemove);
            oldMeal.setTotalFat(oldMeal.getTotalFat() - fatToRemove);

            //update oldMeal
            db.MealModel().update(oldMeal);

            //get daily user info
            DailyUserInfoModel oldInfo = db.DailyUserInfoModel().getDate(food.getDate());

            //update old info
            oldInfo.setTotalCalories(oldInfo.getTotalCalories() - caloriesToRemove);
            oldInfo.setTotalCarbs(oldInfo.getTotalCarbs() - carbsToRemove);
            oldInfo.setTotalProtein(oldInfo.getTotalProtein() - proteinToRemove);
            oldInfo.setTotalFat(oldInfo.getTotalFat() - fatToRemove);

            //update user info
            db.DailyUserInfoModel().update(oldInfo);

            Log.d(TAG, "doInBackground: finished removing foodID: " + food.getFoodID());


            return null;
        }
    }


}
