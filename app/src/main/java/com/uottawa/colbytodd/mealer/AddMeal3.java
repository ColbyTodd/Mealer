package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddMeal3 extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference mDocRef;

    String sPrice;
    String sDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal3);
        //Add back button
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
    public void goToMenuList(View view){
        TextView t = findViewById(R.id.AddMealFeedback3);
        Bundle extras = getIntent().getExtras();
        mDocRef = db.document("meals/" + extras.getString("mealName"));
        Map<String, Object> meal = new HashMap<String, Object>();
        if(verifyFields()){
            meal.put("Price",sPrice);
            meal.put("Name",extras.getString("mealName"));
            meal.put("Meal Type",extras.getString("mealType"));
            meal.put("Ingredients",extras.getString("ingredients"));
            meal.put("Description", sDescription);
            meal.put("Cuisine Type",extras.getString("cuisineType"));
            meal.put("Allergens",extras.getString("allergens"));
            mDocRef.set(meal);

            startActivity(new Intent(this, CookWelcome.class));}
        else
            t.setText("Fields Cannot be Empty");
    }

    private boolean verifyFields(){
        EditText price = (EditText) findViewById(R.id.price);
        EditText description = (EditText) findViewById(R.id.mealDescription);

        sPrice = price.getText().toString();
        sDescription =  description.getText().toString();

        if(sPrice.length()!=0 && sDescription.length()!=0)
            return true;
        else
            return false;

    }
}