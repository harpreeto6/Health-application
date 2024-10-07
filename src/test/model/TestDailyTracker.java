package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDailyTracker {
    
    private DailyTracker day1;
    private Food meal1;
    private Food meal2;
    private Food meal3;

    private String date;
    private Calories calories, calories2, calories3;
    private Protein protein, protein2, protein3;
    private Fat fat, fat2, fat3;
    private Carbohydrates carbohydrates, carbohydrates2, carbohydrates3;

    @BeforeEach
    void runBefore() {

        protein = new Protein(10);
        calories = new Calories(10);
        carbohydrates = new Carbohydrates(10);
        fat = new Fat(10);
        date = "1-05-24";
        meal1 = new Food("Salad",calories,protein,carbohydrates,fat );
        day1 = new DailyTracker(date, meal1);
    }

    void addMealNumSecond() {
        protein2 = new Protein(20);
        calories2 = new Calories(20);
        carbohydrates2 = new Carbohydrates(20);
        fat2 = new Fat(20);
        meal2 = new Food("Burger", calories2, protein2,carbohydrates2,fat2 );
        day1.addFood(meal2);
    }

    void addMealNumThird() {
        protein3 = new Protein(30);
        calories3 = new Calories(30);
        carbohydrates3 = new Carbohydrates(30);
        fat3 = new Fat(30);
        meal3 = new Food("Rice", calories3, protein3,carbohydrates3,fat3);
        day1.addFood(meal3);
    }

    @Test
    void ConstructorTest() { 
        assertEquals("1-05-24", day1.getDate());
        assertEquals(this.meal1.getName(),day1.getFoodHistory().get(0));
    }

    @Test
    void addFoodTest() {
        List<String> history;

        protein2 = new Protein(20);
        calories2 = new Calories(20);
        carbohydrates2 = new Carbohydrates(20);
        fat2 = new Fat(20);

        protein3 = new Protein(30);
        calories3 = new Calories(30);
        carbohydrates3 = new Carbohydrates(30);
        fat3 = new Fat(30);

        meal2 = new Food("Burger", calories2, protein2,carbohydrates2,fat2 );
        meal3 = new Food("Rice", calories3, protein3,carbohydrates3,fat3);
        day1.addFood(meal2);
        day1.addFood(meal3);

        history = day1.getFoodHistory();
        assertEquals("Salad",history.get(0));
        assertEquals("Burger",history.get(1));
        assertEquals("Rice",history.get(2));
        assertEquals(3, day1.getNumFoodItems());
    }

    @Test // Tested in addFood method
    void foodHistoryTest() {
        List<String> history;

        addMealNumSecond();
        addMealNumThird();
        history = day1.getFoodHistory();

        assertEquals("Salad",history.get(0));
        assertEquals("Burger",history.get(1));
        assertEquals("Rice",history.get(2));
        day1.removeItem("Salad");
        history = day1.getFoodHistory();

        assertEquals("Burger",history.get(0));
        assertEquals("Rice",history.get(1));
        assertEquals(2, day1.getNumFoodItems());

    }

    @Test
    void proteinConsumedTest() {
        addMealNumSecond();       
        assertEquals(30 , day1.getProteinConsumed());
        addMealNumThird();
        assertEquals(60, day1.getProteinConsumed());

    }

    @Test
    void carbohydratesConsumedTest() {
        addMealNumSecond();       
        assertEquals(30 , day1.getCarbohydratesConsumed());
        addMealNumThird();
        assertEquals(60, day1.getCarbohydratesConsumed());
    }

    @Test
    void fatConsumedTest() {
        addMealNumSecond();       
        assertEquals(30 , day1.getFatConsumed());
        addMealNumThird();
        assertEquals(60, day1.getFatConsumed());
    }

    @Test
    void caloriesConsumedTest() {
        addMealNumSecond();       
        assertEquals(30 , day1.getCaloriesConsumed());
        addMealNumThird();
        assertEquals(60, day1.getCaloriesConsumed());
    }

    @Test
    void removeItemTest() {
        addMealNumSecond();
        assertTrue(day1.removeItem("Salad"));
        assertFalse(day1.removeItem("Salad"));
        assertEquals(1,day1.getNumFoodItems());
    }
}
