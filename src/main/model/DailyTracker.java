package model;

import java.util.ArrayList;
import java.util.List;

public class DailyTracker {

    private String date;
    private List<Food> foodRecord;
    private int caloriesConsumed = 0;
    private int fatConsumed = 0;
    private int proteinConsumed = 0;
    private int carbohydratesConsumed = 0;

    //EFFECT: Constructs a  DailyTracker and set up the fields with appropriate values
    public DailyTracker(String date, Food food) {
        foodRecord = new ArrayList<>();
        this.date = date;
        foodRecord.add(food);
        carbohydratesConsumed = food.getCarbohydates();
        proteinConsumed = food.getProtein();
        fatConsumed = food.getFat();
        caloriesConsumed = food.getCalories();
        
    }

    //MODIFIES: this
    //EFFECT: add new food into foodRecord List and insert other attributes of Food into appropriate fields
    public void addFood(Food food) {
        this.foodRecord.add(food);
        caloriesConsumed += food.getCalories();
        fatConsumed += food.getCalories();
        proteinConsumed += food.getProtein();
        caloriesConsumed += food.getCarbohydates();
    }

    //EFFECT: Returns a List containing names of all the food items been eaten upto the point
    public List<String> getFoodHistory() {
        ArrayList<String> foodHistory = new ArrayList<>();

        for(Food item : foodRecord) {
            foodHistory.add(item.getName());
        }
        return foodHistory;
    }

    //EFFECT: Returns the amount of calories Consumed throughout day
    public int getCaloriesConsumed() {
        return caloriesConsumed;
    }

    //EFFECT: Returns the amount of protein Consumed throughout day
    public int getProteinConsumed() {
        return proteinConsumed;
    }

    //EFFECT: Returns the amount of fat Consumed throughout day
    public int getFatConsumed() {
        return fatConsumed;
    }
    
    //EFFECT: Returns the amount of carbohydrates Consumed throughout day
    public int getCarbohydratesConsumed() {
        return carbohydratesConsumed;
    }

    //EFFECT: return the date stored as String
    public String getDate() {
        return this.date;
    }

}
