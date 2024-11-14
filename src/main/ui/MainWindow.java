package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
 * This class will act a GUI for the app
 * Class will be using JSwing library to create Graphical user Interface for our app
 */
public class MainWindow {


    private DailyTrackerRecord dailyTrackerRecord;
    private DailyTracker dailyTracker;            
    private double caloriesReminder;                    
    private  double proteinReminder;                    

    private final int itemsReminder = 3;             
    private final double percentReminder = .60;   
    
    private static final String JSON_STORE = "./data/dailyTrackerRecord.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JFrame frame;
    private JPanel buttonPanel;
    private JPanel foodFieldsPanel;
    private JPanel centerPanel;
    private JPanel welcomePanel;
    private JPanel welcomeButtonsPanel;


    private JButton addMealButton;
    private JButton foodRecordButton;
    private JButton newDayButton;
    private JButton loadButton;
    private JButton addGoalsButton;

    private JTextField foodNameField;
    private JTextField caloriesField;
    private JTextField carbohydratesField;
    private JTextField proteinField;
    private JTextField fatField;
    private JTextField proteinGoalField;
    private JTextField caloriesGoalField;
    private JTextField dateField;

    private final String foodNameText = "Enter food name";
    private final String caloriesText = "Enter calories";
    private final String proteinText = "Enter protein";
    private final String fatText = "Enter fat";
    private final String carbohydratesText = "Enter carbohydrates";
    private final String proteinGoalText = "Enter protein goal";
    private final String caloriesGoalText = "Enter calories goal";
    private final String dateFormatText = "DD-MM-YY";

    private boolean goalsSet = false;


    public MainWindow() {
        initialize();
    }

    //MODIFIES : this
    //EFFECTS : Initialize the fields and call appropriate methods in appropriate sequence to make the GUI run
    private void initialize() {
        dailyTrackerRecord = new DailyTrackerRecord();
       
        initializeJPanelAndJFrame();
        //frame.setVisible(true);

        frame.setTitle("Main Window");
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        initialSetup();

        // frame.add(buttonPanel, BorderLayout.SOUTH);
        // frame.add(foodFieldsPanel,BorderLayout.NORTH); 
        // frame.add(centerPanel, BorderLayout.CENTER);


     
        // buttonPanel.setBackground(Color.GRAY);

        // addButtonsToButtonPanel();      
        // createAddFoodFields();
    }

    //MODIFIES: this
    //EFFECT: set up initial GUI to ask user for his protein goals and calories goal plus inputs
    private void initialSetup() {
        
        setUpWelcomePanels();
        setUpWelcomeButtons();
        frame.add(welcomePanel, BorderLayout.NORTH);
        frame.add(welcomeButtonsPanel, BorderLayout.SOUTH);
        
        
    }

    //MODIFIES: this
    //EFFECT: remove non-necessary panels required for main menu from frame and add necessary methods if needed
    private void makeMainMenuVisible() {
        frame.remove(welcomeButtonsPanel);
        frame.remove(welcomePanel);
        centerPanel.removeAll();

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(foodFieldsPanel,BorderLayout.NORTH); 
        frame.add(centerPanel, BorderLayout.CENTER); 

        buttonPanel.setBackground(Color.GRAY);

        addButtonsToButtonPanel();      
        createAddFoodFields();

        refreshPanel(buttonPanel);
        refreshPanel(foodFieldsPanel);
        refreshPanel(centerPanel);
    }

    //MODIFIS: this
    //EFFECT: setup welcomePanel and add appropriate label to it
    private void setUpWelcomePanels() {      
        welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel welcomeLabel = new JLabel("<html><pre> Do you want to start new day or load curent day </pre></html>");
        welcomePanel.add(welcomeLabel);               
    }

