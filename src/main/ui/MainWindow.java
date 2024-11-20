package ui;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private JPanel goalPanel;
    private JPanel goalButtonPanel;
    private JPanel showProgressPanel;
    private JPanel showProgressButtonPanel;


    private JButton addMealButton;
    private JButton foodRecordButton;
    private JButton newDayButton;
    private JButton loadButton;
    private JButton setGoalsButton;
    private JButton saveButton;
    private JButton showProgressButton;
    private JButton showButton;
    private JButton homeButton;

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
        dailyTracker = null;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);   
        initializeJComponents();
        setUpAllButtons();

        frame.setTitle("Main Window");
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        welcomeMenu();

    }

    //MODIFIES: this
    //EFFECT: set up initial GUI to ask user for his protein goals and calories goal plus inputs
    private void welcomeMenu() {
        
        JLabel welcomeLabel = new JLabel("<html><pre> Do you want to start new day or load curent day </pre></html>");
        welcomePanel.add(welcomeLabel);  
        addButtonsToWelcomeButtonsPanel();
        addWelcomeButtonsAndCentralPanelToWelcomePanel();      
    }

    //MODIFIES: this
    //EFFECT: remove non-necessary panels required for main menu from frame and add necessary methods if needed
    private void mainMenu() {
        removeAllPanels();      
        centerPanel.removeAll();

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(foodFieldsPanel,BorderLayout.NORTH); 
        frame.add(centerPanel, BorderLayout.CENTER); 

        buttonPanel.setBackground(Color.GRAY);

        addButtonsToButtonPanel();      
        addFoodFieldsToFoodPanel();

        setUpAddFoodFields();

        refreshPanel(buttonPanel);
        refreshPanel(foodFieldsPanel);
        refreshPanel(centerPanel);
    }

    //MODIFIES: this
    //EFFECT: create several java components so user can set up new day including
    //         proteinGoalField, caloriesGoalField, dateField components
    //         also removes the panels from frame which are not required for this function
    private void newDayMenu() {
        
        removeAllPanels();
     
        addGoalPanelFieldsToGoalPanel();
        setUpGoalPanelFields();

        addSetGoalsButtonToGoalButtonPanel();   
        addGoalMenuPanelsToFrame();

        refreshPanel(goalButtonPanel);   
        refreshPanel(goalPanel);         
    }

    private void showProgressMenu() {

        removeAllPanels();
        refreshAllPanels();
        frame.add(showProgressPanel, BorderLayout.CENTER);
        frame.add(showProgressButtonPanel, BorderLayout.SOUTH);

        showProgressButtonPanel.add(showButton, new FlowLayout(FlowLayout.CENTER));
        showProgressButtonPanel.add(dateField,new FlowLayout(FlowLayout.CENTER));
        showProgressButtonPanel.add(homeButton,new FlowLayout(FlowLayout.CENTER));

    }

    //MODIFIES: this
    //EFFECT: add loadButton, newDayButotn and centralPanel to WelcomePanel
    private void addWelcomeButtonsAndCentralPanelToWelcomePanel() {

        frame.add(welcomePanel, BorderLayout.NORTH);
        frame.add(welcomeButtonsPanel, BorderLayout.SOUTH); 
        frame.add(centerPanel);
    }

    //MODIFIES: this
    //EFFECT: add newDayButton and loadButton to WelcomeButtonsPanel
    private void addButtonsToWelcomeButtonsPanel() {
        welcomeButtonsPanel.add(newDayButton, new FlowLayout(FlowLayout.CENTER));
        welcomeButtonsPanel.add(loadButton, new FlowLayout(FlowLayout.CENTER));
    }

    //MODIFIES: this
    //EFFECT: Initialize frame, buttonPanel, foodfieldsPanel, centerPanel
    private void initializeJComponents() {
        frame = new JFrame();

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        foodFieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        welcomeButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        goalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        goalButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        showProgressPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        showProgressButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        newDayButton = new JButton("New Day");
        loadButton = new JButton("Load Current Day");
        saveButton = new JButton("save");
        addMealButton = new JButton("<html><pre> Add Meal </pre><html>");
        foodRecordButton = new JButton("<html><pre>Food Record</pre></html>");
        setGoalsButton = new JButton("<html><pre> Set Goals </pre><html>");
        showProgressButton = new JButton("<html><pre> Show Progress </pre><html>");
        saveButton = new JButton("<html><pre> Save </pre></html>"); 
        showButton = new JButton("<html><pre> Show </pre></html>"); 
        homeButton = new JButton("<html><pre> Home </pre></html>"); 

        caloriesGoalField = new JTextField(caloriesGoalText,12);
        proteinGoalField = new JTextField(proteinGoalText,12);
        dateField = new JTextField(dateFormatText,12);
    }

    //MODIFIES: this
    //EFFECT: remove all panels from frame
    private void removeAllPanels() {

        frame.remove(centerPanel);
        frame.remove(buttonPanel);
        frame.remove(foodFieldsPanel);

        frame.remove(welcomeButtonsPanel);
        frame.remove(welcomePanel);

        frame.remove(goalPanel);
        frame.remove(goalButtonPanel);

        frame.remove(showProgressButtonPanel);
        frame.remove(showProgressPanel);
    }

    //MODIFIES: this
    //EFFECT: add goal menu panels to frame
    private void addGoalMenuPanelsToFrame() {
        frame.add(goalPanel, BorderLayout.NORTH);
        frame.add(goalButtonPanel, BorderLayout.SOUTH);
    }

    //MODIFIES: this
    //EFFECT: add setGoalsButton to goalButtonPanel
    private void addSetGoalsButtonToGoalButtonPanel() {
        goalButtonPanel.add(setGoalsButton, new FlowLayout(FlowLayout.CENTER));
    }

    //MODIFIES: this
    //EFFCT: add fields required for setting up goal to goal panel
    private void addGoalPanelFieldsToGoalPanel() {
        JLabel goalsInfo = new JLabel("<html><pre> Enter your protein and calories goal </pre></html>");
        
        goalPanel.add(goalsInfo);
        goalPanel.add(caloriesGoalField);
        goalPanel.add(proteinGoalField);
        goalPanel.add(dateField);
    }

    //MODOFIES: this
    //EFFECT: initalize fields required for setting up protein and calories goals and add it to welcomePanel
    private void setUpGoalPanelFields() {
        setUpTextField(proteinGoalField, proteinGoalText);
        setUpTextField(caloriesGoalField, caloriesGoalText);
        setUpTextField(dateField, dateFormatText);       
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

    //MODIFIES : this
    //EFFECTS : Make the GUI visible
    public void show() {
        frame.setVisible(true);
    }

    //MODIFIES : this
    //EFFECTS : create 2 buttons ("Add Meal" and " View \nProgress" ) and add them to buttonsPanel
    //          add ActionListner to both buttons to do the task given in their names
    private void addButtonsToButtonPanel() {        
        buttonPanel.add(addMealButton, new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(foodRecordButton, new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton, new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(showProgressButton, new FlowLayout(FlowLayout.CENTER));

        
    }

    //MODIFIES: this
    //EFFECT : add functionality to show button so it will show progress for the date entered
    private void setUpShowButton() {
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProgressPanel.removeAll();
                refreshPanel(showProgressPanel);
                JLabel msg;
                if (!isDateFormatted(dateField.getText())) {
                    showProgressPanel.add(new JLabel("<html> Please check format of date <html>"));
                } else {
                    int index = checkPrevProgress(dateField.getText());
                    if (index == -1) {
                        showProgressPanel.add(new JLabel("<html> No Record exist for this date <html>"));
                    } else {
                        ArrayList<String> data = showProgress(index);                  
                        for (String i : data) {
                            msg = new JLabel("<html>" + i + "<br></html>");
                            showProgressPanel.add(msg, new FlowLayout(FlowLayout.CENTER));
                        }
                    }
                }
                showProgressMenu();
            }            
        });
    }

    //MODIFIES: this
    //EFFECT: add functionality to showProgressButton so it can display user's progress toward's his goals
    private void setUpShowProgressButton() {
        
        showProgressButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel lbl;
                showProgressPanel.removeAll();
                int lastIndex = dailyTrackerRecord.getRecord().size() - 1;
                if (lastIndex >= 0) {
                    ArrayList<String> msg = showProgress(lastIndex);                  
                    for (String i : msg) {
                        lbl = new JLabel("<html>" + i + "<br></html>");
                        showProgressPanel.add(lbl, new FlowLayout(FlowLayout.CENTER));
                    }
                    refreshPanel(centerPanel);
                    refreshPanel(showProgressPanel);
                    refreshPanel(buttonPanel);
                    showProgressMenu(); 
                }
                               
            }            
        });
    }

    private void setUpHomeButton() {
        homeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenu();
            }
            
        });
    }

    //MODIFIES: this
    //EFFECT: return true if data is saved to a file properly else return false
    private void setUpSaveButton() {

        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.removeAll();
                frame.add(centerPanel);
                if (saveDailyTrackerRecord() == true) {
                    JLabel msg = new JLabel("Data saved properly");
                    centerPanel.add(msg, new FlowLayout(FlowLayout.CENTER));
                    refreshPanel(centerPanel);
                } else {
                    JLabel msg = new JLabel("Data saving failed");
                    centerPanel.add(msg, new FlowLayout(FlowLayout.CENTER));
                    refreshPanel(centerPanel);
                }
            }
            
        });
    }

    //MODIFIES: this
    //EFFECT: load data from the destination location choosen in fields and then open appropriate menus using
    //        methods
    private void setUpLoadButton() {

        loadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.add(centerPanel, BorderLayout.CENTER);
                if (loadDailyTrackerRecord() == true) {
                    if (dailyTrackerRecord.getRecord().isEmpty()) {
                        newDayMenu();
                    } else {
                        mainMenu();
                    }
                }
            }
            
        });
    }

    //MODIFIES: this
    //EFFECT: save data to the destination location shoosen in fields
    private void setUpNewDayButton() {

        newDayButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadDailyTrackerRecord();
                newDayMenu();
            }
            
        });
    }

    //MODIFIES: this
    //EFFECT: add functionality to addGoalsButton which will allow it it display in app if newDay is setup properly
    //        otherwise : display a message conevying new day is not set properly
    private void setUpSetGoalsButton() {

        setGoalsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (setUpNewDay()) {                  
                    mainMenu();                  
                } else {
                    centerPanel.removeAll();
                    resetGoalsFields();
                    JLabel msg = new JLabel("Please check your fields again");
                    centerPanel.add(msg, new FlowLayout(FlowLayout.CENTER));
                    frame.add(centerPanel);
                    refreshPanel(centerPanel);
                }
            }           
        });
    }

    //MODIFIES: this
    //EFFECT: give JButton fnctionality to create and add food into the dailyTracker
    private void setUpAddMealButton() {

        addMealButton.addActionListener(new ActionListener() {

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

    //MODIFIES: this
    //EFFECT: give JButton the feature of showing progress of current days nutrietion intake on jFrame
    private void setUpFoodRecordButton() {
        
        foodRecordButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel lbl;
                centerPanel.removeAll();
                refreshPanel(centerPanel);
                if (dailyTrackerRecord.getRecord().isEmpty()) {

                    JLabel lbl2 = new JLabel("<html>Haven't eaten any meals yet</html>");
                    centerPanel.add(lbl2);

                } else if (!dailyTracker.getFoodRecord().isEmpty()) {
                   
                    ArrayList<String> msg = foodRecord();                  
                    for (String i : msg) {
                        lbl = new JLabel("<html>" + i + "<br></html>");
                        centerPanel.add(lbl);
                    }
                } else {
                    JLabel lbl2 = new JLabel("<html>Haven't eaten any meals yet</html>");
                    centerPanel.add(lbl2);
                }
            }            
        });   
    }

    //MODIFIES:
    //EFFECT: call othe methods to give all Jbutton fields functionality
    private void setUpAllButtons() {

        setUpAddMealButton();
        setUpFoodRecordButton();
        setUpNewDayButton();
        setUpLoadButton();
        setUpSetGoalsButton();
        setUpSaveButton();
        setUpShowProgressButton();
        setUpShowButton();
        setUpHomeButton();
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
    private void addFoodFieldsToFoodPanel() {       
        initializeAddFoodFields();

        foodFieldsPanel.add(foodNameField);
        foodFieldsPanel.add(caloriesField);
        foodFieldsPanel.add(carbohydratesField);
        foodFieldsPanel.add(proteinField);
        foodFieldsPanel.add(fatField);             
    }

    //MODIFIES: this
    //EFFECT: setUp all fadd food fields uisng setUpTextField() Method
    private void setUpAddFoodFields() {

        setUpTextField(foodNameField,foodNameText);
        setUpTextField(caloriesField,caloriesText);
        setUpTextField(carbohydratesField,carbohydratesText);
        setUpTextField(proteinField,proteinText);
        setUpTextField(fatField,fatText);
    }

    //MODIFIES: this
    //EFFECT: initialize required fields for addFood button
    private void initializeAddFoodFields() {
        foodNameField = new JTextField(foodNameText,14);
        caloriesField = new JTextField(caloriesText,8);
        carbohydratesField = new JTextField(carbohydratesText,8);
        proteinField = new JTextField(proteinText,8);
        fatField = new JTextField(fatText,8);
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
        List<DailyTracker> list = dailyTrackerRecord.getRecord();
        String info = "";
        info = ("Your Progress stats is : ");
        text.add(info);
        info = "";
        info = ("You consumed " + list.get(currentIndex).getCaloriesConsumed() + " calories, Your Goal is : " 
                + list.get(currentIndex).getCaloriesGoal() + " calories<");
        text.add(info);
        info = "";                   
        info = ("You consumed " + list.get(currentIndex).getProteinConsumed() + "g of protein, Your Goal is : " 
                + list.get(currentIndex).getProteinGoal() + "g");
        text.add(info);
        info = "";
        info = ("\tYou consumed " + list.get(currentIndex).getCarbohydratesConsumed() + "g of Carbohydrates");
        text.add(info);
        info = "";
        info = ("\tYou consumed " + list.get(currentIndex).getFatConsumed() + "g of fat");
        text.add(info);
        info = "";
        info = ("\tYou burned " + list.get(currentIndex).getCaloriesBurned() + " calories in total");
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
                    new Protein(Double.valueOf(proteinField.getText())),
                    new Carbohydrates(Double.valueOf(carbohydratesField.getText())),
                    new Fat(Double.valueOf(fatField.getText())));
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
        try {
            DailyTrackerRecord temp  = jsonReader.read();
            List<DailyTracker> history = dailyTrackerRecord.getRecord();
            for (DailyTracker dt : history) {
                temp.addDailyTracker(dt);
            }
            dailyTrackerRecord = temp;
            int lastRecordIndex = dailyTrackerRecord.getRecord().size() - 1;
            if (lastRecordIndex <= 0) {
                return false;
            }
            dailyTracker = dailyTrackerRecord.getRecord().get(lastRecordIndex);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // EFFECTS: return true if data is save to file properly else return false
    private boolean saveDailyTrackerRecord() {
        try {
            jsonWriter.open();
            jsonWriter.write(dailyTrackerRecord);
            jsonWriter.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    //EFFECT: check if the dailyTracker present for given date
    //       return -1 if doesn't otherwise return it's index present in dailyTrackerRecord  
    public int checkPrevProgress(String d) {
        List<DailyTracker> history = dailyTrackerRecord.getRecord();
        boolean exists = false;
        int index = -1;
        for (DailyTracker dt : history) {
            index++;
            if (dt.getDate().equals(d)) {
                exists = true;
                break;
            }
        }
        if (exists == false) {
            return -1;
        } else {
            return index;
        }
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

    //MODIFIES: this
    //EFFECT: set visibility of panels to false and then back to true
    private void refreshAllPanels() {
        refreshPanel(buttonPanel);
        refreshPanel(foodFieldsPanel);
        refreshPanel(centerPanel);
        refreshPanel(welcomePanel);
        refreshPanel(welcomeButtonsPanel);
        refreshPanel(goalPanel);
        refreshPanel(goalButtonPanel);

        refreshPanel(showProgressPanel);
        refreshPanel(showProgressButtonPanel);
        
    }
        

    
}
