package ui;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.events.MouseEvent;

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
    private JPanel eastButtonPanel;

    private JScrollPane scrollPane;

    private JButton addMealButton;
    private JButton foodRecordButton;
    private JButton newDayButton;
    private JButton loadButton;
    private JButton setGoalsButton;
    private JButton saveButton;
    private JButton showProgressButton;
    private JButton showButton;
    private JButton homeButton;
    private JButton addActivityButton;
    private JButton addCaloriesButton;
    

    private JTextField foodNameField;
    private JTextField caloriesField;
    private JTextField carbohydratesField;
    private JTextField proteinField;
    private JTextField fatField;
    private JTextField proteinGoalField;
    private JTextField caloriesGoalField;
    private JTextField dateField;
    private JTextField caloriesBurnedField;

    private final String foodNameText = "Enter food name";
    private final String caloriesText = "Enter calories";
    private final String proteinText = "Enter protein";
    private final String fatText = "Enter fat";
    private final String carbohydratesText = "Enter carbohydrates";
    private final String proteinGoalText = "Enter protein goal";
    private final String caloriesGoalText = "Enter calories goal";
    private final String caloriesBurnedText = "Enter calories burned";
    private final String dateFormatText = "DD-MM-YY";

    private List<String> foodImagesAddressStrings;

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
        frame.add(scrollPane, BorderLayout.CENTER);

        setUpAllButtons();
        setUpAllTextField();
        
        addFoodIconAddresses();
        frame.setTitle("Main Window");
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        welcomeMenu();

    }


    //MODIFIES: this
    //EFFECT: set up initial GUI to ask user whether they want to load data or start new day
    private void welcomeMenu() {
        
        JLabel welcomeLabel = new JLabel("<html><pre> Do you want to start new day or load curent day </pre></html>");
        welcomePanel.add(welcomeLabel);  
        addButtonsToWelcomeButtonsPanel();
        addWelcomeButtonsAndCentralPanelToWelcomePanel();      
    }

    //MODIFIES: this
    //EFFECT: remove non-necessary panels required for main menu from frame and add necessary methods if needed
    //       mainMenu alow user to add new Food items to dailyTracker and give options to view them and Progress
    private void mainMenu() {
        removeAllPanels();      
        centerPanel.removeAll();
        foodFieldsPanel.removeAll();
        eastButtonPanel.removeAll();

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(foodFieldsPanel,BorderLayout.NORTH); 
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(eastButtonPanel, BorderLayout.EAST); 

        buttonPanel.setBackground(Color.GRAY);

        addButtonsToButtonPanel();      
        addFoodFieldsToFoodPanel();

        eastButtonPanel.add(addMealButton, new FlowLayout(FlowLayout.CENTER));
        eastButtonPanel.add(addActivityButton, new FlowLayout(FlowLayout.CENTER));
        
        buttonPanel.add(newDayButton, new FlowLayout(FlowLayout.CENTER));

        ImageIcon labelIcon = new ImageIcon(new ImageIcon("src/main/icons/rocket.png")
                            .getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
        JLabel lbl = new JLabel();
        lbl.setIcon(labelIcon);
        centerPanel.add(lbl);

        refreshPanel(buttonPanel);
        refreshPanel(foodFieldsPanel);
        refreshPanel(centerPanel);
        refreshPanel(eastButtonPanel);
    }


    //MODIFIES: this
    //EFFECT: customize panels in frame so user can now add caloriesBurned into dailyTracker
    private void addActivityMenu() {
        eastButtonPanel.removeAll();
        removeAllPanels();
        foodFieldsPanel.removeAll();

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(eastButtonPanel, BorderLayout.EAST);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(foodFieldsPanel, BorderLayout.NORTH);

        foodFieldsPanel.add(caloriesBurnedField);

        eastButtonPanel.add(addCaloriesButton);
        eastButtonPanel.add(homeButton);
        refreshAllPanels();
    }

    //MODIFIES: this
    //EFFECT: add several java components to frame so user can set up new day, including
    //         proteinGoalField, caloriesGoalField, dateField components
    //         also removes the panels from frame which are not required for this function
    private void newDayMenu() {
        
        removeAllPanels();
     
        addGoalPanelFieldsToGoalPanel();
        addSetGoalsButtonToGoalButtonPanel();   
        addGoalMenuPanelsToFrame();

        refreshPanel(goalButtonPanel);   
        refreshPanel(goalPanel);         
    }

    //MODIFIES: this
    //EFFECT: Customize panels in JFrame to display showProgress() details 
    private void showProgressMenu() {

        removeAllPanels();
        refreshAllPanels();
        frame.add(scrollPane);
        frame.add(showProgressButtonPanel, BorderLayout.SOUTH);

        ImageIcon labelIcon = new ImageIcon(new ImageIcon("src/main/icons/calculator.png")
                                            .getImage().getScaledInstance(400,400, Image.SCALE_DEFAULT));
        JLabel lbl = new JLabel();
        lbl.setIcon(labelIcon);
        centerPanel.add(lbl);

        showProgressButtonPanel.add(showButton, new FlowLayout(FlowLayout.CENTER));
        showProgressButtonPanel.add(dateField,new FlowLayout(FlowLayout.CENTER));
        showProgressButtonPanel.add(homeButton,new FlowLayout(FlowLayout.CENTER));

    }

    //MODIFIES: this
    //EFFECT: add loadButton, newDayButotn and centralPanel to WelcomePanel
    private void addWelcomeButtonsAndCentralPanelToWelcomePanel() {
        frame.add(welcomePanel, BorderLayout.NORTH);
        frame.add(welcomeButtonsPanel, BorderLayout.SOUTH); 
    }

    //MODIFIES: this
    //EFFECT: add newDayButton and loadButton to WelcomeButtonsPanel
    private void addButtonsToWelcomeButtonsPanel() {
        welcomeButtonsPanel.add(newDayButton, new FlowLayout(FlowLayout.CENTER));
        welcomeButtonsPanel.add(loadButton, new FlowLayout(FlowLayout.CENTER));
    }

    //MODIFIES: this
    //EFFECT: Initialize all java swing component fields of this class
    private void initializeJComponents() {
        frame = new JFrame();

        initializeAllJPanels();
        initializeAllJButtons(); 
        caloriesGoalField = new JTextField(caloriesGoalText,12);
        proteinGoalField = new JTextField(proteinGoalText,12);
        dateField = new JTextField(dateFormatText,12);
        caloriesBurnedField = new JTextField(caloriesBurnedText, 12);

        initializeAddFoodFields();

    }

    //MODIFIES: this
    //EFFECT: initialize all JPanel fields
    private void initializeAllJPanels() {

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        foodFieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // Vertical stacking of JLabels
        welcomeButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        welcomePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        goalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        goalButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        eastButtonPanel = new JPanel();
        eastButtonPanel.setLayout(new BoxLayout(eastButtonPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(centerPanel);

        showProgressPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        showProgressButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    }

    //MODIFIES: this
    //EFFECT: initialize all JButton fields
    private void initializeAllJButtons() {

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
        addActivityButton = new JButton("<html><pre>  Add  <br>Activity</pre><html>");
        addCaloriesButton =  new JButton("<html><pre> Add <br> Calories </pre></html>");

    }

    //MODIFIES:this
    //EFFECT: initialize foodIconAdressesString and add food icon addresses Strings to it
    private void addFoodIconAddresses() {

        foodImagesAddressStrings = new ArrayList<>();
        foodImagesAddressStrings.add("src/main/icons/bibimbap.png");
        foodImagesAddressStrings.add("src/main/icons/burger.png");
        foodImagesAddressStrings.add("src/main/icons/calories.png");
        foodImagesAddressStrings.add("src/main/icons/donut.png");
        foodImagesAddressStrings.add("src/main/icons/ramen.png");
    }

    //MODIFIES: this
    //EFFECT: initialize required fields for addFood button and addCaloriesButton
    private void initializeAddFoodFields() {
        foodNameField = new JTextField(foodNameText,14);
        caloriesField = new JTextField(caloriesText,8);
        carbohydratesField = new JTextField(carbohydratesText,8);
        proteinField = new JTextField(proteinText,8);
        fatField = new JTextField(fatText,8);
        caloriesBurnedField = new JTextField(caloriesBurnedText,14);
    }

    //MODIFIES: this
    //EFFECT: remove all JPanels from frame
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

        frame.remove(eastButtonPanel);
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
    //EFFCT: add fields required for setting up goal into goalPanel
    private void addGoalPanelFieldsToGoalPanel() {
        JLabel goalsInfo = new JLabel("<html><pre> Enter your protein and calories goal </pre></html>");
        
        goalPanel.add(goalsInfo);
        goalPanel.add(caloriesGoalField);
        goalPanel.add(proteinGoalField);
        goalPanel.add(dateField);
    }

    //MODOFIES: this
    //EFFECT: setUp all the JTextFields in this class using setUpTextField(textField, string) method
    private void setUpAllTextField() {
        setUpTextField(proteinGoalField, proteinGoalText);
        setUpTextField(caloriesGoalField, caloriesGoalText);
        setUpTextField(dateField, dateFormatText);
        setUpTextField(foodNameField,foodNameText);
        setUpTextField(caloriesField,caloriesText);
        setUpTextField(carbohydratesField,carbohydratesText);
        setUpTextField(proteinField,proteinText);
        setUpTextField(fatField,fatText);
        setUpTextField(caloriesBurnedField, caloriesBurnedText);       
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
    //EFFECTS : add saveButton, foodRecordButton and showProgressButton to buttonPanel
    private void addButtonsToButtonPanel() { 
        buttonPanel.add(saveButton, new FlowLayout(FlowLayout.CENTER));       
        buttonPanel.add(foodRecordButton, new FlowLayout(FlowLayout.CENTER));      
        buttonPanel.add(showProgressButton, new FlowLayout(FlowLayout.CENTER));
        
    }

    //MODIFIES: this
    //EFFECT : add functionality to show button so it will show progress for the date entered
    private void setUpShowButton() {
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.removeAll();
                JLabel msg;
                if (!isDateFormatted(dateField.getText())) {
                    centerPanel.add(new JLabel("<html> Please check format of date <html>"));
                } else {
                    int index = checkPrevProgress(dateField.getText());
                    if (index == -1) {
                        centerPanel.add(new JLabel("<html> No Record exist for this date <html>"));
                    } else {
                        ArrayList<String> data = showProgress(index);                  
                        for (String i : data) {
                            msg = new JLabel("<html>" + i + "<br></html>");
                            msg.setAlignmentX(Component.CENTER_ALIGNMENT);
                            centerPanel.add(msg, new FlowLayout(FlowLayout.CENTER));
                        }

                        
                    }
                }
                showProgressMenu();
            }            
        });
    }

    //MODIFIES: this
    //EFFECT: add functionality to showProgressButton so it can display 
    //      user's progress toward's his today's goal and call showProgressMenu()
    private void setUpShowProgressButton() {
        
        showProgressButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel lbl;
                centerPanel.removeAll();
                int lastIndex = dailyTrackerRecord.getRecord().size() - 1;
                if (lastIndex >= 0) {
                    ArrayList<String> msg = showProgress(lastIndex);                  
                    for (String i : msg) {
                        lbl = new JLabel("<html>" + i + "<br></html>");
                        centerPanel.add(lbl, new FlowLayout(FlowLayout.CENTER));
                    }

                    ImageIcon labelIcon = new ImageIcon(new ImageIcon("src/main/icons/success-2.png")
                                            .getImage().getScaledInstance(400,400, Image.SCALE_DEFAULT));
                    lbl = new JLabel();
                    lbl.setIcon(labelIcon);
                    centerPanel.add(lbl);
                    refreshAllPanels();
                    showProgressMenu(); 
                }
                               
            }            
        });
    }

    //MODIFIES: this
    //EFFECT: set up home button so it calls mainMenu() method
    private void setUpHomeButton() {
        homeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenu();
            }
            
        });
    }

    //MODIFIES: this
    //EFFECT: give saveButton ability to save data in file and show appropriate method if 
    //         saving is successfull or not 
    private void setUpSaveButton() {

        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.removeAll();
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
    //        methods depending if record exist in dailyTrackerRecord
    private void setUpLoadButton() {

        loadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //frame.add(centerPanel, BorderLayout.CENTER);
                frame.add(scrollPane, BorderLayout.CENTER);
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
    //EFFECT: give newDayButton ability to open newDayMenu after loading all the previous data
    private void setUpNewDayButton() {

        newDayButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                loadDailyTrackerRecord();
                centerPanel.removeAll();
                ImageIcon labelIcon = new ImageIcon(new ImageIcon("src/main/icons/answer.png")
                                            .getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                        JLabel lbl = new JLabel();
                        lbl.setIcon(labelIcon);                       
                        centerPanel.add(lbl);
                newDayMenu();
            }
            
        });
    }

    //MODIFIES: this
    //EFFECT: give setGoalsButton ability to create and add new dailyTracker and showing mainMenu()
    //      otherwise tell user to check fields
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
                    frame.add(scrollPane, BorderLayout.CENTER);
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
                JLabel lbl = new JLabel();
                
                ImageIcon labelIcon = new ImageIcon(new ImageIcon(foodImagesAddressStrings
                                            .get((int)Math.floor(Math.random() * (4 - 0 + 1) + 0)))
                                            .getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT));
                    
                lbl = new JLabel();
                lbl.setIcon(labelIcon);

                if (isDouble(proteinField.getText()) && isDouble(fatField.getText()) && isDouble(carbohydratesField
                        .getText()) && isDouble(caloriesField.getText()) 
                        && !(foodNameField.getText().equals(foodNameText))) {
                    lbl.setText("<html><pre>Meal Added Succesfully</pre></html>");
                    addFoodToTracker(createFood());
                    resetFoodFields();
                } else {
                    lbl.setText("<html><pre>Add Food failed , Please check FoodFields</pre></html>");
                }
                centerPanel.add(lbl);
                refreshAllPanels();
            }          
        });      
    }

    //MODIFIES: this
    //EFFECT: give foodRecordButton ability to display all the food items eaten throughout the day
    private void setUpFoodRecordButton() {
        
        foodRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.removeAll();
                JLabel lbl = new JLabel();
                refreshPanel(centerPanel);
                if (dailyTracker != null && dailyTracker.getFoodRecord().isEmpty()) {
                    lbl.setText("Haven't eaten any meals yet");
                    lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
                } else {                  
                    ArrayList<String> msg = foodRecord();                  
                    for (String i : msg) {
                        lbl = new JLabel(i);
                        centerPanel.add(lbl);
                    }
                        ImageIcon labelIcon = new ImageIcon(new ImageIcon("src/main/icons/success.png")
                                            .getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
                        lbl = new JLabel();
                        lbl.setIcon(labelIcon);
                                                                       
                }
                centerPanel.add(lbl);
                             
            }            
        });   
    }

     //MODIFIES: this
    //EFFECT: give addCaloriesButton the ability to add calories Burned into dailyTracker
    private void setUpAddCaloriesButton() {
        
        addCaloriesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isDouble(caloriesBurnedField.getText())) {
                    dailyTracker.addCaloriesBurned(Double.valueOf(caloriesBurnedField.getText()));
                    centerPanel.removeAll();
                    refreshPanel(centerPanel);
                    JLabel lbl2 = new JLabel("Calories Burned Today : "  
                                    + String.valueOf(dailyTracker.getCaloriesBurned()));
                    lbl2.setAlignmentX(Component.CENTER_ALIGNMENT);
                    centerPanel.add(lbl2);
                    // ImageIcon labelIcon = new ImageIcon(new ImageIcon("src/main/icons/fire.png").
                    //                         getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                    //     JLabel lbl = new JLabel();
                    //     lbl.setIcon(labelIcon);                       
                    //     centerPanel.add(lbl);
                    
                } else {
                    JLabel lbl2 = new JLabel("Please check your input again");
                    lbl2.setAlignmentX(Component.CENTER_ALIGNMENT);
                    centerPanel.add(lbl2);
                }
                resetFoodFields();
                addActivityMenu();
            }            
        });               
    }

    //MODIFIES: this
    //EFFECT: give JButton the feature of showing progress of current days nutrietion intake on jFrame
    private void setUpAddActivityButton() {
        
        addActivityButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                centerPanel.removeAll();
                refreshPanel(centerPanel);
                JLabel lbl2 = new JLabel("Calories Burned Today : " + String.valueOf(dailyTracker.getCaloriesBurned()));
                lbl2.setAlignmentX(Component.CENTER_ALIGNMENT);
                centerPanel.add(lbl2);

                ImageIcon labelIcon = new ImageIcon(new ImageIcon("src/main/icons/fire.png")
                                            .getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                        JLabel lbl = new JLabel();
                        lbl.setIcon(labelIcon);                       
                        centerPanel.add(lbl);
                
                addActivityMenu();
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

        setUpAddCaloriesButton();
        setUpAddActivityButton();
    }

    //MODIFIES: thhis
    //EFFECT: reset the text inside fields required for food items to default texts
    private void resetGoalsFields() {
        caloriesGoalField.setText(caloriesGoalText);
        proteinGoalField.setText(proteinGoalText);
        dateField.setText(dateFormatText);
    }

    //MODIFIES: thhis
    //EFFECT: reset the text inside JtextFields to default texts
    private void resetFoodFields() {
        foodNameField.setText(foodNameText);
        caloriesField.setText(caloriesText);
        proteinField.setText(proteinText);
        carbohydratesField.setText(carbohydratesText);
        fatField.setText(fatText);
        caloriesBurnedField.setText(caloriesBurnedText);

        resetGoalsFields();
    }

    //MODIFIES : this
    //EFFECT : add foodFields  to foodFieldsPanel
    private void addFoodFieldsToFoodPanel() {       

        foodFieldsPanel.add(foodNameField);
        foodFieldsPanel.add(caloriesField);
        foodFieldsPanel.add(carbohydratesField);
        foodFieldsPanel.add(proteinField);
        foodFieldsPanel.add(fatField);             
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
    //EFFECT: shows calories and protein goal for today to user and the amount of 
    //          macro-nutrients that user consumed
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

    //MODIFIES: this
    //EFFECT: set jp visibility to false and then to true
    private void refreshPanel(JScrollPane jp) {
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
