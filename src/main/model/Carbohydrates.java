package model;

import org.json.JSONObject;

import persistence.Writable;

/** 
 * This class represents carbohydrates(macro-nutrient) present inside a food item
 * **/
public class Carbohydrates implements Writable {

    private double value;      //representing the value of carbohydrates

    //EFFECT: creates Carbohydrate object with its value set up as provided value
    public Carbohydrates(double value) {
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
        return value;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("value", value);
        return json;
    }
}
