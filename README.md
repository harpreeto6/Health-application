# My Personal Project

## Phase 0
  
# TASK 1

- A Health app where user will be able to input whatever food they eat during the day into the app and at the end of the day they will be able to see how much calories they 
eat throughout the day. Possible features could be like adding different values of macro-nutrients along with food consumed, check progress to see how far along user is to meet calories and protein goals, to check previous day records to see how well they are doing in thier eating habits.

# TASK 2

- Application will track the daily calories consumed and calories burned.
- Any person who wants get fit or want to stay healthy will use this app.
- I myself try to do regular workout and eat healthy so i wanted to make something like this help which can help me.

# User Stories

- As a *User*, I want to be able to **insert** different types of **food** into my fitnessTracker.
- As a *User*, I must be able to see the **list of food items** which I consumed throught the day.
- As a *User*, I want to track my daily **calories consumption** at any point of day.
- As a *User*, I want to get **reminders** if i am not following my daily Calorie limit.
- As a *User*, I want to track my intake of **macro-nutrients** as well.
- As a *User*, I want to be able to check **whether calorie content is high or low** in my food.
- As a *User*, I want to be able to track my daily **consumption of previous days**.
- As a *User*, I want to be able to **remove a food item** from my daily Tracker.
- As a *User*, I want to be able to **save my DailyTrackerRecord data to file** (if I so choose).
- As a *User*, I want to be able to be **able to load my DailyTrackerRecord from file** (if I so choose)

# Phase 4: Task 2

Wed Nov 27 15:19:45 PST 2024
New DailyTrackerRecord Constructed 

Wed Nov 27 15:20:19 PST 2024
DailyTracker Constructed with 27-11-24 date 

Wed Nov 27 15:20:19 PST 2024
DailyTracker for 27-11-24 saved inside DailyTrackerRecord 

Wed Nov 27 15:20:29 PST 2024
Food named salad is created

Wed Nov 27 15:20:29 PST 2024
salad saved in DailyTracker

Wed Nov 27 15:20:42 PST 2024
Removed salad from DailyTracker

Wed Nov 27 15:20:51 PST 2024
Record for 01-01-01 date searched inside DailyTrackerRecordClass

Wed Nov 27 15:21:02 PST 2024
Added 150.0 calories burned

Wed Nov 27 15:21:09 PST 2024
Data saved in file

# Phase 4 : Task 3

UML diagram shows a simple design in which Protein, Carbohydrates and Fat classes extends the MacroNutrients abstract class, it is beacuse all 3 classes represents a macronutrient. 
Food class have fields of Protein , Carbohydrates, Calories and Fat classes. This design was choosen so later if we want to add new features like categorizing Food Objects based on macronutrients values then it would be easier to do so using these fields. Food, Protein, Carbohydrates, Calories, Fat, DailyTracker and DailyTrackerRecord classes all implements Writable interface because it helps in making sure that we will be able to convert all these classes into JSON Objects to write and read them from JSON file. EventLog class have List of Event class as its field and does not have field of any other class which shows these classes doesn't depend on any other classes for their functionality. Both GUI class and HealthApp class have fields of JsonWriter, JsonReader , DailyTracker and DailyTrackerRecord classes, these fields make sure that by user using the ui will be able to save and load data from files, also, DailyTrackerRecord and DailyTracker are the classes which help us in realizing the user stories.

**Refactoring Proposal**

As we can see in the UML diagram, Fat, Protein and Carbohydrate classes are all extending the Macronutrient abstarct class and implementing the Writable Interface. I would change the relationship so that Mactronutrient will simply implement Writable Interfcae and i won't have to implement Writable every single time. 
Secondly, I would make DailyTrackerRecord extend DailyTracker and also would have made DailyTracker an Abstract class. This will create a composite pattern between these 2 classes but this pattern wouldn't have a Leaf part. By doing this i would be able to use a single class inside my ui classes and that would help making the HealthApp methods simpler.
Apart from that i would make more classes to refactor my code from GUI class to these new classes. I would make a class that would have methods to call addActionListner to all the buttons inside my GUI class, I would do this by copying all the methods from GUI class which are calling addActionListner on buttons to this new class and then i would modify the implementation and parameters of these methods inside my new class, i would also have to change the implementation of other mathods inside GUI class to keep my app working properly after this refactoring. 
I would extract methods like isDouble() and isDoubleFormatted() method which doesn't directly work on UI to a separate class and will make these methods static.
