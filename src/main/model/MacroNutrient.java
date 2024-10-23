package model;


/*
 * This class is an abstarction of Macronutrient present inside food items
 */

public abstract class MacroNutrient {

    protected double value;

    public MacroNutrient(double value) {
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
