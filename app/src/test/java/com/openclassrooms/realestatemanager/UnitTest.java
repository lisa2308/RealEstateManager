package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {

    @Test
    public void convertDollarToEuroTest() {
        float dollars = 1.50f;
        float convertionRate = 0.88f;
        float expectedResult = Math.round(dollars * convertionRate);

        assertEquals(expectedResult, Utils.convertDollarToEuro(dollars), 0.01);
    }

    @Test
    public void convertEuroToDollarTest() {
        float euros = 1.50f;
        float convertionRate = 1.14f;
        float expectedResult = Math.round(euros * convertionRate);

        assertEquals(expectedResult, Utils.convertEuroToDollars(euros), 0.01);
    }

    @Test
    public void getTodayDateTest() {
        Calendar c = Calendar.getInstance();
        String day = String.format("%02d", c.get(Calendar.DATE));
        String month = String.format("%02d", c.get(Calendar.MONTH) + 1);
        String year = String.valueOf(c.get(Calendar.YEAR));
        String todayDate = day + "/" + month + "/" + year;

        assertEquals(todayDate, Utils.getTodayDate());
    }
}