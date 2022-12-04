package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MealCheckout extends AppCompatActivity {
    Bundle extras;
    String email, address, card, cvv, expiration, first, last, meal, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_meal_checkout);

        extras = getIntent().getExtras();

        email = extras.getString("email");
        address = extras.getString("address");
        card = extras.getString("card");
        cvv = extras.getString("cvv");
        expiration = extras.getString("expiration");
        first = extras.getString("first");
        last = extras.getString("last");
        meal = extras.getString("Meal");
        price = extras.getString("Price");

        TextView emailText = findViewById(R.id.orderEmailText);
        TextView firstText = findViewById(R.id.orderFirstText);
        TextView lastText = findViewById(R.id.orderLastText);
        TextView addressText = findViewById(R.id.orderDeliverText);
        TextView cardText = findViewById(R.id.orderCardText);
        TextView cvvText = findViewById(R.id.orderCVVText);
        TextView expirationText = findViewById(R.id.orderExpirationText);
        TextView mealText = findViewById(R.id.orderName);
        TextView priceText = findViewById(R.id.orderPrice);

        emailText.setText(email);
        firstText.setText(first);
        lastText.setText(" " + last);
        addressText.setText(address);
        cardText.setText(card);
        cvvText.setText(cvv);
        expirationText.setText(expiration);
        mealText.setText(meal);
        priceText.setText("$" + price);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}