package model;

import static org.junit.Assert.assertEquals;
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
    private Food meal4;

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
    }

    @Test // Tested in addFood method
    void foodHistoryTest() {

    }

    @Test
    void proteinConsumedTest() {
        
    }

    @Test
    void carbohydratesConsumedTest() {
        
    }

    @Test
    void fatConsumedTest() {
        
    }

    @Test
    void caloriesConsumedTest() {
        
    }
}
