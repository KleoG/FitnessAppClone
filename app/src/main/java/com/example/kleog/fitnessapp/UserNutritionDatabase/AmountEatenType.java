package com.example.kleog.fitnessapp.UserNutritionDatabase;

/**
 * Created by Kevin on 29/05/2017.
 */

public enum AmountEatenType {
    GRAMS {
        @Override
        public String toString() {
            return "GRAMS";
        }
    },
    LBS {
        @Override
        public String toString() {
            return "LBS";
        }
    },
    UNITS {
        @Override
        public String toString() {
            return "UNITS";
        }
    }

}
