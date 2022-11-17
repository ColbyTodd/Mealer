package com.uottawa.colbytodd.mealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddMeal2 extends AppCompatActivity {
    String sIngredients;
    String sAllergens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal2);
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
    public void goToAddMeal3(View view){
        TextView t = findViewById(R.id.AddMealFeedback2);
        Intent i = new Intent(this, AddMeal3.class);
        Bundle extras = getIntent().getExtras();
        if(verifyFields()){
            i.putExtra("ingredients", sIngredients);
            i.putExtra("allergens", sAllergens);
            i.putExtra("mealName",extras.getString("mealName"));
            i.putExtra("mealType",extras.getString("mealType"));
            i.putExtra("cuisineType",extras.getString("cuisineType"));
            startActivity(i);}
        else
            t.setText("Fields Cannot be Empty");
    }

    private boolean verifyFields(){
        EditText ingredients = (EditText) findViewById(R.id.ingredients);
        EditText allergens = (EditText) findViewById(R.id.allergens);

        sIngredients = ingredients.getText().toString();
        sAllergens =  allergens.getText().toString();

        if(sIngredients.length()!=0 && sAllergens.length()!=0)
            return true;
        else
            return false;

    }
}