package com.example.kleog.fitnessapp;

/**
 * Created by Kevin on 29/05/2017.
 */

public enum MealType {
    BREAKFAST {
        @Override
        public String toString() {
            return "Breakfast";
        }
    },
    LUNCH {
        @Override
        public String toString() {
            return "Lunch";
        }
    },
    DINNER {
        @Override
        public String toString() {
            return "Dinner";
        }
    },
    SNACKS{
        @Override
        public String toString() {
            return "Snacks";
        }
    }
}
