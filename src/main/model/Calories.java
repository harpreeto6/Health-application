package model;

/** *
 * This class is a representationn of Calories present inside a food item
*/
public class Calories {
    private double value;

    //EFFFECT: makes a Calories aboject with its value set as the value provided
    public Calories(double value) {
        this.value = value;
    }

    //REQUIRES: value >= 0
    //MODIFIES: This
    //EFFECT: set value field to given value 
    public void setValue(double value) {
        this.value = value;
    }

    //EFFECT: return value field
    public double getValue() {
        return this.value;
    }
}
