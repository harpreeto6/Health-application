package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import model.DailyTracker;
import model.Food;

public class JsonTest {

    protected void checkFood(String name, double calories, double protein, double carbohydrates, double fat, Food f2) {
        assertTrue(name.equals(f2.getName()));
        assertEquals(calories, f2.getCalories().getValue());
        assertEquals(protein, f2.getProtein().getValue());
        assertEquals(carbohydrates, f2.getCarbohydates().getValue());
        assertEquals(fat, f2.getFat().getValue());
    }

    protected void checkDailyTrackerFields(String date, double caloriesGoal, double proteinGoal, 
                                          double caloriesBurned, double caloriesConsumed, double proteinConsumed,
                                          double carbohydratesConsumed, double fatConsumed, DailyTracker d) {

        assertTrue(date.equals(d.getDate()));
        assertEquals(caloriesGoal, d.getCaloriesGoal());
        assertEquals(proteinGoal, d.getProteinGoal());
        assertEquals(caloriesBurned, d.getCaloriesBurned());
        assertEquals(caloriesConsumed, d.getCaloriesConsumed());
        assertEquals(proteinConsumed, d.getProteinConsumed());
        assertEquals(carbohydratesConsumed, d.getCarbohydratesConsumed());

    }
}
