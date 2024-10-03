package model;

/** *
 * This class is a representationn of Calories present inside a food item
*/
public class Calories {
    private int value;

    //EFFECTS: makes an object of Calories without initializing any field
    public Calories() {
    }
    
    //EFFFECT: makes a Calories aboject with its value set as the value provided
    public Calories(int value) {
        this.value = value;
    }

    //REQUIRES: value >= 0
    //MODIFIES: This
    //EFFECT: set value field to given value 
    public void setValue(int value) {
        this.value = value;
    }

    //EFFECT: return value field
    public int getValue() {
        return this.value;
    }
}