package model;

import org.json.JSONObject;

import persistence.Writable;

/*
 * This class is a representation of protein(macro-nutrients) present inside food items
 */
public class Protein extends MacroNutrient implements Writable {

    //EFFECT: creates Carbohydrate object with its value (super field) set up as provided value
    public Protein(double value) {
        super(value);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("value", value);
        return json;
    }

}
