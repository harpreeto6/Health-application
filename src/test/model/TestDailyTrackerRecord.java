package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestDailyTrackerRecord {

    private  double caloriesGoal;
    private  double proteinGoal;
    private String date;
    private DailyTracker day1;

    private  double caloriesGoal2;
    private  double proteinGoal2;
    private String date2;
    private DailyTracker day2;

    private DailyTrackerRecord record;

    @BeforeEach
    void runBefore() {

        caloriesGoal = 2000;
        proteinGoal = 100;
        date = "05-05-05";
        day1 = new DailyTracker(date, proteinGoal, caloriesGoal);

        caloriesGoal2 = 2000;
        proteinGoal2 = 100;
        date2 = "05-05-06";
        day2 = new DailyTracker(date2, proteinGoal2, caloriesGoal2);

        record = new DailyTrackerRecord();
    }

    @Test
    void constructorTest() {
        assertEquals(0,record.getRecord().size());
    }

    @Test
    void containsTest() {
        record.addDailyTracker(day1);
        record.addDailyTracker(day2);
        assertEquals(0,record.contains(day1.getDate()));
        assertEquals(1, record.contains(day2.getDate()));
        assertEquals(-1,record.contains("12-12-12"));
    }

    @Test
    void addTrackerTest() {
        assertTrue(record.addDailyTracker(day1));
        assertTrue(record.addDailyTracker(day2));
        assertFalse(record.addDailyTracker(day1));
        assertFalse(record.addDailyTracker(day2));
    }

}
