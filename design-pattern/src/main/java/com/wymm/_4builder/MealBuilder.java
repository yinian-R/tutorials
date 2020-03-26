package com.wymm._4builder;

import com.wymm._4builder.item.ChickenBurger;
import com.wymm._4builder.item.Coke;
import com.wymm._4builder.item.Pepsi;
import com.wymm._4builder.item.VegBurger;

public class MealBuilder {
    public Meal prepareVegMeal() {
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return meal;
    }
    
    public Meal prepareNonVegMeal() {
        Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new Pepsi());
        return meal;
    }
}
