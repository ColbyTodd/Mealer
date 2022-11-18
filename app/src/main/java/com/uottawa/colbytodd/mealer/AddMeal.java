package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddMeal extends AppCompatActivity {

    String sMealName;
    String sMealType;
    String sCuisineType;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        email = getIntent().getStringExtra("EMAIL");
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
    public void goToAddMeal2(View view){
        TextView t = findViewById(R.id.AddMealFeedback);
        Intent i = new Intent(this, AddMeal2.class);
        if(verifyFields()){
            i.putExtra("mealName", sMealName);
            i.putExtra("mealType", sMealType);
            i.putExtra("cuisineType", sCuisineType);
            i.putExtra("EMAIL", email);
            startActivity(i);}
        else
            t.setText("Fields Cannot be Empty");
    }

    private boolean verifyFields(){
        EditText mealName = (EditText) findViewById(R.id.mealName);
        EditText mealType = (EditText) findViewById(R.id.mealType);
        EditText cuisineType = (EditText) findViewById(R.id.cuisineType);

        sMealName = mealName.getText().toString();
        sMealType =  mealType.getText().toString();
        sCuisineType = cuisineType.getText().toString();

        if(sMealName.length()!=0 && sMealType.length()!=0 && sCuisineType.length()!=0)
            return true;
        else
            return false;

    }
}