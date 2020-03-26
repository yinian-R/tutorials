package com.wymm._4builder;

public class DemoMain {
    public static void main(String[] args) {
        MealBuilder mealBuilder = new MealBuilder();
        
        Meal vegMeal = mealBuilder.prepareVegMeal();
        System.out.println("Veg Meal");
        vegMeal.showItems();
        System.out.println("Total cast:" + vegMeal.cost());
        
        
        Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
        System.out.println("\nNon Veg Meal");
        nonVegMeal.showItems();
        System.out.println("Total cast:" + nonVegMeal.cost());
    }
}
