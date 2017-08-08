package com.example.kleog.fitnessapp;

import android.content.Context;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;
import java.util.Date;
import com.example.kleog.fitnessapp.Models.UserNutritionDB;
import com.example.kleog.fitnessapp.Models.DailyUserInfoModel;

import org.junit.Before;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest extends InstrumentationTestCase {

    Context context;
    @Before
    public void setUp() throws Exception {
        super.setUp();

        context = new MockContext();

        assertNotNull(context);

    }
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    public void test_insertingAndRetrievingFromDatabase() throws Exception {
        UserNutritionDB db = UserNutritionDB.getDatabase(context);
        db.DailyUserInfoModel().insert(new DailyUserInfoModel(new Date(), 150, 100, 75, 50, 73));
        assertNotNull(db.DailyUserInfoModel().getAll());
    }
}