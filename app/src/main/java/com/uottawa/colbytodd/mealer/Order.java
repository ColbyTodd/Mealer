package com.uottawa.colbytodd.mealer;

public class Order {
    private String meal;
    private String clientEmail;

    public Order(String meal, String clientEmail) {
        this.meal = meal;
        this.clientEmail = clientEmail;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

}
