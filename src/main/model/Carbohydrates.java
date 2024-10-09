package model;

/** 
 * A basic class representing a quantity of Carbohydrates
 * 
 * **/
public class Carbohydrates {

    private double value;      //representing the value of carbohydrates

    //EFFECT: creates Carbohydrate object with its value set up as provided value
    public Carbohydrates(int value) {
        this.value = value;
    }

    //REQUIRES: value >= 0
    //MODIFIES: This
    //EFFECT: set value field to given value 
    public void setValue(int value) {
        this.value = value;
    }

    //EFFECT: return value field 
    public double getValue() {
        return value;
    }
}
