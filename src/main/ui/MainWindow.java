package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

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
    private JPanel initialPanel;


    private JButton addFoodButton;
    private JButton foodRecordButton;

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


    public MainWindow() {
        initialize();
    }

    //MODIFIES : this
    //EFFECTS : Initialize the fields and call appropriate methods in appropriate sequence to make the GUI run
    private void initialize() {
        dailyTrackerRecord = new DailyTrackerRecord();
        dailyTracker = new DailyTracker(JSON_STORE, 120, 2500);
        dailyTrackerRecord.addDailyTracker(dailyTracker);

        initializeJPanelAndJFrame();

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(foodFieldsPanel,BorderLayout.NORTH); //
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setTitle("Main Window");
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //frame.setResizable(false);       
        buttonPanel.setBackground(Color.GRAY);

        addButtonsToButtonPanel();      
        createAddFoodFields();
    }

    //MODIFIES: this
    //EFFECT: set up initial GUI to ask user for his protein goals and calories goal
    private void initialSetup() {
        JLabel loadOrNew = new JLabel("<html><pre> Do you want to start new day or load curent day </pre></html>");
        JButton newDay = new JButton("New Day");
        JButton loadDay = new JButton("Load Current Day");
        loadOrNew.add(newDay);
        loadOrNew.add(loadDay);
        frame.add(loadOrNew);

        JLabel goalsInfo = new JLabel("<html><pre> Enter your protein and calories goal </pre></html>");
        
    }

    //MODIFIES: this
    //EFFECT: load current or new day to the app
    private void performNewOrLoadDay(JButton newDay, JButton loadDay) {

        newDay.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel goalsInfo = new JLabel("<html><pre> Enter your protein and calories goal </pre></html>");
                caloriesGoalField = new JTextField(caloriesGoalText,12);
                proteinGoalField = new JTextField(proteinGoalText,12);
                dateField = new JTextField(dateFormatText,12);
                setUpTextField(proteinField, proteinGoalText);
                setUpTextField(caloriesField, caloriesGoalText);
                setUpTextField(dateField, dateFormatText);
                setUpNewDay();
            }
            
        });

        loadDay.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //nothing 
            }
            
        });
    }

    //MODIFIES: this
    //EFFECT: Initialize JPanels and JFrames for this class
    private void initializeJPanelAndJFrame() {
        frame = new JFrame();
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        foodFieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        initialPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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

        addFoodButton = new JButton("<html><pre> Add Meal </pre><html>");
        foodRecordButton = new JButton("<html><pre>   View<br>Food Record</pre></html>");
        
        buttonPanel.add(addFoodButton);
        buttonPanel.add(foodRecordButton);

        setAddMealButton(addFoodButton);
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
                if (isDouble(proteinField.getText()) && isDouble(fatField.getText()) && isDouble(carbohydratesField.getText()) && 
                    isDouble(caloriesField.getText()) && !(foodNameField.getText().equals(foodNameText))) {
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
                if(!dailyTracker.getFoodRecord().isEmpty()) {
                   
                    ArrayList<String> msg = foodRecord();                  
                    for(String i : msg) {
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
    
    //MODIFY: this
    //EFFECT: Constructs a new dailyTracker and add it into dailyRecord
    private void setUpNewDay() {
        if (isDouble(proteinGoalField.getText()) && isDouble(caloriesGoalField.getText()) && isDateFormatted(dateField.getText())) {
            dailyTracker = new DailyTracker(dateField.getText(), proteinReminder, caloriesReminder);
        }
    }


    //MODIFIES: jTextField
    //EFFECT: remove string from jTextField if focus gained and add back string 
    //       if jTextField lost focus without getting user input 
    private void setUpTextField(JTextField jTextField, String string) {

        jTextField.addFocusListener( new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (jTextField.getText().equals(string)) {
                    jTextField.setForeground(Color.BLACK);
                    jTextField.setBackground(Color.WHITE);
                    jTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jTextField.getText().equals("")) {
                    jTextField.setText(string);
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
        Food f = new Food(foodNameField.getText(),new Calories(Double.valueOf(caloriesField.getText())),new Protein(Double.valueOf(proteinField.getText()) ),
                    new Carbohydrates(Double.valueOf(carbohydratesField.getText())),new Fat( Double.valueOf(fatField.getText())));
        return f;
    }

    //MODIFY: this
    //EFFECT: add food to dailyTracker
    public void addFoodToTracker(Food food) {
        dailyTracker.addFood(food);
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
