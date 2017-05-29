package com.example.kleog.fitnessapp;

/**
 * Created by Kevin on 29/05/2017.
 */

public enum AmountEatenType {
    GRAMS {
        @Override
        public String toString() {
            return "grams";
        }
    },
    Pounds {
        @Override
        public String toString() {
            return "lbs";
        }
    },
    UNITS {
        @Override
        public String toString() {
            return "units";
        }
    }

}