    //MODIFIES: this
    //EFFECT: initialize and setup newDayButton and loadButton and add then to welcomeButtonsPanel
    //        provide functionality to both buttons similar to HealthApp class
    private void setUpWelcomeButtons() {

        welcomeButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        newDayButton = new JButton("New Day");
        loadButton = new JButton("Load Current Day");
        welcomeButtonsPanel.add(newDayButton, new FlowLayout(FlowLayout.CENTER));
        welcomeButtonsPanel.add(loadButton, new FlowLayout(FlowLayout.CENTER));

        newDayButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                newDayButtonPressed();
            }
            
        });

        loadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
            
        });
    }

    //MODIFIES: this
    //EFFECT: create several java components so user can set up new day including
    //         proteinGoalField, caloriesGoalField, dateField components
    //         also removes the panels from frame which are not required for this function
    private void newDayButtonPressed() {

        welcomePanel.removeAll();
        welcomeButtonsPanel.removeAll();
        
        frame.remove(centerPanel);
        frame.remove(buttonPanel);
        frame.remove(foodFieldsPanel);
        frame.remove(welcomeButtonsPanel);
        frame.remove(welcomePanel);

        frame.add(welcomeButtonsPanel);
        frame.add(welcomePanel);
     
        setUpGoalsDisplay();
        setUpAddGoalsButtonDisplay();   
        setUpAddGoalsButton();

        refreshPanel(welcomePanel);
        refreshPanel(welcomeButtonsPanel);            
    }

    //MODIFIES: this
    //EFFECT: initalize addGoalsButton and add it to welcomeButtonsPanel
    private void setUpAddGoalsButtonDisplay() {
        addGoalsButton = new JButton("<html><pre> Set Goals </pre><html>"); 
        welcomeButtonsPanel.add(addGoalsButton, new FlowLayout(FlowLayout.CENTER));
    }

    //MODOFIES: this
    //EFFECT: initalize fields required for setting up protein and calories goals and add it to welcomePanel
    private void setUpGoalsDisplay() {
        JLabel goalsInfo = new JLabel("<html><pre> Enter your protein and calories goal </pre></html>");
        caloriesGoalField = new JTextField(caloriesGoalText,12);
        proteinGoalField = new JTextField(proteinGoalText,12);
        dateField = new JTextField(dateFormatText,12);

        welcomePanel.add(goalsInfo);
        welcomePanel.add(caloriesGoalField);
        welcomePanel.add(proteinGoalField);
        welcomePanel.add(dateField);

        setUpTextField(proteinGoalField, proteinGoalText);
        setUpTextField(caloriesGoalField, caloriesGoalText);
        setUpTextField(dateField, dateFormatText);       
    }

    //MODIFIES: this
    //EFFECT: add functionality to addGoalsButton which will allow it it display in app if newDay is setup properly
    //        otherwise : display a message conevying new day is not set properly
    private void setUpAddGoalsButton() {

        addGoalsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                if (setUpNewDay()) {                  
                    makeMainMenuVisible();                  
                } else {
                    centerPanel.removeAll();
                    resetGoalsFields();
                    JLabel msg = new JLabel("Please check your fields again");
                    centerPanel.add(msg, new FlowLayout(FlowLayout.CENTER) );
                    frame.add(centerPanel);
                    refreshPanel(centerPanel);
                }
            }
            
        });
    }

    //MODIFY: this
    //EFFECT: Constructs a new dailyTracker from caloriesGoalField, 
    //          proteinGoalField, dateField and add it into dailyRecord
    private boolean setUpNewDay() {
        if (isDouble(proteinGoalField.getText()) && isDouble(caloriesGoalField.getText()) 
                && isDateFormatted(dateField.getText())) {
            dailyTracker = new DailyTracker(dateField.getText(), Double.valueOf(proteinGoalField.getText()),
             Double.valueOf(caloriesGoalField.getText()));

            dailyTrackerRecord.addDailyTracker(dailyTracker);
            return true;

        } else {
            return false;
        }
    }

    //MODIFIES: this
    //EFFECT: Initialize frame, buttonPanel, foodfieldsPanel, centerPanel
    private void initializeJPanelAndJFrame() {
        frame = new JFrame();

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        foodFieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    }

    //MODIFIES : this
    //EFFECTS : Make the GUI visible
    public void show() {
        frame.setVisible(true);
    }

    //MODIFIES : this
    //EFFECTS : create 2 buttons ("Add Meal" and " View \nProgress" ) and add them to buttonsPanel
    //          add ActionListner to both buttons to do the task given in their names
    private void addButtonsToButtonPanel() {

        addMealButton = new JButton("<html><pre> Add Meal </pre><html>");
        foodRecordButton = new JButton("<html><pre>   View<br>Food Record</pre></html>");
        
        buttonPanel.add(addMealButton);
        buttonPanel.add(foodRecordButton);

        setAddMealButton(addMealButton);
        setFoodRecordButton(foodRecordButton);

    }

    //REQUIRE: JButton
    //MODIFIES: this
    //EFFECT: give JButton fnctionality to create and add food into the dailyTracker
    private void setAddMealButton(JButton button1) {

        button1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.removeAll();
                refreshPanel(centerPanel);               
                if (isDouble(proteinField.getText()) && isDouble(fatField.getText())
                        && isDouble(carbohydratesField.getText()) 
                        && isDouble(caloriesField.getText()) 
                        && !(foodNameField.getText().equals(foodNameText))) {
                    addFoodToTracker(createFood());
                    resetFoodFields();
                } else {
                    JLabel lbl = new JLabel("<html><pre>Add Food failed , Please check FoodFields</pre></html>");
                    centerPanel.add(lbl);                
                }
            }          
        });      
    }

    //REQUIRES: JButton
    //MODIFIES: this
    //EFFECT: give JButton the feature of showing progress of current days nutrietion intake on jFrame
    private void setFoodRecordButton(JButton button2) {
        
        button2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel lbl;
                centerPanel.removeAll();
                refreshPanel(centerPanel);
                if (!dailyTracker.getFoodRecord().isEmpty()) {
                   
                    ArrayList<String> msg = foodRecord();                  
                    for (String i : msg) {
                        lbl = new JLabel("<html>" + i + "<br></html>");
                        centerPanel.add(lbl);
                    }
                } else {
                    lbl = new JLabel("<html>Haven't eaten any meals yet</html>");
                    centerPanel.add(lbl);
                }
            }
            
        });
         
    
    }

    //MODIFIES: thhis
    //EFFECT: reset the text inside fields required for food items to default texts
    private void resetGoalsFields() {
        caloriesGoalField.setText(caloriesGoalText);
        proteinGoalField.setText(proteinGoalText);
        dateField.setText(dateFormatText);
    }

    //MODIFIES: thhis
    //EFFECT: reset the text inside fields required for food items to default texts
    private void resetFoodFields() {
        foodNameField.setText(foodNameText);
        caloriesField.setText(caloriesText);
        proteinField.setText(proteinText);
        carbohydratesField.setText(carbohydratesText);
        fatField.setText(fatText);
    }

    


    //MODIFIES : this
    //EFFECT : initialize JTextFields required for Food setup and add them to foodFieldsPanel
    //         call setUPTextField(JTextField, String) method on initalized fields 
    private void createAddFoodFields() {

        foodNameField = new JTextField(foodNameText,14);
        caloriesField = new JTextField(caloriesText,8);
        carbohydratesField = new JTextField(carbohydratesText,8);
        proteinField = new JTextField(proteinText,8);
        fatField = new JTextField(fatText,8);

        foodFieldsPanel.add(foodNameField);
        foodFieldsPanel.add(caloriesField);
        foodFieldsPanel.add(carbohydratesField);
        foodFieldsPanel.add(proteinField);
        foodFieldsPanel.add(fatField);

        setUpTextField(foodNameField,foodNameText);
        setUpTextField(caloriesField,caloriesText);
        setUpTextField(carbohydratesField,carbohydratesText);
        setUpTextField(proteinField,proteinText);
        setUpTextField(fatField,fatText);
               
    }
    



    //MODIFIES: jTextField
    //EFFECT: remove string from jTextField if focus gained and add back string 
    //       if jTextField lost focus without getting user input 
    private void setUpTextField(JTextField textField, String string) {

        textField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(string)) {
                    textField.setForeground(Color.BLACK);
                    textField.setBackground(Color.WHITE);
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().equals("")) {
                    textField.setText(string);
                }
            }
            
        });
    }

    //REQUIRE: currentIndex <= this.currentIndex
    //EFFECT: shows calories and protein goal for today to user and the amount of macro-nutrients user consumed
    private ArrayList<String> showProgress(int currentIndex) {
        ArrayList<String> text = new ArrayList<>();
        String info = "";
        info = ("Your Progress stats is : ");
        text.add(info);
        info = "";
        info = ("You consumed " + dailyTrackerRecord.getRecord().get(currentIndex).getCaloriesConsumed() 
                + " calories, Your Goal is : " 
                + dailyTrackerRecord.getRecord().get(currentIndex).getCaloriesGoal() + " calories<");
        text.add(info);
        info = "";                   
        info = ("You consumed " + dailyTrackerRecord.getRecord().get(currentIndex).getProteinConsumed() 
                + "g of protein, Your Goal is : " 
                + dailyTrackerRecord.getRecord().get(currentIndex).getProteinGoal() + "g");
        text.add(info);
        info = "";
        info = ("\tYou consumed " 
                + dailyTrackerRecord.getRecord().get(currentIndex).getCarbohydratesConsumed() 
                + "g of Carbohyddrates");
        text.add(info);
        info = "";
        info = ("\tYou consumed " + dailyTrackerRecord.getRecord().get(currentIndex).getFatConsumed() 
                + "g of fat");
        text.add(info);
        info = "";
        info = ("\tYou burned " + dailyTrackerRecord.getRecord().get(currentIndex).getCaloriesBurned() 
                            + " calories in total");
        text.add(info);
        return text;
    } 

    //MODIFIES: this
    //EFFECT: provide the list of food items eaten throughout current day with their nutritional Value
    private ArrayList<String> foodRecord() {
        int currentIndex = (dailyTrackerRecord.getRecord().size() - 1);
        List<Food> record = dailyTrackerRecord.getRecord().get(currentIndex).getFoodRecord();
        ArrayList<String> text = new ArrayList<>();
        String ans = "";
        for (Food item : record) {
            ans = ("Meal eaten : " + item.getName() + ", calories : " 
                                + item.getCalories().getValue() + " cal ,protein : " 
                                + item.getProtein().getValue() + "g, carbohydrates : " 
                                + item.getCarbohydates().getValue() + "g, fat : " + item.getFat().getValue() + "g\n");
            text.add(ans);
            ans = "";
        }
        return text;
    }

    //EFFECT: create a food item based on the information provided in fields
    public Food createFood() {
        Food f = new Food(foodNameField.getText(),new Calories(Double.valueOf(caloriesField.getText())),
                    new Protein(Double.valueOf(proteinField.getText()) ),
                    new Carbohydrates(Double.valueOf(carbohydratesField.getText())),
                    new Fat(Double.valueOf(fatField.getText())));
        return f;
    }

    //MODIFY: this
    //EFFECT: add food to dailyTracker
    public void addFoodToTracker(Food food) {

        if (dailyTrackerRecord.getRecord().isEmpty()) {
            
        } else {
            int size = dailyTrackerRecord.getRecord().size();
            dailyTracker = dailyTrackerRecord.getRecord().get(size - 1);
            dailyTracker.addFood(food);
        }
        
    }

    // MODIFIES: this
    // EFFECTS: returns true if loading dailyTrackerRecord from file is successfull
    //          returns false otherwise
    private boolean loadDailyTrackerRecord() {
        //stub
        return true;
    }

    // EFFECTS: saves the dailyTrackerRecord to file
    private void saveDailyTrackerRecord() {
        //stub
    }

    //EFFECT: call showProgress() if dailyTracker
    //        with provided date exists in the daiyTrackerRecord
    public void checkPrevProgress() {
        //stub
    }

    //MODIFIES: this
    //EFFECT: set jp visibility to false and then to true
    private void refreshPanel(JPanel jp) {
        jp.setVisible(false);
        jp.setVisible(true);
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

    //EFFECT: return true if input can be successfully parsed into double or else return false
    public boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
        

    
}
