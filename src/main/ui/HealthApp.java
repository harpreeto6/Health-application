package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import model.Calories;
import model.Carbohydrates;
import model.DailyTracker;
import model.DailyTrackerRecord;
import model.Fat;
import model.Food;
import model.Protein;
import persistence.JsonReader;
import persistence.JsonWriter;


/*  
 * user will be able to interacte with app through this class
 * user will be able to add different food items eaten throughout the day into the app
 * user will be able to see their progress towards their Diet goals
 * user will be able to see history of food items eaten
 * user will get reminder if they are not making progress towards their goals
 */
public class HealthApp {

    private DailyTrackerRecord dailyTrackerRecord;
    private DailyTracker dailyTracker;
    private Scanner input;                
    private double caloriesReminder;                    
    private  double proteinReminder;                    

    private final int itemsReminder = 3;             
    private final double percentReminder = .60;   
    
    private static final String JSON_STORE = "./data/dailyTrackerRecord.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //MODIFIES: this
    //EFFECT: runs the HealthApp application by initiating necessary fields 
    public HealthApp() {
        dailyTrackerRecord = new DailyTrackerRecord();
        userInterface();
    }

    //MODIFY: this
    //EFFECT: runs the user interface where user can interact with app
    private void userInterface() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        boolean running = true;
        String userInput;
        System.out.println("\tWelcome to your personal Health app");
        initialSetup();
        
