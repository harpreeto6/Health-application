package model;

public class Protein {

    private double value;

    //EFFECT: creates Carbohydrate object with its value set up as provided value
    public Protein(int value) {
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
        return this.value;
    }

}
