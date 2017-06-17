package com.example.kleog.fitnessapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.kleog.fitnessapp.QuantityActivity.MAX_QUANTITY;
import static com.example.kleog.fitnessapp.QuantityActivity.MIN_QUANTITY;
import static org.junit.Assert.assertEquals;

/**
 * Created by KleoG on 11/06/2017.
 */
@RunWith(AndroidJUnit4.class)
public class QuantityActivityTest {

    @Rule
    public ActivityTestRule<QuantityActivity> activityRule =
            new ActivityTestRule<>(QuantityActivity.class, true, true);

    @Test
    public void increaseQuantity() {
        QuantityActivity quantityActivity = activityRule.getActivity();
        int quantityStartAmount = 20;
        quantityActivity.setQuantity(quantityStartAmount);

        onView(withId(R.id.increaseButton)).perform(click());
        assertEquals(quantityStartAmount + 1, quantityActivity.getQuantity());
    }

    @Test
    public void decreaseQuantity() {
        QuantityActivity quantityActivity = activityRule.getActivity();
        int quantityStartAmount = 20;
        quantityActivity.setQuantity(quantityStartAmount);
        onView(withId(R.id.decreaseButton)).perform(click());
        assertEquals(quantityStartAmount - 1, quantityActivity.getQuantity());
    }

    // Ensure the max qty limit is adhered to
    @Test
    public void limitUpperValueQuantity() {
        QuantityActivity quantityActivity = activityRule.getActivity();
        quantityActivity.setQuantity(MAX_QUANTITY);
        onView(withId(R.id.increaseButton)).perform(click());
        assertEquals(MAX_QUANTITY, quantityActivity.getQuantity());
    }

    // Ensure the min qty limit is adhered to
    @Test
    public void limitLowerValueQuantity() {
        QuantityActivity quantityActivity = activityRule.getActivity();
        quantityActivity.setQuantity(MIN_QUANTITY);
        onView(withId(R.id.decreaseButton)).perform(click());
        assertEquals(MIN_QUANTITY, quantityActivity.getQuantity());
    }
}