        while (running) {
            displayMenu();
            userInput = input.next();
            userInput.toLowerCase();
            if (userInput.equals("q")) {
                System.out.println("GoodBye !!");
                running = false;
                break;
            } else {
                processUserInput(userInput);
            }
        }
    }

    //MODIFIES: this
    //EFFECT: ask user if they want to load previous data or start new day,
            // if no previous data found ask your to setup new dailyTracker.
    private void initialSetup() {
        String userInput;
        boolean running = true;
        
        input = new Scanner(System.in);     
        while (running) {
            System.out.println("\tn -> start a new day");
            System.out.println("\tl -> load current data");
            userInput = input.next();
            userInput.toLowerCase();
            if (userInput.equals("n")) {
                running = false;
                dailyTrackerSetup();              
            } else if (userInput.equals("l")) {
                if (loadDailyTrackerRecord() == true) {
                    if (dailyTrackerRecord.getRecord().isEmpty()) {
                        System.out.println("\tNo previous record found, You can start a new day Now :-)");
                        dailyTrackerSetup();
                    }
                }
                running = false;
            } else {
                System.out.println("\tSelection not valid...");
            }
        }



    }

    //MODIFY: this
    //EFFECT: asking user to input current date and goals
    //        Constructs a new dailyTracker and add it into dailyRecord
    private void dailyTrackerSetup() {
        double proteinGoal;
        double caloriesGoal;
        String date;
        input = new Scanner(System.in);
        System.out.println("\tPlease enter Today's date in DD-MM-YY Format : ");
        date = getFormattedDate();
        System.out.println("\tNow enter your Protein Goal for today in grams : ");
        proteinGoal = getDoubleFromUser();
        setProteinReminderValue(proteinGoal);
        System.out.println("\tNow enter your Calories Goal for today in cal unit : ");
        caloriesGoal = getDoubleFromUser();
        setCaloriesReminderValue(caloriesGoal);
        dailyTracker = new DailyTracker(date, proteinGoal,caloriesGoal);
        System.out.println("\tThank you , setup is done !");
        System.out.println("\tDate : " + dailyTracker.getDate() + ", Protein Goal : " + dailyTracker.getProteinGoal() 
                                   + "g, Calories Goal : " + dailyTracker.getCaloriesGoal() + " cal");
        dailyTrackerRecord.addDailyTracker(dailyTracker);
        //currentIndex++; 
        //foodItemsEaten = 0;
    }

    //EFFECTS: displaying menu of options to user
    private void displayMenu() {
        System.out.println("\n\tSelect from:");
        System.out.println("\ta -> add new meal eaten");
        System.out.println("\th -> get meals eaten");
        System.out.println("\tb -> to add calories burned");
        System.out.println("\ts -> show progess");
        System.out.println("\tn -> start new day");
        System.out.println("\tt -> check if food is healthy");
        System.out.println("\tw -> to save your data");
        System.out.println("\tl -> to load your previous data");
        System.out.println("\tc -> to check stats for previous dates");
        System.out.println("\tq -> quit");

    }

    //MODIFY: this
    //EFFECT: performing different tasks based on what user selected, 
    //         actions are based on descriptions from displayMenu() method
    @SuppressWarnings("methodlength")
    private void processUserInput(String userInput) {
        switch (userInput) {
            case("a"):
                addFood();
                break;
            case("h"):
                foodRecord();
                break;
            case("s"): 
                showProgress((dailyTrackerRecord.getRecord().size() - 1));
                break;
            case("n"): 
                dailyTrackerSetup();
                break;
            case("t"): 
                decideToAdd(createFood());
                break;
            case("c"): 
                checkPrevProgress();
                break;
            case("b"): 
                addCaloriesBurned();
                break;
            case("w"): 
                saveDailyTrackerRecord();
                break;
            case("l"): 
                loadDailyTrackerRecord();;
                break;
            default: 
                System.out.println("\tSelection not valid...");
        }   
    }

    //MODIFIES: this
    //EFFECT: initiate food item by asking user for nutrition value and add it to dailyTracker,
    //        increases the currentIndex Counter, provide reminders if needed
    private void addFood() {
        Food food = createFood();
        addFoodToTracker(food);
    }

    //REQUIRE: currentIndex <= dailyTrackerRecord.currentIndex
    //EFFECT: shows calories and protein goal for today to user and the amount of macro-nutrients user consumed
    private void showProgress(int currentIndex) {
        System.out.println("\tYour Progress stats is : ");
        System.out.println("\tYou consumed " + dailyTrackerRecord.getRecord().get(currentIndex).getCaloriesConsumed() 
                            + " calories, Your Goal is : " 
                            + dailyTrackerRecord.getRecord().get(currentIndex).getCaloriesGoal() + " calories");
        System.out.println("\tYou consumed " + dailyTrackerRecord.getRecord().get(currentIndex).getProteinConsumed() 
                            + "g of protein, Your Goal is : " 
                            + dailyTrackerRecord.getRecord().get(currentIndex).getProteinGoal() + "g");
        System.out.println("\tYou consumed " 
                            + dailyTrackerRecord.getRecord().get(currentIndex).getCarbohydratesConsumed() 
                            + "g of Carbohyddrates");
        System.out.println("\tYou consumed " + dailyTrackerRecord.getRecord().get(currentIndex).getFatConsumed() 
                            + "g of fat");
        System.out.println("\tYou burned " + dailyTrackerRecord.getRecord().get(currentIndex).getCaloriesBurned() 
                            + " calories in total");
    }

    //EFFECT: provide the list of food items eaten throughout current day with their nutritional Value
    private void foodRecord() {
        int currentIndex = (dailyTrackerRecord.getRecord().size() - 1);
        List<Food> record = dailyTrackerRecord.getRecord().get(currentIndex).getFoodRecord();

        for (Food item : record) {
            System.out.println("\tMeal eaten : " + item.getName() + ", calories : " 
                                + item.getCalories().getValue() + " cal ,protein : " 
                                + item.getProtein().getValue() + "g, carbohydrates : " 
                                + item.getCarbohydates().getValue() + "g, fat : " + item.getFat().getValue() + "g");
        }
    }

    //EFFECT: provide reminders if foodItemsEaten >= itemsReminder 
    private void reminders() {
        //int currentIndex = (dailyTrackerRecord.getRecord().size() - 1);
        int foodItemsEaten = (dailyTracker.getNumFoodItems());
        if (foodItemsEaten >= itemsReminder) { 
            if (dailyTracker.getProteinConsumed() < proteinReminder) {
                System.out.println("\t YOU ARE BEHIND YOUR PROTEIN GOAL !!");
            } else { 
                System.out.println("\t YOU ARE OVER YOUR PROTEIN GOAL !!");
            }
            if (dailyTracker.getCaloriesConsumed() < caloriesReminder) {
                System.out.println("\t YOU ARE BEHIND YOUR CALORIES GOAL !! ");
            } else {
                System.out.println("\t YOU ARE OVER YOUR CALORIES GOAL !! ");
            }
        }
    }

    //MODIFY: this
    //EFFECT: set proteinReminder field to proteinGoal * percentReminder
    private void setProteinReminderValue(double proteinGoal) {
        proteinReminder = proteinGoal * percentReminder;
    }

    //EFFECT: set caloriesReminder field to caloriesGoal * percentReminder
    private void setCaloriesReminderValue(double caloriesGoal) {
        caloriesReminder = caloriesGoal * percentReminder;
    }

    //MODIFY: this
    //EFFECT: show user if Food item is high in protein and/or calories and ask if they want to add it to tracker
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
        wantToAdd(food);      
    }

    //MODIFY: this
    //EFFECT: ask user if they want to or don't want to add given food into dailyTracker,
    //        then perform selected operation
    public void wantToAdd(Food food) {
        String userInput;
        boolean running = true;
        while (running) {
            System.out.println("\ta -> add this meal to tracker ");
            System.out.println("\tn -> do not add this meal to tracker ");
            userInput = input.next();
            userInput.toLowerCase();
            switch (userInput) {
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
        double calories;
        double protein;
        double carbohydrates;
        double fat;
        Food food;
        System.out.println("\n\tEnter the name of the food item(s) : ");
        name = input.next();
        System.out.println("\tNow enter the calories contained in this item : ");
        calories = getDoubleFromUser();
        System.out.println("\tNow enter the amout of protein in this item : ");
        protein = getDoubleFromUser();
        System.out.println("\tNow enter the amout of carbohydrates in this item : ");
        carbohydrates = getDoubleFromUser();
        System.out.println("\tNow enter the amout of fat in this item : ");
        fat = getDoubleFromUser();
        food = new Food(name, new Calories(calories),new Protein(protein),
                             new Carbohydrates(carbohydrates), new Fat(fat));
        return food;
    }

    //MODIFY: this
    //EFFECT: add food to dailyTracker, give reminders if needed
    public void addFoodToTracker(Food food) {
        dailyTracker.addFood(food);
        System.out.println("\tMeal added successfully !");     
        //foodItemsEaten++;
        reminders();
    }

    // MODIFIES: this
    // EFFECTS: returns true if loading dailyTrackerRecord from file is successfull
    //          returns false otherwise
    private boolean loadDailyTrackerRecord() {
        try {
            DailyTrackerRecord temp  = jsonReader.read();
            List<DailyTracker> history = dailyTrackerRecord.getRecord();
            for (DailyTracker dt : history) {
                temp.addDailyTracker(dt);
            }
            dailyTrackerRecord = temp;
            int lastRecordIndex = dailyTrackerRecord.getRecord().size() - 1;
            dailyTracker = dailyTrackerRecord.getRecord().get(lastRecordIndex);
            System.out.println("Loaded " + "previous data from " + JSON_STORE);
            return true;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            return false;
        }
    }

    // EFFECTS: saves the dailyTrackerRecord to file
    private void saveDailyTrackerRecord() {
        try {
            loadDailyTrackerRecord();
            jsonWriter.open();
            jsonWriter.write(dailyTrackerRecord);
            jsonWriter.close();
            System.out.println("Saved " + " your progess in "  + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //EFFECT: prompt user to input Double or display an error option if input is not valid 
    //        until valid user input is received (Double), return double
    public double getDoubleFromUser() {
        String userInput = input.next();
        boolean isDouble = isDouble(userInput);
        while (!isDouble) {
            System.out.println("\tPlease check the format of your Input");
            userInput = input.next();
            isDouble = isDouble(userInput);           
        }
        return Double.valueOf(userInput);
    }

    //EFFECT: return true if input can be successfully parsed into double or else return false
    public boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // EFFECT: prompt user for input, display error message if date is not in DD-MM-YY format and 
    //         keep asking user for input until it is not in correct format by showing error message
    //         return formatted date
    public String getFormattedDate() {
        String userInput = input.next();
        boolean isFormatted = isDateFormatted(userInput);
        while (!isFormatted) {
            System.out.println("\tPlease check the format of your Input");
            userInput = input.next();
            isFormatted = isDateFormatted(userInput);           
        }
        return userInput;
    }

    // EFFECT: return true if the given input for date is in DD-MM-YY format 
    public boolean isDateFormatted(String input) {
        if (input.length() != 8) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            if (i == 2 || i == 5) {
                if (input.charAt(i) != '-') {
                    return false;
                }
            }
            if (input.charAt(i) > '9') {             
                return false;
            }
            if (i == 2 || i == 5) {
                if (input.charAt(i) == '-') {
                    continue;
                }
            }          
        }
        return true;
    }

    //EFFECT: ask user to enetr the date and call showProgress() if dailyTracker
    //        with provided date exists in the daiyTrackerRecord, 
    //        otherwise shows an error message displaying no dailyTracker forgiven date exists.
    public void checkPrevProgress() {
        System.out.println("\tPlease enter the date for which you want to check stats");
        String date = getFormattedDate();

        int index = dailyTrackerRecord.contains(date);
        if (index == -1) {
            System.out.println("\tNo data exists for the date provided");
            return;
        } else {
            showProgress(index);
            return;
        }

        // boolean exists = false;
        // int index = -1;
        // for (DailyTracker dt : history) {
        //     index++;
        //     if (dt.getDate().equals(date)) {
        //         exists = true;
        //         break;
        //     }
        // }
        // if (exists == false) {
        //     System.out.println("\tNo data exists for the date provided");
        //     return;
        // } else {
        //     showProgress(index);
        //     return;
        // }
    }

    //MODIFY: this
    //EFFECT: ask user to input calories burned and add it to tracker 
    public void addCaloriesBurned() {
        System.out.println("\tPlease enter the amount of calories burned");
        double caloriesBurned = getDoubleFromUser();
        dailyTracker.addCaloriesBurned(caloriesBurned);
    }
}
