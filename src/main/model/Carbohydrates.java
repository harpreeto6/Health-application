package model;

import org.json.JSONObject;

import persistence.Writable;

/** 
 * This class represents carbohydrates (Macro-nutrient) present inside a food item
 * **/
public class Carbohydrates extends MacroNutrient implements Writable {

    //EFFECT: creates Carbohydrate object with its value (super field) set up as provided value
    public Carbohydrates(double value) {
        super(value);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("value", value);
        return json;
    }
}
