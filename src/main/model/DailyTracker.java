package model;

import java.util.ArrayList;
import java.util.List;

public class DailyTracker {

    private  int caloriesGoal;
    private  int proteinGoal;

    private String date;
    private List<Food> foodRecord;
    private int caloriesConsumed = 0;
    private int fatConsumed = 0;
    private int proteinConsumed = 0;
    private int carbohydratesConsumed = 0;

    //Requires: caloriesGoal and proteinGoal >= 0
    //EFFECT: Constructs a DailyTracker by setting up only Date, caloriesGoal and proteinGoal
    public DailyTracker(String date, int proteinGoal, int caloriesGoal) {
        foodRecord = new ArrayList<>();
        this.date = date;
        this.proteinGoal = proteinGoal;
        this.caloriesGoal = caloriesGoal;
    }

    //EFFECT: Constructs a  DailyTracker and set up the fields with appropriate values
    public DailyTracker(String date, Food food, int caloriesGoal, int proteinGoal) {
        foodRecord = new ArrayList<>();
        this.date = date;
        foodRecord.add(food);
        carbohydratesConsumed = food.getCarbohydates();
        proteinConsumed = food.getProtein();
        fatConsumed = food.getFat();
        caloriesConsumed = food.getCalories();
        this.caloriesGoal = caloriesGoal;
        this.proteinGoal = proteinGoal;
        
    }

    //MODIFIES: this
    //EFFECT: add new food into foodRecord List and insert other attributes of Food into appropriate fields
    public void addFood(Food food) {
        foodRecord.add(food);
        caloriesConsumed += food.getCalories();
        fatConsumed += food.getCalories();
        proteinConsumed += food.getProtein();
        carbohydratesConsumed += food.getCarbohydates();
    }

    //EFFECT: Returns a List containing names of all the food items been eaten upto the point
    public List<String> getFoodHistory() {
        ArrayList<String> foodHistory = new ArrayList<>();
        for (Food item : foodRecord) {
            foodHistory.add(item.getName());
        }
        return foodHistory;
    }

    //EFFECT: Returns the amount of calories Consumed throughout day
    public int getCaloriesConsumed() {
        return this.caloriesConsumed;
    }

    //EFFECT: Returns the amount of protein Consumed throughout day
    public int getProteinConsumed() {
        return this.proteinConsumed;
    }

    //EFFECT: Returns the amount of fat Consumed throughout day
    public int getFatConsumed() {
        return this.fatConsumed;
    }
    
    //EFFECT: Returns the amount of carbohydrates Consumed throughout day
    public int getCarbohydratesConsumed() {
        return this.carbohydratesConsumed;
    }

    //EFFECT: return the date stored as String
    public String getDate() {
        return this.date;
    }

    //EFFECT: return the number of food items eaten
    public int getNumFoodItems() {
        return this.foodRecord.size();
    }

    //EFFECT: return the daily calories goal 
    public int getCaloriesGoal() {
        return this.caloriesGoal;
    }

    //EFFECT: return the daily protein goal field
    public int getProteinGoal() {
        return this.proteinGoal;
    }

    //EFFECT: return the foodRecord for today
    public List<Food> getFoodRecord() {
        return this.foodRecord;
    }

    //MODIFIES: this
    //EFFECT: remove the food item from the list of items been eaten
    public boolean removeItem(String st) {
        int i = 0;
        for (Food item : foodRecord) {
            if (item.getName() == st) {
                this.caloriesConsumed -= item.getCalories();
                this.proteinConsumed -= item.getProtein();
                this.carbohydratesConsumed -= item.getCalories();
                this.fatConsumed -= item.getFat();
                foodRecord.remove(i);
                return true;
            }
            i++;
        }
        return false;
    }

}
