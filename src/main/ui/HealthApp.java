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


/*  
 * user will be able to interacte with app through this class
 * user will be able to add different food items eaten throughout the day into the app
 * user will be able to see their progress towards their Diet goals
 * user will be able to see history of food items eaten
 * user will get reminder if they are not making progress towards their goals
 */
public class HealthApp {

    private List<DailyTracker> dailyRecord;
    private DailyTracker dailyTracker;
    private Scanner input;
    private int currentIndex = -1;                 
    private double caloriesReminder;                    
    private  double proteinReminder;                    
    private int foodItemsEaten = 0;

    private final int itemsReminder = 2;             
    private final double percentReminder = .60;      

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
        setProteinReminder(proteinGoal);
        System.out.println("\tNow enter your Calories Goal for today in cal unit : ");
        caloriesGoal = input.nextInt();
        setCaloriesReminder(caloriesGoal);
        dailyTracker = new DailyTracker(date, proteinGoal,caloriesGoal);
        System.out.println("\tThank you , setup is done !");
        System.out.println("\tDate : " + dailyTracker.getDate() + ", Protein Goal : " + dailyTracker.getProteinGoal() 
                                   + "g, Calories Goal : " + dailyTracker.getCaloriesGoal() + "cal");
        dailyRecord.add(dailyTracker);
        currentIndex++; 
        foodItemsEaten = 0;
    }

    //EFFECTS: displaying menu of options to user
    private void displayMenu() {
        System.out.println("\n\tSelect from:");
        System.out.println("\ta -> add new meal eaten");
        System.out.println("\th -> get meals eaten");
        System.out.println("\ts -> show progess");
        System.out.println("\tn -> start new day");
        System.out.println("\tt -> check if food is healthy");
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
                case("t"): 
                decideToAdd(createFood());
                break;
            default: 
                System.out.println("\tSelection not valid...");
        }   
    }

    //EFFECT: initiate food item by asking user for nutrition value and add it to dailyTracker,
    //        increases the currentIndex Counter, provide reminders if needed
    private void addFood() {
        Food food = createFood();
        addFoodToTracker(food);
    }

    //EFFECT: showing daily progress
    private void showProgress() {
        System.out.println("\tYour Progress for today uptil now is : ");
        System.out.println("\tYou consumed " + dailyRecord.get(currentIndex).getCaloriesConsumed() 
                            + " calories, Your Goal is : " 
                            + dailyRecord.get(currentIndex).getCaloriesGoal() + "calories");
        System.out.println("\tYou consumed " + dailyRecord.get(currentIndex).getProteinConsumed() 
                            + "g of protein, Your Goal is : " + dailyRecord.get(currentIndex).getProteinGoal() + "g");
        System.out.println("\tYou consumed " + dailyRecord.get(currentIndex).getCarbohydratesConsumed() 
                            + "g of Carbohyddrates");
        System.out.println("\tYou consumed " + dailyRecord.get(currentIndex).getFatConsumed() 
                            + "g of fat");
    }

    //EFFECT: provide the list of food items eaten throughout the day with their nutritional Value
    private void foodRecord() {
        List<Food> record = dailyRecord.get(currentIndex).getFoodRecord();

        for (Food item : record) {
            System.out.println("\tMeal eaten :" + item.getName() + " ,protein : " 
                                + item.getProtein().getValue() + "g, carbohydrates : " 
                                + item.getCarbohydates().getValue() + "g, fat : " + item.getFat().getValue() + "g");
        }
    }

    //EFFECT: provide reminders if foodItemsEaten > itemsReminder 
    private void reminders() {
        if (foodItemsEaten >= itemsReminder) { 
            if (dailyRecord.get(currentIndex).getProteinConsumed() <= proteinReminder) {
                System.out.println("\t YOU ARE BEHIND YOUR PROTEIN GOAL !!");
            } else if (dailyRecord.get(currentIndex).getProteinConsumed() 
                        >= dailyRecord.get(currentIndex).getProteinGoal()) { 
                System.out.println("\t YOU ARE OVER YOUR PROTEIN GOAL !!");
            }
            if (dailyRecord.get(currentIndex).getCaloriesConsumed() <= caloriesReminder) {
                System.out.println("\t YOU ARE BEHIND YOUR CALORIES GOAL");
            } else if (dailyRecord.get(currentIndex).getCaloriesConsumed() 
                        >= dailyRecord.get(currentIndex).getCaloriesGoal()) {
                System.out.println("\t YOU ARE OVER YOUR CALORIES GOAL");
            }
        }
    }


    //MODIFY: this
    //EFFECT: set proteinReminder field to 60 % of protein Goal
    private void setProteinReminder(int proteinGoal) {
        proteinReminder = .60 * percentReminder;
    }

    //EFFECT: set caloriesReminder to field
    private void setCaloriesReminder(int caloriesGoal) {
        caloriesReminder = .60 * percentReminder;
    }

    //EFFECT: show user if Food item is high in protein and/or calories and ask if they want to add it
    public void decideToAdd(Food food) {
        if (food.isHighInCalories()) {
            System.out.println("\tThe calorie content in this food is HIGH !!");
        } else {
            System.out.println("\tThe calorie content in this food is LOW !!");
        }

        if (food.isHighInProtein()) {
            System.out.println("\tThe protein content in this food is HIGH !!");
        } else {
            System.out.println("\tThe protein content in this food is LOW !!");
        }

        String userInput;
        boolean running = true;
        while (running) {
            System.out.println("\ta -> add this meal to tracker ");
            System.out.println("\tn -> do not add this meal to tracker ");
            userInput = input.next();
            userInput.toLowerCase();
            switch(userInput) {
                case("n") :
                    running = false;
                    break;
                case("a") :
                    addFoodToTracker(food);
                    running = false;
                    break;
                default :
                System.out.println("\tInvalid Input !!");
            }
        }
    }

    //EFFECT: Ask user for input in order to make food object, return the object
    public Food createFood() {
        String name;
        int calories;
        int protein;
        int carbohydrates;
        int fat;
        Food food;
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
        return food;
    }

    //MODIFY: this
    //EFFECT: add food to dailyTracker, increase currentIndex and give reminders if needed
    public void addFoodToTracker(Food food) {
        dailyTracker.addFood(food);
        System.out.println("\tMeal added successfully !");     
        foodItemsEaten++;
        reminders();
    }
}
