package model;

/** 
 * A basic class representing a quantity of Carbohydrates
 * 
 * **/
public class Carbohydrates {

    private int value;      //representing the value of carbohydrates
    
    public Carbohydrates() {
    }

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
    public int getValue() {
        return value;
    }
}
