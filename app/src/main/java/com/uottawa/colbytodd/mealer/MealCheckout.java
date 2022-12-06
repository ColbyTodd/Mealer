package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MealCheckout extends AppCompatActivity {
    Bundle extras;
    String email, address, card, cvv, expiration, first, last, meal, price;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mDocRef;

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

        findViewById(R.id.orderConfirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDocRef = db.document("clients/" + email + "/purchases/" + meal);
                Map<String, Object> purchase = new HashMap<String, Object>();
                purchase.put("Meal", meal);
                purchase.put("Status", "");
                purchase.put("Cook", ""); //figure out how to get the cook email here
                mDocRef.set(purchase);
                Intent i = new Intent(MealCheckout.this, ClientWelcome.class);
                i.putExtra("email", email);
                startActivity(i);
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            //Ends the activity
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}