package com.example.kleog.fitnessapp;

import android.content.Context;
import android.view.View;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by KleoG on 11/06/2017.
 */

public class QuantityTest {

    public QuantityTest(){

    }

    // simple test, test which checks whether the quantity is as it is expected
    @Test
    public void increaseQuantityTest(){
        QuantityActivity quantityActivity = new QuantityActivity();

        int quantity = 1;

        quantityActivity.setQuantity(1);

        assertEquals(quantityActivity.getQuantity(), quantity);

    }

    // this is a simple test to test the decrease in quantity
    @Test
    public void decreaseQuantityTest(){
        QuantityActivity quantityActivity = new QuantityActivity();

        int quantityStartAmount = 20;
        quantityActivity.setQuantity(quantityStartAmount);

        int quantityChange = -1;
        quantityActivity.setQuantity(quantityChange);

        assertEquals(quantityActivity.getQuantity(), quantityStartAmount + quantityChange);
    }

    /**
     * there's two tests which are same just with different values, could this be done better?
     */
    // this test checks whether their is a limit on the quantity amount the user can input
    // no less than 0 and no more than 100
    @Test
    public void limitUpperValueQuantityTest(){
        QuantityActivity quantityActivity = new QuantityActivity();

        int quantityStartAmount = 100;
        quantityActivity.setQuantity(quantityStartAmount);

        int quantityChange = 1;
        quantityActivity.setQuantity(quantityChange);

        assertEquals(quantityActivity.getQuantity(), quantityStartAmount);

    }

    // this test checks whether their is a limit on the quantity amount the user can input
    // no less than 0 and no more than 100
    @Test
    public void limitLowerValueQuantityTest(){
        QuantityActivity quantityActivity = new QuantityActivity();

        int quantityStartAmount = 0;
        quantityActivity.setQuantity(quantityStartAmount);

        int quantityChange = -1;
        quantityActivity.setQuantity(quantityChange);

        assertEquals(quantityActivity.getQuantity(), quantityStartAmount);

    }

}
