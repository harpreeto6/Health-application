package model;

import java.util.ArrayList;
import java.util.List;

public class DailyTracker {

    private String date;
    private List<Food> foodRecord;
    private int caloriesConsumed;
    private int fatConsumed;
    private int proteinConsumed;
    private int carbohydratesConsumed;

    //EFFECT: Constructs a  DailyTracker and set up the fields with appropriate values
    public DailyTracker(String date, Food food) {
        //stub
    }

    //MODIFIES: this
    //EFFECT: add new food into foodRecord List and insert other attributes of Food into appropriate fields
    public void addFood(Food food) {
        //stub
    }

    //EFFECT: Returns a List containing names of all the food items been eaten upto the point
    public List<String> foodHistory() {
        //stub
        return null;
    }

    //EFFECT: Returns the amount of calories Consumed throughout day
    public int caloriesConsumed() {
        //stub
        return 0;
    }

    //EFFECT: Returns the amount of calories Consumed throughout day
    public int proteinConsumed() {
        //stub
        return 0;
    }

    //EFFECT: Returns the amount of calories Consumed throughout day
    public int fatConsumed() {
        //stub
        return 0;
    }
    
    //EFFECT: Returns the amount of calories Consumed throughout day
    public int carbohydratesConsumed() {
        //stub
        return 0;
    }

}
