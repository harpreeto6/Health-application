package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.DailyTracker;
import model.DailyTrackerRecord;
import model.Food;
import persistence.JsonReader;
import persistence.JsonWriter;


/*
 * This class will act a GUI for the app
 * Class will be using JSwing library to create Graphical user Interface for our app
 */
public class MainWindow {


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
    private JFrame frame;
    private JPanel buttonPanel;

    public MainWindow() {
        initialize();
    }

    //MODIFIES : this
    //EFFECTS : Initialize the fields and call appropriate methods in appropriate sequence to make the GUI run
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Main Window");
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setResizable(false);
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.GRAY);
        addButtonsToButtonPanel();

        frame.add(buttonPanel, BorderLayout.SOUTH);


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

        Button button1 = new Button("Add Meal");
        Button button2 = new Button("View \nProgress");
        
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        button1.addActionListener((ActionListener) new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //stub
            }
            
        });

        button2.addActionListener((ActionListener) new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //stub
            }
            
        });

    }


    //MODIFIES : this
    //EFFECT : create a JLabel and add text fields for user to add Food information  
    private void createAddFoodFields() {
        //stub
    } 

    //MODIFIES : this
    //EFFECT : create a Java Swing componenets add shows the progress  
    private void showProgress() {
        //stub
    } 

    //MODIFY: this
    //EFFECT: Constructs a new dailyTracker and add it into dailyRecord
    private void dailyTrackerSetup() {
        //stub
    }

    //REQUIRE: currentIndex <= this.currentIndex
    //EFFECT: shows calories and protein goal for today to user and the amount of macro-nutrients user consumed
    private void showProgress(int currentIndex) {
        //stub
    }

    //MODIFIES: this
    //EFFECT: provide the list of food items eaten throughout current day with their nutritional Value
    private void foodRecord() {

    }

    //EFFECT: create a food item based on the information provided in fields
    public Food createFood() {
        //stub
        return null;
    }

    //MODIFY: this
    //EFFECT: add food to dailyTracker
    public void addFoodToTracker(Food food) {
        //stub
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
}
