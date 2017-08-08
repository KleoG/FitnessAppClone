package com.example.kleog.fitnessapp.Models;

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
    OUNCE {
        @Override
        public String toString() {
            return "OUNCE";
        }
    },
    ML {
        @Override
        public String toString() {
            return "ML";
        }
    },
    UNITS {
        @Override
        public String toString() {
            return "UNITS";
        }
    }

}
