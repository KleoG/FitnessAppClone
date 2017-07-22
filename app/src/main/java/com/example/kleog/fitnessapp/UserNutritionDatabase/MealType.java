package com.example.kleog.fitnessapp.UserNutritionDatabase;

/**
 * Created by Kevin on 29/05/2017.
 */

public enum MealType {
    BREAKFAST {
        @Override
        public String toString() {
            return "BREAKFAST";
        }
    },
    LUNCH {
        @Override
        public String toString() {
            return "LUNCH";
        }
    },
    DINNER {
        @Override
        public String toString() {
            return "DINNER";
        }
    },
    SNACKS {
        @Override
        public String toString() {
            return "SNACKS";
        }
    }
}
