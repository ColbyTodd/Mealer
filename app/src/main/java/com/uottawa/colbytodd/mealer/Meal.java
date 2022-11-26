package com.uottawa.colbytodd.mealer;

public class Meal {
    private String mealName;
    private String mealType;
    private String cuisineType;
    private String price;

    public Meal(String mealName, String mealType, String cuisineType, String price){
        this.mealName = mealName;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
        this.price = price;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = cuisineType;
    }
}
