package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Calories;
import model.Carbohydrates;
import model.DailyTracker;
import model.Fat;
import model.Food;
import model.Protein;

public class HealthApp {

    private List<DailyTracker> dailyRecord;
    private DailyTracker dailyTracker;
    private Food food;
    private Scanner input;
    private int currentIndex = -1;

    //MODIFIES: this
    //EFFECT: runs the HealthApp application by initiating necessary fields
    public HealthApp() {
        dailyRecord = new ArrayList<>();
        userInterface();
    }

    //MODIFY: this
    //EFFECT: runs the user interface where user can interact with app
    private void userInterface() {
        input = new Scanner(System.in);
        boolean running = true;
        String userInput;
        System.out.println("\tWelcome to your personal Health app");
        dailyTrackerSetup();
        
        while (running) {
            displayMenu();
            userInput = input.next();
            userInput.toLowerCase();
            if (userInput == "q") {
                running = false;
            } else {
                processUserInput(userInput);
            }
        }
    }

    //MODIFY: this
    //EFFECT: asking user to input current date and goals
    //        Constructs a new dailyTracker and add it into dailyRecord
    private void dailyTrackerSetup() {
        int proteinGoal;
        int caloriesGoal;
        String date;
        input = new Scanner(System.in);
        System.out.println("\tPlease enter Today's date in DD-MM-YY Format: ");
        date = input.next();
        System.out.println("\tNow enter your Protein Goal for today in grams : ");
        proteinGoal = input.nextInt();
        System.out.println("\tNow enter your Calories Goal for today in cal unit : ");
        caloriesGoal = input.nextInt();
        dailyTracker = new DailyTracker(date, proteinGoal,caloriesGoal);
        System.out.println("\tThank you , setup is done !");
        System.out.println("\tDate : " + dailyTracker.getDate() + ", Protein Goal : " + dailyTracker.getProteinGoal() 
                                   + "g, Calories Goal : " + dailyTracker.getCaloriesGoal() + "cal");
        dailyRecord.add(dailyTracker);
        currentIndex++; 
    }

    //EFFECTS: displaying menu of options to user
    private void displayMenu() {
        System.out.println("\n\tSelect from:");
        System.out.println("\ta -> add new meal eaten");
        System.out.println("\th -> get meals eaten");
        System.out.println("\ts -> show progess");
        System.out.println("\tn -> start new day");
        System.out.println("\tq -> quit");
    }

    //MODIFY: this
    //EFFECT: performing different tasks based on what user selected
    private void processUserInput(String userInput) {
        switch (userInput) {
            case("a"):
                addFood();
                break;
            case("h"):
                foodRecord();
                break;
            case("s"): 
                showProgress();               
                break;
            case("n"): 
                dailyTrackerSetup();
                break;
            default: 
                System.out.println("\tSelection not valid...");
        }   
    }

    //EFFECT: initiate food item by asking user for nutrition value and it to dailyTracker,
    //        increases the current Index Counter
    private void addFood() {
        input = new Scanner(System.in);
        String name;
        int calories;
        int protein;
        int carbohydrates;
        int fat;
        System.out.println("\n\tEnter the name of the food item(s) : ");
        name = input.next();
        System.out.println("\tNow enter the calories contained in this item : ");
        calories = input.nextInt();
        System.out.println("\tNow enter the amout of protein in this item : ");
        protein = input.nextInt();
        System.out.println("\tNow enter the amout of carbohydrates in this item : ");
        carbohydrates = input.nextInt();
        System.out.println("\tNow enter the amout of fat in this item : ");
        fat = input.nextInt();
        food = new Food(name, new Calories(calories),new Protein(protein),
                             new Carbohydrates(carbohydrates), new Fat(fat));
        dailyTracker.addFood(food);
        System.out.println("\tMeal added successfully !");     
    }

    //EFFECT: showing daily progress
    private void showProgress() {
        System.out.println("\tYour Progress for today uptil now is : ");
        System.out.println("\tYou consumed : " + dailyRecord.get(currentIndex).getCaloriesConsumed() 
                            + " calories, Your Goal was : " 
                            + dailyRecord.get(currentIndex).getCaloriesGoal() + "calories");
        System.out.println("\tYou consumed : " + dailyRecord.get(currentIndex).getProteinConsumed() 
                            + "g of protein, Your Goal was : " + dailyRecord.get(currentIndex).getProteinGoal() + "g");
        System.out.println("\tYou consumed : " + dailyRecord.get(currentIndex).getCarbohydratesConsumed() 
                            + "g of Carbohyddrates");
        System.out.println("\tYou consumed : " + dailyRecord.get(currentIndex).getFatConsumed() 
                            + "g of fat");
    }

    private void foodRecord() {
        List<Food> record = dailyRecord.get(currentIndex).getFoodRecord();

        for (Food item : record) {
            System.out.println("\tMeal eaten :" + item.getName() + " ,protein : " 
                                + item.getCarbohydates() + "g, carbohydrates : " 
                                + item.getCarbohydates() + "g, fat : " + item.getFat() + "g");
        }
    }
}
